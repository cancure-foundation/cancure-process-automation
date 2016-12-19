package org.cancure.cpa.controller.beans;

import java.util.List;

public class PharmacyInvoiceBean {

	private Integer pidn;
	private Double amount;
	private String comments;
	private List<PatientBillsBean> patientBillsBean;
	
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
    public List<PatientBillsBean> getPatientBillsBean() {
        return patientBillsBean;
    }
    public void setPatientBillsBean(List<PatientBillsBean> patientBillsBean) {
        this.patientBillsBean = patientBillsBean;
    }	
}
