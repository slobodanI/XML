package rs.xml.mailproducer.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rs.xml.mailproducer.dto.MailDTO;

@Component
public class MailProducer {
	
	private static final Logger log = LoggerFactory.getLogger(MailProducer.class);

    /*
     * RabbitTemplate je pomocna klasa koja uproscava sinhronizovani
     * pristup RabbitMQ za slanje i primanje poruka.
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;


    private ObjectMapper objectMapper = new ObjectMapper();

    /*
     * U ovom slucaju routingKey ce biti ime queue.
     * Poruka se salje u exchange (default exchange u ovom primeru) i
     * exchange ce rutirati poruke u pravi queue.
     */
    public void sendTo(String routingkey, MailDTO mailDTO) throws JsonProcessingException {
        log.info("MailProducer > Sending mail...");
        String message = null;
        try {
        	mailDTO.setEmail("psa.isa.usr@gmail.com");
            message = objectMapper.writeValueAsString(mailDTO);
            this.rabbitTemplate.convertAndSend(routingkey, message);
        } catch (JsonProcessingException e) {
        	log.info("MailProducer > JsonProcessingException > did not send mail...");
            e.printStackTrace();
        }
    }
	
}
