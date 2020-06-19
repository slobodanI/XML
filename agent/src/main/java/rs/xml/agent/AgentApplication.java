package rs.xml.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.xml.agent.util.UtilClass;

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
