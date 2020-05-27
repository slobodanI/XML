package rs.xml.oglas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.repository.OglasRepository;

@Service
public class OglasService {

	@Autowired
	OglasRepository oglasRepository;
	
	public Oglas findOne(Long id) {
		Optional<Oglas> oglas = oglasRepository.findById(id);
		return oglas.orElseGet(null);
	}

	public List<Oglas> findAll() {
		return oglasRepository.findAll();
	}

	public Page<Oglas> findAll(Pageable page) {
		return oglasRepository.findAll(page);
	}

	public Oglas save(Oglas oglas) {
		return oglasRepository.save(oglas);
	}

	public void remove(Long id) {
		oglasRepository.deleteById(id);
	}
}
