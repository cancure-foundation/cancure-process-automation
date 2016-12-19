package org.cancure.cpa.persistence.repository;

import java.sql.Timestamp;
import java.util.List;

import org.cancure.cpa.persistence.entity.AccountTypes;
import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface InvoicesRepository extends CrudRepository<InvoicesEntity, Integer> {

	List<InvoicesEntity> findByPidn(Integer pidn);

	List<InvoicesEntity> findByPidnAndFromAccountTypeId(Integer pidn, AccountTypes fromAccountTypeId);

	List<InvoicesEntity> findByFromAccountTypeIdAndFromAccountHolderIdAndStatus(AccountTypes accountTypeId,
			Integer accountHolderId, String status);
	
	@Modifying
    @Query("update InvoicesEntity i set i.status = ?1, i.closedDate = ?2, i.balanceAmount = ?3, i.journalId = ?4  where i.id = ?5")
    public int closeInvoice(String status, Timestamp date, Double balanceAmount, Long journalId, Integer id);
    
}
