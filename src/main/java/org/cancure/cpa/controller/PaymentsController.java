package org.cancure.cpa.controller;

import java.util.List;

import org.cancure.cpa.controller.beans.InvoicesBean;
import org.cancure.cpa.controller.beans.JournalBean;
import org.cancure.cpa.controller.beans.PaymentBean;
import org.cancure.cpa.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentsController {

	@Autowired
	private PaymentService paymentService;

	/*@RequestMapping(method = RequestMethod.POST, value = "/journal")
	public Long save(@RequestBody JournalBean journal) {
		return paymentService.saveJournal(journal);
	}*/
	
	@RequestMapping(method = RequestMethod.PUT, value = "/payments")
	public String savePayment(@RequestBody PaymentBean paymentBean) {
		paymentService.startPaymentWorkflow(paymentBean);
		return "success";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/payments/{paymentWorkflowId}")
	public String approvePayment(@PathVariable("paymentWorkflowId") Long paymentWorkflowId) {
		paymentService.approvePayment(paymentWorkflowId);
		return "success";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/invoices/{accountTypeId}/{accountHolderId}")
	public List<InvoicesBean> getAccountInvoices(@PathVariable("accountTypeId") Integer accountTypeId,
			@PathVariable("accountHolderId") Integer accountHolderId) {
		
		return paymentService.getInvoices(accountTypeId, accountHolderId);
		
	}

	@RequestMapping("/journal/{id}")
	public JournalBean get(Long id) {
		return paymentService.getJournal(id);
	}

}
