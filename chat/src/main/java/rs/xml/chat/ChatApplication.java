package rs.xml.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.xml.chat.util.UtilClass;

@SpringBootApplication
public class ChatApplication {
	
	@RequestMapping("/health")
    public String home() {
        return "Hello world";
    }
	
	@Bean
	public UtilClass utilClass() {
		return new UtilClass();
	}	
	
	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

}
