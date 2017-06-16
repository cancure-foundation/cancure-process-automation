package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.List;

import org.cancure.cpa.controller.beans.CampPatientBean;
import org.cancure.cpa.controller.beans.CampPatientTestResultsBean;
import org.cancure.cpa.persistence.entity.CampPatient;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.persistence.repository.CampPatientTestResultsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("campPatientTestResultsService")
public class CampPatientTestResultsServiceImpl implements CampPatientTestResultsService {

    @Autowired
    CampPatientTestResultsRepository testResultsRepository;

    @Override
    public CampPatientTestResults saveTestResult(CampPatientTestResults campPatientTestResults) {

        return testResultsRepository.save(campPatientTestResults);
    }

    @Override
    public Iterable<CampPatientTestResults> listTestResults() {
        // TODO Auto-generated method stub
        return testResultsRepository.findAll();
    }

    @Override
    public List<CampPatientTestResultsBean> getTestResultsByPatientId(Integer campPatientId) {
        // TODO Auto-generated method stub
        List<CampPatientTestResults> testResultsList = testResultsRepository.findByCampPatientId(campPatientId);
        List<CampPatientTestResultsBean> testResultsBeanList = new ArrayList<>();
        for(CampPatientTestResults testResults: testResultsList){
            CampPatientTestResultsBean testResultsBean = new CampPatientTestResultsBean();
            BeanUtils.copyProperties(testResults, testResultsBean);
            testResultsBeanList.add(testResultsBean);
        }
        return testResultsBeanList;
    }

}
