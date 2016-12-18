package org.cancure.cpa.controller.beans;

import java.sql.Timestamp;

import org.cancure.cpa.persistence.entity.AccountTypes;

public class InvoicesBean {

	private Integer id;

	private Timestamp date;

	private Integer pidn;
	
	private Integer fromAccountTypeId;

	private Integer fromAccountHolderId;

	private String fromAccountHolderName;

	private Integer toAccountTypeId;

	private Integer toAccountHolderId;

	private Double amount;

	private String status;

	private Timestamp closedDate;

	private Double balanceAmount;

	private String partnerBillNo;

	private Double partnerBillAmount;

	private String comments;

	private Long journalId; 
	
	public Long getJournalId() {
		return journalId;
	}

	public void setJournalId(Long journalId) {
		this.journalId = journalId;
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

	public String getFromAccountHolderName() {
		return fromAccountHolderName;
	}

	public void setFromAccountHolderName(String fromAccountHolderName) {
		this.fromAccountHolderName = fromAccountHolderName;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Timestamp closedDate) {
		this.closedDate = closedDate;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getPartnerBillNo() {
		return partnerBillNo;
	}

	public void setPartnerBillNo(String partnerBillNo) {
		this.partnerBillNo = partnerBillNo;
	}

	public Double getPartnerBillAmount() {
		return partnerBillAmount;
	}

	public void setPartnerBillAmount(Double partnerBillAmount) {
		this.partnerBillAmount = partnerBillAmount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
