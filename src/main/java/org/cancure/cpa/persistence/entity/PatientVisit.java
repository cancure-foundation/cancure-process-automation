package org.cancure.cpa.persistence.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="patient_visit")
public class PatientVisit {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="pidn",unique = true, nullable = false)
    private Integer pidn;
	
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="account_type_id")
	private AccountTypes accountTypes;
	
	@Column(name="account_holder_id")
	private Integer accountHolderId;
	
	@OneToMany(mappedBy="patientVisit", cascade = CascadeType.ALL)
	private List<PatientVisitDocuments> patientVisitDocumentsList;
	
	public List<PatientVisitDocuments> getPatientVisitDocumentsList() {
		return patientVisitDocumentsList;
	}

	public void setPatientVisitDocumentsList(List<PatientVisitDocuments> patientVisitDocumentsList) {
		this.patientVisitDocumentsList = patientVisitDocumentsList;
	}

	public Integer getAccountHolderId() {
		return accountHolderId;
	}

	public void setAccountHolderId(Integer accountHolderId) {
		this.accountHolderId = accountHolderId;
	}

	@Column(name="task_id")
    private String taskId;
	
	private String status;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public AccountTypes getAccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(AccountTypes accountTypes) {
		this.accountTypes = accountTypes;
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
