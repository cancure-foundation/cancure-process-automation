package org.cancure.cpa.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.InvoicesBean;
import org.cancure.cpa.controller.beans.JournalBean;
import org.cancure.cpa.controller.beans.PatientBillsBean;
import org.cancure.cpa.controller.beans.PaymentBean;
import org.cancure.cpa.persistence.entity.AccountTypes;
import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.cancure.cpa.persistence.entity.Journal;
import org.cancure.cpa.persistence.entity.PatientBills;
import org.cancure.cpa.persistence.entity.PaymentWorkflow;
import org.cancure.cpa.persistence.repository.InvoicesRepository;
import org.cancure.cpa.persistence.repository.JournalRepository;
import org.cancure.cpa.persistence.repository.PaymentWorkflowRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private JournalRepository repo;

	@Autowired
	private InvoicesRepository invoicesRepo;
	
	@Autowired
	private PaymentWorkflowService paymentWorkflowService;
	
	@Autowired
	private PaymentWorkflowRepository paymentWorkflowRepository;
	
	@Autowired
	private PatientBillService patientBillService;

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
				List<PatientBillsBean> patientBillList = new ArrayList<>();
				BeanUtils.copyProperties(entity, bean);
				bean.setFromAccountTypeId(entity.getFromAccountTypeId().getId());
				bean.setToAccountTypeId(entity.getToAccountTypeId().getId());
				//Adding patient Bills
				List<PatientBills> patBillList = patientBillService.getPatientBillByInvoice(entity.getId());
				for(PatientBills patientBill:patBillList){
                    PatientBillsBean patientBillsBean =new PatientBillsBean();
                    BeanUtils.copyProperties(patientBill, patientBillsBean);
                    patientBillList.add(patientBillsBean);
				}
 
				bean.setPatientBillList(patientBillList);
				invoiceBeanList.add(bean);
			}
		}

		return invoiceBeanList;
	}

	@Override
	@Transactional
	public void startPaymentWorkflow(PaymentBean paymentBean) {
		
		PaymentWorkflow paymentWorkflow = new PaymentWorkflow();
		paymentWorkflow.setStatus("open");
		paymentWorkflow.setToAccountHolderId(paymentBean.getToAccountHolderId());
		AccountTypes at = new AccountTypes();
		at.setId(paymentBean.getToAccountTypeId());
		paymentWorkflow.setToAccountTypeId(at);
		paymentWorkflowRepository.save(paymentWorkflow);
		
		paymentWorkflowService.startPaymentWorkflow(paymentBean, paymentWorkflow);
		
	}
	
	@SuppressWarnings("unchecked")
    @Override
	@Transactional
	public void approvePayment(Long paymentWorkflowId) {	
		
		PaymentWorkflow paymentWorkflow = paymentWorkflowRepository.findOne(paymentWorkflowId);
		Map<String, Object> params = paymentWorkflowService.findTask(paymentWorkflowId + "");
		PaymentBean paymentBean = new PaymentBean();
		paymentBean.setToAccountHolderId(Integer.parseInt(params.get("toAccountHolderId").toString()));
		paymentBean.setToAccountTypeId(Integer.parseInt(params.get("toAccountTypeId").toString()));
		paymentBean.setAmount(Double.parseDouble(params.get("mode").toString()));
		paymentBean.setChequeNo(params.get("chequeNo").toString());
		paymentBean.setComments(params.get("amount").toString());
		paymentBean.setMode(params.get("mode").toString());
		paymentBean.setSelectedInvoiceIds(((ArrayList<Integer>) params.get("selectedInvoiceIds")));
		
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
		
		// Move workflow to next step
		paymentWorkflowService.moveToNextTask(paymentWorkflowId, null);
	}

}
