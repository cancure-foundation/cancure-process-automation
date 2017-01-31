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
    private Integer hospitalCostEstimate;
    private Integer medicalCostEstimate;
    private Integer hospitalCostApproved;
    private Integer medicalCostApproved;
    private String patientType;

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

    public Integer getHospitalCostEstimate() {
        return hospitalCostEstimate;
    }

    public void setHospitalCostEstimate(Integer hospitalCostEstimate) {
        this.hospitalCostEstimate = hospitalCostEstimate;
    }

    public Integer getMedicalCostEstimate() {
        return medicalCostEstimate;
    }

    public void setMedicalCostEstimate(Integer medicalCostEstimate) {
        this.medicalCostEstimate = medicalCostEstimate;
    }

    public Integer getHospitalCostApproved() {
        return hospitalCostApproved;
    }

    public void setHospitalCostApproved(Integer hospitalCostApproved) {
        this.hospitalCostApproved = hospitalCostApproved;
    }

    public Integer getMedicalCostApproved() {
        return medicalCostApproved;
    }

    public void setMedicalCostApproved(Integer medicalCostApproved) {
        this.medicalCostApproved = medicalCostApproved;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

}