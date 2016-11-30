package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.AccountTypes;
import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.springframework.data.repository.CrudRepository;

public interface InvoicesRepository extends CrudRepository<InvoicesEntity, Integer>{

	List<InvoicesEntity> findByPidn(Integer pidn);
	
	List<InvoicesEntity> findByPidnAndFromAccountTypeId(Integer pidn, AccountTypes fromAccountTypeId);
}
