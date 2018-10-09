package com.api.autonomo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.autonomo.model.Depot;
import com.api.autonomo.repository.DepotRepository;

@Service
public class DepotService {
	
	@Autowired
	DepotRepository depotRepository;
	
	public Depot saveDepot(Depot depot) {
		return depotRepository.save(depot);
	}
	
	public List<Depot> findAllDepots(){
		return depotRepository.findAll();
	}
	
	public Depot getDepotById(Long depotId) {
		return depotRepository.getOne(depotId);
	}
	
	public void deleteDepot(Depot depot) {
		depotRepository.delete(depot);
	}

}
