package org.cancure.cpa.controller.beans;

import org.springframework.web.multipart.MultipartFile;

public class PatientDocumentBean {

    private Integer docId;
    private String prn;
    private String docCategory;
    private String docType;
    private String docPath;
    private String taskId;
    private MultipartFile patientFile;

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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public MultipartFile getPatientFile() {
        return patientFile;
    }

    public void setPatientFile(MultipartFile patientFile) {
        this.patientFile = patientFile;
    }

    public String getPrn() {
        return prn;
    }

    public void setPrn(String prn) {
        this.prn = prn;
    }

}
