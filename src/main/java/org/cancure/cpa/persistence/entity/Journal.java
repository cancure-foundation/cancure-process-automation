package org.cancure.cpa.persistence.entity;

import java.sql.Timestamp;

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
@Table(name="journal")
public class Journal {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Timestamp date;
	
	@OneToOne (fetch = FetchType.EAGER)
	@JoinColumn(name="from_account_type_id")
	private AccountTypes fromAccountTypeId;
	
	@Column(name="from_account_holder_id")
	private Integer fromAccountHolderId; 
	
	@OneToOne (fetch = FetchType.EAGER)
	@JoinColumn(name="to_account_type_id")
	private AccountTypes toAccountTypeId;
	
	@Column(name="to_account_holder_id")
	private Integer toAccountHolderId; 
	
	private Double amount;
	
	private String mode;
	
	@Column(name="cheque_no")
	private String chequeNo;
	
	private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public AccountTypes getFromAccountTypeId() {
		return fromAccountTypeId;
	}

	public void setFromAccountTypeId(AccountTypes fromAccountTypeId) {
		this.fromAccountTypeId = fromAccountTypeId;
	}

	public Integer getFromAccountHolderId() {
		return fromAccountHolderId;
	}

	public void setFromAccountHolderId(Integer fromAccountHolderId) {
		this.fromAccountHolderId = fromAccountHolderId;
	}

	public AccountTypes getToAccountTypeId() {
		return toAccountTypeId;
	}

	public void setToAccountTypeId(AccountTypes toAccountTypeId) {
		this.toAccountTypeId = toAccountTypeId;
	}

	public Integer getToAccountHolderId() {
		return toAccountHolderId;
	}

	public void setToAccountHolderId(Integer toAccountHolderId) {
		this.toAccountHolderId = toAccountHolderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
