package com.api.autonomo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Items")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String description;

	private Long quantity;
	private Double unitCost;
	private Double amount;

	// @JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false)
	private Invoice invoice;

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
	public Item(String description, Long quantity, Double unitCost, Double amount) {
		this.description = description;
		this.quantity = quantity;
		this.unitCost = unitCost;
		this.amount = amount;
	}

	public Item(String description, Long quantity, Double unitCost, Double amount, Invoice invoice) {
		this.description = description;
		this.quantity = quantity;
		this.unitCost = unitCost;
		this.amount = amount;
		this.invoice = invoice;
	}

	public Item(Long id, String description, Long quantity, Double unitCost, Double amount, Invoice invoice,
			Date createdAt, Date updatedAt, Long updatedBy) {
		this.id = id;
		this.description = description;
		this.quantity = quantity;
		this.unitCost = unitCost;
		this.amount = amount;
		this.invoice = invoice;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
	}

	public Item() {

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

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", description=" + description + ", quantity=" + quantity + ", unitCost=" + unitCost
				+ ", amount=" + amount + ", invoice=" + invoice + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", updatedBy=" + updatedBy + "]";
	}

}
