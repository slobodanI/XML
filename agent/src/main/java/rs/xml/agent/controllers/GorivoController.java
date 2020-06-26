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

import rs.xml.agent.model.Gorivo;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.GorivoServices;

@RestController
@RequestMapping(value = "")
public class GorivoController 
{		
	final static Logger logger = LoggerFactory.getLogger(GorivoController.class);
	
	@Autowired
	private TokenUtils tokenUtils;
	
	
	@Autowired
	HttpServletRequest request;

	@Autowired
	GorivoServices sifrarnikService;

//GORIVO
//------------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping(value = "/gorivo")
	public ResponseEntity<List<Gorivo>> getAllGorivo() 
	{	
		List<Gorivo> gor = sifrarnikService.getAllGorivo();
		return new ResponseEntity<>(gor, HttpStatus.OK);
	}
	
	@GetMapping(value = "/gorivo/{Id}")
	public ResponseEntity<Gorivo> getGorivoById(@PathVariable Long Id) 
	{	
		Gorivo gor = sifrarnikService.findOne(Id);

		return new ResponseEntity<>(gor, HttpStatus.OK);
	}
	
	@PutMapping(value = "/gorivo/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> updateGorivo(@PathVariable Long Id , @RequestBody String info) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST PUT Gorivo, Gorivo payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Gorivo gor = sifrarnikService.updateGorivo(Id, info);
		
		if(gor==null)
		{
			logger.warn("BAD_REQUEST PUT Gorivo, Gorivo payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Postoji_gorivo_sa_tim_imenom",HttpStatus.BAD_REQUEST);
		}
		else
		{
			logger.info("Updated Gorivo with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(gor, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/gorivo", produces = "application/json")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<Gorivo> newGorivo(@RequestBody String info) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST POST Gorivo, Gorivo payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Gorivo gor = sifrarnikService.createGorivo(info);
		
		if(gor!=null)
		{
			logger.info("Created Gorivo with id:" +gor.getId()+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<Gorivo>(gor, HttpStatus.OK);
		}
		else
		{
			logger.warn("BAD_REQUEST POST Gorivo, Gorivo payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
	}
	
	@DeleteMapping(value = "/gorivo/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> deleteGorivo(@PathVariable Long Id) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		sifrarnikService.deleteGorivo(Id);
		logger.info("DELETED Gorivo with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}

//-----------------------------------------------------------------------------------------------------------------------------