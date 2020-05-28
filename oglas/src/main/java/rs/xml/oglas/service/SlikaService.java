package rs.xml.oglas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.oglas.model.Slika;
import rs.xml.oglas.repository.SlikaRepository;

@Service
public class SlikaService {

	@Autowired
	SlikaRepository slikaRepository;
	
	public Slika findOne(Long id) {
		return slikaRepository.findById(id).orElseGet(null);
	}

	public List<Slika> findAll() {
		return slikaRepository.findAll();
	}

	public Page<Slika> findAll(Pageable page) {
		return slikaRepository.findAll(page);
	}

	public Slika save(Slika slika) {
		return slikaRepository.save(slika);
	}

	public void remove(Long id) {
		slikaRepository.deleteById(id);
	}
	
}
