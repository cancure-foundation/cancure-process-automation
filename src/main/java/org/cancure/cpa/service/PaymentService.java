package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.controller.beans.InvoicesBean;
import org.cancure.cpa.controller.beans.JournalBean;

public interface PaymentService {

	public Long saveJournal(JournalBean bean);
	
	public JournalBean getJournal(Long id);

	List<InvoicesBean> getInvoices(Integer accountTypeId, Integer accountHolderId);
	
}
