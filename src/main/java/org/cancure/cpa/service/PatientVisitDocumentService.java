package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.PatientVisitDocuments;

public interface PatientVisitDocumentService {

    public PatientVisitDocuments getPatientVisitDocuments(Integer docId);
}
