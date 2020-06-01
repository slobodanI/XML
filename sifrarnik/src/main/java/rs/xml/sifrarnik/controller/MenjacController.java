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

import rs.xml.sifrarnik.dto.MenjacNewDTO;
import rs.xml.sifrarnik.model.Menjac;
import rs.xml.sifrarnik.services.MenjacService;

@RestController
@RequestMapping(value = "")
public class MenjacController 
{

	
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
	public ResponseEntity<?> updateMenjac(@PathVariable Long Id , @RequestBody MenjacNewDTO info) 
	{	
		Menjac m = menjacService.updateMenjac(Id, info);
		
		if(m==null)
		{
			return new ResponseEntity<String>("Postoji_menjac_sa_tim_imenom", HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(m, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/menjac", produces = "application/json")
	public ResponseEntity<?> newMenjac(@RequestBody MenjacNewDTO info) 
	{	
		Menjac menj = menjacService.createMenjac(info);
		
		if(menj==null)
		{
			return new ResponseEntity<String>("Postoji_menjac_sa_tim_imenom", HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(menj, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(value = "/menjac/{Id}")
	public ResponseEntity<List<Void>>deleteMenjac(@PathVariable Long Id) 
	{	
		menjacService.deleteMenjac(Id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
//--------------------------------------------------------------------------------------------------