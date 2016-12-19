package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.persistence.entity.PatientVisitDocuments;

public interface PatientVisitDocumentService {

    public PatientVisitDocuments getPatientVisitDocuments(Integer docId);

    List<PatientVisitDocuments> getPatientDocByVisitId(Integer patientVisitId);
    
}
