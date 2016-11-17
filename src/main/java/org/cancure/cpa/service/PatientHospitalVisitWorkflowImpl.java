package org.cancure.cpa.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitDocumentBean;
import org.cancure.cpa.persistence.entity.AccountTypes;
import org.cancure.cpa.persistence.entity.PatientVisit;
import org.cancure.cpa.persistence.entity.PatientVisitDocuments;
import org.cancure.cpa.persistence.repository.PatientVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientHospitalVisitWorkflowImpl implements PatientHospitalVisitWorkflowService {

	@Autowired
	private PatientHospitalVisitService patientHospitalVisitService;
	
	@Autowired
	private PatientVisitRepository patientVisitRepository;
	
	@Override
	public String startWorkflow(PatientVisitBean patientVisitBean) {
		
		Integer pidn = patientVisitBean.getPidn();
		Map<String, Object> variables = new HashMap<>();
        variables.put("pidn", pidn);
        
        PatientVisit patientVisit =  transformPatientBeanToEntity(patientVisitBean);
        
        patientVisitRepository.save(patientVisit);
        
        patientHospitalVisitService.startPatientHospitalVisitWorkflow(variables, pidn + "");
        
        // Move to next task
        /*Map<String, Object> activitiVars = new HashMap<String, Object>();
        activitiVars.put("topupNeeded", "TRUE/FALSE"); 
        String taskId = patientHospitalVisitService.moveToNextTask(pidn + "", activitiVars);
        
        patientVisit.setTaskId(taskId);
        patientVisitRepository.save(patientVisit);*/
        
		return null;
	}

	private PatientVisit transformPatientBeanToEntity(PatientVisitBean patientVisitBean) {
		PatientVisit patientVisit = new PatientVisit();
        patientVisit.setDate(new Timestamp(System.currentTimeMillis()));
        patientVisit.setPidn(patientVisitBean.getPidn());
        patientVisit.setAccountTypes(new AccountTypes(5, "Hospital"));
        patientVisit.setAccountHolderId(Integer.parseInt(patientVisitBean.getAccountHolderId()));
        
        if (patientVisitBean.getPatientHospitalVisitDocumentBeanList() != null){
        	List<PatientVisitDocuments> patientVisitDocList = new ArrayList<>();
        	for (PatientVisitDocumentBean bean : patientVisitBean.getPatientHospitalVisitDocumentBeanList()) {
        		PatientVisitDocuments patientVisitDocumentBean = new PatientVisitDocuments();
        		AccountTypes actType = new AccountTypes();
        		actType.setId(bean.getAccountTypeId());
        		patientVisitDocumentBean.setAccountTypes(actType); 
        		patientVisitDocumentBean.setDocType(bean.getDocType());
        		patientVisitDocumentBean.setPatientVisit(patientVisit);
        		patientVisitDocList.add(patientVisitDocumentBean);        		
        	}
        	
        	patientVisit.setPatientVisitDocumentsList(patientVisitDocList);
        }
        
        return patientVisit;
	}

	@Override
	public String topUpApprovedAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectPharmacy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String doClosureTasks() {
		// TODO Auto-generated method stub
		return null;
	}

}
