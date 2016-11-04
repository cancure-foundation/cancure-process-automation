package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Pharmacy {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pharmacy_id")
	private Integer pharmacyId;
	@NotEmpty
	private String name;
	@NotEmpty
	private String address;
	private String contact;
	@NotNull
	private Boolean enabled;
		
	public Pharmacy() {
		// TODO Auto-generated constructor stub
	}

	public Integer getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Integer pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	
	
	}


