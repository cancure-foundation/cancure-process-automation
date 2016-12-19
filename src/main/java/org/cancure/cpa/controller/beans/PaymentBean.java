package org.cancure.cpa.controller.beans;

import java.util.List;

public class PaymentBean {

	private List<Integer> selectedInvoiceIds;
	
	private Double amount;
	
	private String mode;
	
	private String chequeNo;
	
	private String comments;
	
	private Integer toAccountTypeId;
	
	private Integer toAccountHolderId;
	
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

	public List<Integer> getSelectedInvoiceIds() {
		return selectedInvoiceIds;
	}

	public void setSelectedInvoiceIds(List<Integer> selectedInvoiceIds) {
		this.selectedInvoiceIds = selectedInvoiceIds;
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
