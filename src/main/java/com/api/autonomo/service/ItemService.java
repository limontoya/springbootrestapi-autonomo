package com.api.autonomo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.autonomo.model.Item;
import com.api.autonomo.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	/**
	 * Save an invoice item
	 * @param item
	 * @return
	 */
	public Item saveItem(Item item) {
		return itemRepository.save(item);
	}
	
	/**
	 * Search all Items
	 * @return
	 */
	public List<Item> findAllItems(){
		return itemRepository.findAll();
	}
	
	/**
	 * Search an Item by Id
	 * @param itemId
	 * @return
	 */
	public Item getItemById(Long itemId) {
		return itemRepository.getOne(itemId);
	}
	
	/**
	 * Delete an Item
	 * @param item
	 */
	public void deleteItem(Item item) {
		itemRepository.delete(item);
	}

}
