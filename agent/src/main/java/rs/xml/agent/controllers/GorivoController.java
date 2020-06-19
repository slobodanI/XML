package rs.xml.agent.controllers;

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

import rs.xml.agent.model.Gorivo;
import rs.xml.agent.service.GorivoServices;

@RestController
@RequestMapping(value = "")
public class GorivoController 
{

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
	public ResponseEntity<?> updateGorivo(@PathVariable Long Id , @RequestBody String info) 
	{	
		Gorivo gor = sifrarnikService.updateGorivo(Id, info);
		
		if(gor==null)
		{
			return new ResponseEntity<String>("Postoji_gorivo_sa_tim_imenom",HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(gor, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/gorivo", produces = "application/json")
	public ResponseEntity<Gorivo> newGorivo(@RequestBody String info) 
	{	
		Gorivo gor = sifrarnikService.createGorivo(info);
		
		if(gor!=null)
		{
			return new ResponseEntity<Gorivo>(gor, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
	}
	
	@DeleteMapping(value = "/gorivo/{Id}")
	public ResponseEntity<?> deleteGorivo(@PathVariable Long Id) 
	{	
		sifrarnikService.deleteGorivo(Id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}

//-----------------------------------------------------------------------------------------------------------------------------