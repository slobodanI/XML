package rs.xml.agent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import rs.xml.agent.model.Role;
import rs.xml.agent.model.User;
import rs.xml.agent.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepo;

	public List<Role> findById(Long id) {
		Role role = this.roleRepo.getOne(id);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		return roles;
	}

	public List<Role> findByname(String name) {
		Role role = this.roleRepo.findByName(name);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		return roles;
	}

	public List<Role> findAll() throws AccessDeniedException {
		List<Role> result = roleRepo.findAll();
		return result;
	}
}
