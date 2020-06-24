package rs.xml.oglas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "chat")
public interface ChatClient {
	
	@PostMapping("/chat")
    ChatDTO postChat(@RequestBody ChatNewDTO chatNewDTO,
    				 @RequestHeader("username") String username,
    				 @RequestHeader("permissions") String permisije);
	
}
