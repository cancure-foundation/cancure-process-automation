package org.cancure.cpa.persistence.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="approvals")
public class PatientApproval {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	private Timestamp date;
	
	@Column(name="pidn", nullable = false)
    private Integer pidn;
	
	private Double amount;
	
	@Column(name="expiry_date")
	private Date expiryDate;
	
	@OneToOne (fetch = FetchType.EAGER)
	@JoinColumn(name="approved_for_account_type_id")
	private AccountTypes approvedForAccountType;

	@Column(name="patient_visit_id")
	private Integer patientVisitId;
	
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

	public Integer getPidn() {
		return pidn;
	}

	public void setPidn(Integer pidn) {
		this.pidn = pidn;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public AccountTypes getApprovedForAccountType() {
		return approvedForAccountType;
	}

	public void setApprovedForAccountType(AccountTypes approvedForAccountType) {
		this.approvedForAccountType = approvedForAccountType;
	}

}
