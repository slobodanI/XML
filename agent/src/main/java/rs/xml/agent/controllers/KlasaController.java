package rs.xml.agent.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.agent.model.Klasa;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.KlasaService;

@RestController
@RequestMapping(value = "")
public class KlasaController 
{
	final static Logger logger = LoggerFactory.getLogger(KlasaController.class);
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	KlasaService klasaService;
	
	@Autowired
	private TokenUtils tokenUtils;

//KLASA
//------------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping(value = "/klasa")
	public ResponseEntity<List<Klasa>> getAllKlase() 
	{	
		List<Klasa> kls = klasaService.getAllKlase();
		return new ResponseEntity<>(kls, HttpStatus.OK);
	}
	
	@GetMapping(value = "/klasa/{Id}")
	public ResponseEntity<Klasa> getKlasaById(@PathVariable Long Id) 
	{	
		Klasa kls = klasaService.getKlasaById(Id);
		return new ResponseEntity<>(kls, HttpStatus.OK);
	}
	
	@PutMapping(value = "/klasa/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> updateKlasa(@PathVariable Long Id , @RequestBody String info) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST PUT Klasa, Klasa payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Klasa kls = klasaService.updateKlasa(Id, info);
		
		if(kls==null)
		{
			logger.warn("BAD_REQUEST PUT Klasa, Klasa payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Postoji_klasa_sa_tim_imenom",HttpStatus.BAD_REQUEST);
		}
		else
		{
			logger.info("Updated Klasa with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(kls, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/klasa", produces = "application/json")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<Klasa> newKlasa(@RequestBody String info) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST POST Klasa, Klasa payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Klasa kls = klasaService.createKlasa(info);
		
		if(kls!=null)
		{
			logger.info("Created Klasa with id:" +kls.getId()+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<Klasa>(kls, HttpStatus.OK);
		}
		else
		{
			logger.warn("BAD_REQUEST POST Klasa, Klasa payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}
	
	@DeleteMapping(value = "/klasa/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> deleteKlasa(@PathVariable Long Id) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		klasaService.deleteKlasa(Id);
		logger.info("DELETED Klasa with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}
//----------------------------------------------------------------------------------------------------------