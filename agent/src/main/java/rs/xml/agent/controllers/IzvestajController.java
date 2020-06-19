package rs.xml.agent.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.agent.dto.IzvestajDTO;
import rs.xml.agent.dto.NewIzvestajDTO;
import rs.xml.agent.model.Izvestaj;
import rs.xml.agent.model.Oglas;
import rs.xml.agent.model.Zahtev;
import rs.xml.agent.repository.ZahtevRepository;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.IzvestajService;
import rs.xml.agent.service.OglasService;
import rs.xml.agent.service.ZahtevService;

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
	TokenUtils tokenUtils;
		
	@GetMapping("/izvestaj")
	public ResponseEntity<?> getIzvestaji(@RequestParam(required = false, defaultValue = "0") Long zahtevId, HttpServletRequest request){
		
		
		List<Izvestaj> izvestaji = izvestajService.findAll(zahtevId);
		List<IzvestajDTO> izvestajiDTO = new ArrayList<IzvestajDTO>();
		
		
		
		for(Izvestaj iz : izvestaji) {
			IzvestajDTO izDTO = new IzvestajDTO(iz);
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
		
		return new ResponseEntity<>(izvestajDTO,HttpStatus.OK);
		
	}
	
	
	@PostMapping("/izvestaj")
	public ResponseEntity<?> postIzvestaj(@RequestBody @Valid NewIzvestajDTO izvestajDTO, HttpServletRequest request){
		
		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
//		String username = request.getHeader("username");
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
		
		Izvestaj izvestaj = izvestajService.save(izvestajDTO, username);
		if(izvestaj != null) {
			izvestajService.postIzvestajUMikroservice(izvestaj);
		}
		
		
		if(izvestaji.size()+1 == zahtev.getOglasi().size()) {
			zahtev.setIzvestaj(true);
			zahtevRepository.save(zahtev);
		}

		
		return new ResponseEntity<>(izvestajDTO,HttpStatus.OK);
		
	}
	
	

}
