package rs.xml.sifrarnik.controller;

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

import rs.xml.sifrarnik.model.Menjac;
import rs.xml.sifrarnik.services.MenjacService;

@RestController
@RequestMapping(value = "")
public class MenjacController 
{

	final static Logger logger = LoggerFactory.getLogger(MenjacController.class);
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	MenjacService menjacService;

//MENJAC
//------------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping(value = "/menjac")
	public ResponseEntity<List<Menjac>> getAllMenjac() 
	{	
		List<Menjac> m = menjacService.getAllMenjac();
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@GetMapping(value = "/menjac/{Id}")
	public ResponseEntity<Menjac> getMenjacById(@PathVariable Long Id) 
	{	
		Menjac m = menjacService.getMenjacById(Id);
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@PutMapping(value = "/menjac/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> updateMenjac(@PathVariable Long Id , @RequestBody String info) 
	{			
		String username = request.getHeader("username");
	
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST PUT Menjac, Menjac payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		Menjac m = menjacService.updateMenjac(Id, info);
		
		if(m==null)
		{
			logger.warn("BAD_REQUEST PUT Menjac, Menjac payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Postoji_menjac_sa_tim_imenom", HttpStatus.BAD_REQUEST);
		}
		else
		{
			logger.info("Updated Menjac with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(m, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/menjac", produces = "application/json")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<Menjac> newMenjac(@RequestBody String info) 
	{	
		String username = request.getHeader("username");
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST POST Menjac, Menjac payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Menjac menj = menjacService.createMenjac(info);
		
		if(menj!=null)
		{
			logger.info("Created Menjac with id:" +menj.getId()+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<Menjac>(menj, HttpStatus.OK);
		}
		else
		{
			logger.warn("BAD_REQUEST POST Menjac, Menjac payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping(value = "/menjac/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> deleteMenjac(@PathVariable Long Id) 
	{	
		String username = request.getHeader("username");
		menjacService.deleteMenjac(Id);
		logger.info("DELETED Menjac with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
//--------------------------------------------------------------------------------------------------