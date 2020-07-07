package rs.xml.agent.service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.xml.agent.dto.UserUpdateDTO;
import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.model.Role;
import rs.xml.agent.model.User;
import rs.xml.agent.model.UserRegisterRequestDTO;
import rs.xml.agent.repository.UserRepository;

@Service
public class UserService {
	
	// password mora imati minimalno 10 karaktera
	private List<String> badPasswords = Arrays.asList("passwordpassword", "1234567890");
		
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
		for(String pas: badPasswords) {
			if(userRequest.getPassword().equals(pas)) {
				return null;
			}
		}
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
		u.setActivated(false);
		
		List<Role> roles = roleService.findByname("ROLE_USER");
		u.setRoles(roles);
		
		u.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
		
		u = this.userRepository.save(u);
		return u;
	}
	
	public User update(UserUpdateDTO userDTO, String username) {

		User u = this.findByUsername(username);

		if (userDTO.getEmail() != null && userDTO.getEmail() != "") {
			u.setEmail(userDTO.getEmail());
		}

		if (userDTO.getFirstname() != null && userDTO.getFirstname() != "") {
			u.setFirstName(userDTO.getFirstname());
		}

		if (userDTO.getLastname() != null && userDTO.getLastname() != "") {
			u.setLastName(userDTO.getLastname());
		}
		u = userRepository.save(u);
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
	public User activateUserMail(Long uid) {
		User u = userRepository.findById(uid).orElseThrow(
				() -> new NotFoundException("User with id " + uid + " does not exist"));
		
		long millis=System.currentTimeMillis();
		Timestamp now=new Timestamp(millis);
		u.setLastPasswordResetDate(now);
		u.setActivated(true);		
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
	
	public User blockOglaseUser(Long uid) {		
		User u = userRepository.findById(uid).orElseThrow(
				() -> new NotFoundException("User with id " + uid + " does not exist"));
		
		u.setBlockedPostavljanjeOglasa(true);	
		userRepository.save(u);
		
		return u;
	}
	public User unblockOglaseUser(Long uid) {		
		User u = userRepository.findById(uid).orElseThrow(
				() -> new NotFoundException("User with id " + uid + " does not exist"));
		
		u.setBlockedPostavljanjeOglasa(false);	
		userRepository.save(u);
		
		return u;
	}
	
	public User blockZahteveUser(Long uid) {		
		User u = userRepository.findById(uid).orElseThrow(
				() -> new NotFoundException("User with id " + uid + " does not exist"));
		
		u.setBlockedSlanjeZahteva(true);	
		userRepository.save(u);
		
		return u;
	}
	public User unblockZahteveUser(Long uid) {		
		User u = userRepository.findById(uid).orElseThrow(
				() -> new NotFoundException("User with id " + uid + " does not exist"));
		
		u.setBlockedSlanjeZahteva(false);	
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
	
	public User payDebt(Long uid) {
		User u = userRepository.findById(uid).orElseThrow(
				() -> new NotFoundException("User with id " + uid + " does not exist"));
		u.setOwes(0);
		userRepository.save(u);
		return u;
		
	}
	
	@Scheduled(cron = "0 0 * ? * *")
	public void cancelAfter24h() {
		System.out.println("CRON radi");
		List<User> users = userRepository.findPendingMailUsers();
		if(users.isEmpty()) return;
		
		
		long millis=System.currentTimeMillis()-(24 * 60 * 60 * 1000);
		Timestamp oneDayAgo=new Timestamp(millis);
		List<Long> pomocna = new ArrayList<Long>();
		for(User u : users) {
			if(u.getLastPasswordResetDate().before(oneDayAgo)) {
				pomocna.add(u.getId());
			}
		}
		for(Long uid : pomocna) {
			userRepository.deleteById(uid);
		}
		
		System.out.println(oneDayAgo);
		
	}
	
}
