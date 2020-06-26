package rs.xml.agent.controllers;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.agent.dto.OcenaDTO;
import rs.xml.agent.dto.OcenaNewDTO;
import rs.xml.agent.dto.OcenaOdgovorDTO;
import rs.xml.agent.model.Ocena;
import rs.xml.agent.model.Oglas;
import rs.xml.agent.model.Zahtev;
import rs.xml.agent.model.ZahtevStatus;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.OcenaService;
import rs.xml.agent.service.OglasService;
import rs.xml.agent.service.ZahtevService;
import rs.xml.agent.util.UtilClass;

@RestController
public class OcenaController {

	final static Logger logger = LoggerFactory.getLogger(OcenaController.class);

	@Autowired
	OcenaService ocenaService;

	@Autowired
	ZahtevService zahtevService;

	@Autowired
	OglasService oglasService;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private UtilClass utilClass;

	@Autowired
	HttpServletRequest request;

	@GetMapping("/ocena")
	public ResponseEntity<?> getOcenas(@RequestParam(required = false, defaultValue = "nema") String filter,
			HttpServletRequest request) {

		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		List<Ocena> ocenaList = new ArrayList<Ocena>();

		// ocene za moje oglase
		if (filter.equals("zaMene")) {
			ocenaList = ocenaService.findOceneForMe(username);
			// ocene koje sam ja dao
		} else if (filter.equals("moje")) {
			// ocene koje treba da se odobre
			ocenaList = ocenaService.findMyOcene(username);
		} else if (filter.equals("toBeApproved")) {
			ocenaList = ocenaService.findOceneToBeApproved();
		} else {
			ocenaList = ocenaService.findAll();
		}

		List<OcenaDTO> ocenaListDTO = new ArrayList<OcenaDTO>();
		for (Ocena ocena : ocenaList) {
			OcenaDTO cDTO = new OcenaDTO(ocena);
			cDTO = utilClass.escapeOcenaDTO(cDTO);
			ocenaListDTO.add(cDTO);
		}

		return new ResponseEntity<>(ocenaListDTO, HttpStatus.OK);
	}

	@GetMapping("/ocena/{oid}")
	public ResponseEntity<?> getOcena(@PathVariable Long oid) {

		Ocena ocena = ocenaService.findOne(oid);
		OcenaDTO cDTO = new OcenaDTO(ocena);
		cDTO = utilClass.escapeOcenaDTO(cDTO);

		return new ResponseEntity<>(cDTO, HttpStatus.OK);
	}

	@PostMapping("/ocena")
	@PreAuthorize("hasAuthority('MANAGE_OCENA')")
	public ResponseEntity<?> postOcena(@RequestBody @Valid OcenaNewDTO ocenaNewDTO) {

		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		Zahtev zahtev = zahtevService.findOne(ocenaNewDTO.getZahtevId());

		// provere...
		// da li je zahtev placen
		if (zahtev.getStatus() != ZahtevStatus.PAID) {
			logger.warn("SR, FORBIDDEN Post Ocena, Zahtev with id:" + zahtev.getId() + " is not paid, By username:"
					+ username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Zahtev_nije_PAID!", HttpStatus.FORBIDDEN);
		}

		// da li je koriscenje vozila proslo
		Date sada = new Date(System.currentTimeMillis());
		if (zahtev.getDo().after(sada)) {
			logger.warn("SR, FORBIDDEN Post Ocena, Zahtev with id:" + zahtev.getId() + " is not finished, By username:"
					+ username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Još_nije_istekao_zahtev!", HttpStatus.FORBIDDEN);
		}

		// da li sam ja taj koji je poslao zahtev na osnovu koga ocenjujem
		if (!zahtev.getPodnosilacUsername().equals(username)) {
			logger.warn("SR, FORBIDDEN Post Ocena, Zahtev with id:" + zahtev.getId() + " is not owned, By username:"
					+ username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Nemaš_pravo_da_ocenjujes_ovaj_oglas!", HttpStatus.FORBIDDEN);
		}

		// da li je prosledjeni oglasId dobar, tj. da li se taj oglas nalazi u zahtevu
		boolean flag = false;
		for (Oglas oglas : zahtev.getOglasi()) {
			if (oglas.getId() == ocenaNewDTO.getOglasId()) {
				flag = true;
				break;
			}
		}
		if (flag == false) {
			logger.warn("SR, FORBIDDEN Post Ocena, Zahtev with id:" + zahtev.getId()
					+ " does not contain sent oglas, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Ovaj_oglas_se_ne_nalazi_u_zahtevu!", HttpStatus.FORBIDDEN);
		}

		// da li sam vec ocenio oglas...
		if (ocenaService.findOcenaIfExists(ocenaNewDTO.getZahtevId(), ocenaNewDTO.getOglasId()) != null) {
			logger.warn("SR, FORBIDDEN Post Ocena, Oglas with id:" + ocenaNewDTO.getOglasId()
					+ " is already rated, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Ovaj_oglas_ste_vec_ocenili!", HttpStatus.BAD_REQUEST);
		}

		Oglas oglas = oglasService.findOne(ocenaNewDTO.getOglasId());

		Ocena ocena = new Ocena(ocenaNewDTO, username, zahtev.getUsername(), oglas);
		ocena.setOid(username + "-" + utilClass.randomString());
		ocena = ocenaService.save(ocena);
		logger.info("Created ocena with id:" + ocena.getId() + " by username: " + username + ", IP:"
				+ request.getRemoteAddr());
		if (ocena != null) {
			ocenaService.postOcenaUMikroservise(ocena);
		}
		OcenaDTO ocenaDTO = new OcenaDTO(ocena);

		return new ResponseEntity<>(ocenaDTO, HttpStatus.OK);
	}

	@DeleteMapping("/ocena/{oid}")
	@PreAuthorize("hasAuthority('MANAGE_OCENA')")
	public ResponseEntity<?> deleteOcena(@PathVariable Long oid) {

		ocenaService.remove(oid);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/ocena/{oid}/approve")
	@PreAuthorize("hasAuthority('MANAGE_OCENA_ADMIN')")
	public ResponseEntity<?> approveOcena(@PathVariable Long oid) {

		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		if (!ocenaService.approveOcena(oid)) {
			logger.warn("BAD_REQUEST PUT Ocena, ocena with id:" + oid + " is already approved or denied, By username:"
					+ username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Ova_ocena_je_već_APPROVED_ili_DENIED!", HttpStatus.BAD_REQUEST);
		}
		Ocena ocena = ocenaService.findOne(oid);
		if (ocena != null) {
			logger.info("Updated ocena with id:" + ocena.getId() + " by username: " + username + ", IP:"
					+ request.getRemoteAddr());
			ocenaService.putOcenaUMikroservise(ocena);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/ocena/{oid}/deny")
	@PreAuthorize("hasAuthority('MANAGE_OCENA_ADMIN')")
	public ResponseEntity<?> denyOcena(@PathVariable Long oid) {

		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		if (!ocenaService.denyOcena(oid)) {
			logger.warn("SR, BAD_REQUEST PUT Ocena, ocena with id:" + oid
					+ " is already approved or denied, By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Ova_ocena_je_već_APPROVED_ili_DENIED!", HttpStatus.BAD_REQUEST);
		}
		Ocena ocena = ocenaService.findOne(oid);
		if (ocena != null) {
			logger.info("Updated ocena with id:" + ocena.getId() + " by username: " + username + ", IP:"
					+ request.getRemoteAddr());
			ocenaService.putOcenaUMikroservise(ocena);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/ocena/{oid}/odgovor")
	@PreAuthorize("hasAuthority('MANAGE_OCENA')")
	public ResponseEntity<?> ocenaOdgovor(@PathVariable Long oid, @RequestBody @Valid OcenaOdgovorDTO odgovor,
			HttpServletRequest request) {

		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		Ocena ocena = ocenaService.findOne(oid);
		if (!ocena.getUsernameKoga().equals(username)) {
			logger.warn("SR, FORBIDDEN PUT Ocena, ocena with id:" + oid + " is not for this user, By username:"
					+ username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Nije_tvoja_ocena,_ne_mozeš_da_odgovoriš_na_nju!", HttpStatus.FORBIDDEN);
		}

		if (!ocenaService.giveOdgovor(ocena, odgovor.getOdgovor())) {
			logger.warn("SR, BAD_REQUEST PUT Ocena, ocena with id:" + oid + " alredy has an answer, By username:"
					+ username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Već_ste_dali_odgovor_na_ovu_ocenu!", HttpStatus.BAD_REQUEST);
		}
		if (ocena != null) {
			logger.info("Updated ocena with id:" + ocena.getId() + " by username: " + username + ", IP:"
					+ request.getRemoteAddr());
			ocenaService.putOcenaUMikroservise(ocena);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
