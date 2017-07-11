package org.cancure.cpa.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.CampPatientTestResultsBean;
import org.cancure.cpa.controller.beans.CampPatientTestResultsBeanList;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.persistence.entity.PatientBills;
import org.cancure.cpa.persistence.repository.CampPatientTestResultsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("campPatientTestResultsService")
public class CampPatientTestResultsServiceImpl implements CampPatientTestResultsService {

    @Autowired
    CampPatientTestResultsRepository testResultsRepository;
    
    @Value("${spring.files.save.path}")
    private String fileSavePath;

    @Transactional
    @Override
    public void saveTestResult(CampPatientTestResultsBeanList campPatientTestResultsList) throws Exception {
        
    	if (campPatientTestResultsList != null && campPatientTestResultsList.getCampPatientTestResultsBeanList() != null && 
    			!campPatientTestResultsList.getCampPatientTestResultsBeanList().isEmpty()) {
    	    new File(fileSavePath + "/camp/" + campPatientTestResultsList.getCampPatientTestResultsBeanList().get(0).getCampId()).mkdirs();
    		for (CampPatientTestResultsBean bean : campPatientTestResultsList.getCampPatientTestResultsBeanList()) {
    			CampPatientTestResults entity = new CampPatientTestResults();
    			//save file 
    			String originalFileName = bean.getTestFile().getOriginalFilename();
    			String filePath = "/camp/" + bean.getCampId() + "/" + bean.getId() + "_" +originalFileName;
    			File file = new File(fileSavePath + filePath);
                bean.getTestFile().transferTo(file);
                BeanUtils.copyProperties(bean, entity);
                entity.setTestResultPath(filePath);
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
    
    @Override
    public CampPatientTestResults getCampPatientTestResults(Integer testResultId) {
        // TODO Auto-generated method stub
        return testResultsRepository.findOne(testResultId);
    }

    @Override
    public void deleteTestResultsById(Integer testResultId) {
        // TODO Auto-generated method stub
        testResultsRepository.delete(testResultId);
    }

}
