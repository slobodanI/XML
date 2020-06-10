package rs.xml.oglas.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.oglas.dto.KorpaDTO;
import rs.xml.oglas.dto.ZahtevDTO;
import rs.xml.oglas.model.Zahtev;
import rs.xml.oglas.model.ZahtevStatus;
import rs.xml.oglas.service.ZahtevService;

@RestController
public class ZahtevController {
	
	@Autowired
	ZahtevService zahtevService;
	
	@GetMapping("/zahtev")
	public ResponseEntity<?> getZahtevi(){
		
		/*
		 * List<Oglas> oglasList = oglasService.findAll();
		logger.info("get all oglasi {}", "test");;
		List<OglasDTO> oglasListDTO = new ArrayList<OglasDTO>();
		for(Oglas og: oglasList) {
			OglasDTO oDTO = new OglasDTO(og);
			oglasListDTO.add(oDTO);
		}
		 */
		List<Zahtev> zahteviList = zahtevService.findAll();
		List<ZahtevDTO> zahteviListDTO = new ArrayList<ZahtevDTO>();
		for(Zahtev zah: zahteviList) {
			ZahtevDTO zDTO = new ZahtevDTO(zah);
			zahteviListDTO.add(zDTO);
		}
		return new ResponseEntity<>(zahteviListDTO, HttpStatus.OK);
	}
	
	@GetMapping("/zahtev/pending")
	public ResponseEntity<?> getPending(){
		Collection<Zahtev> zahteviList = zahtevService.findPending();
		List<ZahtevDTO> zahteviListDTO = new ArrayList<ZahtevDTO>();
		for(Zahtev zah: zahteviList) {
			ZahtevDTO zDTO = new ZahtevDTO(zah);
			zahteviListDTO.add(zDTO);
		}
		return new ResponseEntity<>(zahteviListDTO, HttpStatus.OK);
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
	
	@PutMapping("/zahtev/{zId}/accept")
	public ResponseEntity<?> acceptZahtev(@PathVariable(name="zId") Long zId, HttpServletRequest request){
		
		String username = request.getHeader("username");

		Zahtev zah = zahtevService.findOne(zId);
		
		if(!zah.getUsername().equals(username)) {
			return new ResponseEntity<String>("Nije_tvoj_zahtev!", HttpStatus.FORBIDDEN);
		}
		
		ZahtevDTO zDTO = new ZahtevDTO(zahtevService.acceptZahtev(zId, username));
		
		return new ResponseEntity<>(zDTO, HttpStatus.OK);
		
	}
	
	@PutMapping("/zahtev/{zId}/decline")
	public ResponseEntity<?> declineZahtev(@PathVariable(name="zId") Long zId, HttpServletRequest request){
		String username = request.getHeader("username");

		Zahtev zah = zahtevService.findOne(zId);
		
		if(!zah.getUsername().equals(username)) {
			return new ResponseEntity<String>("Nije_tvoj_zahtev!", HttpStatus.FORBIDDEN);
		}
		
		ZahtevDTO zDTO = new ZahtevDTO(zahtevService.declineZahtev(zId));
		
		return new ResponseEntity<>(zDTO, HttpStatus.OK);
		
	}

}
