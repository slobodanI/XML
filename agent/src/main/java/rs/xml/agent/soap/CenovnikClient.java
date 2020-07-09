package rs.xml.agent.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import rs.xml.agent.service.CenovnikService;
import rs.xml.agent.xsd.PostCenovnikRequest;
import rs.xml.agent.xsd.PostCenovnikResponse;
import rs.xml.agent.xsd.PutCenovnikRequest;
import rs.xml.agent.xsd.PutCenovnikResponse;

public class CenovnikClient extends WebServiceGatewaySupport {
	
	@Autowired
	CenovnikService cenovnikService;
	
	private static final Logger log = LoggerFactory.getLogger(CenovnikClient.class);
	
	
	public PostCenovnikResponse postCenovnik(rs.xml.agent.model.Cenovnik cenovnik) {
		
		rs.xml.agent.xsd.Cenovnik cenovnikXSD = new rs.xml.agent.xsd.Cenovnik();
		
		cenovnikXSD.setCenaOsiguranja(cenovnik.getCenaOsiguranja());
		cenovnikXSD.setCenaPoKilometru(cenovnik.getCenaPoKilometru());
		cenovnikXSD.setCenaZaDan(cenovnik.getCenaZaDan());
		cenovnikXSD.setCid(cenovnik.getCid());
		cenovnikXSD.setName(cenovnik.getName());
		cenovnikXSD.setPopust(cenovnik.getPopust());
		cenovnikXSD.setUsername(cenovnik.getUsername());
		cenovnikXSD.setZaViseOd(cenovnik.getZaViseOd());
		
		PostCenovnikRequest request = new PostCenovnikRequest();
		request.setCenovnik(cenovnikXSD);
		
		log.info("Post cenovnika preko web servisa... ");
		
		PostCenovnikResponse response = null;
		
		try {
			response = (PostCenovnikResponse) getWebServiceTemplate().marshalSendAndReceive(
					"http://localhost:8085/ws/postCenovnik", request,
					new SoapActionCallback("http://xml.rs/oglas/xsd/PostCenovnikRequest"));
		} catch (Exception e) {
			System.out.println("***ERROR CenovnikClient > greska prilikom slanja!");
		}
		
		return response;
		
		
	}
	
	
	public PutCenovnikResponse putCenovnik(rs.xml.agent.model.Cenovnik cenovnik) {
		
		rs.xml.agent.xsd.Cenovnik cenovnikXSD = new rs.xml.agent.xsd.Cenovnik();
		
		cenovnikXSD.setCenaOsiguranja(cenovnik.getCenaOsiguranja());
		cenovnikXSD.setCenaPoKilometru(cenovnik.getCenaPoKilometru());
		cenovnikXSD.setCenaZaDan(cenovnik.getCenaZaDan());
		cenovnikXSD.setCid(cenovnik.getCid());
		cenovnikXSD.setName(cenovnik.getName());
		cenovnikXSD.setPopust(cenovnik.getPopust());
		cenovnikXSD.setUsername(cenovnik.getUsername());
		cenovnikXSD.setZaViseOd(cenovnik.getZaViseOd());
		
		PutCenovnikRequest request = new PutCenovnikRequest();
		request.setCenovnik(cenovnikXSD);
		
		log.info("Put cenovnika preko web servisa... ");
		
		PutCenovnikResponse response = null;
		
		try {
			response = (PutCenovnikResponse) getWebServiceTemplate().marshalSendAndReceive(
					"http://localhost:8085/ws/putCenovnik", request,
					new SoapActionCallback("http://xml.rs/oglas/xsd/PutCenovnikRequest"));
		} catch (Exception e) {
			System.out.println("***ERROR CenovnikClient > greska prilikom slanja!");
		}
		
		return response;
		
		
	}

}
