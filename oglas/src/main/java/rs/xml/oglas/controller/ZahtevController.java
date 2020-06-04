package rs.xml.oglas.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.oglas.dto.KorpaDTO;
import rs.xml.oglas.dto.ZahtevDTO;
import rs.xml.oglas.model.Zahtev;
import rs.xml.oglas.service.ZahtevService;

@RestController
public class ZahtevController {
	
	@Autowired
	ZahtevService zahtevService;
	
	@GetMapping("/zahtev")
	public ResponseEntity<?> getZahtevi(){
		return new ResponseEntity<>(zahtevService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/zahtev/{zid}")
	public ResponseEntity<?> getZahtev(@PathVariable Long zid){
		
		Zahtev zahtev = zahtevService.findOne(zid);
		
		if(zahtev==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		ZahtevDTO zahtevDTO = new ZahtevDTO(zahtev);
		
		
		return new ResponseEntity<>(zahtevDTO,HttpStatus.OK);
	}
	
	@PostMapping("/zahtev")
	public ResponseEntity<?> postZahtev(@RequestBody KorpaDTO korpa, HttpServletRequest request){
		
		String username = request.getHeader("username");
		
		String odgovor = zahtevService.save(korpa,username);
		if(odgovor.equals("Kreirani zahtevi sa vise oglasa")) {
		return new ResponseEntity<>(odgovor,HttpStatus.OK);
		}else if(odgovor.equals("Kreirano je vise zahteva")) {
			return new ResponseEntity<>(odgovor,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(odgovor,HttpStatus.BAD_REQUEST);
		}
		
		
	}

}
