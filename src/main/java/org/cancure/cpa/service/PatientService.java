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
    
    int updateCostEstimate(Integer hospitalCostEstimate, Integer medicalCostEstimate, Integer prn);
	
}