package rs.xml.oglas.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.oglas.dto.IzvestajDTO;
import rs.xml.oglas.dto.NewIzvestajDTO;
import rs.xml.oglas.model.Izvestaj;
import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Zahtev;
import rs.xml.oglas.repository.ZahtevRepository;
import rs.xml.oglas.service.IzvestajService;
import rs.xml.oglas.service.OglasService;
import rs.xml.oglas.service.ZahtevService;
import rs.xml.oglas.util.UtilClass;

@RestController
public class IzvestajController {
	
	@Autowired
	IzvestajService izvestajService;
	
	@Autowired
	ZahtevService zahtevService;
	
	@Autowired
	OglasService oglasService;
	
	@Autowired
	ZahtevRepository zahtevRepository;
	
	@Autowired 
	UtilClass utilClass;
	
		
	@GetMapping("/izvestaj")
	@PreAuthorize("hasAuthority('MANAGE_IZVESTAJ')")
	public ResponseEntity<?> getIzvestaji(@RequestParam(required = false, defaultValue = "0") Long zahtevId, HttpServletRequest request){
		
		
		List<Izvestaj> izvestaji = izvestajService.findAll(zahtevId);
		List<IzvestajDTO> izvestajiDTO = new ArrayList<IzvestajDTO>();
		
		
		
		for(Izvestaj iz : izvestaji) {
			IzvestajDTO izDTO = new IzvestajDTO(iz);
//			izDTO.setTekst(utilClass.escape(izDTO.getTekst()));
			izvestajiDTO.add(izDTO);
		}
		return new ResponseEntity<>(izvestajiDTO,HttpStatus.OK);
		
	}
	
	@GetMapping("/izvestaj/{iid}")
	public ResponseEntity<?> getIzvestaj(@PathVariable Long iid){
		
		Izvestaj izvestaj = izvestajService.findOne(iid);
		
		if(izvestaj == null) {
			return new ResponseEntity<String>("Izvestaj with id: " +iid+ " doesn not exist!", HttpStatus.NOT_FOUND);
		}
		
		IzvestajDTO izvestajDTO = new IzvestajDTO(izvestaj);
//		izvestajDTO.setTekst(utilClass.escape(izvestajDTO.getTekst()));
		
		return new ResponseEntity<>(izvestajDTO,HttpStatus.OK);
		
	}
	
	
	@PostMapping("/izvestaj")
	@PreAuthorize("hasAuthority('MANAGE_IZVESTAJ')")
	public ResponseEntity<?> postIzvestaj(@RequestBody @Valid NewIzvestajDTO izvestajDTO, HttpServletRequest request){
		
		String username = request.getHeader("username");
		Zahtev zahtev = zahtevService.findOne(izvestajDTO.getZahtevId());
		if(zahtev == null) {
			return new ResponseEntity<>("Greska!Ne postoji zadati zahtev",HttpStatus.NOT_FOUND);
		}
		
		if(!zahtev.getUsername().equals(username)){
			return new ResponseEntity<>("Ne mozes uneti izvestaj,nije tvoj Zahtev!",HttpStatus.BAD_REQUEST);
		}
		boolean flag=false;
		for(Oglas o : zahtev.getOglasi()) {
			if(o.getId().equals(izvestajDTO.getOglasId())) {
				flag=true;
			}
		}
		if(flag==false) {
			return new ResponseEntity<>("Greska!Oglas ne pripada datom zahtevu",HttpStatus.BAD_REQUEST);
		}
		long millis=System.currentTimeMillis();  
	    Date dateNow=new Date(millis);
	    System.out.println("ZAHTEV_DO----->"+zahtev.getDo());
	    System.out.println("SADASNJE VREME----->"+dateNow);
		if(!zahtev.getDo().before(dateNow)) {
			return new ResponseEntity<>("Greska!Zahtev jos nije zavrsen",HttpStatus.BAD_REQUEST);
		}
//		if(zahtev.isIzvestaj()) {
//			return new ResponseEntity<>("Greska!Vec je unet izvestaj za ovaj zahtev",HttpStatus.BAD_REQUEST);
//		}
		List<Izvestaj> izvestaji = izvestajService.findAll(zahtev.getId());
		for(Izvestaj i : izvestaji) {
			if(i.getOglasId().equals(izvestajDTO.getOglasId())) {
				return new ResponseEntity<>("Greska!Vec je unet izvestaj za ovaj oglas!",HttpStatus.BAD_REQUEST);
			}
		}
		
		izvestajService.save(izvestajDTO, username);
		

		
		if(izvestaji.size()+1  == zahtev.getOglasi().size()) {
			
			zahtev.setIzvestaj(true);
			zahtevRepository.save(zahtev);
		}

		
		return new ResponseEntity<>(izvestajDTO,HttpStatus.OK);
		
	}
	
	

}
