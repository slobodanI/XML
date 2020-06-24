package rs.xml.chat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.chat.dto.ChatDTO;
import rs.xml.chat.dto.ChatNewDTO;
import rs.xml.chat.dto.ChatPorukeDTO;
import rs.xml.chat.dto.PorukaDTO;
import rs.xml.chat.dto.PorukaNewDTO;
import rs.xml.chat.model.Chat;
import rs.xml.chat.model.Poruka;
import rs.xml.chat.service.ChatService;
import rs.xml.chat.service.PorukaService;
import rs.xml.chat.util.UtilClass;

@RestController
public class ChatController {
	
	final static Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	@Autowired
	ChatService chatService; 
	
	@Autowired
	UtilClass utilClass;
	
	@Autowired
	PorukaService porukaService;
	
	/**
	 * 
	 * @param request
	 * @return Sve chat-ove korisnika sa prosledjenim username-om u header-u
	 */
	@GetMapping("/chat")
	public ResponseEntity<?> getChats(HttpServletRequest request) {
		String username = request.getHeader("username");
		List<Chat> chatList = chatService.findMyChats(username);
		logger.info("get all my chats {}", "test");
		List<ChatDTO> chatListDTO = new ArrayList<ChatDTO>();
		for(Chat chat: chatList) {
			ChatDTO cDTO = new ChatDTO(chat);
			cDTO = utilClass.escapeChatDTO(cDTO);
			chatListDTO.add(cDTO);
		}
		
		return new ResponseEntity<>(chatListDTO, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param cid
	 * @return Sve poruke iz chat-a
	 */
	@GetMapping("/chat/{cid}")
	public ResponseEntity<?> getChat(@PathVariable Long cid, HttpServletRequest request) {
		String username = request.getHeader("username");
		
		Chat chat = chatService.findOne(cid);
		if(!chat.getReceiverUsername().equals(username) && !chat.getSenderUsername().equals(username)) {
			return new ResponseEntity<String>("Nije_tvoj_chat!", HttpStatus.FORBIDDEN);
		}
		
		// da poruke budu sortirane
		chat.setPoruke(porukaService.findPorukeFromChat(chat.getId()));
		ChatPorukeDTO chatPorukeDTO = new ChatPorukeDTO();
		for(Poruka p : chat.getPoruke()) {
			PorukaDTO porukaDTO = new PorukaDTO(p);
			porukaDTO = utilClass.escapePorukaDTO(porukaDTO);
			chatPorukeDTO.getPoruke().add(porukaDTO);
		}

		return new ResponseEntity<>(chatPorukeDTO, HttpStatus.OK);
	}

	@PostMapping("/chat")
    public ResponseEntity<?> postChat(@RequestBody @Valid ChatNewDTO chatNewDTO, HttpServletRequest request) {
        		
		String username = request.getHeader("username");
		String permisije = request.getHeader("permissions");
		
		Chat chat = new Chat(chatNewDTO);
		
		chatService.save(chat,username);
		
		ChatDTO chatDTO = new ChatDTO(chat);
		
		return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }
		
	@PostMapping("/chat/{cid}/poruka")
    public ResponseEntity<?> postPoruka(@PathVariable Long cid, @RequestBody @Valid PorukaNewDTO porukaNewDTO, HttpServletRequest request) {
        		
		String username = request.getHeader("username");
		
		Chat chat = chatService.findOne(cid);
		if(!chat.getReceiverUsername().equals(username) && !chat.getSenderUsername().equals(username)) {
			return new ResponseEntity<String>("Nije_tvoj_chat!", HttpStatus.FORBIDDEN);
		}
		
		Poruka poruka = new Poruka(porukaNewDTO, chat, username);
		
		porukaService.save(poruka,username);
		
		PorukaDTO porukaDTO = new PorukaDTO(poruka);
		
		return new ResponseEntity<>(porukaDTO, HttpStatus.OK);
    }
}
