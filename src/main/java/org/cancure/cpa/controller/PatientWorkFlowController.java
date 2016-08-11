package org.cancure.cpa.controller;

import java.io.IOException;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentAndInvestigationBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;
import org.cancure.cpa.service.PatientRegistrationWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientWorkFlowController {
    
    @Autowired
    private PatientRegistrationWorkflowService patientRegistrationWorkflowService;   
  
    @RequestMapping(value="/patientregistration/patient/save", method=RequestMethod.POST)
    public String save(PatientBean patientbean) throws IOException {
        patientRegistrationWorkflowService.registerPatient(patientbean);
        return "{\"status\" : \"SUCCESS\"}";
    }
    
    @RequestMapping(value= "/patientregistration/preliminaryexamination/save" , method=RequestMethod.POST)
    public String saveExamination(PatientDocumentAndInvestigationBean patientDocumentAndInvestigationBean) throws IOException {
    	
    	PatientInvestigationBean patientInvestigationBean = patientDocumentAndInvestigationBean.getPatientInvestigationBean();
    	List<PatientDocumentBean> patientDocumentBean = patientDocumentAndInvestigationBean.getPatientDocumentBean();
    	
    	patientInvestigationBean.setInvestigatorType("Doctor");
    	
        patientRegistrationWorkflowService.preliminaryExamination(patientInvestigationBean,patientDocumentBean);
        return "{\"status\" : \"SUCCESS\"}";
    }
    
    @RequestMapping(value= "/patientregistration/backgroundcheck/save/{status}", method=RequestMethod.POST)
    public String saveBGC(@RequestBody PatientInvestigationBean patientInvestigationBean,@PathVariable("status") String status) throws IOException {
        patientRegistrationWorkflowService.backGroundCheck(patientInvestigationBean,status);
        return "{\"status\" : \"SUCCESS\"}";
    }
    
    @RequestMapping(value= "/patientregistration/mbdoctorrecommendation/save", method=RequestMethod.POST)
    public String saveDoctorRecommendation(@RequestBody PatientInvestigationBean patientInvestigationBean) throws IOException {
        patientRegistrationWorkflowService.doctorRecommendation(patientInvestigationBean);
        return "{\"status\" : \"SUCCESS\"}";
    }
    
    @RequestMapping(value= "/patientregistration/secretaryrecommendation/save/{status}", method=RequestMethod.POST)
    public String saveSecretaryRecommendation(@RequestBody PatientInvestigationBean patientInvestigationBean,@PathVariable("status") String status) throws IOException {
        patientRegistrationWorkflowService.secretaryRecommendation(patientInvestigationBean,status); 
        return "{\"status\" : \"SUCCESS\"}";
    }
    
   @RequestMapping(value= "patientregistration/executiveboardrecommendation/accept/save", method=RequestMethod.POST)
    public String saveExecutiveBoardRecommendationAccept(@RequestBody PatientInvestigationBean patientInvestigationBean) throws IOException {
        patientRegistrationWorkflowService.executiveBoardRecommendationAccept(patientInvestigationBean); 
        return "{\"status\" : \"SUCCESS\"}";
    }
   
   @RequestMapping(value= "patientregistration/executiveboardrecommendation/reject/save", method=RequestMethod.POST)
   public String saveExecutiveBoardRecommendationReject(@RequestBody PatientInvestigationBean patientInvestigationBean) throws IOException {
        patientRegistrationWorkflowService.executiveBoardRecommendationReject(patientInvestigationBean); 
        return "{\"status\" : \"SUCCESS\"}";
   }
   
   @RequestMapping(value= "patientregistration/Patientidcard/{prn}")
   public String savePatientIDCard(@PathVariable("prn") Integer prn) throws IOException {
        patientRegistrationWorkflowService.patientIDCard(prn);
        return "{\"status\" : \"SUCCESS\"}";
   }
    
}
