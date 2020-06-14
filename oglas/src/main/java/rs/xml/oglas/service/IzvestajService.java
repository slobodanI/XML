package rs.xml.oglas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.oglas.dto.NewIzvestajDTO;
import rs.xml.oglas.exception.NotFoundException;
import rs.xml.oglas.model.Izvestaj;
import rs.xml.oglas.repository.IzvestajRepository;

@Service
public class IzvestajService {
	
	
	@Autowired 
	private IzvestajRepository izvestajRepository;
	
	public Izvestaj findOne(Long id) {
		Izvestaj izvestaj = izvestajRepository.findById(id).orElseThrow(() -> new NotFoundException("Ocena with id:" +id+ " does not exist!"));
		return izvestaj;
	}
	
	public List<Izvestaj> findAll(Long zahtevId){
		
		if(!(zahtevId==0)) {
			return izvestajRepository.findIzvestajZahteva(zahtevId);
		}
		
		return izvestajRepository.findAll();
	}
	
	public Page<Izvestaj> findAll(Pageable page){
		return izvestajRepository.findAll(page);
	}
	
	public Izvestaj save(NewIzvestajDTO izvestajDTO,String username) {

		Izvestaj iz = new Izvestaj();
		iz.setOglasId(izvestajDTO.getOglasId());
		iz.setPredjeniKilometri(izvestajDTO.getPredjeniKilometri());
		iz.setTekst(izvestajDTO.getTekst());
		iz.setZahtevId(izvestajDTO.getZahtevId());
		
		return izvestajRepository.save(iz);
	}
	
	public void remove(Long id) {
		izvestajRepository.deleteById(id);
	}
}
