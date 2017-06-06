package org.cancure.cpa.persistence.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Camp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="camp_id")
	private Integer campId;
	private String campPlace;
	private Date campDate;
	private Integer medicalTeam;   // it is a dynamic field The existing LIST and LIST_VALUE tables can be used for storing Medical Team
	private String localPartner;
	private String name;
	private String pocContact;
	private String email;
	private int patientCount;    
	
	public Camp(Integer campId, String campPlace, Date campDate, Integer medicalTeam, String localPartner, String name,
			String pocContact, String email, int patientCount) {
		super();
		this.campId = campId;
		this.campPlace = campPlace;
		this.campDate = campDate;
		this.medicalTeam = medicalTeam;
		this.localPartner = localPartner;
		this.name = name;
		this.pocContact = pocContact;
		this.email = email;
		this.patientCount = patientCount;
	}
	
	
	public Integer getCampId() {
		return campId;
	}
	public void setCampId(Integer campId) {
		this.campId = campId;
	}
	public String getCampPlace() {
		return campPlace;
	}
	public void setCampPlace(String campPlace) {
		this.campPlace = campPlace;
	}
	public Date getCampDate() {
		return campDate;
	}
	public void setCampDate(Date campDate) {
		this.campDate = campDate;
	}
	public Integer getMedicalTeam() {
		return medicalTeam;
	}
	public void setMedicalTeam(Integer medicalTeam) {
		this.medicalTeam = medicalTeam;
	}
	public String getLocalPartner() {
		return localPartner;
	}
	public void setLocalPartner(String localPartner) {
		this.localPartner = localPartner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPocContact() {
		return pocContact;
	}
	public void setPocContact(String pocContact) {
		this.pocContact = pocContact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPatientCount() {
		return patientCount;
	}
	public void setPatientCount(int patientCount) {
		this.patientCount = patientCount;
	}
	

	}
	
	
	

