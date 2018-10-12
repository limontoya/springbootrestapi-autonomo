package com.api.autonomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.autonomo.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
