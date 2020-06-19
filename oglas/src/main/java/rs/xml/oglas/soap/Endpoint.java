package rs.xml.oglas.soap;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Slika;
import rs.xml.oglas.model.ZahtevStatus;
import rs.xml.oglas.service.OcenaService;
import rs.xml.oglas.service.OglasService;
import rs.xml.oglas.service.ZahtevService;
import rs.xml.oglas.xsd.OglasUZahtevu;
import rs.xml.oglas.xsd.PostOcenaRequest;
import rs.xml.oglas.xsd.PostOcenaResponse;
import rs.xml.oglas.xsd.PostOglasRequest;
import rs.xml.oglas.xsd.PostOglasResponse;
import rs.xml.oglas.xsd.PostZahtevRequest;
import rs.xml.oglas.xsd.PostZahtevResponse;


@org.springframework.ws.server.endpoint.annotation.Endpoint
public class Endpoint {
	
	@Autowired
	OglasService oglasService;
	
	@Autowired
	ZahtevService zahtevService;
	
	@Autowired
	OcenaService ocenaService;
	
	private static final String NAMESPACE_URI = "http://xml.rs/oglas/xsd";
	
	@Autowired
	public Endpoint () {
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "postOglasRequest")
	@ResponsePayload
	public PostOglasResponse postOglas(@RequestPayload PostOglasRequest request) {
		System.out.println("*** postOglas > " + request.getOglas().getMarka() + ": " + request.getOglas().getModel());
		PostOglasResponse response = new PostOglasResponse();
		
		rs.xml.oglas.model.Oglas oglas = new Oglas();
		rs.xml.oglas.xsd.Oglas oglasXSD = request.getOglas();
		oglas.setOid(oglasXSD.getOid());
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
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "postZahtevRequest")
	@ResponsePayload
	public PostZahtevResponse postZahtev(@RequestPayload PostZahtevRequest request) {
		System.out.println("*** postZahtev > " + request.getZahtev().getPodnosilacUsername() + " za " + request.getZahtev().getUsername());
		PostZahtevResponse response = new PostZahtevResponse();
		
		rs.xml.oglas.model.Zahtev zahtev = new rs.xml.oglas.model.Zahtev();
		rs.xml.oglas.xsd.Zahtev zahtevXSD = request.getZahtev();
		
		zahtev.setZid(zahtevXSD.getZid());
		zahtev.setChatId(null);
		java.sql.Date dateOdToSave = new java.sql.Date(zahtevXSD.getOdDate());
		zahtev.setOd(dateOdToSave);	
		java.sql.Date dateDoToSave = new java.sql.Date(zahtevXSD.getDoDate());
		zahtev.setDo(dateDoToSave);
		zahtev.setIzvestaj(zahtevXSD.isIzvestaj());
		zahtev.setOcenjen(zahtevXSD.isOcenjen());
		
		rs.xml.oglas.model.Oglas oglas = new rs.xml.oglas.model.Oglas();
		for(OglasUZahtevu oglasUZahtevu: zahtevXSD.getOglasi()) {
			oglas = oglasService.findOneByOid(oglasUZahtevu.getOid());
			if(oglas == null) {
				continue;
			}
			zahtev.getOglasi().add(oglas);
		}
		
		zahtev.setPodnosilacUsername(zahtevXSD.getPodnosilacUsername());
		zahtev.setUsername(zahtevXSD.getUsername());
		
		if(zahtevXSD.getStatus().equals(rs.xml.oglas.xsd.ZahtevStatus.CANCELED)) {
			zahtev.setStatus(rs.xml.oglas.model.ZahtevStatus.CANCELED);
		} else if(zahtevXSD.getStatus().equals(rs.xml.oglas.xsd.ZahtevStatus.PENDING)) {
			zahtev.setStatus(rs.xml.oglas.model.ZahtevStatus.PENDING);
		} else if(zahtevXSD.getStatus().equals(rs.xml.oglas.xsd.ZahtevStatus.PAID)) {
			zahtev.setStatus(rs.xml.oglas.model.ZahtevStatus.PAID);
		}
		
		java.sql.Date dateVremePodnosenja = new java.sql.Date(zahtevXSD.getVremePodnosenja());
		zahtev.setVremePodnosenja(dateVremePodnosenja);
		
//		System.out.println("---zahtev getZid:" + zahtev.getZid());
//		System.out.println("---zahtev getUsername:" + zahtev.getUsername());
//		System.out.println("---zahtev getPodnosilacUsername:" + zahtev.getPodnosilacUsername());
//		System.out.println("---zahtev getId:" + zahtev.getId());
//		System.out.println("---zahtev getOd:" + zahtev.getOd());
//		System.out.println("---zahtev getDo:" + zahtev.getDo());
//		System.out.println("---zahtev getStatus:" + zahtev.getStatus());
//		System.out.println("---zahtev getVremePodnosenja:" + zahtev.getVremePodnosenja());
//		System.out.println("---zahtev getOglasi---");
//		for(Oglas o: zahtev.getOglasi()) {
//			System.out.println("---zahtev > oglas getOid:" + o.getOid());
//			System.out.println("---zahtev > oglas getId:" + o.getId());
//			System.out.println("---zahtev > oglas getMarka + getModel:" + o.getMarka() + ": " + o.getModel());
//		}
//		System.out.println("---zahtev getOglasi---");
		zahtev = zahtevService.save(zahtev);
		if(zahtev == null) {
			response.setSuccess(false);
		} else {
			response.setSuccess(true);
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "postOcenaRequest")
	@ResponsePayload
	public PostOcenaResponse postOcena(@RequestPayload PostOcenaRequest request) {
		System.out.println("*** postOcena > " + request.getOcena().getUsernameKo() + " za " + request.getOcena().getUsernameKoga() + ", ocena: " + request.getOcena().getOcena());
		PostOcenaResponse response = new PostOcenaResponse();
		
		rs.xml.oglas.model.Ocena ocena = new rs.xml.oglas.model.Ocena();
		rs.xml.oglas.xsd.Ocena ocenaXSD = request.getOcena();
		
		if(ocenaXSD.getApproved().equals(rs.xml.oglas.xsd.OcenaApprovedStatus.APPROVED)) {
			ocena.setApproved(rs.xml.oglas.model.OcenaApprovedStatus.APPROVED);
		} else if(ocenaXSD.getApproved().equals(rs.xml.oglas.xsd.OcenaApprovedStatus.DENIED)) {
			ocena.setApproved(rs.xml.oglas.model.OcenaApprovedStatus.DENIED);
		} else if(ocenaXSD.getApproved().equals(rs.xml.oglas.xsd.OcenaApprovedStatus.UNKNOWN)) {
			ocena.setApproved(rs.xml.oglas.model.OcenaApprovedStatus.UNKNOWN);
		}
		
		ocena.setDeleted(ocenaXSD.isDeleted());
		ocena.setKomentar(ocenaXSD.getKomentar());
		ocena.setOcena(ocenaXSD.getOcena());
		ocena.setOdgovor(ocenaXSD.getOdgovor());
		rs.xml.oglas.model.Oglas oglas = oglasService.findOneByOid(ocenaXSD.getOglasId());
		ocena.setOglas(oglas);
		ocena.setOid(ocenaXSD.getOid());
		ocena.setUsernameKo(ocenaXSD.getUsernameKo());
		ocena.setUsernameKoga(ocenaXSD.getUsernameKoga());
		rs.xml.oglas.model.Zahtev zahtev = zahtevService.findOneByZid(ocenaXSD.getZahtevId());
		ocena.setZahtevId(zahtev.getId());
		
		ocena = ocenaService.save(ocena);
		if(ocena == null) {
			response.setSuccess(false);
		} else {
			response.setSuccess(true);
		}
		
		return response;
	}
	
	
}
