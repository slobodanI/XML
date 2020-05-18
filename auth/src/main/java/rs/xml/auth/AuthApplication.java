package rs.xml.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class AuthApplication {
	
	@RequestMapping("/health")
    public String home() {
        return "Hello world from auth";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
