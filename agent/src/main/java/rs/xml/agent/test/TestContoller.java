package rs.xml.agent.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Optional;

import rs.xml.agent.model.Slika;

@RestController
public class TestContoller {
	
	@Autowired
	private SlikaRepository slikaRepo;
	
	@PostMapping("/slika")
	public ResponseEntity<String> addSlika(@RequestBody slikaDTO slikaDTO) {		
		//KADA UPISUJES U BAZU SKLONI 'data:image/jpeg;base64,' a kad vracas sliku dodaj 'data:image/jpeg;base64,'
		
		//System.out.println("SRC SLIKE :"+slikaDTO.getPodaci()); // data:image/jpeg;base64,/9j/..... split na ,
		String split[] = slikaDTO.getPodaci().split(",");
		slikaDTO.setPodaci(split[1]);
		
		byte[] imageByte;
		Decoder decoder = Base64.getDecoder();
        imageByte = decoder.decode(slikaDTO.getPodaci());
		
		Slika slika = new Slika();
		slika.setSlika(imageByte);
		
		slikaRepo.save(slika);
		
		return new ResponseEntity<>("data:image/jpeg;base64," + split[1] , HttpStatus.CREATED);
	}
	
	@GetMapping("/slika")
	public ResponseEntity<String> addSlika() {			
		Slika slika = slikaRepo.findById(Long.valueOf(1)).orElseGet(null);
		
		String imageString;
		
		Encoder encoder= Base64.getEncoder();
		imageString = encoder.encodeToString(slika.getSlika());
		
		//radi i ako se kaze data:image/png
		return new ResponseEntity<>("data:image/jpeg;base64," + imageString , HttpStatus.CREATED);
	}
}
