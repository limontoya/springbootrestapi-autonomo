package com.api.autonomo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.api.autonomo.repository.SecurityRepository;

@Service
public class SecurityService implements SecurityRepository {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private OwnerDetailsService ownerDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

	/**
	 * Find an logged in owner by Email
	 * 
	 * @return
	 */
	@Override
	public String findLoggedInOwnerEmail() {

		Object ownerDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();

		if (ownerDetails instanceof UserDetails) {
			logger.info("Authenticated as: "+((UserDetails) ownerDetails).getUsername());
			return ((UserDetails) ownerDetails).getUsername();
		}

		logger.info("Couldnt be Authenticated...");
		return null;
	}
	
	/**
	 * Get an Authentication token to auto login owner by Email
	 * 
	 * @param ownerEmail
	 * @param ownerEmailKey
	 * @return
	 */
	@Override
	public void autoLogin(String ownerEmail, String ownerEmailKey) {

		UserDetails userDetails = ownerDetailsService.loadUserByUsername(ownerEmail);

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, ownerEmailKey, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			logger.info("Authenticated with autoLogin: "+ ownerEmail);
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}

	}

}
