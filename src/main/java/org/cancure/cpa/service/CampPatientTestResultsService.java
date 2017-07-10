package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.controller.beans.CampPatientTestResultsBean;
import org.cancure.cpa.controller.beans.CampPatientTestResultsBeanList;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;

public interface CampPatientTestResultsService {

    void saveTestResult(CampPatientTestResultsBeanList campPatientTestResults);

    Iterable<CampPatientTestResults> listTestResults();
    
    List<CampPatientTestResultsBean> getTestResultsByPatientId(Integer campPatientId);

}
