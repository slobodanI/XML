package rs.xml.oglas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.oglas.dto.KorpaDTO;
import rs.xml.oglas.dto.OglasDTO;
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

	public Page<Zahtev> findAll(Pageable page) {
		return zahtevRepository.findAll(page);
	}

	public String save(KorpaDTO korpaDTO) {
		if(korpaDTO.getOglasi()==null) {
			return "Nema oglasa u korpi!";
		}
		
		if (korpaDTO.isBundle()) {
			List<Long> pomLista = new ArrayList<Long>();

			for (OglasDTO og : korpaDTO.getOglasi()) {
				if (!pomLista.contains(og.getAgendId())) {
					pomLista.add(og.getAgendId());
				}
			}
			System.out.println(pomLista);
			for (Long id : pomLista) {

				Zahtev zahtev = new Zahtev();
				for (OglasDTO og : korpaDTO.getOglasi()) {
					if (og.getAgendId().equals(id)) {
						zahtev.getOglasi().add(oglasRepository.getOne(og.getId()));
						zahtev.setDo(og.getDO());
						zahtev.setOd(og.getOD());
						zahtev.setAgentId(og.getAgendId());
						zahtev.setOcenjen(false);
						zahtev.setIzvestaj(false);
						zahtev.setChatId(null);
						zahtev.setStatus(ZahtevStatus.PENDING);
						zahtev = zahtevRepository.save(zahtev);

					}
				}
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
			for (OglasDTO og1 : korpaDTO.getOglasi()) {
				if (og1.getAgendId().toString().equals("1")) {
					Zahtev zahtev = new Zahtev();
					// jos dodati sta treba
					zahtev.getOglasi().add(oglasRepository.getOne(og1.getId()));
					zahtev.setDo(og1.getDO());
					zahtev.setOd(og1.getOD());
					zahtev.setAgentId(og1.getAgendId());
					zahtev.setOcenjen(false);
					zahtev.setIzvestaj(false);
					zahtev.setChatId(null);
					zahtev.setStatus(ZahtevStatus.RESERVED);
					zahtev.setPodnosilacId(og1.getAgendId());
					

					List<Zahtev> zahtevi = zahtevRepository.findAll();
					if(!zahtevi.isEmpty()) {
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
//						if(zahtev.getDo().after(z.getOd()) && zahtev.getDo().before(z.getDo())) {
//							flag=false;
//						}
						if (flag == false) {
							z.setStatus(ZahtevStatus.CANCELED);
							zahtevRepository.save(z);
						}
					}
					}
					zahtevRepository.save(zahtev);
				} else {
					Zahtev zahtev = new Zahtev();
					// jos dodati sta treba
					zahtev.getOglasi().add(oglasRepository.getOne(og1.getId()));
					zahtev.setDo(og1.getDO());
					zahtev.setOd(og1.getOD());
					zahtev.setAgentId(og1.getAgendId());
					zahtev.setOcenjen(false);
					zahtev.setIzvestaj(false);
					zahtev.setChatId(null);
					zahtev.setStatus(ZahtevStatus.PENDING);
					zahtev = zahtevRepository.save(zahtev);
				}
			}
			return "Kreirano je vise zahteva";

		}

	}


}
