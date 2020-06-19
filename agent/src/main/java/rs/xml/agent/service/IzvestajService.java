package rs.xml.agent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.agent.dto.NewIzvestajDTO;
import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.model.Izvestaj;
import rs.xml.agent.repository.IzvestajRepository;
import rs.xml.agent.soap.IzvestajClient;
import rs.xml.agent.util.UtilClass;
import rs.xml.agent.xsd.PostIzvestajResponse;


@Service
public class IzvestajService {
	
	@Autowired
	UtilClass utilClass;
	
	@Autowired
	private IzvestajClient izvestajClient;
	
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
		iz.setIid(username + "-" + utilClass.randomString());
		iz.setOglasId(izvestajDTO.getOglasId());
		iz.setPredjeniKilometri(izvestajDTO.getPredjeniKilometri());
		iz.setTekst(izvestajDTO.getTekst());
		iz.setZahtevId(izvestajDTO.getZahtevId());
		
		return izvestajRepository.save(iz);
	}
	
	public void remove(Long id) {
		izvestajRepository.deleteById(id);
	}
	
	public void postIzvestajUMikroservice(Izvestaj izvestaj) {
		PostIzvestajResponse response =  izvestajClient.postIzvestaj(izvestaj);
		if(response != null) {
			if(response.isSuccess()) {
				System.out.println("*** OcenaService > saveZahtev > PostZahtev u mirkoservise > USPESNO");
			} else {
				System.out.println("*** OcenaService > saveZahtev > PostZahtev u mirkoservise > NEUSPESNO");
			}
		} else {
			System.out.println("*** OcenaService > saveZahtev > PostZahtev u mirkoservise > NEUSPESNO");
		}
	}
	
}
