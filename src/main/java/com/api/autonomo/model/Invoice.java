package com.api.autonomo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="Invoices")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Invoice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Date date;
	private Double netAmount;
	private Double vatBase;
	private Long vatPercentage;
	private Double totalAmount;
	private String notes;
	
	@JsonBackReference
	@OneToMany(mappedBy="invoice", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	private List<Item> items = new ArrayList<Item>();
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="client_id")
	private Client client;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="depot_pdf_id")
	private Depot depot;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	// TODO: @CreatedBy @LastModifiedBy
	private Long updatedBy;

	/**
	 * Constructors
	 * 
	 */
	public Invoice (Date date, Double netAmount, Double vatBase, Long vatPercentage, Double totalAmount, String notes) {
		this.date = date;
		this.netAmount = netAmount;
		this.vatBase = vatBase;
		this.vatPercentage = vatPercentage;
		this.totalAmount = totalAmount;
		this.notes = notes;
	}
	
	public Invoice (Date date, Double netAmount, Double vatBase, Long vatPercentage, Double totalAmount, String notes, List<Item> items) {
		this.date = date;
		this.netAmount = netAmount;
		this.vatBase = vatBase;
		this.vatPercentage = vatPercentage;
		this.totalAmount = totalAmount;
		this.notes = notes;
		this.items = items;
	}
	
	public Invoice (Date date, Double netAmount, Double vatBase, Long vatPercentage, Double totalAmount, String notes, List<Item> items, Client client) {
		this.date = date;
		this.netAmount = netAmount;
		this.vatBase = vatBase;
		this.vatPercentage = vatPercentage;
		this.totalAmount = totalAmount;
		this.notes = notes;
		this.items = items;
		this.client = client;
	}
	
	public Invoice (Date date, Double netAmount, Double vatBase, Long vatPercentage, Double totalAmount, String notes, List<Item> items, Client client, Depot depot) {
		this.date = date;
		this.netAmount = netAmount;
		this.vatBase = vatBase;
		this.vatPercentage = vatPercentage;
		this.totalAmount = totalAmount;
		this.notes = notes;
		this.items = items;
		this.client = client;
		this.depot = depot;
	}
	
	public Invoice(Long id, Date date, Double netAmount, Double vatBase, Long vatPercentage, Double totalAmount,
			String notes, List<Item> items, Client client, Depot depot, Date createdAt, Date updatedAt,	Long updatedBy) {
		this.id = id;
		this.date = date;
		this.netAmount = netAmount;
		this.vatBase = vatBase;
		this.vatPercentage = vatPercentage;
		this.totalAmount = totalAmount;
		this.notes = notes;
		this.items = items;
		this.client = client;
		this.depot = depot;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
	}

	public Invoice () {
		
	}

	/**
	 * Getters and Setters
	 * 
	 */	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public Double getVatBase() {
		return vatBase;
	}

	public void setVatBase(Double vatBase) {
		this.vatBase = vatBase;
	}

	public Long getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(Long vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
		
	@Override
	public String toString() {
				
		String invoiceDetails = String.format("Invoice [id=%d, date='%s']%n", id, date);
		
		invoiceDetails+=", netAmount=" + netAmount + ", vatBase=" + vatBase
					+ ", vatPercentage=" + vatPercentage + ", totalAmount=" + totalAmount + ", notes=" + notes 
					+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]%n";
			
		if(items != null) {
			for(Item item : items) {
				invoiceDetails += String.format("Items [id=%d, description='%s']%n", item.getId(), item.getDescription());
			}
		}
		
		if(client != null) {
			invoiceDetails += String.format("Client [id=%d, nid='%s', name='%s', phone='%s']%n", client.getId(), client.getNid(), client.getName(), client.getPhone());
		}
		
		if(depot != null) {
			invoiceDetails += String.format("Depot [id=%d, name='%s', size='%s', location='%s']%n", depot.getId(), depot.getName(), depot.getSize(), depot.getLocation());
		}
		
		return invoiceDetails;
	}		
}
