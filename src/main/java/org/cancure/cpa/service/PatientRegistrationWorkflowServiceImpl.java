package org.cancure.cpa.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;
import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.PatientApproval;
import org.cancure.cpa.persistence.entity.PidnGenerator;
import org.cancure.cpa.persistence.repository.PidnGeneratorRepository;
import org.cancure.cpa.util.IDCardGenerator;
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

    @Autowired
    private IDCardGenerator iDCardGenerator;
    
    @Autowired
    private HpocHospitalService hpocHospitalService;
    
    @Autowired
    private PidnGeneratorRepository pidnGeneratorRepository;
    
    @Transactional
    public String registerPatient(PatientBean patient) throws IOException {

        PatientBean patientBean = patientService.save(patient);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("patientName", patient.getName());
        variables.put("phoneNumber", patient.getContact());
        variables.put("prn", patient.getPrn());
        if (patient.getPreliminaryExamHospitalId() != null){
        	variables.put("preliminaryExamHospitalId", patient.getPreliminaryExamHospitalId());
        }
        
        patientRegistrationService.startPatientRegnProcess(variables, String.valueOf(patient.getPrn()));

		// Set Prelim Exam Hospital's HPOCs as the assignee.
		Map<String, Object> patRegMap = new HashMap<String, Object>();
		Integer prelimExamHospitalId = patient.getPreliminaryExamHospitalId();
		if (prelimExamHospitalId != null) {
			List<UserBean> hpocUsers = hpocHospitalService.getHpocUsersFromHospital(prelimExamHospitalId);
			if (hpocUsers != null && !hpocUsers.isEmpty()) {
				List<Integer> hpocIds = new ArrayList<>();
				for (UserBean usr : hpocUsers){
					hpocIds.add(usr.getId());
				}
				String csvIds = StringUtils.join(hpocIds, ',');
				patRegMap.put("assignee", csvIds);
			}
		}
        
        String taskId = patientRegistrationService.movePatientRegn(String.valueOf(patient.getPrn()), patRegMap);
        
        
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
        patientService.updateCostEstimate(patientInvestigationBean.getHospitalCostEstimate(), patientInvestigationBean.getMedicalCostEstimate(), patientInvestigationBean.getPatientType(),
                Integer.parseInt(patientInvestigationBean.getPrn()));
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
        
    	patientService.updateCostApproved(patientInvestigationBean.getHospitalCostApproved(),patientInvestigationBean.getMedicalCostApproved(), Integer.parseInt(patientInvestigationBean.getPrn()));
    	
        Map<String, Object> actVars = new HashMap<String, Object>();
        actVars.put("secApproval", status);
        String taskId=patientRegistrationService.movePatientRegn(String.valueOf(patientInvestigationBean.getPrn()), actVars);
        patientInvestigationBean.setTaskId(taskId);
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,status);
    }
    
    @Transactional
    public void executiveBoardRecommendationAccept(PatientInvestigationBean patientInvestigationBean,String status) throws IOException {
        
        String taskId=patientRegistrationService.ecApprove(String.valueOf(patientInvestigationBean.getPrn()),String.valueOf(patientInvestigationBean.getInvestigatorId()));
        patientInvestigationBean.setTaskId(taskId);
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean, status);
    }
    
    @Transactional
    public void executiveBoardRecommendationReject(PatientInvestigationBean patientInvestigationBean, String status) throws IOException {

        String taskId=patientRegistrationService.ecReject(String.valueOf(patientInvestigationBean.getPrn()),String.valueOf(patientInvestigationBean.getInvestigatorId()));
        patientInvestigationBean.setTaskId(taskId);      
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean,status);
    }

    @Override
    public void patientIDCard(Integer prn) throws Exception {
        // Generate PIDN
        PidnGenerator pidnGen = new PidnGenerator();
    	pidnGen.setPrn(prn);
    	pidnGeneratorRepository.save(pidnGen);
    	
    	patientService.updatePidn(pidnGen.getPidn(), prn);
    	
    	// Save approved amounts.
    	patientService.saveApprovedAmounts(pidnGen.getPidn(), prn);
    	
        iDCardGenerator.generateCard(prn);
        
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

	@Override
	@Transactional
	public void confirmApprovedAmount(PatientInvestigationBean patientInvestigationBean) throws IOException {
		// Update the final approved cost.
		patientService.updateCostApproved(patientInvestigationBean.getHospitalCostApproved(),
				patientInvestigationBean.getMedicalCostApproved(), Integer.parseInt(patientInvestigationBean.getPrn()));
		String taskId = patientRegistrationService.movePatientRegn(String.valueOf(patientInvestigationBean.getPrn()),
				null);
		patientInvestigationBean.setTaskId(taskId);
        patientInvestigationService.savePatientInvestigation(patientInvestigationBean, null);

	}

}
