package org.cancure.cpa.service;

import java.io.IOException;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;

public interface PatientRegistrationWorkflowService {

    String registerPatient(PatientBean patient) throws IOException;
    
    void preliminaryExamination(PatientInvestigationBean patientInvestigationBean,
            List<PatientDocumentBean> patientDocumentBean) throws IOException;
    
    void backGroundCheck(PatientInvestigationBean patientInvestigationBean,String status) throws IOException;
    
    void doctorRecommendation(PatientInvestigationBean patientInvestigationBean) throws IOException;
    
    void secretaryRecommendation(PatientInvestigationBean patientInvestigationBean,String status) throws IOException;
    
    void executiveBoardRecommendationAccept(PatientInvestigationBean patientInvestigationBean, String status) throws IOException;
    
    void executiveBoardRecommendationReject(PatientInvestigationBean patientInvestigationBean, String status) throws IOException;
    
    void savePreliminaryExamClarification(PatientInvestigationBean patientInvestigationBean) throws IOException;
    
    void saveBackgroundCheckClarification(PatientInvestigationBean patientInvestigationBean) throws IOException;
    
    void patientIDCard(Integer prn) throws Exception;
}
