package rs.xml.izvestaj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableFeignClients
@SpringBootApplication
public class IzvestajApplication {
	
	@RequestMapping("/health")
    public String home() {
        return "Hello world";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(IzvestajApplication.class, args);
	}

}
