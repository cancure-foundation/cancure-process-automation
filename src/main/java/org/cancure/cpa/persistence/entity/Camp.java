package org.cancure.cpa.persistence.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Camp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "camp_id")
    private Integer campId;
    
    @Column(name = "camp_name")
    private String campName;
    
    @Column(name = "camp_place")
    private String campPlace;
    
    @Column(name = "camp_date")
    private Date campDate;
    
    @Column(name = "medical_team")
    private String medicalTeam; 
    
    @Column(name = "local_poc_name")
    private String pocName;
    
    @Column(name = "local_poc_phone")
    private String pocPhone;
    
    @Column(name = "local_poc_email")
    private String pocEmail;
    
    @Column(name = "patient_count")
    private Integer patientCount;

    public Integer getCampId() {
        return campId;
    }

    public void setCampId(Integer campId) {
        this.campId = campId;
    }

    public String getCampPlace() {
        return campPlace;
    }

    public void setCampPlace(String campPlace) {
        this.campPlace = campPlace;
    }

    public Date getCampDate() {
        return campDate;
    }

    public void setCampDate(Date campDate) {
        this.campDate = campDate;
    }

    public String getMedicalTeam() {
        return medicalTeam;
    }

    public void setMedicalTeam(String medicalTeam) {
        this.medicalTeam = medicalTeam;
    }

    public String getPocName() {
        return pocName;
    }

    public void setPocName(String pocName) {
        this.pocName = pocName;
    }

    public String getPocPhone() {
        return pocPhone;
    }

    public void setPocPhone(String pocPhone) {
        this.pocPhone = pocPhone;
    }

    public String getPocEmail() {
        return pocEmail;
    }

    public void setPocEmail(String pocEmail) {
        this.pocEmail = pocEmail;
    }

    public void setPatientCount(Integer patientCount) {
        this.patientCount = patientCount;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

}
