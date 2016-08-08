package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="doctor_id")
    private Integer doctorId;
	@NotEmpty
	private String name;

	private String specification;

	@NotEmpty
	private String address;

	private String contact;

	private String email;

	@NotNull
	private Boolean enabled;

	public Doctor() {

	}

	public Doctor(Doctor doctor) {
		super();
		this.doctorId = doctor.getDoctorId();
		this.name = doctor.getName();
		this.specification = doctor.getSpecification();
		this.address = doctor.getAddress();
		this.contact = doctor.getContact();
		this.email = doctor.getEmail();
		this.enabled = doctor.getEnabled();
	}

	
	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
