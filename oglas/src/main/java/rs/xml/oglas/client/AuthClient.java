package rs.xml.oglas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth")
public interface AuthClient {
	
	@PutMapping("/user/addDebt")
	UserDTO putUserDebt(@RequestBody UpdateUserDebtDTO userUpdateDTO,
			 @RequestHeader("Auth") String token);
	
	@PutMapping("/user/addCanceledNumber")
	UserDTO putUserCanceledNumber(@RequestBody UpdateUserDebtDTO userUpdateDTO,
			 @RequestHeader("Auth") String token);
	
	@GetMapping("/user/me")
	UserDTO getUser(@RequestHeader("Auth") String token);
	
	
}
