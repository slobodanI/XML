package rs.xml.sifrarnik.controller;

import java.util.List;

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

import rs.xml.sifrarnik.model.Model;
import rs.xml.sifrarnik.services.ModelService;

@RestController
@RequestMapping(value = "")
public class ModelController 
{

	
	
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
		Model m = modelService.updateModel(Id, info);
		
		if(m==null)
		{
			return new ResponseEntity<String>("Postoji_model_sa_tim_imenom", HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(m, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/model", produces = "application/json")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<Model> newModel(@RequestBody String info) 
	{	
		Model mod = modelService.createModel(info);
		
		if(mod!=null)
		{
			return new ResponseEntity<Model>(mod, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping(value = "/model/{Id}")
	@PreAuthorize("hasAuthority('MANAGE_SIFRARNIK')")
	public ResponseEntity<?> deleteModel(@PathVariable Long Id) 
	{	
		modelService.deleteModel(Id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
//--------------------------------------------------------------------------------------------------