package rs.xml.oglas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.oglas.exception.NotFoundException;
import rs.xml.oglas.model.Ocena;
import rs.xml.oglas.model.OcenaApprovedStatus;
import rs.xml.oglas.repository.OcenaRepository;


@Service
public class OcenaService {
	
	@Autowired
	private OcenaRepository ocenaRepository;
	
	public Ocena findOne(Long id) {
		Ocena ocena = ocenaRepository.findById(id).orElseThrow(() -> new NotFoundException("Ocena with id:" +id+ " does not exist!"));
		if(ocena.isDeleted()) {
			throw new NotFoundException("Ocena with id:" +id+ " does not exist!");
		}
		return ocena;
	}

	public List<Ocena> findAll() {
		return ocenaRepository.findAll();
	}

	public Page<Ocena> findAll(Pageable page) {
		return ocenaRepository.findAll(page);
	}

	public Ocena save(Ocena ocena) {
		return ocenaRepository.save(ocena);
	}

	public void remove(Long id) {
		Ocena ocena = findOne(id); // baca exception
		ocena.setDeleted(true);
		ocenaRepository.save(ocena);
	}
	
	public List<Ocena> findMyOcene(String username) {
		return ocenaRepository.findMyOcene(username);
	}
	
	public Ocena findOcenaByOID(String oid) {
		return ocenaRepository.findOcenaByOID(oid);
	}
	
	public List<Ocena> findOceneForMe(String username) {
		return ocenaRepository.findOceneForMe(username);
	}
	public Long findOglasFromOcena(Long id) {
		return ocenaRepository.findOglasFromOcena(id);
	}
	
	public Ocena findOcenaIfExists(Long zahtevId, Long oglasId) {
		return ocenaRepository.findOcenaIfExists(zahtevId, oglasId);
	}
		
	public List<Ocena> findOceneToBeApproved() {
		return ocenaRepository.findOceneToBeApproved();
	}

	public boolean approveOcena(Long oid) {		
		Ocena ocena = findOne(oid);
		
		if(ocena.getApproved().equals(OcenaApprovedStatus.UNKNOWN)) {
			ocena.setApproved(OcenaApprovedStatus.APPROVED);
			this.save(ocena);
			return true;
		}
		
		return false;				
	}
	
	public boolean denyOcena(Long oid) {
		Ocena ocena = findOne(oid);
		
		if(ocena.getApproved().equals(OcenaApprovedStatus.UNKNOWN)) {
			ocena.setApproved(OcenaApprovedStatus.DENIED);
			this.save(ocena);
			return true;
		}
			
		return false;				
	}

	public boolean giveOdgovor(Ocena ocena, String odgovor) {

		
		if(ocena.getOdgovor().equals("nema odgovora...")) {
			ocena.setOdgovor(odgovor);
			this.save(ocena);
			return true;
		}

		return false;
	}
	
}
