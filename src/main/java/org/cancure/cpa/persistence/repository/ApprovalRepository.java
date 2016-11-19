package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.PatientApproval;
import org.springframework.data.repository.CrudRepository;

public interface ApprovalRepository extends CrudRepository<PatientApproval, Integer>{

}
