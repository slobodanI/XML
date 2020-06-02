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

import rs.xml.sifrarnik.dto.MarkaNewDTO;
import rs.xml.sifrarnik.model.Marka;
import rs.xml.sifrarnik.services.MarkaService;

@RestController
@RequestMapping(value = "")
public class MarkaController 
{

	
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
	public ResponseEntity<?> updateMarka(@PathVariable Long Id , @RequestBody MarkaNewDTO info) 
	{	
		Marka m = markaService.updateMarka(Id, info);

		if(m==null)
		{
			return new ResponseEntity<String>("Postoji_marka_sa_tim_imenom", HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(m, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/marka", produces = "application/json")
	public ResponseEntity<?> newMarka(@RequestBody MarkaNewDTO info) 
	{	
		Marka mar = markaService.createMarka(info);
		
		if(mar==null)
		{
			return new ResponseEntity<String>("Postoji_marka_sa_tim_imenom", HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(mar, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(value = "/marka/{Id}")
	public ResponseEntity<Marka>deleteMarka(@PathVariable Long Id) 
	{	
		Marka m = markaService.deleteMarka(Id);
		
		return new ResponseEntity<>(m,HttpStatus.OK);
	}
	
	
}
//----------------------------------------------------------------------------------------------------------------
