package rs.xml.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.xml.agent.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	
	Permission findByName(String name);
	
}
