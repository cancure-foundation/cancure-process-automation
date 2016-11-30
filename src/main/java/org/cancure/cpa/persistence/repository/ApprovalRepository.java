package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.AccountTypes;
import org.cancure.cpa.persistence.entity.PatientApproval;
import org.springframework.data.repository.CrudRepository;

public interface ApprovalRepository extends CrudRepository<PatientApproval, Integer>{

	List<PatientApproval> findByPidn(Integer pidn);
	
	List<PatientApproval> findByPidnAndApprovedForAccountType(Integer pidn, AccountTypes approvedForAccountType);
}
