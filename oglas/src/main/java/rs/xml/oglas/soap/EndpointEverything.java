package rs.xml.oglas.soap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.ZahtevStatus;
import rs.xml.oglas.service.IzvestajService;
import rs.xml.oglas.service.OcenaService;
import rs.xml.oglas.service.OglasService;
import rs.xml.oglas.service.ZahtevService;
import rs.xml.oglas.xsdgenerated.GetEverythingRequest;
import rs.xml.oglas.xsdgenerated.GetEverythingResponse;
import rs.xml.oglas.xsdgenerated.OglasUZahtevu;

@Endpoint
public class EndpointEverything {
	
	@Autowired
	OglasService oglasService;
	
	@Autowired
	ZahtevService zahtevService;
	
	@Autowired
	IzvestajService izvestajService;
	
	@Autowired
	OcenaService ocenaService;
	
	private static final String NAMESPACE_URI = "http://xml.rs/oglas/xsd";
	
	@Autowired
	public EndpointEverything () {
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEverythingRequest")
	@ResponsePayload
	public GetEverythingResponse getEverything(@RequestPayload GetEverythingRequest request) {
		GetEverythingResponse response = new GetEverythingResponse();
		
		System.out.println("###ENDPOINT > getEverything > start");
		
		System.out.println("###ENDPOINT > getEverything > FOR(findMyOglasi) > start");
		for(rs.xml.oglas.model.Oglas oglas: oglasService.findMyOglasi(request.getUsername())) {
//			System.out.println("###ENDPOINT > getEverything > FOR(findMyOglasi) > oglas: " + oglas.getMarka() + ": " + oglas.getModel());
//			System.out.println("###ENDPOINT > getEverything > FOR(findMyOglasi) > oglasi response: " + response.getOglasi());
			response.getOglasi().add(convertModelOglasToXsdOglas(oglas));
		}
		System.out.println("###ENDPOINT > getEverything > FOR(findMyOglasi) > finnish");
		
		System.out.println("###ENDPOINT > getEverything > FOR(findZahteviForMe) > start");
		for(rs.xml.oglas.model.Zahtev zahtev: zahtevService.findZahteviForMe(request.getUsername())) {
			System.out.println("###ENDPOINT > getEverything > FOR(findZahteviForMe) > zahtev: " + zahtev.getPodnosilacUsername() + " za " + zahtev.getUsername());
			response.getZahtevi().add(convertModelZahtevToXsdZahtev(zahtev));
		}
		System.out.println("###ENDPOINT > getEverything > FOR(findZahteviForMe) > finnish");
		
		System.out.println("###ENDPOINT > getEverything > FOR(findMyIzvestaji) > start");
		for(rs.xml.oglas.model.Izvestaj izvestaj : izvestajService.findMyIzvestaji(request.getUsername())) {
			System.out.println("--------------->"+izvestaj.getTekst());
			response.getIzvestaji().add(convertModelIzvestajToXsdIzvestaj(izvestaj));
		}
		
		System.out.println("###ENDPOINT > getEverything > FOR(findMyIzvestaji) > finnish");

		System.out.println("###ENDPOINT > getEverything > FOR(findMyOcene) > start");
		for(rs.xml.oglas.model.Ocena ocena : ocenaService.findOceneForMe(request.getUsername())) {
			response.getOcene().add(convertModelOcenaToXsdOcena(ocena));
		}
		System.out.println("###ENDPOINT > getEverything > FOR(findMyOcene) > finnish");
		
//		
		System.out.println("###ENDPOINT > getEverything > " + response.toString());
		return response;
	}
	
	private rs.xml.oglas.xsdgenerated.Oglas convertModelOglasToXsdOglas(rs.xml.oglas.model.Oglas oglas) {
		rs.xml.oglas.xsdgenerated.Oglas oglasXSD = new rs.xml.oglas.xsdgenerated.Oglas();
		oglasXSD.setOid(oglas.getOid());
		oglasXSD.setMesto(oglas.getMesto());
		oglasXSD.setMarka(oglas.getMarka());
		oglasXSD.setModel(oglas.getModel());
		oglasXSD.setGorivo(oglas.getGorivo());
		oglasXSD.setMenjac(oglas.getMenjac());
		oglasXSD.setKlasa(oglas.getKlasa());
		oglasXSD.setCena(oglas.getCena());
		
//		System.out.println("### CONVERTOVANJE 1 > oglasXSD: " + oglasXSD);
//		oglas.setCenovnik(null);
		oglasXSD.setKilometraza(oglas.getKilometraza());
		oglasXSD.setPlaniranaKilometraza(oglas.getPlaniranaKilometraza());
		oglasXSD.setSedistaZaDecu(oglas.getSedistaZaDecu());
		
//		System.out.println("### CONVERTOVANJE 2 > oglasXSD: " + oglasXSD);
//		System.out.println("### CONVERTOVANJE > oglas.getSlike(): " + oglas.getSlike() );
		for(rs.xml.oglas.model.Slika slika: oglas.getSlike()) {	
//			System.out.println("### CONVERTOVANJE > FOR(SLIKA) > new slika" );
			rs.xml.oglas.xsdgenerated.Slika slikaXSD = new rs.xml.oglas.xsdgenerated.Slika();
			slikaXSD.setSlika(slika.getSlika());
//			System.out.println("### CONVERTOVANJE > FOR(SLIKA) > slikaXSD: " + slikaXSD);
			oglasXSD.getSlike().add(slikaXSD);
//			System.out.println("### CONVERTOVANJE > FOR(SLIKA) > oglasXSD slike: " + oglasXSD.getSlike());
		}
		
//		System.out.println("### CONVERTOVANJE 3 > oglasXSD: " + oglasXSD);
		
		oglasXSD.setOsiguranje(oglas.isOsiguranje());
		oglasXSD.setUsername(oglas.getUsername());
//		System.out.println("### CONVERTOVANJE 4 > oglasXSD: " + oglasXSD);
		oglasXSD.setOdDate(oglas.getOd().getTime());
		oglasXSD.setDoDate(oglas.getDo().getTime());
		
		oglasXSD.setDeleted(oglas.isDeleted());
//		 System.out.println("### uspesno konvertovanje oglasa!");
		return oglasXSD;
	}
	
	private rs.xml.oglas.xsdgenerated.Zahtev convertModelZahtevToXsdZahtev(rs.xml.oglas.model.Zahtev zahtev) {
		rs.xml.oglas.xsdgenerated.Zahtev zahtevXSD = new rs.xml.oglas.xsdgenerated.Zahtev();
		System.out.println("### CONVERTOVANJE ZAHTEV 1 > zahtevXSD: " + zahtevXSD);
		zahtevXSD.setZid(zahtev.getZid());
		zahtevXSD.setIzvestaj(zahtev.isIzvestaj());
		zahtevXSD.setOcenjen(zahtev.isOcenjen());
		zahtevXSD.setOdDate(zahtev.getOd().getTime());
		zahtevXSD.setDoDate(zahtev.getDo().getTime());
		zahtevXSD.setPodnosilacUsername(zahtev.getPodnosilacUsername());
		zahtevXSD.setUsername(zahtev.getUsername());
		System.out.println("### CONVERTOVANJE ZAHTEV 2 > zahtevXSD: " + zahtevXSD);
		if(zahtev.getStatus().equals(ZahtevStatus.CANCELED)) {
			zahtevXSD.setStatus(rs.xml.oglas.xsdgenerated.ZahtevStatus.CANCELED);
		} else if(zahtev.getStatus().equals(ZahtevStatus.PENDING)) {
			zahtevXSD.setStatus(rs.xml.oglas.xsdgenerated.ZahtevStatus.PENDING);
		} else if(zahtev.getStatus().equals(ZahtevStatus.PAID)) {
			zahtevXSD.setStatus(rs.xml.oglas.xsdgenerated.ZahtevStatus.PAID);
		}
		System.out.println("### CONVERTOVANJE ZAHTEV 3 > zahtevXSD: " + zahtevXSD);
		zahtevXSD.setVremePodnosenja(zahtev.getVremePodnosenja().getTime());
		List<OglasUZahtevu> oglasUZahtevuList = new ArrayList<OglasUZahtevu>();
		System.out.println("### CONVERTOVANJE ZAHTEV 4 > zahtevXSD: " + zahtevXSD);
//		System.out.println("### CONVERTOVANJE ZAHTEV 4 > zahtev.getOglasi(): " + zahtev.getOglasi()); // OVDE PUCA, ne znam zasto...
//		for(Oglas o: zahtev.getOglasi()) {
//			OglasUZahtevu oglasUZahtevu = new OglasUZahtevu();
//			oglasUZahtevu.setOid(o.getOid());
//			oglasUZahtevuList.add(oglasUZahtevu);
//		}
		for(Long oglasId: oglasService.getOglasiFromZahtev(zahtev.getId())) {
			OglasUZahtevu oglasUZahtevu = new OglasUZahtevu();
			oglasUZahtevu.setOid(oglasService.findOne(oglasId).getOid());
			oglasUZahtevuList.add(oglasUZahtevu);
		}
		System.out.println("### CONVERTOVANJE ZAHTEV 5 > zahtevXSD: " + zahtevXSD);
		zahtevXSD.getOglasi().addAll(oglasUZahtevuList);
		
		System.out.println("### uspesno konvertovanje zahteva!");
		
		return zahtevXSD;
	}
	
	private rs.xml.oglas.xsdgenerated.Izvestaj convertModelIzvestajToXsdIzvestaj (rs.xml.oglas.model.Izvestaj izvestaj){
		rs.xml.oglas.xsdgenerated.Izvestaj izvestajXSD =  new rs.xml.oglas.xsdgenerated.Izvestaj();
		System.out.println("### CONVERTOVANJE IZVESTAJ 1 > zahtevXSD: " + izvestajXSD);
		izvestajXSD.setIid(izvestaj.getIid());
		String oglasId = oglasService.findOne(izvestaj.getOglasId()).getOid();
		izvestajXSD.setOglasId(oglasId);
		System.out.println("### CONVERTOVANJE IZVESTAJ 2 > zahtevXSD: " + izvestajXSD);
		String zahtevId = zahtevService.findOne(izvestaj.getZahtevId()).getZid();
		izvestajXSD.setZahtevId(zahtevId);
		System.out.println("### CONVERTOVANJE IZVESTAJ 3 > zahtevXSD: " + izvestajXSD);
		izvestajXSD.setPredjeniKilometri(izvestaj.getPredjeniKilometri());
		izvestajXSD.setTekst(izvestaj.getTekst());
		System.out.println("### uspesno konvertovanje izvestaja!");
		
		return izvestajXSD;
	}
	
	private rs.xml.oglas.xsdgenerated.Ocena convertModelOcenaToXsdOcena(rs.xml.oglas.model.Ocena ocena){
		rs.xml.oglas.xsdgenerated.Ocena ocenaXSD = new rs.xml.oglas.xsdgenerated.Ocena();
		System.out.println("### CONVERTOVANJE OCENA 1 > ocenaXSD: " + ocenaXSD);
		if(ocena.getApproved().equals(rs.xml.oglas.model.OcenaApprovedStatus.APPROVED)) {
			ocenaXSD.setApproved(rs.xml.oglas.xsdgenerated.OcenaApprovedStatus.APPROVED);
		} else if(ocena.getApproved().equals(rs.xml.oglas.model.OcenaApprovedStatus.DENIED)) {
			ocenaXSD.setApproved(rs.xml.oglas.xsdgenerated.OcenaApprovedStatus.DENIED);
		} else if(ocena.getApproved().equals(rs.xml.oglas.model.OcenaApprovedStatus.UNKNOWN)) {
			ocenaXSD.setApproved(rs.xml.oglas.xsdgenerated.OcenaApprovedStatus.UNKNOWN);
		}
		System.out.println("### CONVERTOVANJE OCENA 2 > ocenaXSD: " + ocenaXSD);
		ocenaXSD.setDeleted(ocena.isDeleted());
		ocenaXSD.setKomentar(ocena.getKomentar());
		ocenaXSD.setOcena(ocena.getOcena());
		ocenaXSD.setOdgovor(ocena.getOdgovor());
		Oglas oglas = oglasService.findOne(ocenaService.findOglasFromOcena(ocena.getId()));
		ocenaXSD.setOglasId(oglas.getOid());
		ocenaXSD.setOid(oglas.getOid());
		ocenaXSD.setUsernameKo(ocena.getUsernameKo());
		ocenaXSD.setUsernameKoga(ocena.getUsernameKoga());
		rs.xml.oglas.model.Zahtev zahtev = zahtevService.findOne(ocena.getZahtevId());
		ocenaXSD.setZahtevId(zahtev.getZid());
		System.out.println("### uspesno konvertovanje ocene!");
		
		return ocenaXSD;
	}
	
	
	
}
