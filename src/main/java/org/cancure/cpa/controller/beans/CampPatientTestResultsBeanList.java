package org.cancure.cpa.controller.beans;

import java.util.List;

public class CampPatientTestResultsBeanList {

    private Integer campId;
	private List<CampPatientTestResultsBean> campPatientTestResultsBeanList;

	
	public Integer getCampId() {
        return campId;
    }

    public void setCampId(Integer campId) {
        this.campId = campId;
    }

    public List<CampPatientTestResultsBean> getCampPatientTestResultsBeanList() {
		return campPatientTestResultsBeanList;
	}

	public void setCampPatientTestResultsBeanList(List<CampPatientTestResultsBean> campPatientTestResultsBean) {
		this.campPatientTestResultsBeanList = campPatientTestResultsBean;
	}
	
}
