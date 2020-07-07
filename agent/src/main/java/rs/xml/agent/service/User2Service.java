package rs.xml.agent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rs.xml.agent.model.User;
import rs.xml.agent.repository.UserRepository;

@Service
public class User2Service {
	@Autowired
	private UserRepository userRepository;
	
	public User findByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
}
