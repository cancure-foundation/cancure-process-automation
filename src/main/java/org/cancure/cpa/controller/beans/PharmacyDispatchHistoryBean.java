package org.cancure.cpa.controller.beans;

import java.util.List;

import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.cancure.cpa.persistence.entity.PatientApproval;
import org.cancure.cpa.persistence.entity.PatientVisitForwards;

public class PharmacyDispatchHistoryBean {

	private PatientVisitBean patientVisitBean;
	private PatientVisitForwards patientVisitForwards;
	private List<PatientApproval> patientApprovals;
	private List<InvoicesEntity> invoicesList;
	private PatientBean patient;
	private Double balance;
	public PatientBean getPatient() {
		return patient;
	}
	public void setPatient(PatientBean patient) {
		this.patient = patient;
	}
	public PatientVisitForwards getPatientVisitForwards() {
		return patientVisitForwards;
	}
	public void setPatientVisitForwards(PatientVisitForwards patientVisitForwards) {
		this.patientVisitForwards = patientVisitForwards;
	}
	public PatientVisitBean getPatientVisitBean() {
		return patientVisitBean;
	}
	public void setPatientVisitBean(PatientVisitBean patientVisitBean) {
		this.patientVisitBean = patientVisitBean;
	}
	public List<PatientApproval> getPatientApprovals() {
		return patientApprovals;
	}
	public void setPatientApprovals(List<PatientApproval> patientApprovals) {
		this.patientApprovals = patientApprovals;
	}
	public List<InvoicesEntity> getInvoicesList() {
		return invoicesList;
	}
	public void setInvoicesList(List<InvoicesEntity> invoicesList) {
		this.invoicesList = invoicesList;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}
