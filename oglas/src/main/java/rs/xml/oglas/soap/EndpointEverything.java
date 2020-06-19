//package rs.xml.oglas.soap;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
//import org.springframework.ws.server.endpoint.annotation.RequestPayload;
//import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
//
//import rs.xml.oglas.model.Oglas;
//import rs.xml.oglas.model.ZahtevStatus;
//import rs.xml.oglas.service.OglasService;
//import rs.xml.oglas.service.ZahtevService;
//import rs.xml.oglas.xsd.GetEverythingRequest;
//import rs.xml.oglas.xsd.GetEverythingResponse;
//import rs.xml.oglas.xsd.OglasUZahtevu;
//
//public class EndpointEverything {
//	
//	@Autowired
//	OglasService oglasService;
//	
//	@Autowired
//	ZahtevService zahtevService;
//		
//	private static final String NAMESPACE_URI = "http://xml.rs/oglas/xsd";
//	
//	@Autowired
//	public EndpointEverything () {
//	}
//	
//	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEverythingRequest")
//	@ResponsePayload
//	public GetEverythingResponse getEverything(@RequestPayload GetEverythingRequest request) {
//		GetEverythingResponse response = new GetEverythingResponse();
//		
//		System.out.println("###ENDPOINT > getEverything > start");
//		int countoglas = 0;
//		int countzahtev = 0;
//		
//		System.out.println("###ENDPOINT > getEverything > FOR(findMyOglasi) > start");
//		for(rs.xml.oglas.model.Oglas oglas: oglasService.findMyOglasi(request.getUsername())) {
//			System.out.println("###ENDPOINT > getEverything > FOR(findMyOglasi) > oglas: " + oglas.getMarka() + ": " + oglas.getModel());
//			System.out.println("###ENDPOINT > getEverything > FOR(findMyOglasi) > oglasi response: " + response.getOglasi());
//			response.getOglasi().add(convertModelOglasToXsdOglas(oglas));
//		}
//		System.out.println("###ENDPOINT > getEverything > FOR(findMyOglasi) > finnish");
//		
////		System.out.println("###ENDPOINT > getEverything > FOR(findZahteviForMe) > start");
////		for(rs.xml.oglas.model.Zahtev zahtev: zahtevService.findZahteviForMe(request.getUsername())) {
////			System.out.println("###ENDPOINT > getEverything > FOR(findZahteviForMe) > zahtev: " + zahtev.getPodnosilacUsername() + " za " + zahtev.getUsername());
////			System.out.println("###ENDPOINT > getEverything > FOR(findZahteviForMe) > zahtev: " + zahtev.getPodnosilacUsername() + " za " + zahtev.getUsername());
////			response.getZahtevi().add(converModelZahtevToXsdZahtev(zahtev));
////		}
////		System.out.println("###ENDPOINT > getEverything > FOR(findZahteviForMe) > finnish");
////		
////		System.out.println("###ENDPOINT > getEverything > " + response.toString());
//		return response;
//	}
//	
//	private rs.xml.oglas.xsd.Oglas convertModelOglasToXsdOglas(rs.xml.oglas.model.Oglas oglas) {
//		rs.xml.oglas.xsd.Oglas oglasXSD = new rs.xml.oglas.xsd.Oglas();
//		oglasXSD.setOid(oglas.getOid());
//		oglasXSD.setMesto(oglas.getMesto());
//		oglasXSD.setMarka(oglas.getMarka());
//		oglasXSD.setModel(oglas.getModel());
//		oglasXSD.setGorivo(oglas.getGorivo());
//		oglasXSD.setMenjac(oglas.getMenjac());
//		oglasXSD.setKlasa(oglas.getKlasa());
//		oglasXSD.setCena(oglas.getCena());
//		
//		System.out.println("### CONVERTOVANJE 1 > oglasXSD: " + oglasXSD);
////		oglas.setCenovnik(null);
//		oglasXSD.setKilometraza(oglas.getKilometraza());
//		oglasXSD.setPlaniranaKilometraza(oglas.getPlaniranaKilometraza());
//		oglasXSD.setSedistaZaDecu(oglas.getSedistaZaDecu());
//		
//		System.out.println("### CONVERTOVANJE 2 > oglasXSD: " + oglasXSD);
//		System.out.println("### CONVERTOVANJE > oglas.getSlike(): " + oglas.getSlike() );
//		for(rs.xml.oglas.model.Slika slika: oglas.getSlike()) {	
//			System.out.println("### CONVERTOVANJE > FOR(SLIKA) > new slika" );
//			rs.xml.oglas.xsd.Slika slikaXSD = new rs.xml.oglas.xsd.Slika();
//			slikaXSD.setSlika(slika.getSlika());
//			System.out.println("### CONVERTOVANJE > FOR(SLIKA) > slikaXSD: " + slikaXSD);
//			oglasXSD.getSlike().add(slikaXSD);
//			System.out.println("### CONVERTOVANJE > FOR(SLIKA) > oglasXSD slike: " + oglasXSD.getSlike());
//		}
//		
//		System.out.println("### CONVERTOVANJE 3 > oglasXSD: " + oglasXSD);
//		
//		oglasXSD.setOsiguranje(oglas.isOsiguranje());
//		oglasXSD.setUsername(oglas.getUsername());
//		System.out.println("### CONVERTOVANJE 4 > oglasXSD: " + oglasXSD);
//		oglasXSD.setOdDate(oglas.getOd().getTime());
//		oglasXSD.setDoDate(oglas.getDo().getTime());
//		
//		oglasXSD.setDeleted(oglas.isDeleted());
//		 System.out.println("### uspesno konvertovanje oglasa!");
//		return oglasXSD;
//	}
//	
//	private rs.xml.oglas.xsd.Zahtev converModelZahtevToXsdZahtev(rs.xml.oglas.model.Zahtev zahtev) {
//		rs.xml.oglas.xsd.Zahtev zahtevXSD = new rs.xml.oglas.xsd.Zahtev();
//		System.out.println("### CONVERTOVANJE ZAHTEV 1 > zahtevXSD: " + zahtevXSD);
////		zahtevXSD.setZid(zahtev.getz);
//		zahtevXSD.setIzvestaj(zahtev.isIzvestaj());
//		zahtevXSD.setOcenjen(zahtev.isOcenjen());
//		zahtevXSD.setOdDate(zahtev.getOd().getTime());
//		zahtevXSD.setDoDate(zahtev.getDo().getTime());
//		zahtevXSD.setPodnosilacUsername(zahtev.getPodnosilacUsername());
//		zahtevXSD.setUsername(zahtev.getUsername());
//		System.out.println("### CONVERTOVANJE ZAHTEV 2 > zahtevXSD: " + zahtevXSD);
//		if(zahtev.getStatus().equals(ZahtevStatus.CANCELED)) {
//			zahtevXSD.setStatus(rs.xml.oglas.xsd.ZahtevStatus.CANCELED);
//		} else if(zahtev.getStatus().equals(ZahtevStatus.PENDING)) {
//			zahtevXSD.setStatus(rs.xml.oglas.xsd.ZahtevStatus.PENDING);
//		} else if(zahtev.getStatus().equals(ZahtevStatus.PAID)) {
//			zahtevXSD.setStatus(rs.xml.oglas.xsd.ZahtevStatus.PAID);
//		}
//		System.out.println("### CONVERTOVANJE ZAHTEV 3 > zahtevXSD: " + zahtevXSD);
//		zahtevXSD.setVremePodnosenja(zahtev.getVremePodnosenja().getTime());
//		List<OglasUZahtevu> oglasUZahtevuList = new ArrayList<OglasUZahtevu>();
//		System.out.println("### CONVERTOVANJE ZAHTEV 4 > zahtevXSD: " + zahtevXSD);
//		System.out.println("### CONVERTOVANJE ZAHTEV 4 > zahtev.getOglasi(): " + zahtev.getOglasi());
//		for(Oglas o: zahtev.getOglasi()) {
//			OglasUZahtevu oglasUZahtevu = new OglasUZahtevu();
//			oglasUZahtevu.setOid(o.getOid());
//			oglasUZahtevuList.add(oglasUZahtevu);
//		}
//		System.out.println("### CONVERTOVANJE ZAHTEV 5 > zahtevXSD: " + zahtevXSD);
//		zahtevXSD.getOglasi().addAll(oglasUZahtevuList);
//		
//		System.out.println("### uspesno konvertovanje zahteva!");
//		
//		return zahtevXSD;
//	}
//	
//}
