package com.api.autonomo.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.api.autonomo.model.Depot;
import com.api.autonomo.properties.FileDepotProperties;
import com.api.autonomo.repository.DepotRepository;

@Service
public class DepotService {
	
	private final Path depotFilesLocation;
	private final String depotNotAvailable;
	private final String depotNotContentType;
	
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
	
	/**
	 * Get the Properties for depot files
	 * @param fileDepotProperties
	 */
	@Autowired
	public DepotService(FileDepotProperties fileDepotProperties) {
		this.depotFilesLocation = Paths.get(fileDepotProperties.getUploadDirectory()).toAbsolutePath().normalize();
		this.depotNotAvailable = fileDepotProperties.getNotAvailable();
		this.depotNotContentType = fileDepotProperties.getContentType();
		
		try {
			Files.createDirectories(this.depotFilesLocation);
		}
		catch (Exception e) {
			e.getMessage();
		}
	}
	
	/**
	 * Save the file uploaded for the Depot	 
	 * @param file
	 * @param fileName
	 * @return
	 */
	public String saveDepotFile(MultipartFile file, String fileName) {
		
		fileName = StringUtils.cleanPath(fileName);
		
		try {
			if (fileName.contains("..")) {
				throw new Exception("Filename contains invalid path sequence "+fileName);
			}
			
			Path targetLocation = this.depotFilesLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
		}
		catch (Exception e) {
			return e.getMessage();
		}
	}
	
	/**
	 * Returns the File as a resource
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Resource getDepotFileAsResource(String fileName) throws Exception {
		Path filePath = this.depotFilesLocation.resolve(fileName).normalize();
		
		Resource resource = new UrlResource(filePath.toUri());
		
		if(!resource.exists()) {	
			//If resource with existing data on database does not exist on server disk, return the default broken image
			filePath = this.depotFilesLocation.resolve(this.depotNotAvailable).normalize();
			resource = new UrlResource(filePath.toUri());
		}
		return resource;
	}

	public String getDepotNotContentType() {
		return depotNotContentType;
	}

}
