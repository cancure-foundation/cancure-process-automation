package org.cancure.cpa.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.CampPatientBean;
import org.cancure.cpa.persistence.entity.CampPatient;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.persistence.repository.CampPatientRepository;
import org.cancure.cpa.persistence.repository.CampPatientTestResultsRepository;
import org.cancure.cpa.persistence.repository.CampRepository;
import org.cancure.cpa.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("campPatientService")
public class CampPatientServiceImpl implements CampPatientService {
    @Autowired
    CampPatientRepository campPatientRepo;
    
    @Autowired
    CampRepository campRepo;
    
    @Autowired
    CampPatientTestResultsRepository campPatientTestResultsRepo;

    @Value("${spring.files.save.path}")
    private String fileSavePath;
    
    @Override
    public CampPatient saveCampPatient(CampPatient patientcamp) {
    	
        Integer patientCount = campRepo.findOne(patientcamp.getCampId()).getPatientCount();
        Date date = campRepo.findOne(patientcamp.getCampId()).getCampDate();
        String uid= CommonUtil.generateUID(date, patientCount, patientcamp.getCampId());
        patientcamp.setUid(uid);
        CampPatient campPatient =  campPatientRepo.save(patientcamp);
        Set<CampPatientTestResults> campPatientTestResults = patientcamp.getCampPatientTestResults();
        for(CampPatientTestResults campPatientTestResultsBean : campPatientTestResults){
            campPatientTestResultsBean.setCampPatientId(patientcamp.getCampPatientId());
            campPatientTestResultsRepo.save(campPatientTestResultsBean);
        }
        return campPatient;

    }

    @Override
    @Transactional
    public String updateCampPatientTests(CampPatient patientcamp) {
    	List<CampPatientTestResults> newlySelectedTests = new ArrayList<>();
    	List<CampPatientTestResults> testsToBeDeleted = new ArrayList<>();
    	
    	List<CampPatientTestResults> patientTests = campPatientTestResultsRepo.findByCampPatientId(patientcamp.getCampPatientId());
    	
    	//Check for new tests
    	for (CampPatientTestResults testResult : patientcamp.getCampPatientTestResults()) {
    		if (!isTestPresent(testResult, patientTests)) {
    			newlySelectedTests.add(testResult);
    		}
    	}
    	
    	// Check for removed tests
    	if (patientTests != null && !patientTests.isEmpty()) {
    		for (CampPatientTestResults existing : patientTests) {
    			if (!isTestPresent(existing, patientcamp.getCampPatientTestResults())) {
    				testsToBeDeleted.add(existing);
    			}
    		}
    	}
    	
    	for (CampPatientTestResults newTest : newlySelectedTests) {
    		newTest.setCampPatientId(patientcamp.getCampPatientId());
    		campPatientTestResultsRepo.save(newTest);
    	}
    	
    	for (CampPatientTestResults toDelete : testsToBeDeleted) {
    		    		
    		String filePath = toDelete.getTestResultPath();
    		if (filePath != null) {
    			File file = new File(fileSavePath + filePath);
    			Boolean status = file.delete();
    		}
    		
    		campPatientTestResultsRepo.delete(toDelete);
    	}
    	
    	return "success";
    }
    
    private boolean isTestPresent(CampPatientTestResults newTestResult, Collection<CampPatientTestResults> existingPatientTests) {
    	if (existingPatientTests != null && !existingPatientTests.isEmpty()) {
    		for (CampPatientTestResults existing : existingPatientTests) {
    			if (existing.getTestName().equals(newTestResult.getTestName())) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    @Override
    public List<CampPatientBean> getPatientByCamp(Integer campId) {
        
        List<CampPatient> campPatientList = campPatientRepo.findByCampId(campId);
        List<CampPatientBean> campPatientBeanList = new ArrayList<>();
        for(CampPatient campPatient: campPatientList){
            CampPatientBean campPatientBean = new CampPatientBean();
            BeanUtils.copyProperties(campPatient, campPatientBean);
            campPatientBeanList.add(campPatientBean);
        }
        return campPatientBeanList;
    }
}
