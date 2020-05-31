package rs.xml.oglas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sifrarnik")
public interface SifrarnikClient {
	
	@GetMapping("/marka/{mid}")
    MarkaDTO getMarka(@PathVariable("mid") Long mid);
	
	@GetMapping("/model/{mid}")
    ModelDTO getModel(@PathVariable("mid") Long mid);
	
	@GetMapping("/gorivo/{gid}")
    GorivoDTO getGorivo(@PathVariable("gid") Long gid);
	
	@GetMapping("/klasa/{kid}")
    KlasaDTO getKlasa(@PathVariable("kid") Long kid);
	
	@GetMapping("/mesto/{mid}")
    MestoDTO getMesto(@PathVariable("mid") Long mid);
	
	@GetMapping("/menjac/{mid}")
    MenjacDTO getMenjac(@PathVariable("mid") Long mid);
	
}
