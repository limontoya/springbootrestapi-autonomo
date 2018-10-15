package com.api.autonomo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.autonomo.model.Owner;
import com.api.autonomo.repository.OwnerRepository;

@Service
public class OwnerDetailsService implements UserDetailsService {

	@Autowired
	private OwnerRepository ownerRepository;

	/**
	 * Find an owner by Email and return with granted authorities
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String ownerEmail) throws UsernameNotFoundException {

		Owner owner = ownerRepository.findbyOwnerEmail(ownerEmail);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();

		owner.getRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));

		return new org.springframework.security.core.userdetails.User(owner.getEmail(), owner.getEmailKey(),
				grantedAuthorities);

	}

}
