package rs.xml.chat.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import rs.xml.chat.service.ChatService;
import rs.xml.chat.service.PorukaService;
import rs.xml.chat.xsd.GetEverythingFromChatRequest;
import rs.xml.chat.xsd.GetEverythingFromChatResponse;
import rs.xml.chat.xsd.PostChatRequest;
import rs.xml.chat.xsd.PostChatResponse;
import rs.xml.chat.xsd.PostPorukaRequest;
import rs.xml.chat.xsd.PostPorukaResponse;



@org.springframework.ws.server.endpoint.annotation.Endpoint
public class Endpoint {

	private static final String NAMESPACE_URI = "http://xml.rs/oglas/xsd";
	
	@Autowired
	PorukaService porukaService;
	
	@Autowired
	ChatService chatService;
	
	@Autowired
	public Endpoint() {
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "postChatRequest")
	@ResponsePayload
	public PostChatResponse postChat(@RequestPayload PostChatRequest request) {
		System.out.println("*** postChat > " + request.getChat().getSenderUsername() + " za "
				+ request.getChat().getReceiverUsername());
		PostChatResponse response = new PostChatResponse();

		rs.xml.chat.model.Chat chat = new rs.xml.chat.model.Chat();
		rs.xml.chat.xsd.Chat chatXSD = request.getChat();

		chat.setCid(chatXSD.getCid());
		chat.setReceiverUsername(chatXSD.getReceiverUsername());
		chat.setSenderUsername(chatXSD.getSenderUsername());
		
		rs.xml.chat.model.Poruka poruka = new rs.xml.chat.model.Poruka();
		for(rs.xml.chat.xsd.Poruka p : chatXSD.getPoruke()) {
			poruka = porukaService.findOneByPid(p.getPid());
			if(poruka == null) {
				continue;
			}
			chat.getPoruke().add(poruka);
		}
		
		chat = chatService.save(chat);
		if(chat == null) {
			response.setSuccess(false);
		}else {
			response.setSuccess(true);
		}

		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "postPorukaRequest")
	@ResponsePayload
	public PostPorukaResponse postChat(@RequestPayload PostPorukaRequest request) {
		System.out.println("*** postOcena > " + request.getPoruka().getSenderUsername() + " za "
				+ request.getPoruka().getTimestamp());
		PostPorukaResponse response = new PostPorukaResponse();

		rs.xml.chat.model.Poruka poruka = new rs.xml.chat.model.Poruka();
		rs.xml.chat.xsd.Poruka porukaXSD = request.getPoruka();
		
		
		poruka.setPid(porukaXSD.getPid());
		poruka.setBody(porukaXSD.getBody());
		poruka.setSenderUsername(porukaXSD.getSenderUsername());
		java.sql.Timestamp timestamp = new java.sql.Timestamp(porukaXSD.getTimestamp());
		poruka.setTimestamp(timestamp);
		
		rs.xml.chat.model.Chat chat = chatService.findOneByCid(porukaXSD.getCid());
		poruka.setChat(chat);
		
		poruka = porukaService.save(poruka);
		if(poruka == null) {
			response.setSuccess(false);
		}else {
			response.setSuccess(true);
		}

		return response;
	}

	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEverythingFromChatRequest")
	@ResponsePayload
	public GetEverythingFromChatResponse getEverythingFromChat(@RequestPayload GetEverythingFromChatRequest request) {
		GetEverythingFromChatResponse response = new GetEverythingFromChatResponse();
		
		System.out.println("###ENDPOINT > getEverythingFromChat > start");
		
		
		System.out.println("###ENDPOINT > getEverythingFromChat > FOR(findMyChat) > start");
		for(rs.xml.chat.model.Chat chat : chatService.findMyChats(request.getUsername())) {
			response.getChatovi().add(convertModelChatToXSDChat(chat));
		}
		System.out.println("###ENDPOINT > getEverythingFromChat > FOR(findMyChat) > finish");
		
		System.out.println("###ENDPOINT > getEverythingFromChat >"+response.toString());
		
		return response;
	}
	
	
	private rs.xml.chat.xsd.Chat convertModelChatToXSDChat(rs.xml.chat.model.Chat chat){
		rs.xml.chat.xsd.Chat chatXSD = new rs.xml.chat.xsd.Chat();
		
		System.out.println("### CONVERTOVANJE CHAT 1 > chatXSD: " + chatXSD);
		chatXSD.setCid(chat.getCid());
		chatXSD.setReceiverUsername(chat.getReceiverUsername());
		chatXSD.setSenderUsername(chat.getSenderUsername());
		System.out.println("### CONVERTOVANJE CHAT 2 > chatXSD: " + chatXSD);
		
		for(rs.xml.chat.model.Poruka poruka : porukaService.findPorukeFromChat(chat.getId())) {
			rs.xml.chat.xsd.Poruka p = new rs.xml.chat.xsd.Poruka();
			p.setBody(poruka.getBody());
			p.setPid(poruka.getPid());
			p.setCid(chat.getCid());
			p.setSenderUsername(poruka.getSenderUsername());
			p.setTimestamp(poruka.getTimestamp().getTime());
			chatXSD.getPoruke().add(p);
		}
		
		System.out.println("### uspesno konvertovanje chata!");
		return chatXSD;
	}
	
}
