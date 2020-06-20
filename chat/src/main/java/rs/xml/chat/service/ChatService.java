package rs.xml.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.chat.exception.NotFoundException;
import rs.xml.chat.model.Chat;
import rs.xml.chat.repository.ChatRepository;
import rs.xml.chat.util.UtilClass;


@Service
public class ChatService {
	
	@Autowired
	UtilClass utilClass;
	
	@Autowired
	private ChatRepository chatRepository;
	
	public Chat findOne(Long id) {
		Chat Chat = chatRepository.findById(id).orElseThrow(() -> new NotFoundException("Chat with id:" +id+ " does not exist!"));
		return Chat;
	}
	
	public Chat findOneByCid(String cid) {
		Chat chat = chatRepository.findChatByCid(cid);
		return chat;
	}

	public List<Chat> findAll() {
		return chatRepository.findAll();
	}

	public Page<Chat> findAll(Pageable page) {
		return chatRepository.findAll(page);
	}
	
	public Chat save(Chat chat) {
		return chatRepository.save(chat);
	}
	
	public Chat save(Chat chat,String username) {
		chat.setCid(username + "-" + utilClass.randomString());
		return chatRepository.save(chat);
	}

	public void remove(Long id) {
		chatRepository.deleteById(id);
	}

	public List<Chat> findMyChats(String username) {
		return chatRepository.findMyChats(username);
	}
	
}
