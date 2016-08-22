package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Hospital {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="hospital_id")
    private Integer hospitalId;
	@NotEmpty
	private String name;

	@NotEmpty
	private String address;

	private String contact;

	@NotNull
	private Boolean enabled;

	public Hospital() {

	}

	public Hospital(Hospital doctor) {
		super();
		this.hospitalId = doctor.getHospitalId();
		this.name = doctor.getName();this.address = doctor.getAddress();
		this.contact = doctor.getContact();
		this.enabled = doctor.getEnabled();
	}

	public Integer getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
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
