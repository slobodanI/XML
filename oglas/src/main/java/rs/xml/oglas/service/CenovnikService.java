package rs.xml.oglas.service;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.oglas.dto.NewCenovnikDTO;
import rs.xml.oglas.exception.NotFoundException;
import rs.xml.oglas.exception.UniqueConstrainException;
import rs.xml.oglas.model.Cenovnik;
import rs.xml.oglas.repository.CenovnikRepository;

@Service
public class CenovnikService {

	@Autowired
	CenovnikRepository cenovnikRepository;
	
	public Cenovnik findOne(Long id) {
		return cenovnikRepository.findById(id).orElseThrow(() -> new NotFoundException("Cenovnik with id:" +id+ " does not exist!") );
	}

	public List<Cenovnik> findAll() {
		return cenovnikRepository.findAll();
	}

	public Page<Cenovnik> findAll(Pageable page) {
		return cenovnikRepository.findAll(page);
	}
	
	public Cenovnik save(Cenovnik cenovnik) {
//		jdbcSQLIntegrityConstraintViolationException
		try {
			cenovnikRepository.save(cenovnik);
		} catch (Exception e) {
			throw new UniqueConstrainException("Cenovnik with name:" +cenovnik.getName()+ " already exist!");
		}
		return cenovnik;
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
		
		return cenovnikRepository.save(cen);
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

	
}
