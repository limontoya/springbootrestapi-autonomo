package com.api.autonomo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.autonomo.model.Role;
import com.api.autonomo.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	/**
	 * Save or update a role
	 * 
	 * @param role
	 * @return
	 */
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	/**
	 * Search all roles
	 * 
	 * @return
	 */
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	/**
	 * Get a role by Id
	 * 
	 * @param roleId
	 * @return
	 */
	public Role getRoleById(Long roleId) {
		return roleRepository.getOne(roleId);
	}

	/**
	 * Delete a role
	 * 
	 * @param role
	 */
	public void deleteRole(Role role) {
		roleRepository.delete(role);
	}
}
