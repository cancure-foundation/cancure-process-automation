package org.cancure.cpa.service;

import org.cancure.cpa.controller.beans.PatientVisitBean;

public interface PatientHospitalVisitWorkflowService {

	String startWorkflow(PatientVisitBean patientHospitalVisitBean);
	
	String topUpApprovedAmount();
	
	String selectPharmacy();
	
	String doClosureTasks();
	
}
