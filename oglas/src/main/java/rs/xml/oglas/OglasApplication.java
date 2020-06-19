package rs.xml.oglas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.xml.oglas.util.UtilClass;


@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class OglasApplication {
	
	@RequestMapping("/health")
    public String home() {
        return "Hello world";
    }
	
	@Bean
	public UtilClass utilClass() {
		return new UtilClass();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(OglasApplication.class, args);
	}

}
