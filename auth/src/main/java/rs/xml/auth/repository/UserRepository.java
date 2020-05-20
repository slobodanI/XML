package rs.xml.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.xml.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

}
