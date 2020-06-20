package rs.xml.agent.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import rs.xml.agent.xsd.PostChatRequest;
import rs.xml.agent.xsd.PostChatResponse;
import rs.xml.agent.xsd.PostPorukaRequest;
import rs.xml.agent.xsd.PostPorukaResponse;

public class ChatClient extends WebServiceGatewaySupport {
	
	private static final Logger log = LoggerFactory.getLogger(ChatClient.class);
	
	public PostChatResponse postChat(rs.xml.agent.model.Chat chat) {
		rs.xml.agent.xsd.Chat chatXSD = new rs.xml.agent.xsd.Chat();
		
		chatXSD.setCid(chat.getCid());
		chatXSD.setReceiverUsername(chat.getReceiverUsername());
		chatXSD.setSenderUsername(chat.getSenderUsername());
		
		for(rs.xml.agent.model.Poruka poruka : chat.getPoruke()) {
			rs.xml.agent.xsd.Poruka p = new rs.xml.agent.xsd.Poruka();
			p.setBody(poruka.getBody());
			p.setPid(poruka.getPid());
			p.setCid(chat.getCid());
			p.setSenderUsername(poruka.getSenderUsername());
			p.setTimestamp(poruka.getTimestamp().getTime());
			chatXSD.getPoruke().add(p);
		}
				
		PostChatRequest request = new PostChatRequest();
		request.setChat(chatXSD);

		log.info("Post chat-a preko web servisa... ");
		
		PostChatResponse response = null;
		
		try {
			response = (PostChatResponse) getWebServiceTemplate().marshalSendAndReceive(
					"http://localhost:8089/ws/postChat", request,
					new SoapActionCallback("http://xml.rs/chat/xsd/postChatRequest"));
		} catch (Exception e) {
			System.out.println("***ERROR ChatClient > greska prilikom slanja!");
		}
		
		return response;
		
	}
	
	public PostPorukaResponse postPoruka(rs.xml.agent.model.Poruka poruka) {
		rs.xml.agent.xsd.Poruka porukaXSD = new rs.xml.agent.xsd.Poruka();
		
		porukaXSD.setBody(poruka.getBody());
		porukaXSD.setCid(poruka.getChat().getCid());
		porukaXSD.setPid(poruka.getPid());
		porukaXSD.setSenderUsername(poruka.getSenderUsername());
		porukaXSD.setTimestamp(poruka.getTimestamp().getTime());

				
		PostPorukaRequest request = new PostPorukaRequest();
		request.setPoruka(porukaXSD);

		log.info("Post poruke preko web servisa... ");
		
		PostPorukaResponse response = null;
		
		try {
			response = (PostPorukaResponse) getWebServiceTemplate().marshalSendAndReceive(
					"http://localhost:8089/ws/postPoruka", request,
					new SoapActionCallback("http://xml.rs/chat/xsd/PostPorukaRequest"));
		} catch (Exception e) {
			System.out.println("***ERROR ChatClient > greska prilikom slanja!");
		}
		
		return response;
		
	}

}
