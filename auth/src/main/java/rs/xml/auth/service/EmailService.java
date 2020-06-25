package rs.xml.auth.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import rs.xml.auth.dto.EmailDTO;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	@Async
	public void sendNotificaitionAsync(EmailDTO email) throws MailException, InterruptedException, MessagingException {

		System.out.println("Slanje emaila...");
		
		MimeMessage message=javaMailSender.createMimeMessage();
	    MimeMessageHelper helper;
	    helper=new MimeMessageHelper(message,true);
	    helper.setTo("psa.isa.usr@gmail.com");
	    helper.setFrom(env.getProperty("spring.mail.username"));
	    helper.setSubject(email.getNaslov());
	    helper.setText(email.getTelo(), true);
	    javaMailSender.send(message);

		System.out.println("Email poslat!");
	}

}
