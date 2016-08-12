package org.cancure.cpa.controller.beans;

import java.util.List;

public class PatientDocumentAndInvestigationBean {
	
	private List<PatientDocumentBean> patientDocumentBean;
	
	private PatientInvestigationBean patientInvestigationBean;

	public PatientInvestigationBean getPatientInvestigationBean() {
		return patientInvestigationBean;
	}

	public List<PatientDocumentBean> getPatientDocumentBean() {
		return patientDocumentBean;
	}

	public void setPatientDocumentBean(List<PatientDocumentBean> patientDocumentBean) {
		this.patientDocumentBean = patientDocumentBean;
	}

	public void setPatientInvestigationBean(PatientInvestigationBean patientInvestigationBean) {
		this.patientInvestigationBean = patientInvestigationBean;
	}
}
