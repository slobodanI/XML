package rs.xml.agent.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.agent.soap.EverythingClient;
import rs.xml.agent.xsd.GetEverythingFromChatResponse;
import rs.xml.agent.xsd.GetEverythingResponse;
import rs.xml.agent.xsd.OglasUZahtevu;
import rs.xml.agent.model.Slika;

@Service
public class SyncService {

	@Autowired
	EverythingClient everythingClient;

	@Autowired
	OglasService oglasService;

	@Autowired
	ZahtevService zahtevService;

	@Autowired
	IzvestajService izvestajService;

	@Autowired
	OcenaService ocenaService;

	@Autowired
	ChatService chatService;

	@Autowired
	PorukaService porukaService;

	public void SyncBase(String username) {

		GetEverythingFromChatResponse responseChat = everythingClient.getEverythingFromChat("agent");
		GetEverythingResponse response = everythingClient.getEverything("agent");
		if (response != null) {
			syncOglase(response, username);
			syncZahteve(response, username);
			syncIzvestaje(response, username);
		}
		if (responseChat != null) {
			syncOcene(response, username);
			syncChat(responseChat, username);
		}
	}

	public void syncPoruke(rs.xml.agent.xsd.Chat chatXSD, rs.xml.agent.model.Chat chat) {
		List<rs.xml.agent.xsd.Poruka> poruke = chatXSD.getPoruke();
		if (!poruke.isEmpty()) {
			for (rs.xml.agent.xsd.Poruka porukaXSD : poruke) {
				boolean pom = false;
				for (rs.xml.agent.model.Poruka p : porukaService.findAll()) {
					if (porukaXSD.getPid().equals(p.getPid())) {
						pom = true;
					}
				}
				if (pom == false) {
					rs.xml.agent.model.Poruka poruka = new rs.xml.agent.model.Poruka();
					poruka.setPid(porukaXSD.getPid());
					poruka.setBody(porukaXSD.getBody());
					poruka.setSenderUsername(porukaXSD.getSenderUsername());
					java.sql.Timestamp timestamp = new java.sql.Timestamp(porukaXSD.getTimestamp());
					poruka.setTimestamp(timestamp);
					poruka.setChat(chat);
					poruka = porukaService.save(poruka);
				}
			}
		}

	}

	public void syncChat(GetEverythingFromChatResponse responseChat, String username) {
		List<rs.xml.agent.xsd.Chat> chatovi = responseChat.getChatovi();
		if (!chatovi.isEmpty()) {
			for (rs.xml.agent.xsd.Chat chatXSD : chatovi) {
				boolean pom = false;
				for (rs.xml.agent.model.Chat ch : chatService.findAll()) {
					if (chatXSD.getCid().equals(ch.getCid())) {
						pom = true;
						syncPoruke(chatXSD, ch);
					}
				}
				if (pom == false) {
					rs.xml.agent.model.Chat chat = new rs.xml.agent.model.Chat();
					chat.setCid(chatXSD.getCid());
					chat.setReceiverUsername(chatXSD.getReceiverUsername());
					chat.setSenderUsername(chatXSD.getSenderUsername());
					chat = chatService.save(chat);
					for (rs.xml.agent.xsd.Poruka p : chatXSD.getPoruke()) {
						rs.xml.agent.model.Poruka poruka = new rs.xml.agent.model.Poruka();
						poruka.setBody(p.getBody());
						poruka.setChat(chat);
						poruka.setPid(p.getPid());
						poruka.setSenderUsername(p.getSenderUsername());
						poruka.setTimestamp(new Timestamp(p.getTimestamp()));
						poruka = porukaService.save(poruka);
						chat.getPoruke().add(poruka);
					}
					chat = chatService.save(chat);

				}
			}
		}

	}

	public void syncOcene(GetEverythingResponse response, String username) {
		List<rs.xml.agent.xsd.Ocena> ocene = response.getOcene();
		if (!ocene.isEmpty()) {
			for (rs.xml.agent.xsd.Ocena ocenaXSD : ocene) {
				boolean pom = false;
				for (rs.xml.agent.model.Ocena oc : ocenaService.findAll()) {
					if (ocenaXSD.getOid().equals(oc.getOid())) {
						pom = true;
					}
				}
				if (pom == false) {
					rs.xml.agent.model.Ocena ocena = new rs.xml.agent.model.Ocena();

					if (ocenaXSD.getApproved().equals(rs.xml.agent.xsd.OcenaApprovedStatus.APPROVED)) {
						ocena.setApproved(rs.xml.agent.model.OcenaApprovedStatus.APPROVED);
					} else if (ocenaXSD.getApproved().equals(rs.xml.agent.xsd.OcenaApprovedStatus.DENIED)) {
						ocena.setApproved(rs.xml.agent.model.OcenaApprovedStatus.DENIED);
					} else if (ocenaXSD.getApproved().equals(rs.xml.agent.xsd.OcenaApprovedStatus.UNKNOWN)) {
						ocena.setApproved(rs.xml.agent.model.OcenaApprovedStatus.UNKNOWN);
					}

					ocena.setDeleted(ocenaXSD.isDeleted());
					ocena.setKomentar(ocenaXSD.getKomentar());
					ocena.setOcena(ocenaXSD.getOcena());
					ocena.setOdgovor(ocenaXSD.getOdgovor());
					rs.xml.agent.model.Oglas oglas = oglasService.findOneByOid(ocenaXSD.getOglasId());
					ocena.setOglas(oglas);
					ocena.setOid(ocenaXSD.getOid());
					ocena.setUsernameKo(ocenaXSD.getUsernameKo());
					ocena.setUsernameKoga(ocenaXSD.getUsernameKoga());
					rs.xml.agent.model.Zahtev zahtev = zahtevService.findOneByZid(ocenaXSD.getZahtevId());
					ocena.setZahtevId(zahtev.getId());

					ocena = ocenaService.save(ocena);
				}
			}
		}

	}

	public void syncIzvestaje(GetEverythingResponse response, String username) {

		List<rs.xml.agent.xsd.Izvestaj> izvestaji = response.getIzvestaji();
		if (!izvestaji.isEmpty()) {
			for (rs.xml.agent.xsd.Izvestaj izvestajXSD : izvestaji) {
				boolean pom = false;
				for (rs.xml.agent.model.Izvestaj i : izvestajService.findAll()) {
					if (izvestajXSD.getIid().equals(i.getIid())) {
						pom = true;
					}
				}
				if (pom == false) {
					rs.xml.agent.model.Izvestaj izvestaj = new rs.xml.agent.model.Izvestaj();
					rs.xml.agent.model.Oglas oglas = oglasService.findOneByOid(izvestajXSD.getOglasId());
					rs.xml.agent.model.Zahtev zahtev = zahtevService.findOneByZid(izvestajXSD.getZahtevId());

					izvestaj.setIid(izvestajXSD.getIid());
					izvestaj.setOglasId(oglas.getId());
					izvestaj.setZahtevId(zahtev.getId());
					izvestaj.setPredjeniKilometri(izvestajXSD.getPredjeniKilometri());
					izvestaj.setTekst(izvestajXSD.getTekst());

					izvestaj = izvestajService.save(izvestaj);
				}

			}
		}
	}

	public void syncZahteve(GetEverythingResponse response, String username) {
		List<rs.xml.agent.xsd.Zahtev> zahtevi = response.getZahtevi();
		if (!zahtevi.isEmpty()) {
			for (rs.xml.agent.xsd.Zahtev zahtevXSD : zahtevi) {
				boolean pom = false;
				for (rs.xml.agent.model.Zahtev z : zahtevService.findAll()) {
					if (zahtevXSD.getZid().equals(z.getZid())) {
						pom = true;
					}
				}
				if (pom == false) {
					rs.xml.agent.model.Zahtev zahtev = new rs.xml.agent.model.Zahtev();
					zahtev.setZid(zahtevXSD.getZid());
					zahtev.setChatId(zahtev.getChatId());// hmmmmmmmm
					java.sql.Date dateOdToSave = new java.sql.Date(zahtevXSD.getOdDate());
					zahtev.setOd(dateOdToSave);
					java.sql.Date dateDoToSave = new java.sql.Date(zahtevXSD.getDoDate());
					zahtev.setDo(dateDoToSave);
					zahtev.setIzvestaj(zahtevXSD.isIzvestaj());
					zahtev.setOcenjen(zahtevXSD.isOcenjen());

					rs.xml.agent.model.Oglas oglas = new rs.xml.agent.model.Oglas();
					for (OglasUZahtevu oglasUZahtevu : zahtevXSD.getOglasi()) {
						oglas = oglasService.findOneByOid(oglasUZahtevu.getOid());
						if (oglas == null) {
							continue;
						}
						zahtev.getOglasi().add(oglas);
					}

					zahtev.setPodnosilacUsername(zahtevXSD.getPodnosilacUsername());
					zahtev.setUsername(zahtevXSD.getUsername());

					if (zahtevXSD.getStatus().equals(rs.xml.agent.xsd.ZahtevStatus.CANCELED)) {
						zahtev.setStatus(rs.xml.agent.model.ZahtevStatus.CANCELED);
					} else if (zahtevXSD.getStatus().equals(rs.xml.agent.xsd.ZahtevStatus.PENDING)) {
						zahtev.setStatus(rs.xml.agent.model.ZahtevStatus.PENDING);
					} else if (zahtevXSD.getStatus().equals(rs.xml.agent.xsd.ZahtevStatus.PAID)) {
						zahtev.setStatus(rs.xml.agent.model.ZahtevStatus.PAID);
					}

					java.sql.Date dateVremePodnosenja = new java.sql.Date(zahtevXSD.getVremePodnosenja());
					zahtev.setVremePodnosenja(dateVremePodnosenja);

					zahtevService.save(zahtev);
					// da li treba dodatne provere?
				}
			}
		}

	}

	public void syncOglase(GetEverythingResponse response, String username) {
		List<rs.xml.agent.xsd.Oglas> oglasi = response.getOglasi();
		if (!oglasi.isEmpty()) {
			for (rs.xml.agent.xsd.Oglas og : oglasi) {
				boolean pom = false;
				for (rs.xml.agent.model.Oglas oglas : oglasService.findAll()) {
					if (og.getOid().equals(oglas.getOid())) {
						pom = true;
					}
				}
				if (pom == false) {
					rs.xml.agent.model.Oglas oglas = new rs.xml.agent.model.Oglas();
					oglas.setOid(og.getOid());
					oglas.setMesto(og.getMesto());
					oglas.setMarka(og.getMarka());
					oglas.setModel(og.getModel());
					oglas.setGorivo(og.getGorivo());
					oglas.setMenjac(og.getMenjac());
					oglas.setKlasa(og.getKlasa());
					oglas.setCena(og.getCena());
					oglas.setCenovnik(null);
					oglas.setKilometraza(og.getKilometraza());
					oglas.setPlaniranaKilometraza(og.getPlaniranaKilometraza());
					oglas.setSedistaZaDecu(og.getSedistaZaDecu());

					for (rs.xml.agent.xsd.Slika slikaXSD : og.getSlike()) {
						Slika slika = new Slika();
						slika.setOglas(oglas);
						slika.setSlika(slikaXSD.getSlika());
						oglas.getSlike().add(slika);
					}

					oglas.setOsiguranje(og.isOsiguranje());
					oglas.setUsername(og.getUsername());

					java.sql.Date dateOdToSave = new java.sql.Date(og.getOdDate());
					oglas.setOd(dateOdToSave);

					java.sql.Date dateDoToSave = new java.sql.Date(og.getDoDate());
					oglas.setDo(dateDoToSave);

					oglas.setDeleted(og.isDeleted());
					oglasService.save(oglas);
				}
			}
		}

	}

}
