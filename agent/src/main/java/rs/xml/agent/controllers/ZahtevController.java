package rs.xml.agent.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.agent.dto.KorpaDTO;
import rs.xml.agent.dto.OglasDTOsearch;
import rs.xml.agent.dto.ZahtevDTO;
import rs.xml.agent.model.Oglas;
import rs.xml.agent.model.Zahtev;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.ZahtevService;

@RestController
public class ZahtevController {
	
	@Autowired
	ZahtevService zahtevService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	/**
	 * @param filter
	 * @param request
	 * @return 
	 * filter=zaMene - vraca sve zahteve koji su poslati meni,
	 * filter=moje - vraca sve zahteve koje sam ja poslao,
	 * filter= - vraca sve zahteve
	 */
	@GetMapping("/zahtev")
	public ResponseEntity<?> getZahtevi(@RequestParam(required = false, defaultValue = "nema") String filter, HttpServletRequest request){
		
		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
//		String username = request.getHeader("username");
				
		List<Zahtev> zahteviList = new ArrayList<Zahtev>();
		
		// zahtevni za moje oglase
		if(filter.equals("zaMene")){
			zahteviList = zahtevService.findZahteviForMe(username);			
		//ocene koje sam ja dao
		} else if (filter.equals("moje")) {
			zahteviList = zahtevService.findMyZahtevi(username);
		} else {
			zahteviList = zahtevService.findAll();
		}
	
		List<ZahtevDTO> zahteviListDTO = new ArrayList<ZahtevDTO>();
		for(Zahtev zah: zahteviList) {
			ZahtevDTO zDTO = new ZahtevDTO(zah);
			zahteviListDTO.add(zDTO);
		}
		return new ResponseEntity<>(zahteviListDTO, HttpStatus.OK);
	}
	
	@GetMapping("/zahtev/pending")
	public ResponseEntity<?> getPending(HttpServletRequest request){
		
		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
//		String username = request.getHeader("username");
		
		Collection<Zahtev> zahteviList = zahtevService.findPending();
		List<ZahtevDTO> zahteviListDTO = new ArrayList<ZahtevDTO>();
		for(Zahtev zah: zahteviList) {
			ZahtevDTO zDTO = new ZahtevDTO(zah);
			if(zDTO.getUsername().equals(username)) {
			zahteviListDTO.add(zDTO);
			}
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
	
	@GetMapping("/zahtev/{zid}/oglasi")
	public ResponseEntity<?> getOglaseZahteva(@PathVariable Long zid){
		
		Zahtev zahtev = zahtevService.findOne(zid);
		
		if(zahtev==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		
		List<OglasDTOsearch> oglasiDTO =  new ArrayList<OglasDTOsearch>();
		for(Oglas o : zahtev.getOglasi()) {
			oglasiDTO.add(new OglasDTOsearch(o));
		}
		
		
		return new ResponseEntity<>(oglasiDTO,HttpStatus.OK);
	}
	
	
	@PostMapping("/zahtev")
	public ResponseEntity<?> postZahtev(@RequestBody KorpaDTO korpa, HttpServletRequest request){
		
		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
//		String username = request.getHeader("username");
		
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
		
		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
//		String username = request.getHeader("username");

		Zahtev zah = zahtevService.findOne(zId);
		
		if(!zah.getUsername().equals(username)) {
			return new ResponseEntity<String>("Nije_tvoj_zahtev!", HttpStatus.FORBIDDEN);
		}
		
		ZahtevDTO zDTO = new ZahtevDTO(zahtevService.acceptZahtev(zId, username));
		
		return new ResponseEntity<>(zDTO, HttpStatus.OK);
		
	}
	
	@PutMapping("/zahtev/{zId}/decline")
	public ResponseEntity<?> declineZahtev(@PathVariable(name="zId") Long zId, HttpServletRequest request){
		
		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
//		String username = request.getHeader("username");

		Zahtev zah = zahtevService.findOne(zId);
		
		if(!zah.getUsername().equals(username)) {
			return new ResponseEntity<String>("Nije_tvoj_zahtev!", HttpStatus.FORBIDDEN);
		}
		
		ZahtevDTO zDTO = new ZahtevDTO(zahtevService.declineZahtev(zId));
		
		return new ResponseEntity<>(zDTO, HttpStatus.OK);
		
	}
	
	
	

}