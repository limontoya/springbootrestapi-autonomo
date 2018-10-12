package com.api.autonomo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.autonomo.model.Client;
import com.api.autonomo.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	ClientRepository clientRepository;
	
	/**
	 * Save a Client
	 * @param client
	 * @return
	 */
	public Client saveClient(Client client) {
		return clientRepository.save(client);
	}
	
	/**
	 * Get a list with Clients
	 * @return
	 */
	public List<Client> findAllClients(){
		return clientRepository.findAll();
	}
	
	/**
	 * Get a Client by Id
	 * @param clientId
	 * @return
	 */
	public Client getClientById(Long clientId) {
		return clientRepository.getOne(clientId);
	}
	
	/**
	 * Delete a Client
	 * @param client
	 */
	public void deleteClient(Client client) {
		clientRepository.delete(client);
	}

}
