package org.cancure.cpa.persistence.entity;

import java.sql.Date;

import javax.management.loading.PrivateClassLoader;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

@Entity
public class PatientCamp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="patient_camp_id")
	private Integer uid;                       //unique id 
	private String name;
	private Date  DOB;
	private String gender ;
	private String contact;
	private Integer  testsConducted;
	private Integer campId;
	
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		gender = gender;
	}
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Integer getTestsConducted() {
		return testsConducted;
	}
	public void setTestsConducted(Integer testsConducted) {
		this.testsConducted = testsConducted;
	}
	public Integer getCampId() {
		return campId;
	}
	public void setCampId(Integer campId) {
		this.campId = campId;
	}


}
