package rs.xml.agent.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import rs.xml.agent.xsd.GetEverythingRequest;
import rs.xml.agent.xsd.GetEverythingResponse;

public class EverythingClient extends WebServiceGatewaySupport  {
	
	private static final Logger log = LoggerFactory.getLogger(EverythingClient.class);

	public GetEverythingResponse getEverything(String username) {
				
		GetEverythingRequest request = new GetEverythingRequest();
		request.setUsername(username);
		
		GetEverythingResponse response = null;
		
		log.info("Get everything preko web servisa... ");
		
		try {
			response = (GetEverythingResponse) getWebServiceTemplate().marshalSendAndReceive(
					"http://localhost:8085/ws/getEverything", request,
					new SoapActionCallback("http://xml.rs/oglas/xsd/getEverythingRequest"));
		} catch (Exception e) {
			System.out.println("***ERROR EverythingClient > greska prilikom slanja!");
		}
		
		return response;
	}
	
	
	
}
