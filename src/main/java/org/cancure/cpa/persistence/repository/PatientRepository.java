package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Integer> {

}
