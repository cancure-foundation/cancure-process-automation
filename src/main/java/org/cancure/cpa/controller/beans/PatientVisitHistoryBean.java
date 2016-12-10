package org.cancure.cpa.controller.beans;

import java.util.List;
import java.util.Map;

import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.cancure.cpa.persistence.entity.PatientApproval;

public class PatientVisitHistoryBean {

	private Map<String, String> task;
	
	private PatientBean patientBean;
	
	private PatientVisitBean patientVisit;
	
	List<PatientApprovalBean> patientApprovals;
	
	List<InvoicesBean> invoicesList;

	public Map<String, String> getTask() {
		return task;
	}

	public void setTask(Map<String, String> task) {
		this.task = task;
	}

	public PatientVisitBean getPatientVisit() {
		return patientVisit;
	}

	public void setPatientVisit(PatientVisitBean patientVisit) {
		this.patientVisit = patientVisit;
	}

	public PatientBean getPatientBean() {
		return patientBean;
	}

	public void setPatientBean(PatientBean patientBean) {
		this.patientBean = patientBean;
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
}
