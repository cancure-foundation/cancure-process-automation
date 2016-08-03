package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.PatientDocument;
import org.springframework.data.repository.CrudRepository;

public interface PatientDocumentRepository extends CrudRepository<PatientDocument, Integer> {

}
