package rs.xml.agent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.model.Poruka;
import rs.xml.agent.repository.PorukaRepository;

@Service
public class PorukaService {

	@Autowired
	private PorukaRepository porukaRepository;
	
	public Poruka findOne(Long id) {
		Poruka Poruka = porukaRepository.findById(id).orElseThrow(() -> new NotFoundException("Poruka with id:" +id+ " does not exist!"));
		return Poruka;
	}

	public List<Poruka> findAll() {
		return porukaRepository.findAll();
	}

	public Page<Poruka> findAll(Pageable page) {
		return porukaRepository.findAll(page);
	}

	public Poruka save(Poruka Poruka) {
		return porukaRepository.save(Poruka);
	}

	public void remove(Long id) {
		porukaRepository.deleteById(id);
	}
	
}
