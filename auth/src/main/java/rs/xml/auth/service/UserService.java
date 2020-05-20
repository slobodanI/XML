package rs.xml.auth.service;

import java.sql.Timestamp;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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

	private String salt = "salt";
	
	public User findByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}

	public User findById(Long id) throws AccessDeniedException {
		User u = userRepository.findById(id).orElseGet(null);
		return u;
	}

	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}

	
	public User save(UserRegisterRequestDTO userRequest) {
		User u = new User();
		u.setUsername(userRequest.getUsername());
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		u.setFirstName(userRequest.getFirstname());
		u.setLastName(userRequest.getLastname());
		u.setEmail(userRequest.getEmail());
		u.setAccepted(false);
		u.setBlocked(false);
		u.setCanceled(0);
		u.setAds(0);
		u.setOwes(0);
		
		List<Role> roles = roleService.findByname("ROLE_USER");
		u.setRoles(roles);
		
		u.setLastPasswordResetDate(Timestamp.valueOf(new LocalDateTime().toString()));
		
		u = this.userRepository.save(u);
		return u;
	}

}
