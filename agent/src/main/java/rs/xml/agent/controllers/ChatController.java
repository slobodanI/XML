package rs.xml.agent.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.xml.agent.dto.ChatDTO;
import rs.xml.agent.dto.ChatNewDTO;
import rs.xml.agent.dto.ChatPorukeDTO;
import rs.xml.agent.dto.PorukaDTO;
import rs.xml.agent.dto.PorukaNewDTO;
import rs.xml.agent.model.Chat;
import rs.xml.agent.model.Poruka;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.ChatService;
import rs.xml.agent.service.PorukaService;
import rs.xml.agent.soap.EverythingClient;
import rs.xml.agent.util.UtilClass;
import rs.xml.agent.xsd.GetEverythingFromChatResponse;

@RestController
public class ChatController {
	
	final static Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private UtilClass utilClass;
	
	@Autowired
	ChatService chatService; 
	
	@Autowired
	PorukaService porukaService;
	
	@Autowired
	EverythingClient everythingClient;
	
	/**
	 * 
	 * @param request
	 * @return Sve chat-ove korisnika sa prosledjenim username-om u header-u
	 */
	@GetMapping("/chat")
	public ResponseEntity<?> getChats(HttpServletRequest request) {
		
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		List<Chat> chatList = chatService.findMyChats(username);
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
		
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		Chat chat = chatService.findOne(cid);
		if(!chat.getReceiverUsername().equals(username) && !chat.getSenderUsername().equals(username)) {
			logger.warn("SR, Unauthorized chat access attempt, Chat id:" +cid+ ", By username:" + username + ", IP:" + request.getRemoteAddr());
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
	@PreAuthorize("hasAuthority('MANAGE_CHAT')")
    public ResponseEntity<?> postChat(@RequestBody @Valid ChatNewDTO chatNewDTO, HttpServletRequest request) {
        
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		Chat chat = chatService.save(chatNewDTO, username);
		if(chat != null) {
			logger.info("Created chat with id:" +chat.getId()+ " by username: " +username+ ", IP:" + request.getRemoteAddr());
			chatService.postChatUMikroservise(chat);
		}
		
		ChatDTO chatDTO = new ChatDTO(chat);
		
		return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }
		
	@PostMapping("/chat/{cid}/poruka")
    public ResponseEntity<?> postPoruka(@PathVariable Long cid, @RequestBody @Valid PorukaNewDTO porukaNewDTO, HttpServletRequest request) {
        		
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		
		Chat chat = chatService.findOne(cid);
		if(!chat.getReceiverUsername().equals(username) && !chat.getSenderUsername().equals(username)) {
			logger.warn("SR, Unauthorized chat access attempt, Chat id:" +cid+ ", By username:" + username + ", IP:" + request.getRemoteAddr());
			return new ResponseEntity<String>("Nije_tvoj_chat!", HttpStatus.FORBIDDEN);
		}
		
		Poruka poruka = new Poruka(porukaNewDTO, chat, username);
		poruka.setPid(username + "-" + utilClass.randomString());
		poruka = porukaService.save(poruka);
		if(poruka != null) {
			
			porukaService.postPorukaUMikroservice(poruka);
		}
		PorukaDTO porukaDTO = new PorukaDTO(poruka);
		
		return new ResponseEntity<>(porukaDTO, HttpStatus.OK);
    }
	
	@GetMapping("/chat/testSaopEverything")
	public ResponseEntity<?> testSaopEverything() {
		
		GetEverythingFromChatResponse response = everythingClient.getEverythingFromChat("agent");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
