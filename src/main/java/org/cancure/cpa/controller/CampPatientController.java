package org.cancure.cpa.controller;

import org.cancure.cpa.persistence.entity.CampPatient;
import org.cancure.cpa.service.CampPatientService;
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

    @RequestMapping(method = RequestMethod.POST, value = "camp/patient/save")
    public CampPatient saveCampPatient(@RequestBody CampPatient patientcamp) {
        return campPatientService.saveCampPatient(patientcamp);
    }

    @RequestMapping(method = RequestMethod.POST, value = "camp/patient/list")
    public Iterable<CampPatient> listCampPatient() {
        return campPatientService.listCampPatient();
    }
    
    @RequestMapping("camp/patient/list/{campId}")
    public Iterable<CampPatient> getPatientByCamp(@PathVariable("campId") Integer campId) {
        return campPatientService.listCampPatient();
    }

}
