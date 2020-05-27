package rs.xml.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.xml.auth.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{
	
	Permission findByName(String name);
	
}
