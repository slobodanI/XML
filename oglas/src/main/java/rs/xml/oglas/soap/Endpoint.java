package rs.xml.oglas.soap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Slika;
import rs.xml.oglas.service.OglasService;
import rs.xml.oglas.xsd.PostOglasRequest;
import rs.xml.oglas.xsd.PostOglasResponse;


@org.springframework.ws.server.endpoint.annotation.Endpoint
public class Endpoint {
	
	@Autowired
	OglasService oglasService;
	
	private static final String NAMESPACE_URI = "http://xml.rs/oglas/xsd";
	
	@Autowired
	public Endpoint () {
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "postOglasRequest")
	@ResponsePayload
	public PostOglasResponse postOglas(@RequestPayload PostOglasRequest request) {
		System.out.println("*** postOglas > " + request.getOglas().getMarka() + ": " + request.getOglas().getModel());
		PostOglasResponse response = new PostOglasResponse();
		
		Oglas oglas = new Oglas();
		rs.xml.oglas.xsd.Oglas oglasXSD = request.getOglas();
		oglas.setMesto(oglasXSD.getMesto());
		oglas.setMarka(oglasXSD.getMarka());
		oglas.setModel(oglasXSD.getModel());
		oglas.setGorivo(oglasXSD.getGorivo());
		oglas.setMenjac(oglasXSD.getMenjac());
		oglas.setKlasa(oglasXSD.getKlasa());
		oglas.setCena(oglasXSD.getCena());
		oglas.setCenovnik(null);
		oglas.setKilometraza(oglasXSD.getKilometraza());
		oglas.setPlaniranaKilometraza(oglasXSD.getPlaniranaKilometraza());
		oglas.setSedistaZaDecu(oglasXSD.getSedistaZaDecu());
		
		
		for(rs.xml.oglas.xsd.Slika slikaXSD: oglasXSD.getSlike()) {			
			Slika slika = new Slika();
			slika.setOglas(oglas);
			slika.setSlika(slikaXSD.getSlika());
			oglas.getSlike().add(slika);
		}
		
		oglas.setOsiguranje(oglasXSD.isOsiguranje());
		oglas.setUsername(oglasXSD.getUsername());

		java.sql.Date dateOdToSave = new java.sql.Date(oglasXSD.getOdDate());
		oglas.setOd(dateOdToSave);
		
		java.sql.Date dateDoToSave = new java.sql.Date(oglasXSD.getDoDate());
		oglas.setDo(dateDoToSave);
		
		oglas.setDeleted(false);			
		
		
		oglas = oglasService.save(oglas);
		if(oglas == null) {
			response.setSuccess(false);
		} else {
			response.setSuccess(true);
		}
		
		return response;
	}
	
}
