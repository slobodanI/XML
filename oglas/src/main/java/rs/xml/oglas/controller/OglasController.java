package rs.xml.oglas.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	/*
	 * TODO: -dodaj id korisnika u oglas -proveri da li obican korisnik ima 3
	 * -aktivna oglasa, ako ima ne moze jos oglasa dodavati -dodaj cenovnik -dodaj
	 * -feign client za sifrarnik
	 * -dodaj mesto u sifrarnik
	 */

	@Autowired
	OglasService oglasService;

	@Autowired
	CenovnikService cenovnikService;

	@Autowired
	SlikaService slikaService;

	@GetMapping("/oglas")
	public ResponseEntity<?> getOglasi() {
		// String ip = InetAddress.getLocalHost().getHostAddress();
		return new ResponseEntity<>(oglasService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/oglas/{oid}")
	public ResponseEntity<?> getOglas(@PathVariable Long oid) {
		// String ip = InetAddress.getLocalHost().getHostAddress();
		Oglas oglas = oglasService.findOne(oid);

		if (oglas == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		OglasDTO oglasDTO = new OglasDTO();
		oglasDTO.setBrSedistaZaDecu(oglas.getSedistaZaDecu());
		oglasDTO.setCena(oglas.getCena());
		for (Slika slika : oglas.getSlike()) {
			String imageString;

			Encoder encoder = Base64.getEncoder();
			// radi i ako se kaze data:image/png
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
            return new ResponseEntity<String>("Parametri za pretragu nisu dobro formirani!",HttpStatus.BAD_REQUEST);
        }
               
        Collection<OglasDTOsearch> oglasi = oglasService.search(mesto, odDate, doDate, marka, model, menjac, gorivo, klasa,
        														predjenaInt, planiranaInt, osiguranje, brSedZaDecuInt);
        return new ResponseEntity<Collection<OglasDTOsearch>>(oglasi, HttpStatus.OK);
//        RAD SA VREMENOM
//        java.sql.Date proba = new java.sql.Date(odDate.getTime());        
//        LocalDateTime asd = new LocalDateTime(odDate.getTime());
//        org.joda.time.LocalDate ld = new org.joda.time.LocalDate(odDate.getTime());
//		System.out.println("***LocalDateTime:" + asd); // ***LocalDateTime:2020-06-01T00:00:00.000
//		System.out.println("***sql Date:" + proba); // ***sql Date:2020-06-01
//		System.out.println("***LocalDate joda:" + ld); // ***LocalDate joda:2020-06-01
//		return new ResponseEntity<String>(
//				"mesto:"+mesto + "\nOd:"+odDate.toString() + "\nDo:"+doDate.toString() +
//				"\nmarka:"+marka + "\nmodel:"+model + "\nmenjac:"+menjac + "\ngorivo:"+gorivo +"\nklasa:"+klasa +
//				"\npredjena:"+predjenaInt + "\nplanirana:"+planiranaInt + "\nosiguranje:"+osiguranje + "\nbrSedZaDecu:"+brSedZaDecuInt
//				,HttpStatus.OK);
	}
}
