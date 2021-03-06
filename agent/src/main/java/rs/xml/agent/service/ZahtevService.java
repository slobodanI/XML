package rs.xml.agent.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rs.xml.agent.dto.ChatNewDTO;
import rs.xml.agent.dto.KorpaDTO;
import rs.xml.agent.dto.OglasUKorpiDTO;
import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.exceptions.ServiceNotAvailable;
import rs.xml.agent.model.Chat;
import rs.xml.agent.model.Oglas;
import rs.xml.agent.model.Zahtev;
import rs.xml.agent.model.ZahtevStatus;
import rs.xml.agent.repository.OglasRepository;
import rs.xml.agent.repository.ZahtevRepository;
import rs.xml.agent.soap.ZahtevClient;
import rs.xml.agent.util.UtilClass;
import rs.xml.agent.xsd.PostZahtevResponse;
import rs.xml.agent.xsd.PutZahtevResponse;

@Service
public class ZahtevService {

	@Autowired
	ZahtevRepository zahtevRepository;
	@Autowired
	OglasRepository oglasRepository;
	@Autowired
	ChatService chatService;
	@Autowired
	UtilClass util;
	@Autowired
	ZahtevClient zahtevClient;

	public Zahtev findOne(Long id) {
		Zahtev zahtev = zahtevRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Zahtev with id:" + id + " does not exist!"));
		return zahtev;// .orElseGet(null);
	}

	public Zahtev findOneByZid(String zahtevId) {
		Zahtev zahtev = zahtevRepository.findOglasByZid(zahtevId);
		return zahtev;
	}

	public List<Zahtev> findAll() {
		return zahtevRepository.findAll();
	}

	public Collection<Zahtev> findPending() {
		return zahtevRepository.findPending();
	}

	public Page<Zahtev> findAll(Pageable page) {
		return zahtevRepository.findAll(page);
	}

	public Zahtev save(Zahtev zahtev) {
		return zahtevRepository.save(zahtev);
	}

	// paziti i na reserved,kad rucno unese upozoriti ga ako vec ima reserved
	public String save(KorpaDTO korpaDTO, String username) {

		long millis = System.currentTimeMillis();
		Date dateNow = new Date(millis);

		if (korpaDTO.getOglasi() == null) {
			return "Nema oglasa u korpi!";
		}

		// da li korisnik zeli oglase u bundle
		if (korpaDTO.isBundle()) {
			List<String> pomLista = new ArrayList<String>();
			List<Oglas> listaOglasa = new ArrayList<Oglas>();
			Date datumOd = null;
			Date datumDo = null;
			// pomocna lista koja sadrzi sve oglase iz korpe,
			// i lista koja sadrzi username korisnika koji su postavili oglase koje se
			// nalaze u korpi,
			// zbog pravljenja bundle-a
			for (OglasUKorpiDTO og : korpaDTO.getOglasi()) {
				Oglas oglas = oglasRepository.getOne(og.getOglasId());
				datumOd = og.getOd();
				datumDo = og.getDo();
				listaOglasa.add(oglas);
				if (!pomLista.contains(oglas.getUsername())) {
					pomLista.add(oglas.getUsername());
				}
			}
			if (datumOd.after(datumDo)) {
				return "Niste dobro uneli datum!";
			}

			// id je username korisnika koji je postavio oglas,
			// iteriram kako bih napravio bundle oglasa istog korisnika
			for (String id : pomLista) {

				Zahtev zahtev = new Zahtev();
				Set<Oglas> ogl = new HashSet<Oglas>();
				for (Oglas og : listaOglasa) {
					if (og.getUsername().equals(id)) {
						ogl.add(og);
						zahtev.setDo(datumDo);
						zahtev.setOd(datumOd);
						zahtev.setUsername(og.getUsername());
						zahtev.setOcenjen(false);
						zahtev.setIzvestaj(false);
						zahtev.setChatId(null);
						zahtev.setVremePodnosenja(dateNow);

						zahtev.setStatus(ZahtevStatus.PENDING);
						zahtev.setPodnosilacUsername(username);
						zahtev.setZid(username + "-" + util.randomString());
					}
				}
				zahtev.setOglasi(ogl);
				zahtev = zahtevRepository.save(zahtev);
				postZahtevUMikroservise(zahtev);
			}
			return "Kreirani zahtevi sa vise oglasa";

		} else {
			List<Oglas> listaOglasa = new ArrayList<Oglas>();
			Date datumOd = new Date(millis);
			Date datumDo = new Date(millis);
			for (OglasUKorpiDTO og1 : korpaDTO.getOglasi()) {
				Oglas oglas = oglasRepository.getOne(og1.getOglasId());
				datumOd = new Date(og1.getOd().getTime());
				datumDo = new Date(og1.getDo().getTime());
				listaOglasa.add(oglas);
			}
			if (datumOd.after(datumDo)) {
				return "Niste dobro uneli datum!";
			}
			for (Oglas og1 : listaOglasa) {
				if (og1.getUsername().equals(username)) {
					Zahtev zahtev = new Zahtev();
					// jos dodati sta treba
					zahtev.getOglasi().add(og1);
					zahtev.setDo(datumDo);
					zahtev.setOd(datumOd);
					zahtev.setUsername(og1.getUsername());
					zahtev.setOcenjen(false);
					zahtev.setIzvestaj(false);
					zahtev.setChatId(null);
					zahtev.setPodnosilacUsername(username);
					zahtev.setVremePodnosenja(dateNow);
					zahtev.setStatus(ZahtevStatus.PAID);
					zahtev.setZid(username + "-" + util.randomString());

					List<Zahtev> zahtevi = zahtevRepository.findAll();

					List<Zahtev> pom = new ArrayList<Zahtev>();
					// trazim sve zahteve koji sadrze oglas,
					// da ih odbijem u slucaju da se poklapa datum
					if (!zahtevi.isEmpty()) {
						for (Zahtev z : zahtevi) {
							boolean sadrzi = false;
							for (Oglas o : z.getOglasi()) {
								if (o.equals(og1)) {
									sadrzi = true;
								}
							}
							if (sadrzi == true) {
								pom.add(z);
							}

						}
					}
					// provera svih postojecih zahteva,
					// da li postoji zahtev u odabranom vremenskom periodu i u kom je statusu
					if (!pom.isEmpty()) {
						for (Zahtev z : pom) {
							boolean flag = true;
							if (!z.getOd().after(zahtev.getDo()) && !z.getOd().before(zahtev.getOd())) {
								flag = false;
							}
							if (!z.getDo().after(zahtev.getDo()) && !z.getDo().before((zahtev.getOd()))) {
								flag = false;
							}
							if (zahtev.getOd().after(z.getOd()) && zahtev.getOd().before(z.getDo())
									&& zahtev.getDo().after(z.getOd()) && zahtev.getDo().before(z.getDo())) {
								flag = false;
							}
//						if(zahtev.getDo().after(z.getOd()) && zahtev.getDo().before(z.getDo())) {
//							flag=false;
//						}
							if (flag == false) {
								if (z.getStatus().equals(ZahtevStatus.PAID)) {
									return "Automobil je vec rezervisan u odabranom vremenskom periodu";
								}
								z.setStatus(ZahtevStatus.CANCELED);
								zahtevRepository.save(z);
							}
						}
					}
					zahtevRepository.save(zahtev);
					postZahtevUMikroservise(zahtev);
				} else {
					Zahtev zahtev = new Zahtev();
					// jos dodati sta treba
					zahtev.getOglasi().add(og1);
					zahtev.setDo(datumDo);
					zahtev.setOd(datumOd);
					zahtev.setUsername(og1.getUsername());
					zahtev.setOcenjen(false);
					zahtev.setIzvestaj(false);
					zahtev.setChatId(null);
					zahtev.setVremePodnosenja(dateNow);
					zahtev.setStatus(ZahtevStatus.PENDING);
					zahtev.setPodnosilacUsername(username);
					zahtev.setZid(username + "-" + util.randomString());
					zahtev = zahtevRepository.save(zahtev);
					postZahtevUMikroservise(zahtev);
				}
			}
			return "Kreirano je vise zahteva";

		}

	}

	public Zahtev acceptZahtev(Long id, String username) {
		Zahtev zahtev = this.findOne(id);

		List<Zahtev> zahtevi = zahtevRepository.findPending();
		// provera svih postojecih PENDING zahteva,
		// da li postoji zahtev u odabranom vremenskom period
		if (!zahtevi.isEmpty()) {
			for (Zahtev z : zahtevi) {
				boolean flag = true;
				if (!z.getOd().after(zahtev.getDo()) && !z.getOd().before(zahtev.getOd())) {
					flag = false;
				}
				if (!z.getDo().after(zahtev.getDo()) && !z.getDo().before((zahtev.getOd()))) {
					flag = false;
				}
				if (zahtev.getOd().after(z.getOd()) && zahtev.getOd().before(z.getDo())
						&& zahtev.getDo().after(z.getOd()) && zahtev.getDo().before(z.getDo())) {
					flag = false;
				}

				if (flag == false) {

					z.setStatus(ZahtevStatus.CANCELED);
					zahtevRepository.save(z);

				}
			}
		}
		zahtev.setStatus(ZahtevStatus.PAID);
		zahtevRepository.save(zahtev);

		// kreiranje chat-a
		ChatNewDTO chatNewDTO = new ChatNewDTO();
		chatNewDTO.setReceiverUsername(username);
		chatNewDTO.setSendereUsername(zahtev.getPodnosilacUsername());

		try {
//			ChatDTO chatDTO = new ChatDTO(chatService.save(chatNewDTO));
			Chat chat = chatService.save(chatNewDTO, username);
			zahtev.setChatId(chat.getCid());
			zahtevRepository.save(zahtev);
			if (chat != null) {
				chatService.postChatUMikroservise(chat);
			}
		} catch (Exception e) {
			System.out.println("***ERROR: zahtevService > acceptZahtev > chatClient ");
			throw new ServiceNotAvailable("Chat service is not available");
		}
		putZahtevUMikroservise(zahtev);
		return zahtev;
	}

	public Zahtev declineZahtev(Long id) {
		Zahtev z = this.findOne(id);

		z.setStatus(ZahtevStatus.CANCELED);
		zahtevRepository.save(z);
		putZahtevUMikroservise(z);
		return z;
	}

	public Zahtev cancelZahtev(Long id) {
		Zahtev z = this.findOne(id);

		z.setStatus(ZahtevStatus.CANCELED);
		zahtevRepository.save(z);
		putZahtevUMikroservise(z);
		return z;
	}

	@Scheduled(cron = "0 0 * ? * *")
	public void cancelAfter24h() {
		List<Zahtev> listZ = zahtevRepository.findPending();

		if (listZ.isEmpty()) {
			return;
		}
		long millis = System.currentTimeMillis() - (24 * 60 * 60 * 1000);
		Date oneHourAgo = new Date(millis);

		System.out.println(oneHourAgo);
		for (Zahtev z : listZ) {
			System.out.println(z.getVremePodnosenja());
			if (z.getVremePodnosenja().before(oneHourAgo)) {
				z.setStatus(ZahtevStatus.CANCELED);
				zahtevRepository.save(z);
			}
		}
	}

	public List<Zahtev> findMyZahtevi(String username) {
		return zahtevRepository.findMyZahtevi(username);
	}

	public List<Zahtev> findZahteviForMe(String username) {
		return zahtevRepository.findZahteviForMe(username);
	}

	private void postZahtevUMikroservise(Zahtev zahtev) {
		PostZahtevResponse response = zahtevClient.postZahtev(zahtev);
		if (response != null) {
			if (response.isSuccess()) {
				System.out.println("*** ZahtevService > saveZahtev > PostZahtev u mirkoservise > USPESNO");
			} else {
				System.out.println("*** ZahtevService > saveZahtev > PostZahtev u mirkoservise > NEUSPESNO");
			}
		} else {
			System.out.println("*** ZahtevService > saveZahtev > PostZahtev u mirkoservise > NEUSPESNO");
		}

	}
	
	private void putZahtevUMikroservise(Zahtev zahtev) {
		PutZahtevResponse response = zahtevClient.putZahtev(zahtev);
		if (response != null) {
			if (response.isSuccess()) {
				System.out.println("*** ZahtevService > putZahtev > PutZahtev u mirkoservise > USPESNO");
			} else {
				System.out.println("*** ZahtevService > putZahtev > PutZahtev u mirkoservise > NEUSPESNO");
			}
		} else {
			System.out.println("*** ZahtevService > putZahtev > PutZahtev u mirkoservise > NEUSPESNO");
		}

	}

}
