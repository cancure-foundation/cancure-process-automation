package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name="payment_details")
public class Donation {
	
	@Column(name="transaction_id")
	private String transactionId;

	@Column(name="product_name")
	private String productName;
	
	@Column(name="product_quantity")
	private String productQuantity;
	
	
	
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
	
}
