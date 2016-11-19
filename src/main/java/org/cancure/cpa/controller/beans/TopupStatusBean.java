package org.cancure.cpa.controller.beans;

import java.util.List;

public class TopupStatusBean {

	private String status;
	private Integer pidn;
	private Integer patientVisitId;
	private List<PatientApprovalBean> patientApproval;
	public Integer getPatientVisitId() {
		return patientVisitId;
	}
	public void setPatientVisitId(Integer patientVisitId) {
		this.patientVisitId = patientVisitId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPidn() {
		return pidn;
	}
	public void setPidn(Integer pidn) {
		this.pidn = pidn;
	}
	public List<PatientApprovalBean> getPatientApproval() {
		return patientApproval;
	}
	public void setPatientApproval(List<PatientApprovalBean> patientApproval) {
		this.patientApproval = patientApproval;
	}
}
