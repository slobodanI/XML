package rs.xml.agent.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import rs.xml.agent.model.Oglas;
import rs.xml.agent.xsd.PostOglasRequest;
import rs.xml.agent.xsd.PostOglasResponse;

public class OglasClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(OglasClient.class);

	public PostOglasResponse postOglas(Oglas oglasToXSD) {
		
		rs.xml.agent.xsd.Oglas oglas = new rs.xml.agent.xsd.Oglas();
		
		oglas.setMesto(oglasToXSD.getMesto());
		oglas.setMarka(oglasToXSD.getMarka());
		oglas.setModel(oglasToXSD.getModel());
		oglas.setGorivo(oglasToXSD.getGorivo());
		oglas.setMenjac(oglasToXSD.getMenjac());
		oglas.setKlasa(oglasToXSD.getKlasa());
		oglas.setCena(oglasToXSD.getCena());
		oglas.setKilometraza(oglasToXSD.getKilometraza());
		oglas.setPlaniranaKilometraza(oglasToXSD.getPlaniranaKilometraza());
		oglas.setSedistaZaDecu(oglasToXSD.getSedistaZaDecu());
				
		for(rs.xml.agent.model.Slika slika: oglasToXSD.getSlike()) {			
			rs.xml.agent.xsd.Slika slikaXSD = new rs.xml.agent.xsd.Slika();
			slikaXSD.setSlika(slika.getSlika());
			oglas.getSlike().add(slikaXSD);
		}
		
		oglas.setOsiguranje(oglasToXSD.isOsiguranje());
		oglas.setUsername(oglasToXSD.getUsername());
			
		oglas.setOdDate(oglasToXSD.getOd().getTime());
		oglas.setDoDate(oglasToXSD.getDo().getTime());
		
		oglas.setDeleted(false);			
		
		PostOglasRequest request = new PostOglasRequest();
		request.setOglas(oglas);

		log.info("Post oglas preko web servisa... ");
		
		PostOglasResponse response = null;
		
		try {
			response = (PostOglasResponse) getWebServiceTemplate().marshalSendAndReceive(
					"http://localhost:8085/ws/postOglas", request,
					new SoapActionCallback("http://xml.rs/oglas/xsd/PostOglasRequest"));
		} catch (Exception e) {
			System.out.println("***ERROR OglasClient > greska prilikom slanja!");
		}
		
		return response;
	}

}