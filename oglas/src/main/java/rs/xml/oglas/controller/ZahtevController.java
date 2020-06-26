package rs.xml.oglas.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.oglas.dto.KorpaDTO;
import rs.xml.oglas.dto.OglasDTOsearch;
import rs.xml.oglas.dto.ZahtevDTO;
import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Zahtev;
import rs.xml.oglas.service.ZahtevService;
import rs.xml.oglas.util.UtilClass;

@RestController
public class ZahtevController {

	final static Logger logger = LoggerFactory.getLogger(ZahtevController.class);

	@Autowired
	ZahtevService zahtevService;

	@Autowired
	UtilClass utilClass;

	@Autowired
	HttpServletRequest request;

	/**
	 * @param filter
	 * @param request
	 * @return filter=zaMene - vraca sve zahteve koji su poslati meni, filter=moje -
	 *         vraca sve zahteve koje sam ja poslao, filter= - vraca sve zahteve
	 */
	@GetMapping("/zahtev")
	@PreAuthorize("hasAuthority('MANAGE_ZAHTEV')")
	public ResponseEntity<?> getZahtevi(@RequestParam(required = false, defaultValue = "nema") String filter,
			HttpServletRequest request) {
		String username = request.getHeader("username");

		List<Zahtev> zahteviList = new ArrayList<Zahtev>();

		// zahtevni za moje oglase
		if (filter.equals("zaMene")) {
			zahteviList = zahtevService.findZahteviForMe(username);
			// ocene koje sam ja dao
		} else if (filter.equals("moje")) {
			zahteviList = zahtevService.findMyZahtevi(username);
		} else {
			zahteviList = zahtevService.findAll();
		}

		List<ZahtevDTO> zahteviListDTO = new ArrayList<ZahtevDTO>();
		for (Zahtev zah : zahteviList) {
			ZahtevDTO zDTO = new ZahtevDTO(zah);
//			zDTO = utilClass.escapeZahtevDTO(zDTO);
			zahteviListDTO.add(zDTO);
		}
		return new ResponseEntity<>(zahteviListDTO, HttpStatus.OK);
	}

	@GetMapping("/zahtev/pending")
	@PreAuthorize("hasAuthority('MANAGE_ZAHTEV')")
	public ResponseEntity<?> getPending(HttpServletRequest request) {
		String username = request.getHeader("username");
		Collection<Zahtev> zahteviList = zahtevService.findPending();
		List<ZahtevDTO> zahteviListDTO = new ArrayList<ZahtevDTO>();
		for (Zahtev zah : zahteviList) {
			ZahtevDTO zDTO = new ZahtevDTO(zah);
			if (zDTO.getUsername().equals(username)) {
//			zDTO = utilClass.escapeZahtevDTO(zDTO);
				zahteviListDTO.add(zDTO);
			}
		}
		return new ResponseEntity<>(zahteviListDTO, HttpStatus.OK);
	}

	@GetMapping("/zahtev/{zid}")
	@PreAuthorize("hasAuthority('MANAGE_ZAHTEV')")
	public ResponseEntity<?> getZahtev(@PathVariable Long zid) {

		String username = request.getHeader("username");
		Zahtev zahtev = zahtevService.findOne(zid);

		if (zahtev == null) {
			logger.warn("NOT_FOUND GET Zahtev with id: " + zid + ",  By username:" + username + ", IP:"
					+ request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		ZahtevDTO zahtevDTO = new ZahtevDTO(zahtev);
		// zahtevDTO = utilClass.escapeZahtevDTO(zahtevDTO);

		return new ResponseEntity<>(zahtevDTO, HttpStatus.OK);
	}

	@GetMapping("/zahtev/{zid}/oglasi")
	@PreAuthorize("hasAuthority('MANAGE_ZAHTEV')")
	public ResponseEntity<?> getOglaseZahteva(@PathVariable Long zid) {
		String username = request.getHeader("username");
		Zahtev zahtev = zahtevService.findOne(zid);

		if (zahtev == null) {
			logger.warn("NOT_FOUND GET Zahtev with id: " + zid + ",  By username:" + username + ", IP:"
					+ request.getRemoteAddr());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		List<OglasDTOsearch> oglasiDTO = new ArrayList<OglasDTOsearch>();
		for (Oglas o : zahtev.getOglasi()) {
			OglasDTOsearch og = new OglasDTOsearch(o);
			// og=utilClass.escapeOglasDTOsearch(og);
			oglasiDTO.add(og);
		}

		return new ResponseEntity<>(oglasiDTO, HttpStatus.OK);
	}

	@PostMapping("/zahtev")
	@PreAuthorize("hasAuthority('MANAGE_ZAHTEV')")
	public ResponseEntity<?> postZahtev(@RequestBody KorpaDTO korpa, HttpServletRequest request) {

		String username = request.getHeader("username");

		String odgovor = zahtevService.save(korpa, username);
		if (odgovor.equals("Kreirani zahtevi sa vise oglasa")) {
			logger.info("Created Zahtev by username: " + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(odgovor, HttpStatus.OK);
		} else if (odgovor.equals("Kreirano je vise zahteva")) {
			logger.info("Created Zahtev by username: " + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<>(odgovor, HttpStatus.OK);
		} else {
			logger.warn("BAD_REQUEST POST Zahtev, Zahtev payload is bad, By username:" + username + ", IP:"
					+ request.getRemoteAddr());
			return new ResponseEntity<>(odgovor, HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/zahtev/{zId}/accept")
	@PreAuthorize("hasAuthority('MANAGE_ZAHTEV')")
	public ResponseEntity<?> acceptZahtev(@PathVariable(name = "zId") Long zId, HttpServletRequest request) {

		String username = request.getHeader("username");
		String permisije = request.getHeader("permissions");

		Zahtev zah = zahtevService.findOne(zId);

		if (!zah.getUsername().equals(username)) {
			logger.warn("FORBIDDEN PUT Zahtev, Zahtev with id: " + zId + " is not yours, By username:" + username
					+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Nije_tvoj_zahtev!", HttpStatus.FORBIDDEN);
		}

		ZahtevDTO zDTO = new ZahtevDTO(zahtevService.acceptZahtev(zId, username, permisije));
		logger.info("Updated Zahtev with id: " + zId + " by username: " + username + ", IP:" + request.getRemoteAddr());

		return new ResponseEntity<>(zDTO, HttpStatus.OK);

	}

	@PutMapping("/zahtev/{zId}/decline")
	@PreAuthorize("hasAuthority('MANAGE_ZAHTEV')")
	public ResponseEntity<?> declineZahtev(@PathVariable(name = "zId") Long zId, HttpServletRequest request) {
		String username = request.getHeader("username");

		Zahtev zah = zahtevService.findOne(zId);

		if (!zah.getUsername().equals(username)) {
			logger.warn("FORBIDDEN PUT Zahtev, Zahtev with id: " + zId + " is not yours, By username:" + username
					+ ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Nije_tvoj_zahtev!", HttpStatus.FORBIDDEN);
		}

		ZahtevDTO zDTO = new ZahtevDTO(zahtevService.declineZahtev(zId));
		logger.info("Updated Zahtev with id: " + zId + " by username: " + username + ", IP:" + request.getRemoteAddr());
		return new ResponseEntity<>(zDTO, HttpStatus.OK);

	}

}
