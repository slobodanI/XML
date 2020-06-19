package rs.xml.agent.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import rs.xml.agent.service.ZahtevService;
import rs.xml.agent.xsd.PostOcenaRequest;
import rs.xml.agent.xsd.PostOcenaResponse;
import rs.xml.agent.xsd.PutOcenaRequest;
import rs.xml.agent.xsd.PutOcenaResponse;

public class OcenaClient extends WebServiceGatewaySupport {
	
	@Autowired
	ZahtevService zahtevService;
	
	private static final Logger log = LoggerFactory.getLogger(OcenaClient.class);
	
	public PostOcenaResponse postOcena(rs.xml.agent.model.Ocena ocena) {
		rs.xml.agent.xsd.Ocena ocenaXSD = new rs.xml.agent.xsd.Ocena();
		
		if(ocena.getApproved().equals(rs.xml.agent.model.OcenaApprovedStatus.APPROVED)) {
			ocenaXSD.setApproved(rs.xml.agent.xsd.OcenaApprovedStatus.APPROVED);
		} else if(ocena.getApproved().equals(rs.xml.agent.model.OcenaApprovedStatus.DENIED)) {
			ocenaXSD.setApproved(rs.xml.agent.xsd.OcenaApprovedStatus.DENIED);
		} else if(ocena.getApproved().equals(rs.xml.agent.model.OcenaApprovedStatus.UNKNOWN)) {
			ocenaXSD.setApproved(rs.xml.agent.xsd.OcenaApprovedStatus.UNKNOWN);
		}
		
		ocenaXSD.setDeleted(ocena.isDeleted());
		ocenaXSD.setKomentar(ocena.getKomentar());
		ocenaXSD.setOcena(ocena.getOcena());
		ocenaXSD.setOdgovor(ocena.getOdgovor());
		ocenaXSD.setOglasId(ocena.getOglas().getOid());
		ocenaXSD.setOid(ocena.getOid());
		ocenaXSD.setUsernameKo(ocena.getUsernameKo());
		ocenaXSD.setUsernameKoga(ocena.getUsernameKoga());
		rs.xml.agent.model.Zahtev zahtev = zahtevService.findOne(ocena.getZahtevId());
		ocenaXSD.setZahtevId(zahtev.getZid());
				
		PostOcenaRequest request = new PostOcenaRequest();
		request.setOcena(ocenaXSD);

		log.info("Post ocena preko web servisa... ");
		
		PostOcenaResponse response = null;
		
		try {
			response = (PostOcenaResponse) getWebServiceTemplate().marshalSendAndReceive(
					"http://localhost:8085/ws/postOcena", request,
					new SoapActionCallback("http://xml.rs/oglas/xsd/PostOcenaRequest"));
		} catch (Exception e) {
			System.out.println("***ERROR OcenaClient > greska prilikom slanja!");
		}
		
		return response;
		
	}
	
	public PutOcenaResponse putOcena(rs.xml.agent.model.Ocena ocena){
		rs.xml.agent.xsd.Ocena ocenaXSD = new rs.xml.agent.xsd.Ocena();
		
		if(ocena.getApproved().equals(rs.xml.agent.model.OcenaApprovedStatus.APPROVED)) {
			ocenaXSD.setApproved(rs.xml.agent.xsd.OcenaApprovedStatus.APPROVED);
		} else if(ocena.getApproved().equals(rs.xml.agent.model.OcenaApprovedStatus.DENIED)) {
			ocenaXSD.setApproved(rs.xml.agent.xsd.OcenaApprovedStatus.DENIED);
		} else if(ocena.getApproved().equals(rs.xml.agent.model.OcenaApprovedStatus.UNKNOWN)) {
			ocenaXSD.setApproved(rs.xml.agent.xsd.OcenaApprovedStatus.UNKNOWN);
		}
		
		ocenaXSD.setDeleted(ocena.isDeleted());
		ocenaXSD.setKomentar(ocena.getKomentar());
		ocenaXSD.setOcena(ocena.getOcena());
		ocenaXSD.setOdgovor(ocena.getOdgovor());
		ocenaXSD.setOglasId(ocena.getOglas().getOid());
		ocenaXSD.setOid(ocena.getOid());
		ocenaXSD.setUsernameKo(ocena.getUsernameKo());
		ocenaXSD.setUsernameKoga(ocena.getUsernameKoga());
		rs.xml.agent.model.Zahtev zahtev = zahtevService.findOne(ocena.getZahtevId());
		ocenaXSD.setZahtevId(zahtev.getZid());
				
		PutOcenaRequest request = new PutOcenaRequest();
		request.setOcena(ocenaXSD);

		log.info("Post ocena preko web servisa... ");
		
		PutOcenaResponse response = null;
		
		try {
			response = (PutOcenaResponse) getWebServiceTemplate().marshalSendAndReceive(
					"http://localhost:8085/ws/putOcena", request,
					new SoapActionCallback("http://xml.rs/oglas/xsd/PutOcenaRequest"));
		} catch (Exception e) {
			System.out.println("***ERROR OcenaClient > greska prilikom slanja!");
		}
		
		return response;
	}
	
}
