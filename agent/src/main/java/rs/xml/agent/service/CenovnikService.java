package rs.xml.agent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.agent.dto.NewCenovnikDTO;
import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.exceptions.UniqueConstrainException;
import rs.xml.agent.model.Cenovnik;
import rs.xml.agent.model.Zahtev;
import rs.xml.agent.repository.CenovnikRepository;
import rs.xml.agent.soap.CenovnikClient;
import rs.xml.agent.util.UtilClass;
import rs.xml.agent.xsd.PostCenovnikResponse;
import rs.xml.agent.xsd.PutCenovnikResponse;
import rs.xml.agent.xsd.PutZahtevResponse;

@Service
public class CenovnikService {

	@Autowired
	UtilClass utilClass;
	
	@Autowired
	CenovnikRepository cenovnikRepository;
	
	@Autowired
	CenovnikClient cenovnikClient;
	
	public Cenovnik findOne(Long id) {
		return cenovnikRepository.findById(id).orElseThrow(() -> new NotFoundException("Cenovnik with id:" +id+ " does not exist!") );
	}
	
	public Cenovnik findOneByCid(String cid) {
		return cenovnikRepository.findOneByCid(cid);
	}

	public List<Cenovnik> findAll() {
		return cenovnikRepository.findAll();
	}

	public Page<Cenovnik> findAll(Pageable page) {
		return cenovnikRepository.findAll(page);
	}
	
	public Cenovnik save(Cenovnik cenovnik,String username) {
//		jdbcSQLIntegrityConstraintViolationException
		cenovnik.setCid(username + "-" + utilClass.randomString());
		try {
			cenovnikRepository.save(cenovnik);
		} catch (Exception e) {
			throw new UniqueConstrainException("Cenovnik with name:" +cenovnik.getName()+ " already exist!");
		}
		postCenovnikUMikroservise(cenovnik);
		return cenovnik;
	}
	
	public Cenovnik saveUpdated(Cenovnik cenovnik) {
		return cenovnikRepository.save(cenovnik);
	}

	public void remove(Long id) {
		findOne(id); // baci exception ako ne postoji
		cenovnikRepository.deleteById(id);
	}
	
	public List<Cenovnik> findAllFromUser(String username) {
		return cenovnikRepository.findAllFromUser(username);
	}
	
	public Cenovnik updateMyCenovnik(Long cid, NewCenovnikDTO cenovnikDTO, String username) {
		Cenovnik cen = findOne(cid);
		
		if(!cen.getUsername().equals(username)) {
			return null;
		}
		
		cen.setCenaZaDan(cenovnikDTO.getCenaZaDan());
		cen.setCenaPoKilometru(cenovnikDTO.getCenaPoKilometru());
		cen.setCenaOsiguranja(cenovnikDTO.getCenaOsiguranja());
		cen.setPopust(cenovnikDTO.getPopust());
		cen.setZaViseOd(cenovnikDTO.getZaViseOd());
		cen.setName(username + "-" +cenovnikDTO.getName());
		cen = cenovnikRepository.save(cen);
		putCenovnikUMikroservise(cen);
		return cen;
	}
	
	public boolean deleteMyCenovnik(Long cid, String username) {
		Cenovnik cen = findOne(cid);
		if(cen.getUsername().equals(username)) {
			cenovnikRepository.deleteById(cid);
			return true;
		}
		else {
			return false;
		}
	}

	
	private void postCenovnikUMikroservise(Cenovnik cenovnik) {
		PostCenovnikResponse response = cenovnikClient.postCenovnik(cenovnik);
		if (response != null) {
			if (response.isSuccess()) {
				System.out.println("*** CenovnikService > saveCenovnik > PostCenovnik u mirkoservise > USPESNO");
			} else {
				System.out.println("*** CenovnikService > saveCenovnik > PostCenovnik u mirkoservise > NEUSPESNO");
			}
		} else {
			System.out.println("*** CenovnikService > saveCenovnik > PostCenovnik u mirkoservise > NEUSPESNO");
		}

	}
	
	private void putCenovnikUMikroservise(Cenovnik cenovnik) {
		PutCenovnikResponse response = cenovnikClient.putCenovnik(cenovnik);
		if (response != null) {
			if (response.isSuccess()) {
				System.out.println("*** CenovnikService > putCenovnik > PutCenovnik u mirkoservise > USPESNO");
			} else {
				System.out.println("*** CenovnikService > putCenovnik > PutCenovnik u mirkoservise > NEUSPESNO");
			}
		} else {
			System.out.println("*** CenovnikService > putCenovnik > PutCenovnik u mirkoservise > NEUSPESNO");
		}

	}
	
}
