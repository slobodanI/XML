package rs.xml.mailproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class MailProducerApplication {

	@RequestMapping("/health")
    public String home() {
        return "Hello world from mail service";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(MailProducerApplication.class, args);
	}

}
