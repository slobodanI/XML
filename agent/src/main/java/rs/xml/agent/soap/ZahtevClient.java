package rs.xml.agent.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import rs.xml.agent.xsd.PostZahtevRequest;
import rs.xml.agent.xsd.PostZahtevResponse;

public class ZahtevClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(ZahtevClient.class);
	
	public PostZahtevResponse postZahtev(rs.xml.agent.model.Zahtev zahtev) {
		rs.xml.agent.xsd.Zahtev zahtevXSD = new rs.xml.agent.xsd.Zahtev();
		
		zahtevXSD.setZid(zahtev.getZid());
		zahtevXSD.setOdDate(zahtev.getOd().getTime());	
		zahtevXSD.setDoDate(zahtev.getDo().getTime());
		zahtevXSD.setIzvestaj(zahtev.isIzvestaj());
		zahtevXSD.setOcenjen(zahtev.isOcenjen());
				
		for(rs.xml.agent.model.Oglas oglas: zahtev.getOglasi()) {
			rs.xml.agent.xsd.OglasUZahtevu oglasUZahtevu = new rs.xml.agent.xsd.OglasUZahtevu();
			oglasUZahtevu.setOid(oglas.getOid());
			zahtevXSD.getOglasi().add(oglasUZahtevu);
		}
		
		zahtevXSD.setPodnosilacUsername(zahtev.getPodnosilacUsername());
		zahtevXSD.setUsername(zahtev.getUsername());
		
		if(zahtev.getStatus().equals(rs.xml.agent.model.ZahtevStatus.CANCELED)) {
			zahtevXSD.setStatus(rs.xml.agent.xsd.ZahtevStatus.CANCELED);
		} else if(zahtev.getStatus().equals(rs.xml.agent.model.ZahtevStatus.PENDING)) {
			zahtevXSD.setStatus(rs.xml.agent.xsd.ZahtevStatus.PENDING);
		} else if(zahtev.getStatus().equals(rs.xml.agent.model.ZahtevStatus.PAID)) {
			zahtevXSD.setStatus(rs.xml.agent.xsd.ZahtevStatus.PAID);
		}

		zahtevXSD.setVremePodnosenja(zahtev.getVremePodnosenja().getTime());
		
		PostZahtevRequest request = new PostZahtevRequest();
		request.setZahtev(zahtevXSD);

		log.info("Post zahteva preko web servisa... ");
		
		PostZahtevResponse response = null;
		
		try {
			response = (PostZahtevResponse) getWebServiceTemplate().marshalSendAndReceive(
					"http://localhost:8085/ws/postZahtev", request,
					new SoapActionCallback("http://xml.rs/oglas/xsd/PostZahtevRequest"));
		} catch (Exception e) {
			System.out.println("***ERROR ZahtevClient > greska prilikom slanja!");
		}
		
		return response;
		
	}
	
}
