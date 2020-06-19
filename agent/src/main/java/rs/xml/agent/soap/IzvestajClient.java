package rs.xml.agent.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import rs.xml.agent.service.OglasService;
import rs.xml.agent.service.ZahtevService;
import rs.xml.agent.xsd.PostIzvestajRequest;
import rs.xml.agent.xsd.PostIzvestajResponse;

public class IzvestajClient extends WebServiceGatewaySupport {
	
	@Autowired
	ZahtevService zahtevService;
	
	@Autowired
	OglasService oglasService;
	
	private static final Logger log = LoggerFactory.getLogger(IzvestajClient.class);
	
	public PostIzvestajResponse postIzvestaj(rs.xml.agent.model.Izvestaj izvestaj) {
		
		rs.xml.agent.xsd.Izvestaj izvestajXSD = new rs.xml.agent.xsd.Izvestaj();
		rs.xml.agent.model.Oglas oglas = oglasService.findOne(izvestaj.getOglasId());
		rs.xml.agent.model.Zahtev zahtev = zahtevService.findOne(izvestaj.getZahtevId());
		
		izvestajXSD.setIid(izvestaj.getIid());
		izvestajXSD.setOglasId(oglas.getOid());
		izvestajXSD.setPredjeniKilometri(izvestaj.getPredjeniKilometri());
		izvestajXSD.setTekst(izvestaj.getTekst());
		izvestajXSD.setZahtevId(zahtev.getZid());
		
		PostIzvestajRequest request = new PostIzvestajRequest();
		request.setIzvestaj(izvestajXSD);
		
		log.info("Post izvestaja preko web servisa... ");
		
		PostIzvestajResponse response = null;
		
		try {
			response = (PostIzvestajResponse) getWebServiceTemplate().marshalSendAndReceive(
					"http://localhost:8085/ws/postIzvestaj", request,
					new SoapActionCallback("http://xml.rs/oglas/xsd/PostIzvestajRequest"));
		} catch (Exception e) {
			System.out.println("***ERROR OcenaClient > greska prilikom slanja!");
		}
		
		return response;
		
	}
	
}
