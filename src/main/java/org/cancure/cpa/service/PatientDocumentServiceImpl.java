package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.persistence.entity.PatientDocument;
import org.cancure.cpa.persistence.repository.PatientDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("patientDocumentService")
public class PatientDocumentServiceImpl implements PatientDocumentService {

    @Autowired
    PatientDocumentRepository patientDocumentRepo;

    @Override
    public List<PatientDocument> findByTaskId(String taskId) {
        
        List<PatientDocument> patientDocument=patientDocumentRepo.findByTaskId(taskId);
        return patientDocument;

    }

    @Override
    public List<PatientDocument> findByPrn(Integer prn) {
        
        List<PatientDocument> patientDocument=patientDocumentRepo.findByPrn(prn);
        return patientDocument;

    }
    
    @Override
    public PatientDocument findOne(Integer id) {
        return patientDocumentRepo.findOne(id);
    }
 
}
