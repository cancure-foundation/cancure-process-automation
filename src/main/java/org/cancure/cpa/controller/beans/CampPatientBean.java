package org.cancure.cpa.controller.beans;

import java.util.ArrayList;
import java.util.List;

public class CampPatientBean {

    private Long campPatientId; //Primary key
    private String uid;
    private String name;
    private Integer age;
    private String gender;
    private String phone;
    private Integer campId;
    private List<CampPatientTestResultsBean> campPatientTestResultsBean = new ArrayList<>();

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

    public Long getCampPatientId() {
        return campPatientId;
    }

    public void setCampPatientId(Long campPatientId) {
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

	public List<CampPatientTestResultsBean> getCampPatientTestResultsBean() {
		return campPatientTestResultsBean;
	}

	public void setCampPatientTestResultsBean(List<CampPatientTestResultsBean> campPatientTestResultsBean) {
		this.campPatientTestResultsBean = campPatientTestResultsBean;
	}

}
