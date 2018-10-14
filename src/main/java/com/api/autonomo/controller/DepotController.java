package com.api.autonomo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.autonomo.model.Depot;
import com.api.autonomo.service.DepotService;

@RestController
@RequestMapping("/autonomo")
public class DepotController {

	@Autowired
	DepotService depotService;

	/**
	 * Manage file uploads creating Depots for each file uploaded
	 * 
	 * @param depot
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/depots")
	public ResponseEntity<?> createDepot(@ModelAttribute Depot depot, @RequestParam("file") MultipartFile file) {
		try {

			if (!file.isEmpty()) {
				// First get the file and save it on Server disk
				String fileName = depotService.saveDepotFile(file, file.getOriginalFilename());

				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName)
						.toUriString();

				// Then get info from file and set it to the Entity
				depot.setName(fileName);
				depot.setContentType(file.getContentType());
				depot.setSize(file.getSize());
				depot.setLocation(fileDownloadUri);

				// Save on database
				depotService.saveDepot(depot);
				return ResponseEntity.ok().body(depot);
			}

		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.notFound().build();

	}

	/**
	 * Gets all depots, no need to load files
	 * 
	 * @return
	 */
	@GetMapping("/depots")
	public List<Depot> getAllDepots() {
		return depotService.findAllDepots();
	}

	/**
	 * Get a depot by Id
	 * 
	 * @param depotId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/depots/{id}")
	public ResponseEntity<Depot> getDepotById(@PathVariable(value = "id") Long depotId) throws Exception {

		Depot depot = (Depot) depotService.getDepotById(depotId);

		if (depot == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(depot);
	}

	/**
	 * Get a depot's File by Id
	 * 
	 * @param depotId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/depots/{id}/file")
	public ResponseEntity<?> getDepotFileById(@PathVariable(value = "id") Long depotId, HttpServletRequest request)
			throws Exception {

		Depot depot = (Depot) depotService.getDepotById(depotId);
		String contentType = null;

		// Load file as a resource
		Resource resource = depotService.getDepotFileAsResource(depot.getName());

		try {
			contentType = getContentTypeForResource(request, resource);

		} catch (Exception e) {
			// TODO nothing happens just cannot determine file type.. shows default file img
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);

		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	/**
	 * Updates an existing depot File and its name by Id
	 * 
	 * @param depotId
	 * @param depotDetails
	 * @return
	 */
	@PutMapping("/depots/{id}/file")
	public ResponseEntity<?> updateDepotByIdByFile(@PathVariable(value = "id") Long depotId,
			@RequestPart MultipartFile file, @Nullable @RequestPart String name) {

		Depot depot = depotService.getDepotById(depotId);

		if (depot == null) {
			return ResponseEntity.notFound().build();
		}

		if (file != null && !file.isEmpty()) {

			// Set the name given
			try {
				if (name != null) {
					// Not a valid name? setting the original file name
					if (name.isEmpty() || name.equals(" ")) {
						depot.setName(file.getOriginalFilename());
					} else
						depot.setName(name);

				} else
					depot.setName(file.getOriginalFilename());
			} catch (Exception e) {
				// null name variable might call an NullPointerException, setting the original
				// file name
				depot.setName(file.getOriginalFilename());
			}

			// Saving file on Server disk
			String fileName = depotService.saveDepotFile(file, depot.getName());

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();

			// Setting data from file
			depot.setContentType(file.getContentType());
			depot.setSize(file.getSize());
			depot.setLocation(fileDownloadUri);

			// Updates file and/or name for Depot
			Depot updatedDepot = depotService.saveDepot(depot);
			return ResponseEntity.ok().body(updatedDepot);
		} else
			return ResponseEntity.notFound().build();
	}

	/**
	 * Updates an existing depot by Id, only name and location will be updated
	 * 
	 * @param depotId
	 * @param depotDetails
	 * @return
	 */
	@PutMapping("/depots/{id}")
	public ResponseEntity<?> updateDepotById(@PathVariable(value = "id") Long depotId,
			@Valid @RequestBody Depot depotToUpdate) {

		Depot depot = depotService.getDepotById(depotId);

		if (depot == null) {
			return ResponseEntity.notFound().build();
		}

		depot.setLocation(depotToUpdate.getLocation());
		depot.setName(depotToUpdate.getName());

		Depot updatedDepot = depotService.saveDepot(depot);

		return ResponseEntity.ok().body(updatedDepot);
	}

	/**
	 * Set a default Content type when null
	 * 
	 * @param request
	 * @param resource
	 * @return
	 */
	private String getContentTypeForResource(HttpServletRequest request, Resource resource) {

		String contentType = "";
		String contentTypeDefault = depotService.getDepotNotContentType();

		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

			if (contentType == null)
				contentType = contentTypeDefault;

			return contentType;
		} catch (Exception e) {
			return contentTypeDefault;
		}
	}

}
