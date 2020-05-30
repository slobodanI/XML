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

import rs.xml.sifrarnik.model.Klasa;
import rs.xml.sifrarnik.services.KlasaService;

@RestController
@RequestMapping(value = "")
public class KlasaController 
{

	@Autowired
	KlasaService klasaService;

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
	public ResponseEntity<Klasa> updateKlasa(@PathVariable Long Id , @RequestBody String info) 
	{	
		Klasa kls = klasaService.updateKlasa(Id, info);
		
		if(kls==null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(kls, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/klasa", produces = "application/json")
	public ResponseEntity<Klasa> newKlasa(@RequestBody String info) 
	{	
		Klasa kls = klasaService.createKlasa(info);
		
		if(kls==null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(kls, HttpStatus.OK);
		}

	}
	
	@DeleteMapping(value = "/klasa/{Id}")
	public ResponseEntity<List<Void>>deleteKlasa(@PathVariable Long Id) 
	{	
		klasaService.deleteKlasa(Id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}
//----------------------------------------------------------------------------------------------------------