package rs.xml.agent.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.model.Poruka;
import rs.xml.agent.repository.PorukaRepository;
import rs.xml.agent.soap.ChatClient;
import rs.xml.agent.xsd.PostPorukaResponse;

@Service
public class PorukaService {
	
	@Autowired
	private ChatClient chatClient;

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
	
	public Set<Poruka> findPorukeFromChat(Long chatId){
		return porukaRepository.findPoruke(chatId);
	}
	
	public void postPorukaUMikroservice(Poruka poruka) {
		PostPorukaResponse response = chatClient.postPoruka(poruka);
		if(response != null) {
			if(response.isSuccess()) {
				System.out.println("*** ChatService > savePoruka > PostPoruka u mirkoservise > USPESNO");
			} else {
				System.out.println("*** ChatService > savePoruka > PostPoruka u mirkoservise > NEUSPESNO");
			}
		} else {
			System.out.println("*** ChatService > savePoruka > PostPoruka u mirkoservise > NEUSPESNO");
		}
	}
	
}
