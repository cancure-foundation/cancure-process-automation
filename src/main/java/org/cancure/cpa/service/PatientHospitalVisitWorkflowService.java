package org.cancure.cpa.service;

public interface PatientHospitalVisitWorkflowService {

	String startWorkflow();
	
	String topUpApprovedAmount();
	
	String selectPharmacy();
	
	String doClosureTasks();
	
}
