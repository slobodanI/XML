package rs.xml.oglas.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rs.xml.oglas.dto.KorpaDTO;
import rs.xml.oglas.dto.OglasUKorpiDTO;
import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Zahtev;
import rs.xml.oglas.model.ZahtevStatus;
import rs.xml.oglas.repository.OglasRepository;
import rs.xml.oglas.repository.ZahtevRepository;

@Service
public class ZahtevService {

	@Autowired
	ZahtevRepository zahtevRepository;
	@Autowired
	OglasRepository oglasRepository;

	public Zahtev findOne(Long id) {
		Optional<Zahtev> zahtev = zahtevRepository.findById(id);
		return zahtev.orElseGet(null);
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

	// paziti i na reserved,kad rucno unese upozoriti ga ako vec ima reserved
	public String save(KorpaDTO korpaDTO, String username) {
		
		long millis=System.currentTimeMillis();  
	    Date dateNow=new Date(millis);
	    
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

					}
				}
				zahtev.setOglasi(ogl);
				zahtev = zahtevRepository.save(zahtev);
			}
			return "Kreirani zahtevi sa vise oglasa";
//			Zahtev zahtev = new Zahtev();
//			for(OglasDTO og : korpaDTO.getOglasi()) {
//				zahtev.getOglasi().add( oglasRepository.getOne(og.getId()));
//				zahtev.setDo(og.getDO());
//				zahtev.setOd(og.getOD());
//				zahtev.setAgentId(og.getAgendId());
//				zahtev.setOcenjen(false);
//				zahtev.setIzvestaj(false);
//				zahtev.setChatId(null);
//				zahtev.setStatus(ZahtevStatus.PENDING);
//				//jos dodati sta treba
//				
//			}
//			
//			zahtev = zahtevRepository.save(zahtev);
//			return "Kreiran zahtev sa vise oglasa";

		} else {
			List<Oglas> listaOglasa = new ArrayList<Oglas>();
			Date datumOd = null;
			Date datumDo = null;
			for (OglasUKorpiDTO og1 : korpaDTO.getOglasi()) {
				Oglas oglas = oglasRepository.getOne(og1.getOglasId());
				datumOd = og1.getOd();
				datumDo = og1.getDo();
				listaOglasa.add(oglas);
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
					zahtev = zahtevRepository.save(zahtev);
				}
			}
			return "Kreirano je vise zahteva";

		}

	}

	public Zahtev acceptZahtev(Long id) {
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

		return zahtev;
	}

	public Zahtev declineZahtev(Long id) {
		Zahtev z = this.findOne(id);
		z.setStatus(ZahtevStatus.CANCELED);
		zahtevRepository.save(z);
		return z;
	}
	
	@Scheduled(cron = "0 0 * ? * *")
	public void cancelAfter24h() {
		List<Zahtev> listZ = zahtevRepository.findPending();
		
		if(listZ.isEmpty()) {
			return;
		}
		long millis=System.currentTimeMillis()-(60 * 60 * 1000);
		Date oneHourAgo=new Date(millis);
		
		System.out.println(oneHourAgo);
		for(Zahtev z : listZ) {
			System.out.println(z.getVremePodnosenja());
			if(z.getVremePodnosenja().before(oneHourAgo)) {
				z.setStatus(ZahtevStatus.CANCELED);
				zahtevRepository.save(z);
			}
		}
	}
	
	

}
