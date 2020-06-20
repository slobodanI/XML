package rs.xml.chat.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.chat.exception.NotFoundException;
import rs.xml.chat.model.Poruka;
import rs.xml.chat.repository.PorukaRepository;
import rs.xml.chat.util.UtilClass;

@Service
public class PorukaService {
	
	@Autowired
	UtilClass utilClass;

	@Autowired
	private PorukaRepository porukaRepository;
	
	public Poruka findOne(Long id) {
		Poruka Poruka = porukaRepository.findById(id).orElseThrow(() -> new NotFoundException("Poruka with id:" +id+ " does not exist!"));
		return Poruka;
	}
	
	public Poruka findOneByPid(String pid) {
		Poruka poruka = porukaRepository.findPorukaByPid(pid);
		return poruka;
	}

	public List<Poruka> findAll() {
		return porukaRepository.findAll();
	}

	public Page<Poruka> findAll(Pageable page) {
		return porukaRepository.findAll(page);
	}
	
	public Poruka save(Poruka poruka) {
		return porukaRepository.save(poruka);
	}

	public Poruka save(Poruka poruka,String username) {
		poruka.setPid(username + "-" + utilClass.randomString());
		return porukaRepository.save(poruka);
	}

	public void remove(Long id) {
		porukaRepository.deleteById(id);
	}
	
	public List<Poruka> findPorukeFromChat(Long chatId){
		return porukaRepository.findPoruke(chatId);
	}
	
}
