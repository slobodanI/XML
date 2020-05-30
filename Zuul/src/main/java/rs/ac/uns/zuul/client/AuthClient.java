package rs.ac.uns.zuul.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth")
public interface AuthClient {
	
	@GetMapping("/check/{token}")
    String getPermissions(@PathVariable("token") String token);
	
	@GetMapping("/check/{token}/username")
	String getUsername(@PathVariable("token") String token);
	
}
