package org.cancure.cpa.controller.beans;

import java.util.Set;

import org.cancure.cpa.persistence.entity.CampPatientTestResults;

public class CampPatientBean {

    private Integer campPatientId;
    private String uid;
    private String name;
    private Integer age;
    private String gender;
    private String phone;
    private Integer campId;
    private Set<CampPatientTestResults> campPatientTestResults;

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

    public Integer getCampId() {
        return campId;
    }

    public void setCampId(Integer campId) {
        this.campId = campId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<CampPatientTestResults> getCampPatientTestResults() {
        return campPatientTestResults;
    }

    public void setCampPatientTestResults(Set<CampPatientTestResults> campPatientTestResults) {
        this.campPatientTestResults = campPatientTestResults;
    }

    
}
