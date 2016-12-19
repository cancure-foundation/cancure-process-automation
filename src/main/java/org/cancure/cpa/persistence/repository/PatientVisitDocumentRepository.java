package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.PatientVisitDocuments;
import org.springframework.data.repository.CrudRepository;

public interface PatientVisitDocumentRepository extends CrudRepository<PatientVisitDocuments, Integer> {

}
