package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.PatientBills;
import org.springframework.data.repository.CrudRepository;

public interface PatientBillRepository extends CrudRepository<PatientBills, Integer> {

}
