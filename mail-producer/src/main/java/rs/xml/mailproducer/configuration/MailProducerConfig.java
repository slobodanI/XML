package rs.xml.mailproducer.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailProducerConfig {
	
	@Value("mail-queue")
    private String queue;

    @Bean
    Queue queue(){
        return new Queue(queue, false);
    }

    /*
     * Registrujemo bean koji ce sluziti za konekciju na RabbitMQ
     * gde se mi u primeru kacimo u lokalu.
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername("zpbtlzju");
        connectionFactory.setPassword("JzrwM8K1auo_bde4VsNpo6HjMWAJwPZz");
        connectionFactory.setVirtualHost("zpbtlzju");
        connectionFactory.setHost("bee.rmq.cloudamqp.com");
//        connectionFactory.setPort();
        return connectionFactory;
    }

	public String getQueue() {
		return queue;
	}  
    
}
