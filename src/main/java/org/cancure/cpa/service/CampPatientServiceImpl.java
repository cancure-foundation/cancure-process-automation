package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.List;

import org.cancure.cpa.controller.beans.CampPatientBean;
import org.cancure.cpa.persistence.entity.CampPatient;
import org.cancure.cpa.persistence.repository.CampPatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("campPatientService")
public class CampPatientServiceImpl implements CampPatientService {
    @Autowired
    CampPatientRepository campPatientRepo;

    @Override
    public CampPatient saveCampPatient(CampPatient patientcamp) {

        return campPatientRepo.save(patientcamp);

    }

    @Override
    public Iterable<CampPatient> listCampPatient() {

        return campPatientRepo.findAll();
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
