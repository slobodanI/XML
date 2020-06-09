package rs.xml.oglas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class OglasApplication {
	
	@RequestMapping("/health")
    public String home() {
        return "Hello world";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(OglasApplication.class, args);
	}

}
