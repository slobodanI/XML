package rs.xml.agent.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.agent.model.Marka;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.MarkaService;

@RestController
@RequestMapping(value = "")
public class MarkaController 
{

	final static Logger logger = LoggerFactory.getLogger(MarkaController.class);
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	MarkaService markaService;

//MARKA
//------------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping(value = "/marka")
	public ResponseEntity<List<Marka>> getAllMarke() 
	{	
		List<Marka> m = markaService.getAllMarke();
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@GetMapping(value = "/marka/{Id}")
	public ResponseEntity<Marka> getMarkaById(@PathVariable Long Id) 
	{	
		Marka m = markaService.getMarkaById(Id);
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@PutMapping(value = "/marka/{Id}")
	public ResponseEntity<?> updateMarka(@PathVariable Long Id , @RequestBody String info) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST PUT Marka, Marka payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Marka m = markaService.updateMarka(Id, info);

		if(m==null)
		{
			logger.warn("BAD_REQUEST PUT Marka, Marka payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Postoji_marka_sa_tim_imenom", HttpStatus.BAD_REQUEST);
		}
		else
		{
			logger.info("Updated Marka with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(m, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/marka", produces = "application/json")
	public ResponseEntity<Marka> newMarka(@RequestBody String info) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST POST Marka, Marka payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Marka mar = markaService.createMarka(info);
		
		if(mar!=null)
		{
			logger.info("Created Marka with id:" +mar.getId()+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<Marka>(mar, HttpStatus.OK);
		}
		else
		{
			logger.warn("BAD_REQUEST POST Marka, Marka payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping(value = "/marka/{Id}")
	public ResponseEntity<?> deleteMarka(@PathVariable Long Id) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		markaService.deleteMarka(Id);
		logger.info("DELETED Marka with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
//----------------------------------------------------------------------------------------------------------------
