package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.List;

import org.cancure.cpa.controller.beans.InvoicesBean;
import org.cancure.cpa.controller.beans.JournalBean;
import org.cancure.cpa.persistence.entity.AccountTypes;
import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.cancure.cpa.persistence.entity.Journal;
import org.cancure.cpa.persistence.repository.InvoicesRepository;
import org.cancure.cpa.persistence.repository.JournalRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private JournalRepository repo;

	@Autowired
	private InvoicesRepository invoicesRepo;

	@Override
	public Long saveJournal(JournalBean bean) {
		Journal entity = new Journal();
		BeanUtils.copyProperties(bean, entity);
		AccountTypes fromAccountTypeId = new AccountTypes();
		fromAccountTypeId.setId(bean.getFromAccountTypeId());
		entity.setFromAccountTypeId(fromAccountTypeId);

		AccountTypes toAccountTypeId = new AccountTypes();
		toAccountTypeId.setId(bean.getToAccountTypeId());
		entity.setToAccountTypeId(toAccountTypeId);

		repo.save(entity);
		return entity.getId();
	}

	@Override
	public JournalBean getJournal(Long id) {
		Journal entity = repo.findOne(id);
		JournalBean bean = new JournalBean();
		BeanUtils.copyProperties(entity, bean);
		bean.setFromAccountTypeId(entity.getFromAccountTypeId().getId());
		bean.setToAccountTypeId(entity.getToAccountTypeId().getId());
		return bean;
	}

	@Override
	public List<InvoicesBean> getInvoices(Integer accountTypeId, Integer accountHolderId) {

		List<InvoicesEntity> entities = invoicesRepo
				.findByFromAccountTypeIdAndFromAccountHolderIdAndStatus(accountTypeId, accountHolderId, "open");

		List<InvoicesBean> invoiceBeanList = new ArrayList<>();

		if (entities != null && !entities.isEmpty()) {
			for (InvoicesEntity entity : entities) {
				InvoicesBean bean = new InvoicesBean();
				BeanUtils.copyProperties(entity, bean);
				bean.setFromAccountTypeId(entity.getFromAccountTypeId().getId());
				bean.setToAccountTypeId(entity.getToAccountTypeId().getId());

				invoiceBeanList.add(bean);
			}
		}

		return invoiceBeanList;
	}

}
