package rs.xml.oglas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "chat")
public interface ChatClient {
	
	@PostMapping("/chat")
    ChatDTO postChat(@RequestBody ChatNewDTO chatNewDTO);
	
}
