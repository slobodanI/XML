package rs.xml.agent.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.agent.dto.NewCenovnikDTO;
import rs.xml.agent.dto.NewOglasDTO;
import rs.xml.agent.dto.OglasDTO;
import rs.xml.agent.model.Cenovnik;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.CenovnikService;

@RestController
public class CenovnikController {
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	CenovnikService cenovnikService;
	
	/**
	 * @param request
	 * @return 
	 * Return sve cenovnike korisnika.
	 * Ako je korisnik admin return sve cenovnike u sistemu.
	 */
	@GetMapping("/cenovnik")
	public ResponseEntity<?> getAllCenovnik(HttpServletRequest request) {
		
		String token = request.getHeader("Auth").substring(7);
		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
//		String username = request.getHeader("username");
//		String permisije = request.getHeader("permissions");
		
		List<Cenovnik> cenovnikList = new ArrayList<Cenovnik>();
		if(permisije.contains("ROLE_ADMIN")){
			cenovnikList = cenovnikService.findAll();
		}
		else {
			cenovnikList = cenovnikService.findAllFromUser(username);
		}
			
		return new ResponseEntity<>(cenovnikList, HttpStatus.OK);
	}
	
	@GetMapping("/cenovnik/{cid}")
	public ResponseEntity<?> getCenovnikById(@PathVariable Long cid, HttpServletRequest request) {
		
		String token = request.getHeader("Auth").substring(7);
		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
		
//		String username = request.getHeader("username");
//		String permisije = request.getHeader("permissions");
		
		Cenovnik cenovnik = cenovnikService.findOne(cid);;
		
		// ako nije admin...
		if(!permisije.contains("ROLE_ADMIN")) {
			// da li je njegov cenovnik
			if(cenovnik.getUsername().equals(username)) {
				return new ResponseEntity<>(cenovnik, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("Nije_tvoj_cenovnik!", HttpStatus.FORBIDDEN);
			}
		}
		
		return new ResponseEntity<>(cenovnik, HttpStatus.OK);
	}
	
	@PostMapping("/cenovnik")
	public ResponseEntity<?> postCenovnik(@RequestBody @Valid NewCenovnikDTO cenovnikDTO, HttpServletRequest request) {
		
		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
//		String username = request.getHeader("username");
		
		Cenovnik cen = new Cenovnik(cenovnikDTO, username);
		
		cen = cenovnikService.save(cen);
		
		return new ResponseEntity<>(cen, HttpStatus.OK);
	}
	
	@PutMapping("/cenovnik/{cid}")
	public ResponseEntity<?> putCenovnik(@PathVariable Long cid, @RequestBody @Valid NewCenovnikDTO cenovnikDTO, HttpServletRequest request) {
		
		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
//		String username = request.getHeader("username");
		Cenovnik cenovnik = cenovnikService.updateMyCenovnik(cid, cenovnikDTO, username);
		if(cenovnik == null) {
			return new ResponseEntity<String>("Nije_tvoj_cenovnik!", HttpStatus.FORBIDDEN);
		} 

		return new ResponseEntity<>(cenovnik, HttpStatus.OK);
	}
	
	@DeleteMapping("/cenovnik/{cid}")
	public ResponseEntity<?> deleteCenovnikById(@PathVariable Long cid, HttpServletRequest request) {
		
		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
		
//		String username = request.getHeader("username");
		if(!cenovnikService.deleteMyCenovnik(cid, username)) {
			return new ResponseEntity<String>("Nije_tvoj_cenovnik!", HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}
