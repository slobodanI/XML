package rs.xml.sifrarnik.controller;

import java.util.List;

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

import rs.xml.sifrarnik.model.Model;
import rs.xml.sifrarnik.services.ModelService;

@RestController
@RequestMapping(value = "api/model")
public class ModelController 
{

	
	
	@Autowired
	ModelService modelService;

//MODEL
//------------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping(value = "/returnAllModel")
	public ResponseEntity<List<Model>> getAllModel() 
	{	
		List<Model> m = modelService.getAllModel();
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@GetMapping(value = "/returnModelById/{Id}")
	public ResponseEntity<Model> getModelById(@PathVariable Long Id) 
	{	
		Model m = modelService.getModelById(Id);
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateModel/{Id}")
	public ResponseEntity<Model> updateModel(@PathVariable Long Id , @RequestBody String info) 
	{	
		Model m = modelService.updateModel(Id, info);
		
		if(m==null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(m, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/newModel", produces = "application/json")
	public ResponseEntity<Void> newModel(@RequestBody String info) 
	{	
		modelService.createModel(info);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteModel/{Id}")
	public ResponseEntity<List<Void>>deleteModel(@PathVariable Long Id) 
	{	
		modelService.deleteModel(Id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
//--------------------------------------------------------------------------------------------------