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

import rs.xml.sifrarnik.model.Mesto;
import rs.xml.sifrarnik.services.MestoService;

@RestController
@RequestMapping(value = "")
public class MestoController 
{

	
	
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
	public ResponseEntity<?> updateMesto(@PathVariable Long Id , @RequestBody String info) 
	{	
		Mesto m = mestoService.updateMesto(Id, info);
		
		if(m==null)
		{
			return new ResponseEntity<String>("Postoji_mesto_sa_tim_imenom", HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(m, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/mesto", produces = "application/json")
	public ResponseEntity<Mesto> newMesto(@RequestBody String info) 
	{	
		Mesto m = mestoService.createMesto(info);
		
		if(m!=null)
		{
			return new ResponseEntity<Mesto>(m, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping(value = "/mesto/{Id}")
	public ResponseEntity<?> deleteMesto(@PathVariable Long Id) 
	{	
		mestoService.deleteMesto(Id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
//--------------------------------------------------------------------------------------------------
