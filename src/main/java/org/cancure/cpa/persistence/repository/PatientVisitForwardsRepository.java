package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.PatientVisitForwards;
import org.springframework.data.repository.CrudRepository;

public interface PatientVisitForwardsRepository extends CrudRepository<PatientVisitForwards, Integer> {

	public List<PatientVisitForwards> findByAccountTypeIdAndAccountHolderIdAndStatus(Integer accountTypeId,
			Integer accountHolderId, String status);
	
	public List<PatientVisitForwards> findByAccountTypeIdAndAccountHolderIdAndPatientVisitId(Integer accountTypeId,
			Integer accountHolderId, Integer patientVisitId);
}
