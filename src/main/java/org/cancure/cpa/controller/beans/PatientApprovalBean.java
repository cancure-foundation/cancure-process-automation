package org.cancure.cpa.controller.beans;

import java.sql.Timestamp;
import java.util.Date;

public class PatientApprovalBean {

	private Integer id;
	private Timestamp date;
	private String pidn;
	private Double amount;
	private Integer approvedForAccountTypeId;
	private Date expiryDate;
	private Integer patientVisitId;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPatientVisitId() {
		return patientVisitId;
	}
	public void setPatientVisitId(Integer patientVisitId) {
		this.patientVisitId = patientVisitId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getPidn() {
		return pidn;
	}
	public void setPidn(String pidn) {
		this.pidn = pidn;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getApprovedForAccountTypeId() {
		return approvedForAccountTypeId;
	}
	public void setApprovedForAccountTypeId(Integer approvedForAccountTypeId) {
		this.approvedForAccountTypeId = approvedForAccountTypeId;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}
