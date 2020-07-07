package rs.xml.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import rs.xml.agent.util.UtilClass;

@EnableScheduling
@SpringBootApplication
public class AgentApplication {
	
	@Bean
	public UtilClass utilClass() {
		return new UtilClass();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AgentApplication.class, args);
	}

}
