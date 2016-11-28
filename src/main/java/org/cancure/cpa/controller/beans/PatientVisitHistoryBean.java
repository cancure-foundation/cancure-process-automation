package org.cancure.cpa.controller.beans;

import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.cancure.cpa.persistence.entity.PatientApproval;
import org.cancure.cpa.persistence.entity.PatientVisit;

public class PatientVisitHistoryBean {

	private Map<String, String> task;
	
	private PatientBean patientBean;
	
	private PatientVisit patientVisit;
	
	List<PatientApproval> patientApprovals;
	
	List<InvoicesEntity> invoicesList;

	public Map<String, String> getTask() {
		return task;
	}

	public void setTask(Map<String, String> task) {
		this.task = task;
	}

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
