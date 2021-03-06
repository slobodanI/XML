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

import rs.xml.sifrarnik.model.Mesto;
import rs.xml.sifrarnik.services.MestoService;

@RestController
@RequestMapping(value = "")
public class MestoController 
{

	final static Logger logger = LoggerFactory.getLogger(MenjacController.class);
	
	@Autowired
	HttpServletRequest request;	
	
	@Autowired
	MestoService mestoService;

//MESTO
//------------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping(value = "/mesto")
	public ResponseEntity<List<Mesto>> getAllMesto() 
	{	
		List<Mesto> m = mestoService.getAllMesto();
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@GetMapping(value = "/mesto/{Id}")
	public ResponseEntity<Mesto> getMestoById(@PathVariable Long Id) 
	{	
		Mesto m = mestoService.getMestoById(Id);
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@PutMapping(value = "/mesto/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> updateMesto(@PathVariable Long Id , @RequestBody String info) 
	{	
		String username = request.getHeader("username");
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST PUT Mesto, Mesto payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		Mesto m = mestoService.updateMesto(Id, info);
		
		if(m==null)
		{
			logger.warn("BAD_REQUEST PUT Mesto, Mesto payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Postoji_mesto_sa_tim_imenom", HttpStatus.BAD_REQUEST);
		}
		else
		{
			logger.info("Updated Mesto with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(m, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/mesto", produces = "application/json")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<Mesto> newMesto(@RequestBody String info) 
	{	
		String username = request.getHeader("username");
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST POST Mesto, Mesto payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Mesto m = mestoService.createMesto(info);
		
		if(m!=null)
		{
			logger.info("Created Mesto with id:" +m.getId()+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<Mesto>(m, HttpStatus.OK);
		}
		else
		{
			logger.warn("BAD_REQUEST POST Mesto, Mesto payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping(value = "/mesto/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> deleteMesto(@PathVariable Long Id) 
	{	
		String username = request.getHeader("username");
		mestoService.deleteMesto(Id);
		logger.info("DELETED Mesto with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
//--------------------------------------------------------------------------------------------------
