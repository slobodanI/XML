package rs.xml.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.xml.agent.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);
	
}
