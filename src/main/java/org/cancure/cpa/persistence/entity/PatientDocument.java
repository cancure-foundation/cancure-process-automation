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
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "patient_document")
public class PatientDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="doc_id")
    private Integer docId;
    
    
    @Column(name="doc_category")
    private String docCategory;
    
    
    @Column(name="doc_type")
    private String docType;
    
    
    @Column(name="doc_path")
    private String docPath;
    
    /*@JsonIgnore
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="prn")
    private Patient patientDoc;*/
    @Column(name="prn")
    private Integer prn;

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

    public Integer getPrn() {
        return prn;
    }

    public void setPrn(Integer prn) {
        this.prn = prn;
    }

    /*public Patient getPatientDoc() {
        return patientDoc;
    }

    public void setPatientDoc(Patient patientDoc) {
        this.patientDoc = patientDoc;
    }*/
    
    
}
