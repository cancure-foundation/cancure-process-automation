package org.cancure.cpa.controller.beans;

import org.springframework.web.multipart.MultipartFile;

public class PharmacyInvoiceBean {

	private Integer pidn;
	private Double amount;
	private Double partnerBillAmount;
	private String partnerBillNo;
	private String comments;
	private MultipartFile partnerBillFile;
	
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
	public Double getPartnerBillAmount() {
		return partnerBillAmount;
	}
	public void setPartnerBillAmount(Double partnerBillAmount) {
		this.partnerBillAmount = partnerBillAmount;
	}
	public String getPartnerBillNo() {
		return partnerBillNo;
	}
	public void setPartnerBillNo(String partnerBillNo) {
		this.partnerBillNo = partnerBillNo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
    public MultipartFile getPartnerBillFile() {
        return partnerBillFile;
    }
    public void setPartnerBillFile(MultipartFile partnerBillFile) {
        this.partnerBillFile = partnerBillFile;
    }
	
}
