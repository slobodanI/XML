package rs.xml.oglas.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.oglas.client.ChatDTO;
import rs.xml.oglas.dto.OcenaDTO;
import rs.xml.oglas.dto.OcenaNewDTO;
import rs.xml.oglas.model.Ocena;
import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Zahtev;
import rs.xml.oglas.model.ZahtevStatus;
import rs.xml.oglas.service.OcenaService;
import rs.xml.oglas.service.OglasService;
import rs.xml.oglas.service.ZahtevService;


@RestController
public class OcenaController {
	
	
final static Logger logger = LoggerFactory.getLogger(OcenaController.class);
	
	@Autowired
	OcenaService ocenaService; 
	
	@Autowired
	ZahtevService zahtevService;
	
	@Autowired
	OglasService oglasService;
	
	@GetMapping("/ocena")
	public ResponseEntity<?> getOcenas(@RequestParam(required = false, defaultValue = "nema") String filter, HttpServletRequest request) {
		String username = request.getHeader("username");
		
		List<Ocena> ocenaList = new ArrayList<Ocena>();
		
		// ocene za moje oglase
		if(filter.equals("zaMene")){
			ocenaList = ocenaService.findOceneForMe(username);			
		//ocene koje sam ja dao
		} else if (filter.equals("moje")) {
			ocenaList = ocenaService.findMyOcene(username);
		} else {
			ocenaList = ocenaService.findAll();
		}
		
		logger.info("get all ocene, filter: {}, ukupno ocena:{}", filter, ocenaList.size());
		List<OcenaDTO> ocenaListDTO = new ArrayList<OcenaDTO>();
		for(Ocena ocena: ocenaList) {
			OcenaDTO cDTO = new OcenaDTO(ocena);
			ocenaListDTO.add(cDTO);
		}
		
		return new ResponseEntity<>(ocenaListDTO, HttpStatus.OK);
	}
	
	@GetMapping("/ocena/{oid}")
	public ResponseEntity<?> getOcena(@PathVariable Long oid) {
		
		Ocena ocena = ocenaService.findOne(oid);
		OcenaDTO cDTO = new OcenaDTO(ocena);
		
		return new ResponseEntity<>(cDTO, HttpStatus.OK);
	}
	
	@PostMapping("/ocena")
    public ResponseEntity<?> postOcena(@RequestBody @Valid OcenaNewDTO ocenaNewDTO, HttpServletRequest request) {        		
		
		String username = request.getHeader("username");
		
		Zahtev zahtev = zahtevService.findOne(ocenaNewDTO.getZahtevId());
		
		// provere...
		// da li je zahtev placen
		if(zahtev.getStatus() != ZahtevStatus.PAID) {
			return new ResponseEntity<String>("Zahtev_nije_PAID!", HttpStatus.FORBIDDEN);
		}
		
		// da li je koriscenje vozila proslo
		Date sada = new Date(System.currentTimeMillis());
		if(zahtev.getDo().after(sada)) {
			return new ResponseEntity<String>("Još_nije_istekao_zahtev!", HttpStatus.FORBIDDEN);
		}
		
		// da li sam ja taj koji je poslao zahtev na osnovu koga ocenjujem
		 if(!zahtev.getPodnosilacUsername().equals(username)) {
			 return new ResponseEntity<String>("Nemaš_pravo_da_ocenjujes_ovaj_oglas!", HttpStatus.FORBIDDEN);
		 }
		 
		 // da li je prosledjeni oglasId dobar, tj. da li se taj oglas nalazi u zahtevu
		 boolean flag = false;
		 for(Oglas oglas: zahtev.getOglasi()) {
			 if(oglas.getId() == ocenaNewDTO.getOglasId()) {
				 flag = true;
				 break;
			 }
		 }
		 if(flag == false) {
			 return new ResponseEntity<String>("Ovaj_oglas_se_ne_nalazi_u_zahtevu!", HttpStatus.FORBIDDEN);
		 }
		
		 // da li sam vec ocenio oglas...
		 if(ocenaService.findOcenaIfExists(ocenaNewDTO.getZahtevId(), ocenaNewDTO.getOglasId()) != null) {
			 return new ResponseEntity<String>("Ovaj_oglas_ste_vec_ocenili!", HttpStatus.BAD_REQUEST);
		 }
		 
		 
		Oglas oglas = oglasService.findOne(ocenaNewDTO.getOglasId());
		
		Ocena ocena = new Ocena(ocenaNewDTO, username, zahtev.getUsername(), oglas);
		ocenaService.save(ocena);
		
		OcenaDTO ocenaDTO = new OcenaDTO(ocena);
		
		return new ResponseEntity<>(ocenaDTO, HttpStatus.OK);
    }
	
	@DeleteMapping("/ocena/{oid}")
	public ResponseEntity<?> deleteOcena(@PathVariable Long oid) {
		
		ocenaService.remove(oid);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
