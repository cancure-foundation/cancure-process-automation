package org.cancure.cpa.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.InvoicesBean;
import org.cancure.cpa.controller.beans.JournalBean;
import org.cancure.cpa.controller.beans.PaymentBean;
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

		AccountTypes at = new AccountTypes();
		at.setId(accountTypeId);
		List<InvoicesEntity> entities = invoicesRepo
				.findByFromAccountTypeIdAndFromAccountHolderIdAndStatus(at, accountHolderId, "open");

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

	@Override
	@Transactional
	public void savePayment(PaymentBean paymentBean) {
		
		List<Integer> invoiceList = paymentBean.getSelectedInvoiceIds();
		if (invoiceList == null || invoiceList.isEmpty()) {
			return;
		}
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		JournalBean bean = new JournalBean();
		bean.setDate(now);
		bean.setFromAccountHolderId(1); //Cancure
		bean.setFromAccountTypeId(1); // Cancure
		bean.setToAccountHolderId(paymentBean.getToAccountHolderId());
		bean.setToAccountTypeId(paymentBean.getToAccountTypeId());
		bean.setAmount(paymentBean.getAmount());
		bean.setChequeNo(paymentBean.getChequeNo());
		bean.setComments(paymentBean.getComments());
		bean.setMode(paymentBean.getMode());
		
		Long journalId = saveJournal(bean);
		
		for (Integer invoiceId : invoiceList) {
			invoicesRepo.closeInvoice("closed", now, 0d, journalId, invoiceId);
		}
	}

}