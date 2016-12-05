package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.AccountTypes;
import org.cancure.cpa.persistence.entity.PatientApproval;
import org.springframework.data.repository.CrudRepository;

public interface ApprovalRepository extends CrudRepository<PatientApproval, Integer>{

	//@Query("Select p,r from PatientApproval p join p.approvedForAccountType r where p.pidn = ?1 ")
	List<PatientApproval> findByPidn(Integer pidn);
	
	List<PatientApproval> findByPidnAndApprovedForAccountType(Integer pidn, AccountTypes accountTypeId);
}
