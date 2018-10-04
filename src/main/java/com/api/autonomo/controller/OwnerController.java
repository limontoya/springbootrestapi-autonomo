package com.api.autonomo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.autonomo.dao.OwnerDAO;
import com.api.autonomo.model.Owner;

@RestController
@RequestMapping("/autonomo")
public class OwnerController {

	@Autowired
	OwnerDAO ownerDAO;
	
	/**
	 * Save an owner
	 * @param owner
	 * @return
	 */
	@PostMapping("/owners")
	public Owner createOwner(@Valid @RequestBody Owner owner) {
		return ownerDAO.saveOwner(owner);
	}
	
	/**
	 * Get all owners
	 * @return
	 */
	@GetMapping("/owners")
	public List<Owner> getAllOwners(){
		return ownerDAO.findAllOwners();
	}
	
	/**
	 * Get an owner by id
	 * @param ownerId
	 * @return
	 */
	@GetMapping("/owners/{id}")
	public ResponseEntity<Owner> getOwnerById(@PathVariable(value="id") Long ownerId){
		
		Owner owner = ownerDAO.getOwnerById(ownerId);
		
		if(owner == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(owner);
	}
	
	/**
	 * Update an owner by id
	 * @param ownerId
	 * @param ownerDetails
	 * @return
	 */
	@PutMapping("/owners/{id}")
	public ResponseEntity<Owner> updateOwnerById(@PathVariable(value="id") Long ownerId, @Valid @RequestBody Owner ownerDetails){
		
		Owner owner = ownerDAO.getOwnerById(ownerId);
		
		if(owner == null) {
			return ResponseEntity.notFound().build();
		}
		
		owner.setNid(ownerDetails.getNid());
		owner.setName(ownerDetails.getName());
		owner.setEmail(ownerDetails.getEmail());
		owner.setEmailKey(ownerDetails.getEmailKey());
		owner.setEmailKeyRestore(ownerDetails.getEmailKeyRestore());
		//owner.setImage(ownerDetails.getImage());
		owner.setAddress(ownerDetails.getAddress());
		owner.setPhone(ownerDetails.getPhone());
		
		Owner updateOwner = ownerDAO.saveOwner(owner);
				
		return ResponseEntity.ok().body(updateOwner);
	}
	
	/**
	 * Delete an owner by id
	 * @param ownerId
	 * @return
	 */
	@DeleteMapping("/owners/{id}")
	public ResponseEntity<Owner> deleteOwner(@PathVariable(value="id") Long ownerId){
		
		Owner owner = ownerDAO.getOwnerById(ownerId);
		
		if(owner==null) {
			return ResponseEntity.notFound().build();
		}
		
		ownerDAO.deleteOwner(owner);
		
		return ResponseEntity.ok().build();
	}
	
}
