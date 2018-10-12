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

import com.api.autonomo.model.Client;
import com.api.autonomo.service.ClientService;

@RestController
@RequestMapping("/autonomo")
public class ClientController {
	
	@Autowired
	ClientService clientService;
	
	/**
	 * Create a new Client
	 * @param client
	 * @return
	 */
	@PostMapping("/clients")
	public Client createClient(@Valid @RequestBody Client client) {
		return clientService.saveClient(client);
	}
	
	/**
	 * Get a list of Clients
	 * @return
	 */
	@GetMapping("/clients")
	public List<Client> getAllClients(){
		return clientService.findAllClients();
	}
	
	/**
	 * Get a Client by Id
	 * @param clientId
	 * @return
	 */
	@GetMapping("/clients/{id}")
	public ResponseEntity<Client> getClientById(@PathVariable(value="id") Long clientId){
		
		Client client = clientService.getClientById(clientId);
		
		if (client==null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(client);
	}
	
	/**
	 * Update a Client by Id
	 * @param clientId
	 * @param clientDetails
	 * @return
	 */
	@PutMapping("/clients/{id}")
	public ResponseEntity<Client> updateClientById(@PathVariable(value="id") Long clientId, @Valid @RequestBody Client clientDetails){
		
		Client client = clientService.getClientById(clientId);
		
		if (client == null) {
			return ResponseEntity.notFound().build();
		}
		
		client.setName(clientDetails.getName());
		client.setNid(clientDetails.getNid());
		client.setPhone(clientDetails.getPhone());
		
		Client updatedClient = clientService.saveClient(client);
		
		return ResponseEntity.ok().body(updatedClient);
	}
	
	/**
	 * Delete a Client by Id
	 * @param clientId
	 * @return
	 */
	@DeleteMapping("/clients/{id}")
	public ResponseEntity<Client> deleteClient(@PathVariable(value="id") Long clientId){
		
		Client client = clientService.getClientById(clientId);
		
		if(client == null) {
			return ResponseEntity.notFound().build();
		}
		
		clientService.deleteClient(client);
		
		return ResponseEntity.ok().build();
	}

}
