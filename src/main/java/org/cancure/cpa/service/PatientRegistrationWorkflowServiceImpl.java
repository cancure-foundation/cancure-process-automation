package org.cancure.cpa.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class PatientRegistrationWorkflowServiceImpl implements PatientRegistrationWorkflowService {
    @Autowired
    private PatientRegistrationService patientRegistrationService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientInvestigationService patientInvestigationService;

    public void registerPatient(PatientBean patient) throws IOException {

        patientService.save(patient);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("patientName", patient.getName());
        variables.put("phoneNumber", patient.getContact());
        variables.put("prn", patient.getPrn());

        patientRegistrationService.startPatientRegnProcess(variables, String.valueOf(patient.getPrn()));

        patientRegistrationService.movePatientRegn(String.valueOf(patient.getPrn()), null);

    }

    public void preliminaryExamination(PatientInvestigationBean patientInvestigationBean,
            List<PatientDocumentBean> patientDocumentBean) throws IOException {

        patientInvestigationService.savePatientExamination(patientInvestigationBean, patientDocumentBean);
        
        patientRegistrationService.movePatientRegn(patientInvestigationBean.getPrn().toString(), null);
    }
    
    public void backGroundCheck(PatientInvestigationBean patientInvestigationBean,String status) throws IOException {
        
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,status);
        Map<String, Object> actVars = new HashMap<String, Object>();
        actVars.put("bgCheck", status);
        patientRegistrationService.movePatientRegn(String.valueOf(patientInvestigationBean.getPrn()), actVars);
        
    }
    
    public void doctorRecommendation(PatientInvestigationBean patientInvestigationBean) throws IOException {
        
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,null);
        patientRegistrationService.mbApprove(String.valueOf(patientInvestigationBean.getPrn()),String.valueOf(patientInvestigationBean.getInvestigatorId()));
    }
    
    public void secretaryRecommendation(PatientInvestigationBean patientInvestigationBean,String status) throws IOException {
        
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,status);
        Map<String, Object> actVars = new HashMap<String, Object>();
        actVars.put("secApproval", status);
        patientRegistrationService.movePatientRegn(String.valueOf(patientInvestigationBean.getPrn()), actVars);
    }
    
    public void executiveBoardRecommendationAccept(PatientInvestigationBean patientInvestigationBean) throws IOException {
        
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,null);
        patientRegistrationService.ecApprove(String.valueOf(patientInvestigationBean.getPrn()),String.valueOf(patientInvestigationBean.getInvestigatorId()));
    }
    
    public void executiveBoardRecommendationReject(PatientInvestigationBean patientInvestigationBean) throws IOException {
        
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,null);
        patientRegistrationService.ecReject(String.valueOf(patientInvestigationBean.getPrn()),String.valueOf(patientInvestigationBean.getInvestigatorId()));
    }

    @Override
    public void patientIDCard(Integer prn) throws IOException {
        // Generate PIDN
        // To do
        
                
        patientRegistrationService.movePatientRegn(String.valueOf(prn), null);
        
    }
}
