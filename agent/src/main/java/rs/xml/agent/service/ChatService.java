package rs.xml.agent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.agent.dto.ChatNewDTO;
import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.model.Chat;
import rs.xml.agent.repository.ChatRepository;

@Service
public class ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	
	public Chat findOne(Long id) {
		Chat Chat = chatRepository.findById(id).orElseThrow(() -> new NotFoundException("Chat with id:" +id+ " does not exist!"));
		return Chat;
	}

	public List<Chat> findAll() {
		return chatRepository.findAll();
	}

	public Page<Chat> findAll(Pageable page) {
		return chatRepository.findAll(page);
	}

	public Chat save(ChatNewDTO chatDTO) {
		
		Chat chat = new Chat(chatDTO);
		return chatRepository.save(chat);
	}

	public void remove(Long id) {
		chatRepository.deleteById(id);
	}

	public List<Chat> findMyChats(String username) {
		return chatRepository.findMyChats(username);
	}
	
}
