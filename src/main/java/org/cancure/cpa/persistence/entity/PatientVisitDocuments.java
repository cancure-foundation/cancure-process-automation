package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="patient_visit_documents")
public class PatientVisitDocuments {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="doc_id")
	private Integer docId;
	
	/**
	 * Who the document is for? Medicine/Lab prescriptions.
	 */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="account_type_id")
	private AccountTypes accountTypes;
	
	@Column(name="doc_type")
	private String docType;
	
	@Column(name="doc_path")
	private String docPath;
	
	@ManyToOne (fetch = FetchType.LAZY) 
	@JoinColumn(name="patient_visit_id")
	private PatientVisit patientVisit;

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public AccountTypes getAccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(AccountTypes accountTypes) {
		this.accountTypes = accountTypes;
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

	public PatientVisit getPatientVisit() {
		return patientVisit;
	}

	public void setPatientVisit(PatientVisit patientVisit) {
		this.patientVisit = patientVisit;
	}
}
