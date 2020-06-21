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
import rs.xml.agent.soap.ChatClient;
import rs.xml.agent.util.UtilClass;
import rs.xml.agent.xsd.PostChatResponse;


@Service
public class ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UtilClass utilClass;
	
	@Autowired
	private ChatClient chatClient;
	
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

	public Chat save(Chat chat) {
		return chatRepository.save(chat);
	}
	
	public Chat save(ChatNewDTO chatDTO, String username) {
		
		Chat chat = new Chat(chatDTO);
		chat.setCid(username + "-" + utilClass.randomString());
		return chatRepository.save(chat);
	}

	public void remove(Long id) {
		chatRepository.deleteById(id);
	}

	public List<Chat> findMyChats(String username) {
		return chatRepository.findMyChats(username);
	}
	
	public void postChatUMikroservise(Chat chat) {
		PostChatResponse response = chatClient.postChat(chat);
		if(response != null) {
			if(response.isSuccess()) {
				System.out.println("*** ChatService > saveChat > PostChat u mirkoservise > USPESNO");
			} else {
				System.out.println("*** ChatService > saveChat > PostChat u mirkoservise > NEUSPESNO");
			}
		} else {
			System.out.println("*** ChatService > saveChat > PostChat u mirkoservise > NEUSPESNO");
		}
	}
	

	
}
