package com.api.autonomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.autonomo.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
