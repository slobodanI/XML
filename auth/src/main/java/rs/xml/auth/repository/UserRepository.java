package rs.xml.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rs.xml.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.accepted = true and u.activated = false")
	List<User> findPendingMailUsers();
}
