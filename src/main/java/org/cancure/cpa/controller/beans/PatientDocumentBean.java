package org.cancure.cpa.controller.beans;

import org.springframework.web.multipart.MultipartFile;

public class PatientDocumentBean {
    
    private Integer prn;
    private String docCategory;
    private String docType;
    private String docPath;
    private MultipartFile patientFile;

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

    public MultipartFile getPatientFile() {
        return patientFile;
    }

    public void setPatientFile(MultipartFile patientFile) {
        this.patientFile = patientFile;
    }

    public Integer getPrn() {
        return prn;
    }

    public void setPrn(Integer prn) {
        this.prn = prn;
    }
    
    
}
