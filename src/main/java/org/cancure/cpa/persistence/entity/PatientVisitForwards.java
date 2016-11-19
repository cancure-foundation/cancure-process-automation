package org.cancure.cpa.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="patient_visit_forwards")
public class PatientVisitForwards {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="pidn", nullable = false)
    private Integer pidn;
	
	@Column(name="patient_visit_id", nullable = false)
	private Integer patientVisitId;
	
	@ManyToOne
	@JoinColumn(name="account_type_id")
	private AccountTypes accountTypeId;
	
	@Column(name="account_holder_id", nullable = false)
	private Integer accountHolderId;

	private Timestamp date;
	
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
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

	public AccountTypes getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(AccountTypes accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public Integer getAccountHolderId() {
		return accountHolderId;
	}

	public void setAccountHolderId(Integer accountHolderId) {
		this.accountHolderId = accountHolderId;
	}
}
