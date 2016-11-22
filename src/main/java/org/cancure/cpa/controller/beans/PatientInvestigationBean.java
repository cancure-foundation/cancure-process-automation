package org.cancure.cpa.controller.beans;

import java.sql.Timestamp;


public class PatientInvestigationBean {

    private String prn;
    private String investigatorType;
    private String investigatorId;
    private Timestamp investigationDate;
    private String status;
    private String comments;
    private String taskId;
    private int hospitalCostEstimate;
    private int medicalCostEstimate;

    public String getInvestigatorType() {
        return investigatorType;
    }

    public void setInvestigatorType(String investigatorType) {
        this.investigatorType = investigatorType;
    }

    public String getInvestigatorId() {
        return investigatorId;
    }

    public void setInvestigatorId(String investigatorId) {
        this.investigatorId = investigatorId;
    }    

    public Timestamp getInvestigationDate() {
        return investigationDate;
    }

    public void setInvestigationDate(Timestamp investigationDate) {
        this.investigationDate = investigationDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPrn() {
        return prn;
    }

    public void setPrn(String prn) {
        this.prn = prn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getHospitalCostEstimate() {
        return hospitalCostEstimate;
    }

    public void setHospitalCostEstimate(int hospitalCostEstimate) {
        this.hospitalCostEstimate = hospitalCostEstimate;
    }

    public int getMedicalCostEstimate() {
        return medicalCostEstimate;
    }

    public void setMedicalCostEstimate(int medicalCostEstimate) {
        this.medicalCostEstimate = medicalCostEstimate;
    }
    

}