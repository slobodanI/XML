package rs.xml.izvestaj.service;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.izvestaj.client.OglasDTO;
import rs.xml.izvestaj.client.ZahtevClient;
import rs.xml.izvestaj.client.ZahtevDTO;
import rs.xml.izvestaj.dto.NewIzvestajDTO;
import rs.xml.izvestaj.exception.NotFoundException;
import rs.xml.izvestaj.model.Izvestaj;
import rs.xml.izvestaj.repository.IzvestajRepository;

@Service
public class IzvestajService {
	
	@Autowired 
	private IzvestajRepository izvestajRepository;
	
	@Autowired
	ZahtevClient zahtevClient;
	
	public Izvestaj findOne(Long id) {
		Izvestaj izvestaj = izvestajRepository.findById(id).orElseThrow(() -> new NotFoundException("Ocena with id:" +id+ " does not exist!"));
		return izvestaj;
	}
	
	public List<Izvestaj> findAll(){
		return izvestajRepository.findAll();
	}
	
	public Page<Izvestaj> findAll(Pageable page){
		return izvestajRepository.findAll(page);
	}
	
	public Izvestaj save(NewIzvestajDTO izvestajDTO,String username) {
		try {
			ZahtevDTO zahtevDTO = zahtevClient.getZahtev(izvestajDTO.getZahtevId());
			OglasDTO oglasDTO = zahtevClient.getOglas(izvestajDTO.getOglasId());
//			if(!username.equals(zahtevDTO.getUsername())) {
//				return null;
//			}
//			Set<OglasDTO> oglasi = zahtevDTO.getOglasi();
//			boolean pom = false;
//			for(OglasDTO o : oglasi) {
//				if(o.getId().equals(izvestajDTO.getOglasId())) {
//					pom=true;
//				}
//			}
//			if(pom==false) {
//				return null;
//			}
			
			
//			long millis=System.currentTimeMillis();  
//		    Date dateNow=new Date(millis);
//			if(!zahtevDTO.getDo1().before(dateNow)) {
//				return null;
//			}
			
			
			System.out.println("IZVESTAJ*****"+oglasDTO.getId()+" zahtev  ");
			System.out.println("IZVESTAJ*****"+oglasDTO.getMarka()+" zahtev  ");
			System.out.println("IZVESTAJ*****"+oglasDTO.getMenjac()+" zahtev  ");
			System.out.println("IZVESTAJ*****"+oglasDTO.getUsername()+" zahtev  ");
			
		}catch(Exception e) {
			System.out.println(">>>IzvestajService > saveIzvestaj: ERROR!");
			throw new NotFoundException("Ne_postoji_prosledjeni_zahtev");
		}
		
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
