package org.cancure.cpa.service;

import java.io.IOException;

import org.cancure.cpa.controller.beans.PatientVisitBean;

public interface PatientHospitalVisitWorkflowService {

	String startWorkflow(PatientVisitBean patientHospitalVisitBean) throws IOException;

	String topUpApprovedAmount(String pidn, Integer patientVisitId, String topupApproved);

	String selectPharmacy(String pidn, Integer patientVisitId);

}
