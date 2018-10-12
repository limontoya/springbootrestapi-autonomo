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

import com.api.autonomo.model.Invoice;
import com.api.autonomo.service.InvoiceService;

@RestController
@RequestMapping("/autonomo")
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;
	
	/**
	 * Save an invoice
	 * @param invoice
	 * @return
	 */
	@PostMapping("/invoices")
	public Invoice createInvoice(@Valid @RequestBody Invoice invoice) {
		return invoiceService.saveInvoice(invoice);
	}
	
	/**
	 * Get all invoices
	 * @return
	 */
	@GetMapping("/invoices")
	public List<Invoice> getAllInvoices(){
		return invoiceService.findAllInvoices();
	}
	
	/**
	 * Get an invoice by Id
	 * @param invoiceId
	 * @return
	 */
	@GetMapping("/invoices/{id}")
	public ResponseEntity<Invoice> getInvoiceById(@PathVariable(value="id") Long invoiceId){
		
		Invoice invoice = invoiceService.getInvoiceById(invoiceId);
		
		if (invoice == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(invoice);
	}
	
	/**
	 * Update an invoice by Id
	 * @param invoiceId
	 * @param invoiceDetails
	 * @return
	 */
	@PutMapping("/invoices/{id}")
	public ResponseEntity<Invoice> updateInvoiceById(@PathVariable(value="id") Long invoiceId, @Valid @RequestBody Invoice invoiceDetails){
		
		Invoice invoice = invoiceService.getInvoiceById(invoiceId);
		
		if (invoice == null) {
			return ResponseEntity.notFound().build();
		}
		
		invoice.setDate(invoiceDetails.getDate());
		invoice.setNetAmount(invoiceDetails.getNetAmount());
		invoice.setNotes(invoiceDetails.getNotes());
		invoice.setTotalAmount(invoiceDetails.getTotalAmount());
		invoice.setVatBase(invoiceDetails.getVatBase());
		invoice.setVatPercentage(invoiceDetails.getVatPercentage());
		
		Invoice updatedInvoice = invoiceService.saveInvoice(invoice);
		
		return ResponseEntity.ok().body(updatedInvoice);		
	}
	
	/**
	 * Delete an invoice by Id
	 * @param invoiceId
	 * @return
	 */
	@DeleteMapping("/invoices/{id}")
	public ResponseEntity<Invoice> deleteInvoice(@PathVariable(value="id") Long invoiceId){
		
		Invoice invoice = invoiceService.getInvoiceById(invoiceId);
		
		if (invoice == null) {
			return ResponseEntity.notFound().build();
		}
		
		invoiceService.deleteInvoice(invoice);
		
		return ResponseEntity.ok().build();
		
	}

}
