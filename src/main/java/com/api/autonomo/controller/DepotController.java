package com.api.autonomo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.autonomo.model.Depot;
import com.api.autonomo.service.DepotService;

@RestController
@RequestMapping("/autonomo")
public class DepotController {
	
	@Autowired
	DepotService depotService;
	
	FileUploadController fileUploadController;
	
	/**
	 * Manage file uploads creating Depots for each file
	 * @param depot
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/depots")
    public ResponseEntity<?> createDepot(@ModelAttribute Depot depot,
    		@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
        	
        	if(!file.isEmpty()) {
            	fileUploadController = new FileUploadController();
            	depot.setLocation(fileUploadController.upload(file, request));
            	
            	depot.setName(file.getOriginalFilename());
            	depot.setContentType(file.getContentType());
            	depot.setSize(file.getSize());	
            	
            	depotService.saveDepot(depot);
            	
            	return ResponseEntity.ok().body(depot);
        	}
        	
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.notFound().build();
        
    }
	
	/**
	 * Gets all depots
	 * @return
	 */
	@GetMapping("/depots")
	public List<Depot> getAllDepots(){
		return depotService.findAllDepots();
	}
	
	/**
	 * Get a depot by Id
	 * @param depotId
	 * @return
	 */
	@GetMapping("/depots/{id}")
	public ResponseEntity<Depot> getDepotById(@PathVariable(value="id") Long depotId) {
		
		Depot depot = depotService.getDepotById(depotId);
		
		if(depot == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(depot);
	}
	
	/**
	 * Updates an existing depot by Id
	 * @param depotId
	 * @param depotDetails
	 * @return
	 */
	@PutMapping("/depots/{id}")
	public ResponseEntity<Depot> updateDepotById(@PathVariable(value="id") Long depotId, @Valid @RequestBody Depot depotToUpdate,
    		@RequestParam("file") MultipartFile file, HttpServletRequest request){
		
		Depot depot = depotService.getDepotById(depotId);
		
		if(depot == null) {
			return ResponseEntity.notFound().build();
		}
		
		if(!file.isEmpty()) {
        	fileUploadController = new FileUploadController();
        	
        	depot.setLocation(fileUploadController.upload(file, request));
        	depot.setContentType(file.getContentType());
        	depot.setSize(file.getSize());
        	
        	if (depotToUpdate.getName().isEmpty() || depotToUpdate.getName().equals(" ")) {
        		depot.setName(depotToUpdate.getName());
        	}
        	else depot.setName(file.getOriginalFilename());        
    	}
				
		Depot updatedDepot = depotService.saveDepot(depot);
		
		return ResponseEntity.ok().body(updatedDepot);
	}

}
