package rs.xml.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.chat.exception.NotFoundException;
import rs.xml.chat.model.Poruka;
import rs.xml.chat.repository.PorukaRepository;

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
