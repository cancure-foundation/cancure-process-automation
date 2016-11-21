package org.cancure.cpa.controller.beans;

import org.springframework.web.multipart.MultipartFile;

public class PatientVisitDocumentBean {

	private Long docId;
	private String docType;
	private String docPath;
	private Integer accountTypeId;
	private MultipartFile patientVisitFile;

	public MultipartFile getPatientVisitFile() {
		return patientVisitFile;
	}

	public void setPatientVisitFile(MultipartFile patientVisitFile) {
		this.patientVisitFile = patientVisitFile;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public Integer getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Integer accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
}
