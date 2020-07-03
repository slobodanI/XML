package rs.xml.agent.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.agent.dto.NewOglasDTO;
import rs.xml.agent.dto.OglasDTO;
import rs.xml.agent.dto.OglasDTOsearch;
import rs.xml.agent.model.Cenovnik;
import rs.xml.agent.model.Oglas;
import rs.xml.agent.model.User;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.CenovnikService;
import rs.xml.agent.service.OcenaService;
import rs.xml.agent.service.OglasService;
import rs.xml.agent.service.SlikaService;
import rs.xml.agent.service.UserService;
import rs.xml.agent.soap.EverythingClient;
import rs.xml.agent.xsd.GetEverythingResponse;

@RestController
public class OglasController {

	final static Logger logger = LoggerFactory.getLogger(OglasController.class);

	@Autowired
	private TokenUtils tokenUtils;
	/*
	 * TODO: -dodaj cenovnik -da li da vracam brisane oglase metodom findAll ???
	 */

	@Autowired
	OglasService oglasService;
	
	@Autowired
	UserService userService;

	@Autowired
	CenovnikService cenovnikService;

	@Autowired
	SlikaService slikaService;
	
	@Autowired	
	OcenaService ocenaService;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	HttpServletRequest request;

	@GetMapping("/oglas")
	public ResponseEntity<?> getOglasi(@RequestParam(required = false, defaultValue = "nema") String filter) {
		// String ip = InetAddress.getLocalHost().getHostAddress();
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		List<Oglas> oglasList = new ArrayList<Oglas>();

		if (filter.equals("moje")) {
			oglasList = oglasService.findMyOglasi(username);
			List<OglasDTOsearch> oglasListDTO = new ArrayList<OglasDTOsearch>();
			for (Oglas og : oglasList) {
				OglasDTOsearch oDTO = new OglasDTOsearch(og);
				int suma = 0;
				int broj = 0;
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
				if (broj != 0) {
					d = (float) suma / broj;
				}

				try {
					km = oglasService.getKilometri(og.getId());
				} catch (Exception e) {
					// TODO: handle exception
				}
				oDTO.setOcena2(d);
				oDTO.setPredjenaInt(km);
				oDTO.setBrojOcena(broj);
//				oDTO = utilClass.escapeOglasDTOsearch(oDTO);
				oglasListDTO.add(oDTO);
			}
			return new ResponseEntity<>(oglasListDTO, HttpStatus.OK);

		} else {
			oglasList = oglasService.findAll();
			List<OglasDTO> oglasListDTO = new ArrayList<OglasDTO>();
			for (Oglas og : oglasList) {
				OglasDTO oDTO = new OglasDTO(og);
//				oDTO = utilClass.escapeOglasDTO(oDTO);
				oglasListDTO.add(oDTO);
			}
			return new ResponseEntity<>(oglasListDTO, HttpStatus.OK);
		}

	}

	@GetMapping("/oglas/{oid}")
	public ResponseEntity<?> getOglas(@PathVariable Long oid) {
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		Oglas oglas = oglasService.findOne(oid);

		if (oglas == null || oglas.isDeleted()) {
			logger.warn("NOT_FOUND GET Oglas, Oglas with id:" + oid + " is deleted, By username:" + username + ", IP:"
					+ request.getRemoteAddr());
			return new ResponseEntity<String>("Oglas with id:" + oid + " does not exist!", HttpStatus.NOT_FOUND);
		}

		OglasDTO oglasDTO = new OglasDTO(oglas);
//		oglasDTO = utilClass.escapeOglasDTO(oglasDTO);

		return new ResponseEntity<>(oglasDTO, HttpStatus.OK);
	}

	@PostMapping("/oglas")
	@PreAuthorize("hasAuthority('CREATE_OGLAS')")
	public ResponseEntity<?> postOglas(@RequestBody @Valid NewOglasDTO oglasDTO) {
		String token = request.getHeader("Auth").substring(7);
		String permisijeMoje = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);

		User user = userService.findByUsername(username);
		if(user.isBlockedPostavljanjeOglasa()) {
			return new ResponseEntity<String>("Korisniku je zabranjeno postavljanje oglasa!", HttpStatus.FORBIDDEN);
		}
		
		if (oglasDTO.getOD().after(oglasDTO.getDO())) {
			logger.warn("BAD_REQUEST POST Oglas, Oglas payload is bad, By username:" + username + ", IP:"
					+ request.getRemoteAddr());
			return new ResponseEntity<String>("OD mora biti pre DO datuma!", HttpStatus.BAD_REQUEST);
		}

		Oglas oglas = new Oglas(oglasDTO);
		oglasService.createOglasWithFeignClient(oglas, oglasDTO);

		Cenovnik cen = cenovnikService.findOne(oglasDTO.getCenovnik());
		//dodati ogranicenje da nije null
		oglas.setCenovnik(cen);
		oglas.setCena(cen.getCenaZaDan());
		oglas.setUsername(username);
		if(cen.getCenaOsiguranja()>0) {
			oglas.setOsiguranje(true);
		}
		// ako je obican user poslao zahtev
		if (permisijeMoje.contains("ROLE_USER") && !permisijeMoje.contains("ROLE_AGENT")
				&& !permisijeMoje.contains("ROLE_ADMIN")) {
			// da li ima 3 aktivna oglasa?
			oglas = oglasService.saveAsBasicUser(oglas, username);
			if (oglas == null) {
				logger.warn("FORBIDDEN POST Oglas, User has 3 active oglas, By username:" + username + ", IP:"
						+ request.getRemoteAddr());
				return new ResponseEntity<String>("Korisnik_ima_3_aktivna_oglasa", HttpStatus.FORBIDDEN);
			}
			logger.info("Created Oglas by username: " + username + ", IP:" + request.getRemoteAddr());
		}
		// ako je agent ili admin
		else {
			oglas = oglasService.save(oglas);
			logger.info("Created Oglas by username: " + username + ", IP:" + request.getRemoteAddr());
		}

		oglas.setUsername(username);
		oglas.setSlike(null);
		return new ResponseEntity<>(oglas, HttpStatus.OK);
	}

	@PutMapping("/oglas/{oid}")
	@PreAuthorize("hasAuthority('MANAGE_OGLAS')")
	public ResponseEntity<?> updateOglas(@PathVariable Long oid, @RequestBody @Valid NewOglasDTO oglasDTO) {

		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		if (oglasDTO.getOD().after(oglasDTO.getDO())) {
			logger.warn("BAD_REQUEST PUT Oglas, Oglas payload is bad, By username:" + username + ", IP:"
					+ request.getRemoteAddr());
			return new ResponseEntity<String>("OD mora biti pre DO datuma!", HttpStatus.BAD_REQUEST);
		}

		Oglas oglas = oglasService.updateOglas(oid, oglasDTO, username);
		if (oglas == null) {
			logger.warn("FORBIDDEN PUT Oglas, Oglas with id:" + oid + " is not yours, By username:" + username + ", IP:"
					+ request.getRemoteAddr());
			return new ResponseEntity<String>("Nije_tvoj_oglas!", HttpStatus.FORBIDDEN);
		}
		logger.info("Updated Oglas with id:" + oid + " by username: " + username + ", IP:" + request.getRemoteAddr());
		OglasDTO oglasDTOret = new OglasDTO(oglas);
		return new ResponseEntity<>(oglasDTOret, HttpStatus.OK);
	}

	@DeleteMapping("oglas/{oid}")
	@PreAuthorize("hasAuthority('MANAGE_OGLAS')")
	public ResponseEntity<?> deleteOglas(@PathVariable Long oid) {

		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		if (oglasService.deleteOglas(oid, username) == null) {
			logger.warn("FORBIDDEN DELETE Oglas, Oglas with id:" + oid + " is not yours, By username:" + username
					+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Nije_tvoj_oglas!", HttpStatus.FORBIDDEN);
		}
		logger.info("DELETED Oglas with id:" + oid + " by username: " + username + ", IP:" + request.getRemoteAddr());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// sto se tice datuma, vraticu oglas samo ako oglas OD-DO sadrzi trazeni OD-DO
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam(required = true) String mesto,
			@RequestParam(required = true) String Od, @RequestParam(required = true) String Do,
			@RequestParam(required = false, defaultValue = "nema") String marka, // ako nema default value, bude null
			@RequestParam(required = false, defaultValue = "nema") String model,
			@RequestParam(required = false, defaultValue = "nema") String menjac,
			@RequestParam(required = false, defaultValue = "nema") String gorivo,
			@RequestParam(required = false, defaultValue = "nema") String klasa,
			@RequestParam(required = false, defaultValue = "0") String predjena, // kilometraza
			@RequestParam(required = false, defaultValue = "0") String planirana, // kilometraza
			@RequestParam(required = false, defaultValue = "nema") String osiguranje,
			@RequestParam(required = false, defaultValue = "0") String brSedZaDecu,
			@RequestParam(required = false, defaultValue = "0") String cenaOd,
			@RequestParam(required = false, defaultValue = "0") String cenaDo) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date odDate;
		Date doDate;
		int predjenaInt;
		int planiranaInt;
//        boolean osiguranjeBool;
		int brSedZaDecuInt;
		int cenaOdInt;
		int cenaDoInt;

		try {
			odDate = (Date) formatter.parse(Od);
			doDate = (Date) formatter.parse(Do);
			predjenaInt = Integer.parseInt(predjena);
			planiranaInt = Integer.parseInt(planirana);
//            osiguranjeBool = Boolean.parseBoolean(osiguranje);
			brSedZaDecuInt = Integer.parseInt(brSedZaDecu);
			cenaOdInt = Integer.parseInt(cenaDo);
			cenaDoInt = Integer.parseInt(cenaDo);
		} catch (ParseException | NumberFormatException e) {
			System.out.println("***Parametri za pretragu nisu dobro formirani!");
			return new ResponseEntity<String>("Parametri_za_pretragu_nisu_dobro_formirani!", HttpStatus.BAD_REQUEST);
		}

//        System.out.println(
//        "mesto:"+mesto + "\nOd:"+odDate.toString() + "\nDo:"+doDate.toString() +
//		"\nmarka:"+marka + "\nmodel:"+model + "\nmenjac:"+menjac + "\ngorivo:"+gorivo +"\nklasa:"+klasa +
//		"\npredjena:"+predjenaInt + "\nplanirana:"+planiranaInt + "\nosiguranje:"+osiguranje + "\nbrSedZaDecu:"+brSedZaDecuInt);

		Collection<OglasDTOsearch> oglasi = oglasService.search(mesto, odDate, doDate, marka, model, menjac, gorivo,
				klasa, predjenaInt, planiranaInt, osiguranje, brSedZaDecuInt,cenaOdInt,cenaDoInt);
		return new ResponseEntity<Collection<OglasDTOsearch>>(oglasi, HttpStatus.OK);
//        RAD SA VREMENOM
//      java.sql.Date proba = new java.sql.Date(odDate.getTime());        
//      LocalDateTime asd = new LocalDateTime(odDate.getTime());
//      org.joda.time.LocalDate ld = new org.joda.time.LocalDate(odDate.getTime());
//		System.out.println("***LocalDateTime:" + asd); // ***LocalDateTime:2020-06-01T00:00:00.000
//		System.out.println("***sql Date:" + proba); // ***sql Date:2020-06-01
//		System.out.println("***LocalDate joda:" + ld); // ***LocalDate joda:2020-06-01
	}

	@Autowired
	EverythingClient everythingClient;

	@GetMapping("/oglas/testSaopEverything")
	public ResponseEntity<?> testSaopEverything() {

		GetEverythingResponse response = everythingClient.getEverything("agent");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
