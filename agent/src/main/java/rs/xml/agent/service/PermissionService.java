package rs.xml.agent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import rs.xml.agent.model.Permission;
import rs.xml.agent.model.Role;
import rs.xml.agent.repository.PermissionRepository;
import rs.xml.agent.repository.RoleRepository;

@Service
public class PermissionService {
	
	@Autowired
	PermissionRepository permisionRepo;

	public List<Permission> findById(Long id) {
		Permission permission = this.permisionRepo.getOne(id);
		List<Permission> permissions = new ArrayList<>();
		permissions.add(permission);
		return permissions;
	}

	public List<Permission> findByname(String name) {
		Permission permission = this.permisionRepo.findByName(name);
		List<Permission> permissions = new ArrayList<>();
		permissions.add(permission);
		return permissions;
	}

	public List<Permission> findAll() throws AccessDeniedException {
		List<Permission> result = permisionRepo.findAll();
		return result;
	}
	
}
