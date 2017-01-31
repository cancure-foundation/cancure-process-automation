package org.cancure.cpa.persistence.entity;

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
@Table(name="payment_workflow")
public class PaymentWorkflow {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne (fetch = FetchType.EAGER)
	@JoinColumn(name="to_account_type_id")
	private AccountTypes toAccountTypeId;
	
	@Column(name="to_account_holder_id")
	private Integer toAccountHolderId; 
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
