package rs.xml.chat.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import rs.xml.chat.service.ChatService;
import rs.xml.chat.service.PorukaService;
import rs.xml.chat.xsd.GetEverythingFromChatRequest;
import rs.xml.chat.xsd.GetEverythingFromChatResponse;

public class EndpointEverything {
	
	
	private static final String NAMESPACE_URI = "http://xml.rs/oglas/xsd";
	
	@Autowired
	ChatService chatService;
	
	@Autowired
	PorukaService porukaService;
	
	@Autowired
	public EndpointEverything () {
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
		for(rs.xml.chat.model.Poruka poruka : chat.getPoruke()) {
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
	
//	private rs.xml.chat.xsd.Poruka convertModelPorukaToXSDPoruka(rs.xml.chat.model.Poruka poruka){
//		rs.xml.chat.xsd.Poruka porukaXSD = new rs.xml.chat.xsd.Poruka();
//		
//		System.out.println("### CONVERTOVANJE CHAT 1 > porukaXSD: " + porukaXSD);
//		
//		porukaXSD.setBody(poruka.getBody());
//		porukaXSD.setCid(poruka.getChat().getCid());
//		porukaXSD.setPid(poruka.getPid());
//		porukaXSD.setSenderUsername(poruka.getSenderUsername());
//		porukaXSD.setTimestamp(poruka.getTimestamp().getTime());
//		System.out.println("### uspesno konvertovanje poruke!");
//		
//		return porukaXSD;
//	}
	
	
}
