package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cancure.cpa.controller.beans.CampPatientBean;
import org.cancure.cpa.persistence.entity.Camp;
import org.cancure.cpa.persistence.entity.CampPatient;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.persistence.entity.User;
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
