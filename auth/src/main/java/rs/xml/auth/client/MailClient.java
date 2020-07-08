package rs.xml.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import rs.xml.auth.dto.MailDTO;

@FeignClient(name = "mail-producer")
public interface MailClient {

	@PostMapping("/mail")
    String sendMail(@RequestBody MailDTO MailDTO);

}
