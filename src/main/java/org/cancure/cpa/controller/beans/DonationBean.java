package org.cancure.cpa.controller.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class DonationBean {

	private String tid;
	@NotNull
	@Size(max = 100)
	private String billing_name;
	
	@NotNull
	private String org;
	
	@NotNull
	private String desig;
	private String billing_address;
	
	@NotNull
	@Email
	private String billing_email;
	private String billing_zip;
	
	@NotNull
	private String billing_country;
	private String billing_state;
	private String billing_city;
	
	@NotNull
	private String billing_tel;
	private String telo;
	private String telr;
	private String merchant_id;
	
	@NotNull
	private String amount;
	
	@NotNull
	private String currency;
	
	@NotNull
	private String language;
	
	private String redirect_url;
	private String cancel_url;
	private String product_name;
	
	@NotNull
	private String order_id;
	
	@NotNull
	private String invoice;
	
	@NotNull
	private String product_id;
	
	@NotNull
	private String product_quantity;
	private String submit;
	private String workingKey;
	private String accessCode;
	
	public String getWorkingKey() {
		return workingKey;
	}
	public void setWorkingKey(String workingKey) {
		this.workingKey = workingKey;
	}
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getBilling_name() {
		return billing_name;
	}
	public void setBilling_name(String billing_name) {
		this.billing_name = billing_name;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getDesig() {
		return desig;
	}
	public void setDesig(String desig) {
		this.desig = desig;
	}
	public String getBilling_address() {
		return billing_address;
	}
	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
	}
	public String getBilling_email() {
		return billing_email;
	}
	public void setBilling_email(String billing_email) {
		this.billing_email = billing_email;
	}
	public String getBilling_zip() {
		return billing_zip;
	}
	public void setBilling_zip(String billing_zip) {
		this.billing_zip = billing_zip;
	}
	public String getBilling_country() {
		return billing_country;
	}
	public void setBilling_country(String billing_country) {
		this.billing_country = billing_country;
	}
	public String getBilling_state() {
		return billing_state;
	}
	public void setBilling_state(String billing_state) {
		this.billing_state = billing_state;
	}
	public String getBilling_city() {
		return billing_city;
	}
	public void setBilling_city(String billing_city) {
		this.billing_city = billing_city;
	}
	public String getBilling_tel() {
		return billing_tel;
	}
	public void setBilling_tel(String billing_tel) {
		this.billing_tel = billing_tel;
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
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRedirect_url() {
		return redirect_url;
	}
	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}
	public String getCancel_url() {
		return cancel_url;
	}
	public void setCancel_url(String cancel_url) {
		this.cancel_url = cancel_url;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_quantity() {
		return product_quantity;
	}
	public void setProduct_quantity(String product_quantity) {
		this.product_quantity = product_quantity;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
}
