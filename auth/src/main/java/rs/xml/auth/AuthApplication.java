package rs.xml.auth;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@SpringBootApplication
public class AuthApplication {
	
	@RequestMapping("/health")
    public String home() {
        return "Hello world from auth";
    }
	
//	@Bean
//	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
//		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
//		System.setProperty("javax.net.ssl.keyStore", "src/main/resources/auth.jks");
//		System.setProperty("javax.net.ssl.keyStorePassword", "password");
//		System.setProperty("javax.net.ssl.trustStore", "src/main/resources/auth.jks");
//		System.setProperty("javax.net.ssl.trustStorePassword", "password");
//		EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
//		builder.withClientName("auth");
////		builder.withSystemSSLConfiguration();
//		builder.withMaxTotalConnections(10);
//		builder.withMaxConnectionsPerHost(10);
//		args.setEurekaJerseyClient(builder.build());
//		return args;
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
