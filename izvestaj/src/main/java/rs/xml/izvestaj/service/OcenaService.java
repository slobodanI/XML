package rs.xml.izvestaj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.izvestaj.exception.NotFoundException;
import rs.xml.izvestaj.model.Ocena;
import rs.xml.izvestaj.repository.OcenaRepository;

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
		ocenaRepository.deleteById(id);
	}
	
//	public List<Ocena> findMyOcene(String username) {
//		return ocenaRepository.findMyOcene(username);
//	}
//	
}
