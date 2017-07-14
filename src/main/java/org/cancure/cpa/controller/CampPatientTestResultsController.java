package org.cancure.cpa.controller;

import java.util.List;

import org.cancure.cpa.controller.beans.CampPatientTestResultsBean;
import org.cancure.cpa.controller.beans.CampPatientTestResultsBeanList;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.service.CampPatientTestResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CampPatientTestResultsController {
    @Autowired
    CampPatientTestResultsService campPatientTestResultsService;

    @RequestMapping(method = RequestMethod.POST, value = "camp/patient/testresult")
    public String saveCampPatientTestResults(
            CampPatientTestResultsBeanList campPatientTestResultsBean) throws Exception {
        campPatientTestResultsService.saveTestResult(campPatientTestResultsBean);
        return "{\"status\" : \"SUCCESS\"}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "camp/patient/{patientId}/testresult")
    public List<CampPatientTestResultsBean> getTestResultsByPatientId(@PathVariable("patientId") Integer patientId) {
        return campPatientTestResultsService.getTestResultsByPatientId(patientId);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "camp/patient/testresult/{testResultId}")
    public String deleteTestResultsById(@PathVariable("testResultId") Integer testResultId) {
         campPatientTestResultsService.deleteTestResultsById(testResultId);
         return "{\"status\" : \"SUCCESS\"}";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "camp/patient/testresult/email")
    public String emailTestReport(@PathVariable("patientId") Integer patientId) throws Exception {
        campPatientTestResultsService.notifyLocalPartner(patientId);
        return "{\"status\" : \"SUCCESS\"}";
    }
}
