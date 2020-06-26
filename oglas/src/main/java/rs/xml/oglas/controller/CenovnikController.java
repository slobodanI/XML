package rs.xml.oglas.controller;

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
import org.springframework.web.bind.annotation.RestController;

import rs.xml.oglas.dto.NewCenovnikDTO;
import rs.xml.oglas.dto.NewOglasDTO;
import rs.xml.oglas.dto.OglasDTO;
import rs.xml.oglas.model.Cenovnik;
import rs.xml.oglas.service.CenovnikService;

@RestController
public class CenovnikController {

	final static Logger logger = LoggerFactory.getLogger(CenovnikController.class);

	@Autowired
	CenovnikService cenovnikService;

	/**
	 * @param request
	 * @return Return sve cenovnike korisnika. Ako je korisnik admin return sve
	 *         cenovnike u sistemu.
	 */
	@GetMapping("/cenovnik")
	@PreAuthorize("hasAuthority('MANAGE_CENOVNIK')")
	public ResponseEntity<?> getAllCenovnik(HttpServletRequest request) {

		String username = request.getHeader("username");
		String permisije = request.getHeader("permissions");

		List<Cenovnik> cenovnikList = new ArrayList<Cenovnik>();
		if (permisije.contains("ROLE_ADMIN")) {
			cenovnikList = cenovnikService.findAll();
		} else {
			cenovnikList = cenovnikService.findAllFromUser(username);
		}

		return new ResponseEntity<>(cenovnikList, HttpStatus.OK);
	}

	@GetMapping("/cenovnik/{cid}")
	@PreAuthorize("hasAuthority('MANAGE_CENOVNIK')")
	public ResponseEntity<?> getCenovnikById(@PathVariable Long cid, HttpServletRequest request) {

		String username = request.getHeader("username");
		String permisije = request.getHeader("permissions");

		Cenovnik cenovnik = cenovnikService.findOne(cid);
		;

		// ako nije admin...
		if (!permisije.contains("ROLE_ADMIN")) {
			// da li je njegov cenovnik
			if (cenovnik.getUsername().equals(username)) {
				return new ResponseEntity<>(cenovnik, HttpStatus.OK);
			} else {
				logger.warn("SR, Unauthorized cenovnik access attempt, Cenovnik id:" + cid + ", By username:" + username
						+ ", IP:" + request.getRemoteAddr());
				return new ResponseEntity<String>("Nije_tvoj_cenovnik!", HttpStatus.FORBIDDEN);
			}
		}

		return new ResponseEntity<>(cenovnik, HttpStatus.OK);
	}

	@PostMapping("/cenovnik")
	@PreAuthorize("hasAuthority('MANAGE_CENOVNIK')")
	public ResponseEntity<?> postCenovnik(@RequestBody @Valid NewCenovnikDTO cenovnikDTO, HttpServletRequest request) {

		String username = request.getHeader("username");

		Cenovnik cen = new Cenovnik(cenovnikDTO, username);

		cen = cenovnikService.save(cen);

		logger.info("Created cenovnik with id:" + cen.getId() + " by username: " + username + ", IP:"
				+ request.getRemoteAddr());
		return new ResponseEntity<>(cen, HttpStatus.OK);
	}

	@PutMapping("/cenovnik/{cid}")
	@PreAuthorize("hasAuthority('MANAGE_CENOVNIK')")
	public ResponseEntity<?> putCenovnik(@PathVariable Long cid, @RequestBody @Valid NewCenovnikDTO cenovnikDTO,
			HttpServletRequest request) {

		String username = request.getHeader("username");
		Cenovnik cenovnik = cenovnikService.updateMyCenovnik(cid, cenovnikDTO, username);
		if (cenovnik == null) {
			logger.warn("SR, Unauthorized cenovnik access attempt, Cenovnik id:" + cid + ", By username:" + username
					+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Nije_tvoj_cenovnik!", HttpStatus.FORBIDDEN);
		}
		logger.info("Updated cenovnik with id:" + cenovnik.getId() + " by username: " + username + ", IP:"
				+ request.getRemoteAddr());
		return new ResponseEntity<>(cenovnik, HttpStatus.OK);
	}

	@DeleteMapping("/cenovnik/{cid}")
	@PreAuthorize("hasAuthority('MANAGE_CENOVNIK')")
	public ResponseEntity<?> deleteCenovnikById(@PathVariable Long cid, HttpServletRequest request) {

		String username = request.getHeader("username");
		if (!cenovnikService.deleteMyCenovnik(cid, username)) {
			logger.warn("SR, Unauthorized cenovnik access attempt, Cenovnik id:" + cid + ", By username:" + username
					+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Nije_tvoj_cenovnik!", HttpStatus.FORBIDDEN);
		}
		logger.info(
				"Deleted cenovnik with id:" + cid + " by username: " + username + ", IP:" + request.getRemoteAddr());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
