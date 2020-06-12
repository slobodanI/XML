package rs.xml.izvestaj.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.izvestaj.dto.IzvestajDTO;
import rs.xml.izvestaj.dto.NewIzvestajDTO;
import rs.xml.izvestaj.model.Izvestaj;
import rs.xml.izvestaj.service.IzvestajService;

@RestController
public class izvestajController {
	
	@Autowired 
	IzvestajService izvestajService;
		
	@GetMapping("/izvestaj")
	public ResponseEntity<?> getIzvestaji(){
		
		List<Izvestaj> izvestaji = izvestajService.findAll();
		List<IzvestajDTO> izvestajiDTO = new ArrayList<IzvestajDTO>();
		
		for(Izvestaj iz : izvestaji) {
			IzvestajDTO izDTO = new IzvestajDTO(iz);
			izvestajiDTO.add(izDTO);
		}
		return new ResponseEntity<>(izvestajiDTO,HttpStatus.OK);
		
	}
	
	@GetMapping("/izvestaj/{iid}")
	public ResponseEntity<?> getIzvestaj(@PathVariable Long iid){
		
		Izvestaj izvestaj = izvestajService.findOne(iid);
		
		if(izvestaj == null) {
			return new ResponseEntity<String>("Izvestaj with id: " +iid+ " doesn not exist!", HttpStatus.NOT_FOUND);
		}
		
		IzvestajDTO izvestajDTO = new IzvestajDTO(izvestaj);
		
		return new ResponseEntity<>(izvestajDTO,HttpStatus.OK);
		
	}
	
	
	@PostMapping("/izvestaj")
	public ResponseEntity<?> postIzvestaj(@RequestBody @Valid NewIzvestajDTO izvestajDTO, HttpServletRequest request){
		
		String username = request.getHeader("username");
		izvestajService.save(izvestajDTO, username);
		return new ResponseEntity<>(izvestajDTO,HttpStatus.OK);
		
	}
	
	
	
}
