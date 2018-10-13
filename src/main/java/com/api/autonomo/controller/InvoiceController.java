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

import com.api.autonomo.model.Client;
import com.api.autonomo.model.Depot;
import com.api.autonomo.model.Invoice;
import com.api.autonomo.service.ClientService;
import com.api.autonomo.service.DepotService;
import com.api.autonomo.service.InvoiceService;

@RestController
@RequestMapping("/autonomo")
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	DepotService depotService;
	
	/**
	 * Save an invoice
	 * @param invoice
	 * @return
	 */
	@PostMapping("/invoices")
	public Invoice createInvoice(@Valid @RequestBody Invoice invoice) {
		//One invoice to many items
		invoice.getItems().forEach(item->item.setInvoice(invoice));
		
		//One client to One Invoice
		//>If client exists, set the existing client; else create a new Client
		if( invoice.getClient().getId() != null){		
			Client client = clientService.getClientById(invoice.getClient().getId());		
			invoice.setClient(client);
		}
		
		//One depot to One Invoice pdf file
		//>If depot exists, set the existing depot; else create a new Depot
		if( invoice.getDepot().getId() != null){		
			Depot depot = depotService.getDepotById(invoice.getDepot().getId());		
			invoice.setDepot(depot);
		}
		
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
		System.out.println("Invoice NO soy null> "+invoice.getId() );
		invoice.setDate(invoiceDetails.getDate());
		invoice.setNetAmount(invoiceDetails.getNetAmount());
		invoice.setNotes(invoiceDetails.getNotes());
		invoice.setTotalAmount(invoiceDetails.getTotalAmount());
		invoice.setVatBase(invoiceDetails.getVatBase());
		invoice.setVatPercentage(invoiceDetails.getVatPercentage());
		
		//One to many
		System.out.println("Items de invoice> "+invoiceDetails.getItems().toString() );
		invoiceDetails.getItems().forEach(item->item.setInvoice(invoice));
		invoice.setItems(invoiceDetails.getItems());		
		//One to one
		System.out.println("Cliente de invoice> "+invoiceDetails.getClient().toString() );
		if(!invoice.getClient().getId().equals(invoiceDetails.getClient().getId()))	invoice.setClient(invoiceDetails.getClient());
		//One to one
		System.out.println("Depot de invoice> "+invoiceDetails.getDepot().toString() );
		if(!invoice.getDepot().getId().equals(invoiceDetails.getDepot().getId())) invoice.setDepot(invoiceDetails.getDepot());
		
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
