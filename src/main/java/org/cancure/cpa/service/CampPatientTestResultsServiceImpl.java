package org.cancure.cpa.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.CampPatientTestResultsBean;
import org.cancure.cpa.controller.beans.CampPatientTestResultsBeanList;
import org.cancure.cpa.persistence.entity.Camp;
import org.cancure.cpa.persistence.entity.CampPatient;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.persistence.entity.PatientBills;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.CampPatientRepository;
import org.cancure.cpa.persistence.repository.CampPatientTestResultsRepository;
import org.cancure.cpa.persistence.repository.CampRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("campPatientTestResultsService")
public class CampPatientTestResultsServiceImpl implements CampPatientTestResultsService {

    @Autowired
    CampPatientTestResultsRepository testResultsRepository;
    
    @Autowired
    CampPatientRepository campPatientRepo;
    
    @Autowired
    CampRepository campRepo;
    
    @Value("${spring.files.save.path}")
    private String fileSavePath;

    @Transactional
    @Override
    public void saveTestResult(CampPatientTestResultsBeanList campPatientTestResultsList) throws Exception {
        
    	if (campPatientTestResultsList != null && campPatientTestResultsList.getCampPatientTestResultsBeanList() != null && 
    			!campPatientTestResultsList.getCampPatientTestResultsBeanList().isEmpty()) {
    	    Integer campId = campPatientTestResultsList.getCampId();
    	    new File(fileSavePath + "/camp/" + campId).mkdirs();
    		for (CampPatientTestResultsBean bean : campPatientTestResultsList.getCampPatientTestResultsBeanList()) {
    			CampPatientTestResults entity = new CampPatientTestResults();
    			//save file 
    			String originalFileName = bean.getTestFile().getOriginalFilename();
    			String filePath = "/camp/" + campId + "/" + bean.getId() + "_" +originalFileName;
    			File file = new File(fileSavePath + filePath);
                bean.getTestFile().transferTo(file);
                BeanUtils.copyProperties(bean, entity);
                entity.setTestResultPath(filePath);
    			testResultsRepository.save(entity);
    		}
    	}
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
    public void notifyLocalPartner(Integer campPatientId) throws Exception {
        
        CampPatient campPatient = campPatientRepo.findOne(campPatientId);
        Camp camp = campRepo.findOne(campPatient.getCampId());
        
        User user = new User();
        user.setName(camp.getPocName());
        user.setEmail(camp.getPocEmail());
        user.setPhone(camp.getPocPhone());
        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        
        List<CampPatientTestResults> campPatientTestResults =  testResultsRepository.findByCampPatientId(campPatientId);
        List<String> attachmentPaths = new ArrayList<>();
        List<String> patientTestNames = new ArrayList<>();
        for(CampPatientTestResults campPatientTestResultsBean : campPatientTestResults){
        	if (campPatientTestResultsBean.getTestResultPath() != null) {
        		attachmentPaths.add( fileSavePath + campPatientTestResultsBean.getTestResultPath());
        	}
            patientTestNames.add(campPatientTestResultsBean.getTestName());
        }
        
        Map<String, Object> values = new HashMap<>();
        values.put("patientName", campPatient.getName());
        values.put("campVenue", camp.getCampPlace());
        values.put("campDate", camp.getCampDate());
        values.put("patientUID", campPatient.getUid());
        values.put("patientAge", campPatient.getAge());
        values.put("patientGender", campPatient.getGender());
        values.put("patientPhone", campPatient.getPhone());
        values.put("patientTestNames", patientTestNames);

        new EmailNotifier().notify(userSet, "CampTestReport_email", values, attachmentPaths);
        
    }

    
    @Override
    public CampPatientTestResults getCampPatientTestResults(Integer testResultId) {
        return testResultsRepository.findOne(testResultId);
    }

    @Override
    public void deleteTestResultsById(Integer testResultId) {
		CampPatientTestResults campPatientTestResults = testResultsRepository.findOne(testResultId);
		String filePath = campPatientTestResults.getTestResultPath();
		if (filePath != null) {
			File file = new File(fileSavePath + filePath);
			Boolean status = file.delete();
			if (status) {
				campPatientTestResults.setTestResultPath(null);
				campPatientTestResults.setTestResultText(null);
				testResultsRepository.save(campPatientTestResults);
			}
		}
    }

}
