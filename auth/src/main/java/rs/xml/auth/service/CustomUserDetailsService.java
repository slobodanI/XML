package rs.xml.auth.service;


import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.xml.auth.model.User;
import rs.xml.auth.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	protected final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	// password mora imati minimalno 10 karaktera
	private List<String> badPasswords = Arrays.asList("passwordpassword", "1234567890");
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	// Funkcija koja na osnovu username-a iz baze vraca objekat User-a
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			LOGGER.warn("SR, Unsuccessful login attempt, with username: " + username);
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return user;
		}
	}

	// Funkcija pomocu koje korisnik menja svoju lozinku
	public String changePassword(String oldPassword, String newPassword) {
		for(String pas: badPasswords) {
			if(newPassword.equals(pas)) {
				return "unsuccessful";
			}
		}		
		User user = null;
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			LOGGER.debug("SR, Re-authenticating user '" + username + "' for password change request.");
			user = (User) loadUserByUsername(username);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword + user.getSalt()));
		} else {
			LOGGER.debug("SR, No authentication manager set. can't change Password!");

			return "unsuccessful";
		}

		LOGGER.debug("SR, Changing password for user '" + username + "'");

		// pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
		// ne zelimo da u bazi cuvamo lozinke u plain text formatu
		user.setPassword(passwordEncoder.encode(newPassword + user.getSalt()));
		userRepository.save(user);
		
		return "successful";
	}
}
