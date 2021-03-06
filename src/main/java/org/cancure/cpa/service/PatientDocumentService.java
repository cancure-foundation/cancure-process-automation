package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.persistence.entity.PatientDocument;


public interface PatientDocumentService {
    
    List<PatientDocument> findByTaskId(String taskId);
    
    PatientDocument findOne(Integer id);

    List<PatientDocument> findByPrn(Integer prn);
}
