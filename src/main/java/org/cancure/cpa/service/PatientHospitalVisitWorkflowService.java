package org.cancure.cpa.service;

import java.io.IOException;

import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitForwardsBean;
import org.cancure.cpa.controller.beans.TopupStatusBean;
import org.cancure.cpa.persistence.entity.PatientVisitForwards;

public interface PatientHospitalVisitWorkflowService {

	String startWorkflow(PatientVisitBean patientHospitalVisitBean) throws IOException;

	String topUpApprovedAmount(TopupStatusBean topupBean);

	String selectPharmacy(PatientVisitForwardsBean forward);

}
