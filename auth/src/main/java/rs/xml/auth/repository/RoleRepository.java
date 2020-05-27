package rs.xml.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.xml.auth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);
	
}
