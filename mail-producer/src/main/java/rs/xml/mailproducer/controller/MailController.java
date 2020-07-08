package rs.xml.mailproducer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import rs.xml.mailproducer.component.MailProducer;
import rs.xml.mailproducer.configuration.MailProducerConfig;
import rs.xml.mailproducer.dto.MailDTO;

@RestController
public class MailController {

	private static final Logger log = LoggerFactory.getLogger(MailController.class);
	
	@Autowired
	MailProducer mailProducer;
	
	@PostMapping("/mail")
	public ResponseEntity<?> sendMail(@RequestBody MailDTO mailDTO) {
		log.info("MailController > sending mail...");
		
		try {
			mailProducer.sendTo("mail-queue", mailDTO);
		} catch (JsonProcessingException e) {
			log.error("MailController > JsonProcessingException > did not send mail...");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		log.info("MailController > mail sent...");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
