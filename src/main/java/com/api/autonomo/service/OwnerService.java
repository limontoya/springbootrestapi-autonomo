package com.api.autonomo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.autonomo.model.Owner;
import com.api.autonomo.repository.OwnerRepository;

@Service
public class OwnerService {

	@Autowired
	OwnerRepository ownerRepository;

	/**
	 * Save or update an owner
	 * 
	 * @param owner
	 * @return
	 */
	public Owner saveOwner(Owner owner) {
		return ownerRepository.save(owner);
	}

	/**
	 * Search all owners
	 * 
	 * @return
	 */
	public List<Owner> findAllOwners() {
		return ownerRepository.findAll();
	}

	/**
	 * Get an owner by Id
	 * 
	 * @param ownerId
	 * @return
	 */
	public Owner getOwnerById(Long ownerId) {
		return ownerRepository.getOne(ownerId);
	}

	/**
	 * Delete an owner
	 * 
	 * @param owner
	 */
	public void deleteOwner(Owner owner) {
		ownerRepository.delete(owner);
	}

}
