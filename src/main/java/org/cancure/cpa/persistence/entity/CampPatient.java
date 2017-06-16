package org.cancure.cpa.persistence.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "camp_patient")
public class CampPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "camp_patient_id")
    private Integer campPatientId;

    private String uid;

    private String name;

    private Date dob;

    private String gender;

    private String phone;

    @Column(name = "camp_id")
    private Integer campId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Integer getCampPatientId() {
        return campPatientId;
    }

    public void setCampPatientId(Integer campPatientId) {
        this.campPatientId = campPatientId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getCampId() {
        return campId;
    }

    public void setCampId(Integer campId) {
        this.campId = campId;
    }

}
