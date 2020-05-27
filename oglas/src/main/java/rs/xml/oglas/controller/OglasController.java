package rs.xml.oglas.controller;

import java.util.Base64;
import java.util.Base64.Encoder;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.oglas.dto.NewOglasDTO;
import rs.xml.oglas.dto.OglasDTO;
import rs.xml.oglas.dto.SlikaDTO;
import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Slika;
import rs.xml.oglas.service.CenovnikService;
import rs.xml.oglas.service.OglasService;
import rs.xml.oglas.service.SlikaService;

@RestController
public class OglasController {
	
	/*
	 * TODO: 
	 * -dodaj id korisnika u oglas
	 * -proveri da li obican korisnik ima 3 aktivna oglasa, ako ima ne moze jos oglasa dodavati
	 * -dodaj cenovnik
	 * -dodaj feign client za sifrarnik
	 * */
	
	@Autowired
	OglasService oglasService;
	
	@Autowired
	CenovnikService cenovnikService;
	
	@Autowired
	SlikaService slikaService;
	
	@GetMapping("/oglas")
    public ResponseEntity<?> getOglasi() {
        //String ip = InetAddress.getLocalHost().getHostAddress();
        return new ResponseEntity<>(oglasService.findAll(), HttpStatus.OK);
    }
	
	@GetMapping("/oglas/{oid}")
    public ResponseEntity<?> getOglas(@PathVariable Long oid) {
        //String ip = InetAddress.getLocalHost().getHostAddress();
		Oglas oglas = oglasService.findOne(oid);
		
		if(oglas == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		OglasDTO oglasDTO = new OglasDTO();
		oglasDTO.setBrSedistaZaDecu(oglas.getSedistaZaDecu());
		oglasDTO.setCena(oglas.getCena());
		for(Slika slika: oglas.getSlike()) {
			String imageString;
			
			Encoder encoder= Base64.getEncoder();
			//radi i ako se kaze data:image/png
			imageString = encoder.encodeToString(slika.getSlika());
			SlikaDTO slikaDTO = new SlikaDTO();
			slikaDTO.setSlika("data:image/jpeg;base64," + imageString);
			oglasDTO.getSlike().add(slikaDTO);
		}
				
        return new ResponseEntity<>(oglasDTO, HttpStatus.OK);
    }
	
	@PostMapping("/oglas")
    public ResponseEntity<?> postOglas(@RequestBody @Valid NewOglasDTO oglasDTO) {
        
		Oglas oglas = new Oglas(oglasDTO);
		oglas = oglasService.save(oglas);
		oglas.setSlike(null);
        return new ResponseEntity<>(oglas, HttpStatus.OK);
    }
}
