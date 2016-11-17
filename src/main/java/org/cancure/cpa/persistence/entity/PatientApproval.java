package org.cancure.cpa.persistence.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumn(name="approved_for_account_type_id")
	private AccountTypes approvedForAccountType;

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
