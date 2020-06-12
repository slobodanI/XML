package rs.xml.izvestaj.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "oglasi")
public interface ZahtevClient {

	@GetMapping("/oglas/{oid}")
	OglasDTO getOglas(@PathVariable("oid") Long oid);
	
	@GetMapping("/zahtev/{zid}")
	ZahtevDTO getZahtev(@PathVariable("zid") Long zid);
	
}
