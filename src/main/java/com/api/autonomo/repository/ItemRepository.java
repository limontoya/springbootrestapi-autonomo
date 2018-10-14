package com.api.autonomo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.autonomo.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	Page<Item> findByInvoiceId(Long invoiceId, Pageable pageable);

}
