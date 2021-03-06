package rs.xml.agent.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		// ovo koristi ako ce se uvek generisati klase na osnovu wsdl-a
//    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//    // this package must match the package in the <generatePackage> specified in
//    // pom.xml
//    marshaller.setContextPath("com.example.consumingwebservice.soap");
//    return marshaller;

		// ovo koristi ako hoces lepo da vidis izgenerisane klase u package-u
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		String[] packagesToScan = { "rs.xml.agent.xsd" };
		marshaller.setPackagesToScan(packagesToScan);

		return marshaller;
	}

//  @Bean
//  public CountryC countryClient(Jaxb2Marshaller marshaller) {
//    OglasClient client = new OglasClient();
//    client.setDefaultUri("http://localhost:8080/ws");
//    client.setMarshaller(marshaller);
//    client.setUnmarshaller(marshaller);
//    return client;
//  }

	@Bean
	public OglasClient oglasClient(Jaxb2Marshaller marshaller) {
		OglasClient client = new OglasClient();
		client.setDefaultUri("http://localhost:8080/oglasi/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public ZahtevClient zahtevClient(Jaxb2Marshaller marshaller) {
		ZahtevClient client = new ZahtevClient();
		client.setDefaultUri("http://localhost:8080/oglasi/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public OcenaClient ocenaClient(Jaxb2Marshaller marshaller) {
		OcenaClient client = new OcenaClient();
		client.setDefaultUri("http://localhost:8080/oglasi/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public IzvestajClient izvestajClient(Jaxb2Marshaller marshaller) {
		IzvestajClient client = new IzvestajClient();
		client.setDefaultUri("http://localhost:8080/oglasi/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public CenovnikClient cenovnikClient(Jaxb2Marshaller marshaller) {
		CenovnikClient client = new CenovnikClient();
		client.setDefaultUri("http://localhost:8080/oglasi/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public ChatClient chatClient(Jaxb2Marshaller marshaller) {
		ChatClient client = new ChatClient();
		client.setDefaultUri("http://localhost:8080/chat/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public EverythingClient everythingClient(Jaxb2Marshaller marshaller) {

		EverythingClient client = new EverythingClient();
		client.setDefaultUri("http://localhost:8080/oglasi/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}