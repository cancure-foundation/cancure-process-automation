package org.cancure.cpa.service;

import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitHistoryBean;
import org.cancure.cpa.controller.beans.TopupStatusBean;

public interface PatientHospitalVisitWorkflowService {

	String startWorkflow(PatientVisitBean patientHospitalVisitBean, Integer userId) throws Exception;

	String topUpApprovedAmount(TopupStatusBean topupBean);

	PatientVisitHistoryBean selectPatient(String pidn);

	PatientVisitHistoryBean searchByPatientVisitId(String patientVisitId);
}
