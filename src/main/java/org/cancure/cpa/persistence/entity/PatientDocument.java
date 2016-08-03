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

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "patient_document")
public class PatientDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="doc_id")
    private Integer docId;
    
    @NotEmpty
    @Column(name="doc_category")
    private String docCategory;
    
    @NotEmpty
    @Column(name="doc_type")
    private String docType;
    
    @NotEmpty
    @Column(name="doc_path")
    private String docPath;

    @JsonIgnore
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="prn")
    private Patient patientDoc;

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getDocCategory() {
        return docCategory;
    }

    public void setDocCategory(String docCategory) {
        this.docCategory = docCategory;
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

    public Patient getPatientDoc() {
        return patientDoc;
    }

    public void setPatientDoc(Patient patientDoc) {
        this.patientDoc = patientDoc;
    }

    
}
