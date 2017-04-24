package org.cancure.cpa.service;

import java.io.IOException;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;

public interface PatientService {

    PatientBean save(PatientBean p) throws IOException;
	
	PatientBean get(Integer id);

	List<PatientBean> searchByName(String name);

    void savePatientDocuments(List<PatientDocumentBean> patientDocuments);

    int updateTaskId(String taskId, Integer id);
	
	int updatePidn(Integer pidn, Integer prn);

    List<PatientBean> searchByAadhar(String aadharNo);
    
    List<PatientBean> searchByPidn(Integer pidn);
    
    void updateCostEstimate(Integer hospitalCostEstimate, Integer medicalCostEstimate, String patientType, Integer prn);
	
    void updateCostApproved(Integer hospitalCostApproved, Integer medicalCostApproved, Integer prn);

	void saveApprovedAmounts(Integer pidn, Integer prn);

	void updateMbApprovalViewedDoctors(String doctors, Integer prn);
}