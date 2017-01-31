package org.cancure.cpa.controller.beans;

import java.util.List;

public class PharmacyInvoiceBean {

	private Integer pidn;
	private Double amount;
	private String comments;
	private List<PatientBillsBean> patientBillsBean;
	private String billStatus;
	private Integer patientVisitId;
	
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
    public String getBillStatus() {
        return billStatus;
    }
    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }
    public Integer getPatientVisitId() {
        return patientVisitId;
    }
    public void setPatientVisitId(Integer patientVisitId) {
        this.patientVisitId = patientVisitId;
    }
    
}
