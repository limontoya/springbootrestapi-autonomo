package com.api.autonomo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.autonomo.model.Invoice;
import com.api.autonomo.repository.InvoiceRepository;

@Service
public class InvoiceService {

	@Autowired
	InvoiceRepository invoiceRepository;

	/**
	 * Save or update an invoice
	 * 
	 * @param invoice
	 * @return
	 */
	public Invoice saveInvoice(Invoice invoice) {
		return invoiceRepository.save(invoice);
	}

	/**
	 * Get all invoices
	 * 
	 * @return
	 */
	public List<Invoice> findAllInvoices() {
		return invoiceRepository.findAll();
	}

	/**
	 * Get an invoice by Id
	 * 
	 * @param invoiceId
	 * @return
	 */
	public Invoice getInvoiceById(Long invoiceId) {
		return invoiceRepository.getOne(invoiceId);
	}

	/**
	 * Delete an invoice
	 * 
	 * @param invoice
	 */
	public void deleteInvoice(Invoice invoice) {
		invoiceRepository.delete(invoice);
	}

}
