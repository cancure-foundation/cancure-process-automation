package org.cancure.cpa.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientHospitalVisitWorkflowImpl implements PatientHospitalVisitWorkflowService {

	@Autowired
	private PatientHospitalVisitService patientHospitalVisitService;
	
	@Override
	public String startWorkflow() {
		
		/*Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("patientName", patient.getName());
        variables.put("pidn", patient.getPrn());
        
        patientHospitalVisitService.startPatientHospitalVisitWorkflow(variables, pidn);
        
        // Move to next task
        Map<String, Object> activitiVars = new HashMap<String, Object>();
        activitiVars.put("topupNeeded", "TRUE/FALSE"); 
        String taskId = patientHospitalVisitService.moveToNextTask(pidn, activitiVars);
        */
		
		return null;
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
