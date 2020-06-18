package rs.xml.oglas.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.oglas.dto.NewOglasDTO;
import rs.xml.oglas.dto.OglasDTO;
import rs.xml.oglas.dto.OglasDTOsearch;
import rs.xml.oglas.dto.SlikaDTO;
import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Slika;
import rs.xml.oglas.service.CenovnikService;
import rs.xml.oglas.service.OglasService;
import rs.xml.oglas.service.SlikaService;

@RestController
public class OglasController {

	final static Logger logger = LoggerFactory.getLogger(OglasController.class);
	
	
	/*
	 * TODO: 
	 * -dodaj cenovnik
	 * -da li da vracam brisane oglase metodom findAll ???
	 */

	@Autowired
	OglasService oglasService;

	@Autowired
	CenovnikService cenovnikService;

	@Autowired
	SlikaService slikaService;

	@GetMapping("/oglas")
	public ResponseEntity<?> getOglasi(@RequestParam(required = false, defaultValue = "nema") String filter, HttpServletRequest request) {
		// String ip = InetAddress.getLocalHost().getHostAddress();
		
		String username = request.getHeader("username");
		List<Oglas> oglasList = new ArrayList<Oglas>();
		
		if(filter.equals("moje")) {
			oglasList = oglasService.findMyOglasi(username);
			List<OglasDTOsearch> oglasListDTO = new ArrayList<OglasDTOsearch>();
			for(Oglas og: oglasList) {
				OglasDTOsearch oDTO = new OglasDTOsearch(og);
				int suma = 0;
				int broj  = 0;
				float d = 0;
				int km = 0;
				try {
					suma = oglasService.getSumOcena(og.getId());
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					broj = oglasService.getBrojOcena(og.getId());
				} catch (Exception e) {
					// TODO: handle exception
				}
				if(broj != 0) {
				 d = (float) suma/broj;
				}
				
				try {
					km = oglasService.getKilometri(og.getId());
				} catch (Exception e) {
					// TODO: handle exception
				}			
				oDTO.setOcena2(d);
				oDTO.setPredjenaInt(km);
				oDTO.setBrojOcena(broj);
				oglasListDTO.add(oDTO);
			}
			logger.info("get all oglasi {}", "test");;
			return new ResponseEntity<>(oglasListDTO, HttpStatus.OK);
			
		}else {
			oglasList = oglasService.findAll();
			List<OglasDTO> oglasListDTO = new ArrayList<OglasDTO>();
			for(Oglas og: oglasList) {
				OglasDTO oDTO = new OglasDTO(og);
				oglasListDTO.add(oDTO);
			}
			logger.info("get all oglasi {}", "test");;
			return new ResponseEntity<>(oglasListDTO, HttpStatus.OK);
		}
		

	}

	@GetMapping("/oglas/{oid}")
	public ResponseEntity<?> getOglas(@PathVariable Long oid) {
		Oglas oglas = oglasService.findOne(oid);

		if (oglas == null || oglas.isDeleted()) {
			return new ResponseEntity<String>("Oglas with id:" +oid+" does not exist!", HttpStatus.NOT_FOUND);
		}
				
		OglasDTO oglasDTO = new OglasDTO(oglas);

		return new ResponseEntity<>(oglasDTO, HttpStatus.OK);
	}

	@PostMapping("/oglas")
	@PreAuthorize("hasAuthority('CREATE_OGLAS')")
    public ResponseEntity<?> postOglas(@RequestBody @Valid NewOglasDTO oglasDTO, HttpServletRequest request) {
        
		if(oglasDTO.getOD().after(oglasDTO.getDO())) {
			return new ResponseEntity<String>("OD mora biti pre DO datuma!",HttpStatus.BAD_REQUEST);
		}
		
		String username = request.getHeader("username");
		String permisije = request.getHeader("permissions");
		
		Oglas oglas = new Oglas(oglasDTO);
		oglasService.createOglasWithFeignClient(oglas, oglasDTO);
//		if(!oglasService.createOglasWithFeignClient(oglas, oglasDTO)) {
//			return new ResponseEntity<String>("Ne_postoje_proslenjeni_model/marka/klasa/mesto/gorivo/menjac", HttpStatus.BAD_REQUEST);
//		}
		oglas.setUsername(username);
		//ako je obican user poslao zahtev
		if(permisije.contains("ROLE_USER") && !permisije.contains("ROLE_AGENT") && !permisije.contains("ROLE_ADMIN")) {			
			// da li ima 3 aktivna oglasa?
			oglas = oglasService.saveAsBasicUser(oglas, username);
			if(oglas == null) {
				return new ResponseEntity<String>("Korisnik_ima_3_aktivna_oglasa", HttpStatus.FORBIDDEN);
			}
		} 
		//ako je agent ili admin
		else {
			oglas = oglasService.save(oglas);	        
		}
		
		oglas.setUsername(username);
		oglas.setSlike(null);
		return new ResponseEntity<>(oglas, HttpStatus.OK);
    }
	
	@PutMapping("/oglas/{oid}")
	public ResponseEntity<?> updateOglas(@PathVariable Long oid, @RequestBody @Valid NewOglasDTO oglasDTO, HttpServletRequest request) {
		
		if(oglasDTO.getOD().after(oglasDTO.getDO())) {
			return new ResponseEntity<String>("OD mora biti pre DO datuma!",HttpStatus.BAD_REQUEST);
		}
		
		String username = request.getHeader("username");
		String permisije = request.getHeader("permissions");
		
		Oglas oglas = oglasService.updateOglas(oid, oglasDTO, username);
		if(oglas == null) {
			return new ResponseEntity<String>("Nije_tvoj_oglas!", HttpStatus.FORBIDDEN);
		}
		OglasDTO oglasDTOret = new OglasDTO(oglas);
		return new ResponseEntity<>(oglasDTOret, HttpStatus.OK);
	}
	
	@DeleteMapping("oglas/{oid}")
	public ResponseEntity<?> deleteOglas(@PathVariable Long oid, HttpServletRequest request) {
		
		String username = request.getHeader("username");
			
		if(oglasService.deleteOglas(oid, username) == null) {
			return new ResponseEntity<String>("Nije_tvoj_oglas!", HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// sto se tice datuma, vraticu oglas samo ako oglas OD-DO sadrzi trazeni OD-DO 
	@GetMapping("/search")
	public ResponseEntity<?> search(
			@RequestParam(required = true) String mesto,
			@RequestParam(required = true) String Od,
			@RequestParam(required = true) String Do,
			@RequestParam(required = false, defaultValue = "nema") String marka, // ako nema default value, bude null
			@RequestParam(required = false, defaultValue = "nema") String model,
			@RequestParam(required = false, defaultValue = "nema") String menjac,
			@RequestParam(required = false, defaultValue = "nema") String gorivo,
			@RequestParam(required = false, defaultValue = "nema") String klasa,
			@RequestParam(required = false, defaultValue = "0") String predjena, //kilometraza
			@RequestParam(required = false, defaultValue = "0") String planirana, //kilometraza
			@RequestParam(required = false, defaultValue = "nema") String osiguranje,
			@RequestParam(required = false, defaultValue = "0") String brSedZaDecu 
			) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Date odDate;
		Date doDate;
		int predjenaInt;
        int planiranaInt;
//        boolean osiguranjeBool;
        int brSedZaDecuInt;
        
        try {
            odDate = (Date) formatter.parse(Od);
            doDate = (Date) formatter.parse(Do);
            predjenaInt = Integer.parseInt(predjena);
            planiranaInt = Integer.parseInt(planirana);
//            osiguranjeBool = Boolean.parseBoolean(osiguranje);
            brSedZaDecuInt = Integer.parseInt(brSedZaDecu);
        } catch (ParseException | NumberFormatException e) {
        	System.out.println("***Parametri za pretragu nisu dobro formirani!");
            return new ResponseEntity<String>("Parametri_za_pretragu_nisu_dobro_formirani!",HttpStatus.BAD_REQUEST);
        }
        
//        System.out.println(
//        "mesto:"+mesto + "\nOd:"+odDate.toString() + "\nDo:"+doDate.toString() +
//		"\nmarka:"+marka + "\nmodel:"+model + "\nmenjac:"+menjac + "\ngorivo:"+gorivo +"\nklasa:"+klasa +
//		"\npredjena:"+predjenaInt + "\nplanirana:"+planiranaInt + "\nosiguranje:"+osiguranje + "\nbrSedZaDecu:"+brSedZaDecuInt);
        
        Collection<OglasDTOsearch> oglasi = oglasService.search(mesto, odDate, doDate, marka, model, menjac, gorivo, klasa,
        														predjenaInt, planiranaInt, osiguranje, brSedZaDecuInt);
        return new ResponseEntity<Collection<OglasDTOsearch>>(oglasi, HttpStatus.OK);
//        RAD SA VREMENOM
//      java.sql.Date proba = new java.sql.Date(odDate.getTime());        
//      LocalDateTime asd = new LocalDateTime(odDate.getTime());
//      org.joda.time.LocalDate ld = new org.joda.time.LocalDate(odDate.getTime());
//		System.out.println("***LocalDateTime:" + asd); // ***LocalDateTime:2020-06-01T00:00:00.000
//		System.out.println("***sql Date:" + proba); // ***sql Date:2020-06-01
//		System.out.println("***LocalDate joda:" + ld); // ***LocalDate joda:2020-06-01
	}
	
}
