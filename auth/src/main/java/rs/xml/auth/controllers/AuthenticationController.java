package rs.xml.auth.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import rs.xml.auth.client.MailClient;
import rs.xml.auth.dto.AgentRegisterDTO;
import rs.xml.auth.dto.AgentUpdateDTO;
import rs.xml.auth.dto.EmailDTO;
import rs.xml.auth.dto.MailDTO;
import rs.xml.auth.dto.UpdateUserDebtDTO;
import rs.xml.auth.dto.UserDTO;
import rs.xml.auth.dto.UserUpdateDTO;
import rs.xml.auth.model.User;
import rs.xml.auth.model.UserRegisterRequestDTO;
import rs.xml.auth.model.UserTokenState;
import rs.xml.auth.security.JwtAuthenticationRequest;
import rs.xml.auth.security.TokenUtils;
import rs.xml.auth.service.CustomUserDetailsService;
import rs.xml.auth.service.EmailService;
import rs.xml.auth.service.UserService;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private MailClient mailClient;
	
	@Autowired
	HttpServletRequest request;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody @Valid JwtAuthenticationRequest authenticationRequest, HttpServletResponse response)
			throws AuthenticationException, IOException {

		User u = (User) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword() + u.getSalt()));

		// Ubaci username + password u kontext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token
		User user = (User) authentication.getPrincipal();
//		System.out.println("*********USER: ");
		String permisije = "";
		int duzina = user.getAuthorities().size();
		int counter = 0;
		for (GrantedAuthority grantAuth : user.getAuthorities()) {
//			System.out.println("AUTH: " + grantAuth.getAuthority() );
			counter++;
			if (duzina == counter) {
				permisije += grantAuth.getAuthority();
			} else {
				permisije += grantAuth.getAuthority() + "|";
			}

		}
//		System.out.println(">>>Permisije:" + permisije);
		String jwt = tokenUtils.generateToken(user.getUsername(), permisije);
		int expiresIn = tokenUtils.getExpiredIn();

		// Vrati token kao odgovor na uspesno autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}

	// obo treba jos doraditi - dodao sam salt, dal sam na to mislio...
	@RequestMapping(method = POST, value = "/signup")
	public ResponseEntity<?> addUser(@RequestBody @Valid UserRegisterRequestDTO userRequest,
			UriComponentsBuilder ucBuilder) {

		User existUser = this.userService.findByUsername(userRequest.getUsername());
		if (existUser != null) {
			return new ResponseEntity<String>("User_with_that_name_already_exists", HttpStatus.BAD_REQUEST);
		}

		User user = this.userService.save(userRequest);
		if (user == null) {
			return new ResponseEntity<String>(
					"This_password_is_on_the_common_password_list,_please_chose_another_password.",
					HttpStatus.BAD_REQUEST);
		}
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@RequestMapping(method = POST, value = "/agent")
	public ResponseEntity<?> addAgent(@RequestBody @Valid AgentRegisterDTO userRequest,
			UriComponentsBuilder ucBuilder) {

		User existUser = this.userService.findByUsername(userRequest.getUsername());
		if (existUser != null) {
			return new ResponseEntity<String>("User_with_that_name_already_exists", HttpStatus.BAD_REQUEST);
		}
		if(userRequest.getCompanyName() == null || userRequest.getCompanyName()=="") {
			if( userRequest.getFirstname() == null || userRequest.getLastname()==null || userRequest.getFirstname() == "" || userRequest.getLastname() == "") {
				return new ResponseEntity<String>("Morate uneti ili ime kompanije ili ime i prezime!", HttpStatus.BAD_REQUEST);
			}
		}
		User user = this.userService.saveAgent(userRequest);
		
		if (user == null) {
			return new ResponseEntity<String>(
					"This_password_is_on_the_common_password_list,_please_chose_another_password.",
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/agent")
	public ResponseEntity<?> updateAgent(@RequestBody AgentUpdateDTO userDTO, HttpServletRequest request) {
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		User u = userService.updateAgent(userDTO, username);

		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/user")
	public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userDTO, HttpServletRequest request) {
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		User u = userService.update(userDTO, username);

		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);

		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = tokenUtils.refreshToken(token);
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChanger passwordChanger) {
		String ret = userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

		if (ret == "unsuccessful") {
			return new ResponseEntity<>("Unsuccessful!", HttpStatus.BAD_REQUEST);
		} else {
			Map<String, String> result = new HashMap<>();
			result.put("result", ret);
			return ResponseEntity.ok().body(result);
		}
	}

	// --------------------------------------------------------------------
	// --------------------------------------------------------------------
	// --------------------------------------------------------------------

	// Za pristup ovoj metodi neophodno je da ulogovani korisnik ima ADMIN ulogu
	// Ukoliko nema, server ce vratiti gresku 403 Forbidden
	// Korisnik jeste autentifikovan, ali nije autorizovan da pristupi resursu
	@RequestMapping(method = GET, value = "/user/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> loadById(@PathVariable Long userId) {
		User user = this.userService.findById(userId);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(method = GET, value = "/user")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public List<User> loadAll() {
		return this.userService.findAll();
	}

	@RequestMapping(method = GET, value = "/user/me")
	public ResponseEntity<?> loadMyProfile() {
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);

		User user = userService.findByUsername(username);
		if (user == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		UserDTO uDTO = new UserDTO(user);
		

		return new ResponseEntity<>(uDTO, HttpStatus.OK);
	}
	
	@RequestMapping(method = GET, value = "/user/{username}")
	public ResponseEntity<?> loadUser(@PathVariable String username) {

		User user = userService.findByUsername(username);
		if (user == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		UserDTO uDTO = new UserDTO(user);

		return new ResponseEntity<>(uDTO, HttpStatus.OK);
	}

	@RequestMapping("/whoami")
	public User user(Principal user) {
		return this.userService.findByUsername(user.getName());
	}

	@RequestMapping(method = GET, value = "/foo")
	public Map<String, String> getFoo() {
		Map<String, String> fooObj = new HashMap<>();
		fooObj.put("foo", "bar");
		return fooObj;
	}

	/**
	 * Pozvano iz gateway-a
	 * 
	 * @param token
	 * @return permisije
	 */
	@GetMapping("/check/{token}")
	public ResponseEntity<?> getPermissions(@PathVariable String token) { /* dodaj exception */
		String permissije = "";
		if (tokenUtils.validateTokenForGateway(token, request.getRemoteAddr())) {
			permissije = this.tokenUtils.getPermissionFromToken(token);
			if (permissije == null) {
				return new ResponseEntity<String>("NE POSTOJE PERMISIJE U TOKENU", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("TOKEN NIJE VALIDAN", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(permissije, HttpStatus.OK);
	}

	/**
	 * Pozvano iz gateway-a
	 * 
	 * @param token
	 * @return username
	 */
	@GetMapping("/check/{token}/username")
	public ResponseEntity<?> getUsername(@PathVariable String token) { /* dodaj exception */
		if (tokenUtils.validateTokenForGateway(token, request.getRemoteAddr())) {
			String username = this.tokenUtils.getUsernameFromToken(token);
			if (username == null) {
				return new ResponseEntity<String>("NE POSTOJI USERNAME U TOKENU", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<String>(username, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("TOKEN NIJE VALIDAN", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/user/{uid}/activate")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<?> activateUser(@PathVariable(name = "uid") Long uid) {

		User user = userService.activateUser(uid);
		EmailDTO emailDTO = new EmailDTO("Accept registration trough email",
				"Potvrdite registraciju pritiskom na ovaj link: "
						+ "<br> http://localhost:8080/Admin/PotvrdaRegistracije.html?userID=" + uid + "<br>",
				"");
		try {
			emailService.sendNotificaitionAsync(emailDTO);
		} catch (Exception e) {
			// logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			System.out.println("### Greska prilikom slanja mail-a! ###");
		}
		
//		mailDTO - polja su na engleskom, takva polja prihvava mail mikroservis
		
		//email je zakucan u mail mikroservisu
		MailDTO mailDTO = new MailDTO("", "DEPLOYED - Accept registration trough email", 
				"Potvrdite registraciju pritiskom na ovaj link: "
				+ "<br> http://localhost:8080/Admin/PotvrdaRegistracije.html?userID=" + uid + "<br>");
		
		try {
			mailClient.sendMail(mailDTO);
		} catch (Exception e) {
			System.out.println("### DEPLOY - Greska prilikom slanja mail-a! ###");
		}
		
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/user/{uid}/mailActivate")
//   @PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<?> activateUserMail(@PathVariable(name = "uid") Long uid) {

		User user = userService.activateUserMail(uid);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/user/{uid}/block")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<?> blockUser(@PathVariable(name = "uid") Long uid) {

		User user = userService.blockUser(uid);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/user/{uid}/unblock")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<?> unblockUser(@PathVariable(name = "uid") Long uid) {

		User user = userService.unblockUser(uid);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/user/{uid}/blockOglase")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<?> blockOglaseUser(@PathVariable(name = "uid") Long uid) {

		User user = userService.blockOglaseUser(uid);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/user/{uid}/unblockOglase")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<?> unblockOglaseUser(@PathVariable(name = "uid") Long uid) {

		User user = userService.unblockOglaseUser(uid);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/user/{uid}/blockZahteve")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<?> blockZahteveUser(@PathVariable(name = "uid") Long uid) {

		User user = userService.blockZahteveUser(uid);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/user/{uid}/unblockZahteve")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<?> unblockZahteveUser(@PathVariable(name = "uid") Long uid) {

		User user = userService.unblockZahteveUser(uid);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@DeleteMapping("/user/{uid}")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<?> deleteUser(@PathVariable(name = "uid") Long uid) {

		User user = userService.deleteUser(uid);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/user/payDebt")
	public ResponseEntity<?> payDebt() {
		String token = request.getHeader("Auth").substring(7);
		String username = tokenUtils.getUsernameFromToken(token);
		User user = userService.findByUsername(username);
		user = userService.payDebt(user.getId());

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PutMapping("/user/addDebt")
	public ResponseEntity<?> addDebt(@RequestBody UpdateUserDebtDTO userDTO ) {
		User user = userService.findByUsername(userDTO.getUsername());
		
		user = userService.addDebt(user.getId(), userDTO.getDebt());

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PutMapping("/user/addCanceledNumber")
	public ResponseEntity<?> addCanceledNumber(@RequestBody UpdateUserDebtDTO userDTO) {
		
		User user = userService.findByUsername(userDTO.getUsername());
		user.setCanceled(user.getCanceled()+1);
	
		return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);
	}
	

	static class PasswordChanger {

		public String oldPassword;

		@NotNull
		@Size(min = 10, message = "Password must contain atleast 10 characters")
		public String newPassword;
	}
}