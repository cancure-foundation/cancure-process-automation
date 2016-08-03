package org.cancure.cpa.controller;

import java.io.IOException;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;
import org.cancure.cpa.service.PatientRegistrationWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientWorkFlowController {
    
    @Autowired
    private PatientRegistrationWorkflowService patientRegistrationWorkflowService;   
  
    @RequestMapping(value="patientregistration/patient/save", method=RequestMethod.POST)
    public String save(PatientBean patientbean) throws IOException {
        patientRegistrationWorkflowService.registerPatient(patientbean);
        return "SUCCESS";
    }
    
    @RequestMapping("patientregistration/preliminaryexamination/save")
    public String saveExamination(PatientInvestigationBean patientInvestigationBean,List<PatientDocumentBean> patientDocumentBean) throws IOException {
        patientRegistrationWorkflowService.preliminaryExamination(patientInvestigationBean,patientDocumentBean);
        return "SUCCESS";
    }
    
    @RequestMapping("patientregistration/backgroundcheck/save/{status}")
    public String saveBGC(PatientInvestigationBean patientInvestigationBean,@PathVariable("status") String status) throws IOException {
        patientRegistrationWorkflowService.backGroundCheck(patientInvestigationBean,status);
        return "SUCCESS";
    }
    
    @RequestMapping("patientregistration/mbdoctorrecommendation/save")
    public String saveDoctorRecommendation(PatientInvestigationBean patientInvestigationBean) throws IOException {
        patientRegistrationWorkflowService.doctorRecommendation(patientInvestigationBean);
        return "SUCCESS";
    }
    
    @RequestMapping("patientregistration/secretaryrecommendation/save/{status}")
    public String saveSecretaryRecommendation(PatientInvestigationBean patientInvestigationBean,@PathVariable("status") String status) throws IOException {
        patientRegistrationWorkflowService.secretaryRecommendation(patientInvestigationBean,status); 
        return "SUCCESS";
    }
    
   @RequestMapping("patientregistration/executiveboardrecommendation/accept/save")
    public String saveExecutiveBoardRecommendationAccept(PatientInvestigationBean patientInvestigationBean) throws IOException {
        patientRegistrationWorkflowService.secretaryRecommendation(patientInvestigationBean,null); 
        return "SUCCESS";
    }
   
   @RequestMapping("patientregistration/executiveboardrecommendation/reject/save")
   public String saveExecutiveBoardRecommendationReject(PatientInvestigationBean patientInvestigationBean) throws IOException {
        patientRegistrationWorkflowService.secretaryRecommendation(patientInvestigationBean,null); 
        return "SUCCESS";
   }
   
   @RequestMapping("patientregistration/Patientidcard/{prn}")
   public String savePatientIDCard(@PathVariable("prn") Integer prn) throws IOException {
        patientRegistrationWorkflowService.patientIDCard(prn);
        return "SUCCESS";
   }
    
}
