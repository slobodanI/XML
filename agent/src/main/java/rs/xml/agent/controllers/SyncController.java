package rs.xml.agent.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.SyncService;

@RestController
public class SyncController {
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	SyncService syncService;
	
	@GetMapping("/sync")
	public ResponseEntity<?> syncBase(HttpServletRequest request){
		
		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);
			
		syncService.SyncBase(username);
		return new ResponseEntity<>("Uspesno", HttpStatus.OK);
	}

}
