package rs.xml.oglas.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import rs.xml.oglas.client.AuthClient;
import rs.xml.oglas.client.UpdateUserDebtDTO;
import rs.xml.oglas.dto.IzvestajDTO;
import rs.xml.oglas.dto.NewIzvestajDTO;
import rs.xml.oglas.model.Cenovnik;
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
	
	final static Logger logger = LoggerFactory.getLogger(IzvestajController.class);
	
	@Autowired
	IzvestajService izvestajService;
	
	@Autowired
	ZahtevService zahtevService;
	
	@Autowired
	OglasService oglasService;
	
	@Autowired
	ZahtevRepository zahtevRepository;
	
	@Autowired
	AuthClient authClient;
	
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
	@PreAuthorize("hasAuthority('MANAGE_IZVESTAJ')")
	public ResponseEntity<?> getIzvestaj(@PathVariable Long iid){		
		Izvestaj izvestaj = izvestajService.findOne(iid);
				
		IzvestajDTO izvestajDTO = new IzvestajDTO(izvestaj);
//		izvestajDTO.setTekst(utilClass.escape(izvestajDTO.getTekst()));
		
		return new ResponseEntity<>(izvestajDTO,HttpStatus.OK);
		
	}
	
	
	@PostMapping("/izvestaj")
	@PreAuthorize("hasAuthority('MANAGE_IZVESTAJ')")
	public ResponseEntity<?> postIzvestaj(@RequestBody @Valid NewIzvestajDTO izvestajDTO, HttpServletRequest request){
		String token = request.getHeader("Auth");
		String username = request.getHeader("username");
		Zahtev zahtev = zahtevService.findOne(izvestajDTO.getZahtevId());
		
		if(!zahtev.getUsername().equals(username)){			
			logger.warn("SR, Unauthorized izvestaj access attempt, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>("Ne mozes uneti izvestaj,nije tvoj Zahtev!",HttpStatus.BAD_REQUEST);
		}
		boolean flag=false;
		for(Oglas o : zahtev.getOglasi()) {
			if(o.getId().equals(izvestajDTO.getOglasId())) {
				flag=true;
			}
		}
		if(flag==false) {
			logger.warn("POST izvestaj BAD_REQUEST, bad payload, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>("Greska!Oglas ne pripada datom zahtevu",HttpStatus.BAD_REQUEST);
		}
		long millis=System.currentTimeMillis();  
	    Date dateNow=new Date(millis);
	    System.out.println("ZAHTEV_DO----->"+zahtev.getDo());
	    System.out.println("SADASNJE VREME----->"+dateNow);
		if(!zahtev.getDo().before(dateNow)) {
			logger.warn("POST izvestaj BAD_REQUEST, zahtev with id:" +zahtev.getId()+ " is not finished, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>("Greska!Zahtev jos nije zavrsen",HttpStatus.BAD_REQUEST);
		}
//		if(zahtev.isIzvestaj()) {
//			return new ResponseEntity<>("Greska!Vec je unet izvestaj za ovaj zahtev",HttpStatus.BAD_REQUEST);
//		}
		List<Izvestaj> izvestaji = izvestajService.findAll(zahtev.getId());
		for(Izvestaj i : izvestaji) {
			if(i.getOglasId().equals(izvestajDTO.getOglasId())) {
				logger.warn("POST izvestaj BAD_REQUEST, izvestaj is already created, By username:" + username + ", IP:" + request.getRemoteAddr());
				return new ResponseEntity<>("Greska!Vec je unet izvestaj za ovaj oglas!",HttpStatus.BAD_REQUEST);
			}
		}
		
		Izvestaj izvestaj1 = izvestajService.save(izvestajDTO, username);
		
		Oglas oglas = oglasService.findOne(izvestajDTO.getOglasId());
		Cenovnik cen = oglas.getCenovnik();
		if(oglas.getPlaniranaKilometraza()< izvestajDTO.getPredjeniKilometri()) {
			UpdateUserDebtDTO updDTO = new UpdateUserDebtDTO();
			updDTO.setUsername(zahtev.getPodnosilacUsername());
			updDTO.setDebt((izvestajDTO.getPredjeniKilometri()-oglas.getPlaniranaKilometraza())*cen.getCenaPoKilometru());
			
			authClient.putUserDebt(updDTO, token);
			
		}
		
		logger.info("Created izvestaj with id:" +izvestaj1.getId()+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
		if(izvestaji.size()+1  == zahtev.getOglasi().size()) {
			
			zahtev.setIzvestaj(true);
			zahtevRepository.save(zahtev);
			logger.info("Updated zahtev with id:" +zahtev.getId()+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
		}
	
		return new ResponseEntity<>(izvestajDTO,HttpStatus.OK);
		
	}
	
}
