package org.cancure.cpa.controller.beans;

import java.sql.Timestamp;

import javax.persistence.Column;

public class JournalBean {

	private Long id;
	
	private Timestamp date;
	
	private Integer fromAccountTypeId;
	
	private Integer fromAccountHolderId; 
	
	private Integer toAccountTypeId;
	
	private Integer toAccountHolderId;

	private Double amount;
	
	private String mode;
	
	private String chequeNo;
	
	private String comments;

	
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

	public Integer getFromAccountTypeId() {
		return fromAccountTypeId;
	}

	public void setFromAccountTypeId(Integer fromAccountTypeId) {
		this.fromAccountTypeId = fromAccountTypeId;
	}

	public Integer getFromAccountHolderId() {
		return fromAccountHolderId;
	}

	public void setFromAccountHolderId(Integer fromAccountHolderId) {
		this.fromAccountHolderId = fromAccountHolderId;
	}

	public Integer getToAccountTypeId() {
		return toAccountTypeId;
	}

	public void setToAccountTypeId(Integer toAccountTypeId) {
		this.toAccountTypeId = toAccountTypeId;
	}

	public Integer getToAccountHolderId() {
		return toAccountHolderId;
	}

	public void setToAccountHolderId(Integer toAccountHolderId) {
		this.toAccountHolderId = toAccountHolderId;
	} 
}
