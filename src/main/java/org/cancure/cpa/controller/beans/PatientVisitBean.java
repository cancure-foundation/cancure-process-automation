package org.cancure.cpa.controller.beans;

import java.util.Date;
import java.util.List;

public class PatientVisitBean {

	private Long id;
	private Integer pidn;
	private Date date;
	private String accountTypeId;
	private String accountHolderId;
	private String taskId;
	private String status;
	private String topupNeeded;
	
	public String getTopupNeeded() {
		return topupNeeded;
	}
	public void setTopupNeeded(String topupNeeded) {
		this.topupNeeded = topupNeeded;
	}
	private List<PatientVisitDocumentBean> patientHospitalVisitDocumentBeanList;
	
	public List<PatientVisitDocumentBean> getPatientHospitalVisitDocumentBeanList() {
		return patientHospitalVisitDocumentBeanList;
	}
	public void setPatientHospitalVisitDocumentBeanList(
			List<PatientVisitDocumentBean> patientHospitalVisitDocumentBeanList) {
		this.patientHospitalVisitDocumentBeanList = patientHospitalVisitDocumentBeanList;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPidn() {
		return pidn;
	}
	public void setPidn(Integer pidn) {
		this.pidn = pidn;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public String getAccountHolderId() {
		return accountHolderId;
	}
	public void setAccountHolderId(String accountHolderId) {
		this.accountHolderId = accountHolderId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
