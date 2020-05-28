package rs.xml.oglas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.oglas.model.Cenovnik;
import rs.xml.oglas.repository.CenovnikRepository;

@Service
public class CenovnikService {

	@Autowired
	CenovnikRepository cenovnikRepository;
	
	public Cenovnik findOne(Long id) {
		return cenovnikRepository.findById(id).orElseGet(null);
	}

	public List<Cenovnik> findAll() {
		return cenovnikRepository.findAll();
	}

	public Page<Cenovnik> findAll(Pageable page) {
		return cenovnikRepository.findAll(page);
	}
	
	public Cenovnik save(Cenovnik cenovnik) {
		return cenovnikRepository.save(cenovnik);
	}

	public void remove(Long id) {
		cenovnikRepository.deleteById(id);
	}
}
