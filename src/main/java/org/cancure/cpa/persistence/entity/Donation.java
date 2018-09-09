package org.cancure.cpa.persistence.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="payment_details")
public class Donation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pay_id")
	private BigInteger payId;
	
	@Column(name="transaction_id")
	private String transactionId;

	@Column(name="product_name")
	private String productName;
	
	@Column(name="product_quantity")
	private Integer productQuantity;
	
	@Column(name="product_amount")
	private String productAmount;
	
	
	@Column(name="payer_fname")
	private String payerFname;
	
	
	
	@Column(name="message")
	private String message;
	
	
	@Column(name="payer_address")
	private String payerAddress;
	
	
	@Column(name="payer_city")
	private String payerCity;
	
	
	@Column(name="payer_state")
	private String payerState;
	
	
	@Column(name="payer_zip")
	private String payerZip;
	
	
	@Column(name="payer_country")
	private String payerCountry;
	
	
	@Column(name="organisation")
	private String organisation;
	
	
	
	@Column(name="designation")
	private String designation;
	
	
	@Column(name="mobile")
	private String mobile;
	
	
	@Column(name="telo")
	private String telo;
	
	
	@Column(name="telr")
	private String telr;
	
	
	@Column(name="payer_email")
	private String payerEmail;
	
	
	@Column(name="status")
	private String status;
	
	@Column(name="order_status")
	private String orderStatus;
	
	@Column(name="order_id")
	private String orderId;
	
	@Column(name="payment_mode")
	private String paymentMode;
	
	@Column(name="status_message")
	private String statusMessage;
	
	@Column(name="failure_message")
	private String failureMessage;
	
	@Column(name="tracking_id")
	private String trackingId;

	public BigInteger getPayId() {
		return payId;
	}

	public void setPayId(BigInteger payId) {
		this.payId = payId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(String productAmount) {
		this.productAmount = productAmount;
	}

	public String getPayerFname() {
		return payerFname;
	}

	public void setPayerFname(String payerFname) {
		this.payerFname = payerFname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPayerAddress() {
		return payerAddress;
	}

	public void setPayerAddress(String payerAddress) {
		this.payerAddress = payerAddress;
	}

	public String getPayerCity() {
		return payerCity;
	}

	public void setPayerCity(String payerCity) {
		this.payerCity = payerCity;
	}

	public String getPayerState() {
		return payerState;
	}

	public void setPayerState(String payerState) {
		this.payerState = payerState;
	}

	public String getPayerZip() {
		return payerZip;
	}

	public void setPayerZip(String payerZip) {
		this.payerZip = payerZip;
	}

	public String getPayerCountry() {
		return payerCountry;
	}

	public void setPayerCountry(String payerCountry) {
		this.payerCountry = payerCountry;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelo() {
		return telo;
	}

	public void setTelo(String telo) {
		this.telo = telo;
	}

	public String getTelr() {
		return telr;
	}

	public void setTelr(String telr) {
		this.telr = telr;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((payId == null) ? 0 : payId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Donation other = (Donation) obj;
		if (payId == null) {
			if (other.payId != null)
				return false;
		} else if (!payId.equals(other.payId))
			return false;
		return true;
	}
}
