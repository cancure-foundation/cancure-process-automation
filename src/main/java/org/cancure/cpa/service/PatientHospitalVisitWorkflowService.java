package org.cancure.cpa.service;

import java.io.IOException;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitForwardsBean;
import org.cancure.cpa.controller.beans.TopupStatusBean;

public interface PatientHospitalVisitWorkflowService {

	String startWorkflow(PatientVisitBean patientHospitalVisitBean) throws IOException;

	String topUpApprovedAmount(TopupStatusBean topupBean);

	String selectPartners(List<PatientVisitForwardsBean> forwardList) throws Exception;

}
