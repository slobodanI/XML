package rs.xml.oglas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.oglas.dto.NewIzvestajDTO;
import rs.xml.oglas.exception.NotFoundException;
import rs.xml.oglas.model.Izvestaj;
import rs.xml.oglas.model.Zahtev;
import rs.xml.oglas.repository.IzvestajRepository;
import rs.xml.oglas.util.UtilClass;

@Service
public class IzvestajService {

	@Autowired
	UtilClass utilClass;

	@Autowired
	private IzvestajRepository izvestajRepository;

	@Autowired
	private ZahtevService zahtevService;

	public Izvestaj findOne(Long id) {
		Izvestaj izvestaj = izvestajRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Izvestaj with id:" + id + " does not exist!"));
		return izvestaj;
	}

	public List<Izvestaj> findAll(Long zahtevId) {

		if (!(zahtevId == 0)) {
			return izvestajRepository.findIzvestajZahteva(zahtevId);
		}

		return izvestajRepository.findAll();
	}

	//u izvestaju nemam username,pa prolazim na starinski nacin
	public List<Izvestaj> findMyIzvestaji(String username) {
		List<Zahtev> zahtevi = zahtevService.findZahteviForMe(username);
		List<Izvestaj> izvestaji = new ArrayList<Izvestaj>();
		
		for(Zahtev z : zahtevi) {
			List<Izvestaj> pom = new ArrayList<Izvestaj>();
			pom=izvestajRepository.findIzvestajZahteva(z.getId());
			if(pom != null) {
			izvestaji.addAll(pom);
			}
		}

		return izvestaji;
	}

	public Page<Izvestaj> findAll(Pageable page) {
		return izvestajRepository.findAll(page);
	}

	public Izvestaj save(NewIzvestajDTO izvestajDTO, String username) {

		Izvestaj iz = new Izvestaj();
		iz.setOglasId(izvestajDTO.getOglasId());
		iz.setPredjeniKilometri(izvestajDTO.getPredjeniKilometri());
		iz.setTekst(izvestajDTO.getTekst());
		iz.setZahtevId(izvestajDTO.getZahtevId());
		iz.setIid(username + "-" + utilClass.randomString());

		return izvestajRepository.save(iz);
	}

	public Izvestaj save(Izvestaj iz) {
		return izvestajRepository.save(iz);
	}

	public void remove(Long id) {
		izvestajRepository.deleteById(id);
	}
}
