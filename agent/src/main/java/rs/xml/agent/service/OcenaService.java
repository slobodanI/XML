package rs.xml.agent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.model.Ocena;
import rs.xml.agent.model.OcenaApprovedStatus;
import rs.xml.agent.repository.OcenaRepository;
import rs.xml.agent.soap.OcenaClient;
import rs.xml.agent.xsd.PostOcenaResponse;
import rs.xml.agent.xsd.PutOcenaResponse;


@Service
public class OcenaService {
	
	@Autowired
	private OcenaRepository ocenaRepository;
	
	@Autowired
	private OcenaClient ocenaClient;
	
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
	
	public List<Ocena> findOceneForMe(String username) {
		return ocenaRepository.findOceneForMe(username);
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
	
	public void postOcenaUMikroservise(Ocena ocena) {
		PostOcenaResponse response = ocenaClient.postOcena(ocena);
		if(response != null) {
			if(response.isSuccess()) {
				System.out.println("*** OcenaService > saveZahtev > PostZahtev u mirkoservise > USPESNO");
			} else {
				System.out.println("*** OcenaService > saveZahtev > PostZahtev u mirkoservise > NEUSPESNO");
			}
		} else {
			System.out.println("*** OcenaService > saveZahtev > PostZahtev u mirkoservise > NEUSPESNO");
		}
		
	}
	
	public void putOcenaUMikroservise(Ocena ocena) {
		PutOcenaResponse response = ocenaClient.putOcena(ocena);
		if(response != null) {
			if(response.isSuccess()) {
				System.out.println("*** OcenaService > saveZahtev > PostZahtev u mirkoservise > USPESNO");
			} else {
				System.out.println("*** OcenaService > saveZahtev > PostZahtev u mirkoservise > NEUSPESNO");
			}
		} else {
			System.out.println("*** OcenaService > saveZahtev > PostZahtev u mirkoservise > NEUSPESNO");
		}
		
	}
	
}
