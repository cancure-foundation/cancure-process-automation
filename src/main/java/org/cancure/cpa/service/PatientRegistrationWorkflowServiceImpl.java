package org.cancure.cpa.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientRegistrationWorkflowServiceImpl implements PatientRegistrationWorkflowService {
    @Autowired
    private PatientRegistrationService patientRegistrationService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientInvestigationService patientInvestigationService;

    @Transactional
    public String registerPatient(PatientBean patient) throws IOException {

        PatientBean patientBean = patientService.save(patient);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("patientName", patient.getName());
        variables.put("phoneNumber", patient.getContact());
        variables.put("prn", patient.getPrn());

        patientRegistrationService.startPatientRegnProcess(variables, String.valueOf(patient.getPrn()));

        String taskId = patientRegistrationService.movePatientRegn(String.valueOf(patient.getPrn()), null);
        
        List<PatientDocumentBean> documents = patientBean.getDocument();
        if (documents != null){
            for (PatientDocumentBean doc : documents){
                doc.setTaskId(taskId);
                doc.setPrn(String.valueOf(patient.getPrn()));
            }
            
            patientService.savePatientDocuments(documents);
        }
        patientService.updateTaskId(taskId, patient.getPrn());
        return String.valueOf(patient.getPrn());

    }

    @Transactional
    public void preliminaryExamination(PatientInvestigationBean patientInvestigationBean,
            List<PatientDocumentBean> patientDocumentBean) throws IOException {
        
        String taskId=patientRegistrationService.movePatientRegn(patientInvestigationBean.getPrn().toString(), null);
        patientInvestigationBean.setTaskId(taskId);
        
        if (patientDocumentBean != null){
	        for(PatientDocumentBean patientDocument :patientDocumentBean){
	            patientDocument.setTaskId(taskId);
	        }
        }
        patientInvestigationService.savePatientExamination(patientInvestigationBean, patientDocumentBean);

    }
    
    @Transactional
    public void backGroundCheck(PatientInvestigationBean patientInvestigationBean,String status) throws IOException {
        
        Map<String, Object> actVars = new HashMap<String, Object>();
        actVars.put("bgCheck", status);
        String taskId=patientRegistrationService.movePatientRegn(String.valueOf(patientInvestigationBean.getPrn()), actVars);
        patientInvestigationBean.setTaskId(taskId);
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,status);
    }
    
    @Transactional
    public void doctorRecommendation(PatientInvestigationBean patientInvestigationBean) throws IOException {

        String taskId=patientRegistrationService.mbApprove(String.valueOf(patientInvestigationBean.getPrn()),String.valueOf(patientInvestigationBean.getInvestigatorId()));
        patientInvestigationBean.setTaskId(taskId);
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,null);
    }
    
    @Transactional
    public void secretaryRecommendation(PatientInvestigationBean patientInvestigationBean,String status) throws IOException {
        
        Map<String, Object> actVars = new HashMap<String, Object>();
        actVars.put("secApproval", status);
        String taskId=patientRegistrationService.movePatientRegn(String.valueOf(patientInvestigationBean.getPrn()), actVars);
        patientInvestigationBean.setTaskId(taskId);
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,status);
    }
    
    @Transactional
    public void executiveBoardRecommendationAccept(PatientInvestigationBean patientInvestigationBean) throws IOException {
        
        String taskId=patientRegistrationService.ecApprove(String.valueOf(patientInvestigationBean.getPrn()),String.valueOf(patientInvestigationBean.getInvestigatorId()));
        patientInvestigationBean.setTaskId(taskId);
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,null);
    }
    
    @Transactional
    public void executiveBoardRecommendationReject(PatientInvestigationBean patientInvestigationBean) throws IOException {

        String taskId=patientRegistrationService.ecReject(String.valueOf(patientInvestigationBean.getPrn()),String.valueOf(patientInvestigationBean.getInvestigatorId()));
        patientInvestigationBean.setTaskId(taskId);      
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,null);
    }

    @Override
    @Transactional
    public void patientIDCard(Integer prn) throws IOException {
        // Generate PIDN
        // To do
        
                
        patientRegistrationService.movePatientRegn(String.valueOf(prn), null);
        
    }

    @Override
    public void savePreliminaryExamClarification(PatientInvestigationBean patientInvestigationBean)throws IOException {
        
    	saveBackgroundCheckClarification(patientInvestigationBean);         
    }

    @Override
    public void saveBackgroundCheckClarification(PatientInvestigationBean patientInvestigationBean)
            throws IOException {
        
    	String taskId=patientRegistrationService.movePatientRegn(String.valueOf(patientInvestigationBean.getPrn()), null);
        patientInvestigationBean.setTaskId(taskId);
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean, null);
    }

}
