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

import com.api.autonomo.model.Item;
import com.api.autonomo.service.ItemService;

@RestController
@RequestMapping("/autonomo")
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	/**
	 * Create a new Item for invoice
	 * @param item
	 * @return
	 */
	@PostMapping("/items")
	public Item createItem(@Valid @RequestBody Item item) {
		return itemService.saveItem(item);
	}
	
	/**
	 * Search all items
	 * @return
	 */
	@GetMapping("/items")
	public List<Item> getAllItems() {
		return itemService.findAllItems();
	}
	
	/**
	 * Get item by Id
	 * @param itemId
	 * @return
	 */
	@GetMapping("/items/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable(value="id") Long itemId) {
		
		Item item = itemService.getItemById(itemId);
		
		if (item == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(item);
	}
	
	/**
	 * Update an Item by Id
	 * @param itemId
	 * @param itemDetails
	 * @return
	 */
	@PutMapping("/items/{id}")
	public ResponseEntity<Item> updateItemById(@PathVariable(value="id") Long itemId, @Valid @RequestBody Item itemDetails){
		
		Item item = itemService.getItemById(itemId);
		
		if (item == null) {
			return ResponseEntity.notFound().build();
		}
		
		item.setAmount(itemDetails.getAmount());
		item.setDescription(itemDetails.getDescription());
		item.setQuantity(itemDetails.getQuantity());
		item.setUnitCost(itemDetails.getUnitCost());
		
		Item updatedItem = itemService.saveItem(item);
		
		return ResponseEntity.ok().body(updatedItem);		
	}
	
	/**
	 * Delete an Item by Id
	 * @param itemId
	 * @return
	 */
	@DeleteMapping("/items/{id}")
	public ResponseEntity<Item> deleteItem(@PathVariable(value="id") Long itemId){
		
		Item item = itemService.getItemById(itemId);
		
		if (item == null) {
			return ResponseEntity.notFound().build();
		}
		
		itemService.deleteItem(item);
		
		return ResponseEntity.ok().build();
		
	}

}
