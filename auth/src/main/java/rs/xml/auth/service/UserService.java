package rs.xml.auth.service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import rs.xml.auth.exceptions.NotFoundException;
import rs.xml.auth.model.Role;
import rs.xml.auth.model.User;
import rs.xml.auth.model.UserRegisterRequestDTO;
import rs.xml.auth.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;
	
	public User findByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}

	public User findById(Long id) throws AccessDeniedException {
		Optional<User> u = userRepository.findById(id);
		return u.orElse(null);
	}

	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}

	
	public User save(UserRegisterRequestDTO userRequest) {
		User u = new User();
		u.setUsername(userRequest.getUsername());
		u.setSalt(getNextSalt());
		u.setPassword(passwordEncoder.encode(userRequest.getPassword() + u.getSalt()));
		u.setFirstName(userRequest.getFirstname());
		u.setLastName(userRequest.getLastname());
		u.setEmail(userRequest.getEmail());
		u.setAccepted(false);
		u.setBlocked(false);
		u.setDeleted(false);
		u.setCanceled(0);
//		u.setAds(0);
		u.setOwes(0);
		
		List<Role> roles = roleService.findByname("ROLE_USER");
		u.setRoles(roles);
		
		u.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
		
		u = this.userRepository.save(u);
		return u;
	}
	
	public String getNextSalt() {
		Random RANDOM = new SecureRandom();
	    byte[] salt = new byte[16];
	    RANDOM.nextBytes(salt);
	    return salt.toString();
	}
	
	public User activateUser(Long uid) {
		User u = userRepository.findById(uid).orElseThrow(
				() -> new NotFoundException("User with id " + uid + " does not exist"));
		
		u.setAccepted(true);		
		userRepository.save(u);
		
		return u;
	}
	
	public User blockUser(Long uid) {		
		User u = userRepository.findById(uid).orElseThrow(
				() -> new NotFoundException("User with id " + uid + " does not exist"));
		
		u.setBlocked(true);	
		userRepository.save(u);
		
		return u;
	}
	
	public User unblockUser(Long uid) {
		User u = userRepository.findById(uid).orElseThrow(
				() -> new NotFoundException("User with id " + uid + " does not exist"));
		
		u.setBlocked(false);	
		userRepository.save(u);
		
		return u;
	}
	
	public User deleteUser(Long uid) {
		User u = userRepository.findById(uid).orElseThrow(
				() -> new NotFoundException("User with id " + uid + " does not exist"));
		
		u.setDeleted(true);
		userRepository.save(u);
		
		return u;
	}
	
}
