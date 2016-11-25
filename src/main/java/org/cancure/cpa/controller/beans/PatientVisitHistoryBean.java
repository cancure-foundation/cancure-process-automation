package org.cancure.cpa.controller.beans;

import java.util.List;

import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.cancure.cpa.persistence.entity.PatientApproval;
import org.cancure.cpa.persistence.entity.PatientVisit;

public class PatientVisitHistoryBean {

	private PatientBean patientBean;
	
	private PatientVisit patientVisit;
	
	List<PatientApproval> patientApprovals;
	
	List<InvoicesEntity> invoicesList;

	public PatientVisit getPatientVisit() {
		return patientVisit;
	}

	public void setPatientVisit(PatientVisit patientVisit) {
		this.patientVisit = patientVisit;
	}

	public PatientBean getPatientBean() {
		return patientBean;
	}

	public void setPatientBean(PatientBean patientBean) {
		this.patientBean = patientBean;
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
}
