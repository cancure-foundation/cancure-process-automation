package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.controller.beans.CampPatientTestResultsBean;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;

public interface CampPatientTestResultsService {

    CampPatientTestResults saveTestResult(CampPatientTestResults campPatientTestResults);

    Iterable<CampPatientTestResults> listTestResults();
    
    List<CampPatientTestResultsBean> getTestResultsByPatientId(Integer campPatientId);

}
