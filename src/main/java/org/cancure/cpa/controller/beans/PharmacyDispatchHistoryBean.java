package org.cancure.cpa.controller.beans;

import java.util.List;

import org.cancure.cpa.persistence.entity.PatientBills;

public class PharmacyDispatchHistoryBean {

	private PatientVisitBean patientVisitBean;
	private PatientVisitForwardsBean patientVisitForwards;
	private List<PatientApprovalBean> patientApprovals;
	private List<InvoicesBean> invoicesList;
	private PatientBean patient;
	private Double balance;
	private List<PatientBills> patientBills;
	public PatientBean getPatient() {
		return patient;
	}
	public void setPatient(PatientBean patient) {
		this.patient = patient;
	}
	public PatientVisitForwardsBean getPatientVisitForwards() {
		return patientVisitForwards;
	}
	public void setPatientVisitForwards(PatientVisitForwardsBean patientVisitForwards) {
		this.patientVisitForwards = patientVisitForwards;
	}
	public PatientVisitBean getPatientVisitBean() {
		return patientVisitBean;
	}
	public void setPatientVisitBean(PatientVisitBean patientVisitBean) {
		this.patientVisitBean = patientVisitBean;
	}
	public List<PatientApprovalBean> getPatientApprovals() {
		return patientApprovals;
	}
	public void setPatientApprovals(List<PatientApprovalBean> patientApprovals) {
		this.patientApprovals = patientApprovals;
	}
	public List<InvoicesBean> getInvoicesList() {
		return invoicesList;
	}
	public void setInvoicesList(List<InvoicesBean> invoicesList) {
		this.invoicesList = invoicesList;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public List<PatientBills> getPatientBills() {
		return patientBills;
	}
	public void setPatientBills(List<PatientBills> patientBills) {
		this.patientBills = patientBills;
	}
	
}
