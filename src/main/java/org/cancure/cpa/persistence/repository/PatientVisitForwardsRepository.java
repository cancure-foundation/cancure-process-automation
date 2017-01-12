package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.AccountTypes;
import org.cancure.cpa.persistence.entity.PatientVisitForwards;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PatientVisitForwardsRepository extends CrudRepository<PatientVisitForwards, Integer> {

	public List<PatientVisitForwards> findByAccountTypeIdAndAccountHolderIdAndStatus(AccountTypes accountType,
			Integer accountHolderId, String status);
	
	public List<PatientVisitForwards> findByAccountTypeIdAndAccountHolderIdAndPidn(AccountTypes accountType,
			Integer accountHolderId, Integer pidn);
	
	public List<PatientVisitForwards> findByAccountTypeIdAndAccountHolderIdAndPatientVisitId(AccountTypes accountType,
			Integer accountHolderId, Integer patientVisitId);
	
	@Modifying
	@Query("update PatientVisitForwards u set u.status = ?1 where u.patientVisitId = ?2" )
	public void updateBillStatus(String status, Integer patientVisitId);
}
