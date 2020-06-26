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

import rs.xml.agent.model.Model;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.ModelService;

@RestController
@RequestMapping(value = "")
public class ModelController 
{

	final static Logger logger = LoggerFactory.getLogger(MenjacController.class);
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	HttpServletRequest request;	
	
	@Autowired
	ModelService modelService;

//MODEL
//------------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping(value = "/model")
	public ResponseEntity<List<Model>> getAllModel() 
	{	
		List<Model> m = modelService.getAllModel();
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@GetMapping(value = "/model/{Id}")
	public ResponseEntity<Model> getModelById(@PathVariable Long Id) 
	{	
		Model m = modelService.getModelById(Id);
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@PutMapping(value = "/model/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> updateModel(@PathVariable Long Id , @RequestBody String info) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST PUT Model, Model payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Model m = modelService.updateModel(Id, info);
		
		if(m==null)
		{
			logger.warn("BAD_REQUEST PUT Model, Model payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Postoji_model_sa_tim_imenom", HttpStatus.BAD_REQUEST);
		}
		else
		{
			logger.info("Updated Model with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(m, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/model", produces = "application/json")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<Model> newModel(@RequestBody String info) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		if(info == null || info.length()<1) {
			logger.warn("BAD_REQUEST POST Model, Model payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Model mod = modelService.createModel(info);
		
		if(mod!=null)
		{
			logger.info("Created Model with id:" +mod.getId()+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<Model>(mod, HttpStatus.OK);
		}
		else
		{
			logger.warn("BAD_REQUEST POST Model, Model payload is bad, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping(value = "/model/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> deleteModel(@PathVariable Long Id) 
	{	
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		modelService.deleteModel(Id);
		logger.info("DELETED Model with id:" +Id+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
//--------------------------------------------------------------------------------------------------