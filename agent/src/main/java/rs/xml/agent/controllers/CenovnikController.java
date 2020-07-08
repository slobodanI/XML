package rs.xml.agent.controllers;

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

import rs.xml.agent.dto.CenovnikDTO;
import rs.xml.agent.dto.NewCenovnikDTO;
import rs.xml.agent.model.Cenovnik;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.CenovnikService;

@RestController
public class CenovnikController {

	final static Logger logger = LoggerFactory.getLogger(CenovnikController.class);

	@Autowired
	private TokenUtils tokenUtils;

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

		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		List<CenovnikDTO> cenovnikList = new ArrayList<CenovnikDTO>();

		for (Cenovnik c : cenovnikService.findAllFromUser(username)) {
			cenovnikList.add(new CenovnikDTO(c));
		}

		return new ResponseEntity<>(cenovnikList, HttpStatus.OK);
	}

	@GetMapping("/cenovnik/{cid}")
	@PreAuthorize("hasAuthority('MANAGE_CENOVNIK')")
	public ResponseEntity<?> getCenovnikById(@PathVariable Long cid, HttpServletRequest request) {

		String token = request.getHeader("Auth").substring(7);
		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);

//		String username = request.getHeader("username");
//		String permisije = request.getHeader("permissions");

		Cenovnik cenovnik = cenovnikService.findOne(cid);

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
		CenovnikDTO cen = new CenovnikDTO(cenovnik);
		return new ResponseEntity<>(cen, HttpStatus.OK);
	}

	@PostMapping("/cenovnik")
	@PreAuthorize("hasAuthority('MANAGE_CENOVNIK')")
	public ResponseEntity<?> postCenovnik(@RequestBody @Valid NewCenovnikDTO cenovnikDTO, HttpServletRequest request) {

		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);

//		String username = request.getHeader("username");
		List<Cenovnik> cenovnici = cenovnikService.findAll();
		for (Cenovnik cen : cenovnici) {
			if (cen.getName().equals((username + "-" + cenovnikDTO.getName()))) {
				return new ResponseEntity<String>("Vec postoji cenovnik sa tim nazivom!", HttpStatus.BAD_REQUEST);
			}
		}

		Cenovnik cen = new Cenovnik(cenovnikDTO, username);

		cen = cenovnikService.save(cen,username);
		logger.info("Created cenovnik with id:" + cen.getId() + " by username: " + username + ", IP:"
				+ request.getRemoteAddr());
		CenovnikDTO cenovnik = new CenovnikDTO(cen);
		System.out.println("CENOVNIK CID----->"+cen.getCid());
		return new ResponseEntity<>(cenovnik, HttpStatus.OK);
	}

	@PutMapping("/cenovnik/{cid}")
	@PreAuthorize("hasAuthority('MANAGE_CENOVNIK')")
	public ResponseEntity<?> putCenovnik(@PathVariable Long cid, @RequestBody @Valid NewCenovnikDTO cenovnikDTO,
			HttpServletRequest request) {

		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);

		List<Cenovnik> cenovnici = cenovnikService.findAll();
		for (Cenovnik cen : cenovnici) {
			if (cen.getName().equals((username + "-" + cenovnikDTO.getName()))) {
				return new ResponseEntity<String>("Vec postoji cenovnik sa tim nazivom!", HttpStatus.BAD_REQUEST);
			}
		}

//		String username = request.getHeader("username");
		Cenovnik cenovnik = cenovnikService.updateMyCenovnik(cid, cenovnikDTO, username);
		if (cenovnik == null) {
			logger.warn("SR, Unauthorized cenovnik access attempt, Cenovnik id:" + cid + ", By username:" + username
					+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Nije_tvoj_cenovnik!", HttpStatus.FORBIDDEN);
		}
		logger.info("Updated cenovnik with id:" + cenovnik.getId() + " by username: " + username + ", IP:"
				+ request.getRemoteAddr());
		CenovnikDTO cen = new CenovnikDTO(cenovnik);
		return new ResponseEntity<>(cen, HttpStatus.OK);
	}

	@DeleteMapping("/cenovnik/{cid}")
	@PreAuthorize("hasAuthority('MANAGE_CENOVNIK')")
	public ResponseEntity<?> deleteCenovnikById(@PathVariable Long cid, HttpServletRequest request) {

		String token = request.getHeader("Auth").substring(7);
//		String permisije = tokenUtils.getPermissionFromToken(token);
		String username = tokenUtils.getUsernameFromToken(token);

//		String username = request.getHeader("username");
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
