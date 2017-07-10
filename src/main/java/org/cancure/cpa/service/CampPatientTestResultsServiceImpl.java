package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.CampPatientTestResultsBean;
import org.cancure.cpa.controller.beans.CampPatientTestResultsBeanList;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.persistence.repository.CampPatientTestResultsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("campPatientTestResultsService")
public class CampPatientTestResultsServiceImpl implements CampPatientTestResultsService {

    @Autowired
    CampPatientTestResultsRepository testResultsRepository;

    @Transactional
    @Override
    public void saveTestResult(CampPatientTestResultsBeanList campPatientTestResultsList) {
    	if (campPatientTestResultsList != null && campPatientTestResultsList.getCampPatientTestResultsBeanList() != null && 
    			!campPatientTestResultsList.getCampPatientTestResultsBeanList().isEmpty()) {
    		for (CampPatientTestResultsBean bean : campPatientTestResultsList.getCampPatientTestResultsBeanList()) {
    			CampPatientTestResults entity = new CampPatientTestResults();
    			BeanUtils.copyProperties(bean, entity);
    			testResultsRepository.save(entity);
    		}
    	}
    }

    @Override
    public Iterable<CampPatientTestResults> listTestResults() {
        // TODO Auto-generated method stub
        return testResultsRepository.findAll();
    }

    @Override
    public List<CampPatientTestResultsBean> getTestResultsByPatientId(Integer campPatientId) {
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
