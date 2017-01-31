package org.cancure.cpa.controller.beans;

import java.sql.Timestamp;

public class PatientVisitForwardsBean {

	private Integer id;
	private Integer pidn;
	private PatientBean patient;
	private Integer patientVisitId;
	private Integer accountTypeId;
	private String accountTypeName;
	private Integer accountHolderId;
	private Timestamp date;
	private String billStatus;
	
	public String getAccountTypeName() {
		return accountTypeName;
	}
	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}
	public PatientBean getPatient() {
		return patient;
	}
	public void setPatient(PatientBean patient) {
		this.patient = patient;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPidn() {
		return pidn;
	}
	public void setPidn(Integer pidn) {
		this.pidn = pidn;
	}
	public Integer getPatientVisitId() {
		return patientVisitId;
	}
	public void setPatientVisitId(Integer patientVisitId) {
		this.patientVisitId = patientVisitId;
	}
	public Integer getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(Integer accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public Integer getAccountHolderId() {
		return accountHolderId;
	}
	public void setAccountHolderId(Integer accountHolderId) {
		this.accountHolderId = accountHolderId;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
    public String getBillStatus() {
        return billStatus;
    }
    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }
	
}
