package org.cancure.cpa.controller;

import java.util.List;

import org.cancure.cpa.controller.beans.CampPatientBean;
import org.cancure.cpa.persistence.entity.CampPatient;
import org.cancure.cpa.service.CampPatientService;
import org.cancure.cpa.service.CampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CampPatientController {

    @Autowired
    CampPatientService campPatientService;
    
    @Autowired
    CampService campService; 

    @RequestMapping(method = RequestMethod.POST, value = "camp/patient")
    public CampPatient saveCampPatient(@RequestBody CampPatient patientcamp) {
        CampPatient campPatient = campPatientService.saveCampPatient(patientcamp);
        if(campPatient!=null){
            campService.updatePatientCount(campPatient.getCampId());
        }
        return campPatient;
    }
    
    @RequestMapping("camp/{campId}/patients")
    public List<CampPatientBean> getPatientByCamp(@PathVariable("campId") Integer campId) {
        return campPatientService.getPatientByCamp(campId);
    }
}
