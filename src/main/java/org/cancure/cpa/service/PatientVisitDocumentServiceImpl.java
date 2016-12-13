package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.PatientVisitDocuments;
import org.cancure.cpa.persistence.repository.PatientVisitDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("patientVisitDocumentService")
public class PatientVisitDocumentServiceImpl implements PatientVisitDocumentService {

    @Autowired
    private PatientVisitDocumentRepository patientVisitDocumentRepo;
    
    @Override
    public PatientVisitDocuments getPatientVisitDocuments(Integer docId) {
        
        return patientVisitDocumentRepo.findOne(docId);
    }

}
