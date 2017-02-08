package org.cancure.cpa.service;

import static org.cancure.cpa.common.Constants.PAYMENT_DEF_KEY;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.cancure.cpa.controller.beans.PaymentBean;
import org.cancure.cpa.persistence.entity.PaymentWorkflow;
import org.cancure.cpa.persistence.entity.Settings;
import org.cancure.cpa.persistence.repository.PaymentWorkflowRepository;
import org.cancure.cpa.persistence.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentWorkflowService {

	private static Logger logger = Logger.getLogger(PaymentWorkflowService.class);

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SettingsRepository settingsRepository;
	
	@Autowired
	private PaymentWorkflowRepository paymentWorkflowRepository;
	
	public String getMaxTopupApprovalWaitTime(){
		return "R/" + getSettingsInHours(13);
	}
	
	public String getMaxSelectPharmaTime(){
		return "R/" + getSettingsInHours(13);
	}

	/**
	 * Return time in ISO 8601 Format (Duration).
	 * Duration calculated in hours.
	 * @param id
	 * @return
	 */
	private String getSettingsInHours(Integer id){
		Settings sett = settingsRepository.findOne(id);
		return "PT" + sett.getValue() + "H";
	}

	public String startPaymentWorkflow(PaymentBean paymentBean, PaymentWorkflow paymentWorkflow) {

		Map<String, Object> variables = new HashMap<>();
		variables.put("selectedInvoiceIds", paymentBean.getSelectedInvoiceIds());
		variables.put("mode", paymentBean.getMode());
		variables.put("chequeNo", paymentBean.getChequeNo());
		variables.put("comments", paymentBean.getComments());
		variables.put("toAccountTypeId", paymentBean.getToAccountTypeId());
		variables.put("toAccountHolderId", paymentBean.getToAccountHolderId());
		variables.put("paymentWorkflowId", paymentWorkflow.getId());
		variables.put("amount", paymentBean.getAmount());
		
		ProcessInstance procInst = runtimeService.startProcessInstanceByKey(PAYMENT_DEF_KEY, paymentWorkflow.getId() + "",
				variables);
		logger.debug("Started payment workflow with id = " + paymentWorkflow.getId());
		return procInst.getId();
	}

	public String moveToNextTask(Long workflowId, Map<String, Object> activitiVars) {

		String taskId = (String)findTask(workflowId + "").get("id");
		logger.info("Moving Task Id ..." + taskId);
		// Complete current task
		taskService.complete(taskId, activitiVars);
		return taskId;
	}
	
	@Transactional
	public void performClosureTasks(Long paymentWorkflowId){
		PaymentWorkflow paymentWorkflow = paymentWorkflowRepository.findOne(paymentWorkflowId);
		paymentWorkflow.setStatus("closed");
		paymentWorkflowRepository.save(paymentWorkflow);
		logger.info("Closing payment task for workflowId ..." + paymentWorkflowId);
	}

	public Map<String, Object> findTask(String patientWorkflowId) {
		ProcessInstance procInsts = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(PAYMENT_DEF_KEY).processInstanceBusinessKey(patientWorkflowId)
				.singleResult();
		Task taskData = taskService.createTaskQuery().processInstanceId(procInsts.getProcessInstanceId()).includeProcessVariables()
				.singleResult();
		//taskData.get
		return extractOneTask(taskData);
	}
	
	private Map<String, Object> extractOneTask(Task t){
		Map<String, Object> map = new HashMap<>();
		map.put("id", t.getId());
		map.put("taskDefinitionKey", t.getTaskDefinitionKey());
		
		Map<String, Object> processVars = t.getProcessVariables();
		map.put("mode", processVars.get("mode"));
		map.put("chequeNo", processVars.get("chequeNo"));
		map.put("comments", processVars.get("comments"));
		map.put("toAccountTypeId", processVars.get("toAccountTypeId"));
		map.put("toAccountHolderId", processVars.get("toAccountHolderId"));
		map.put("selectedInvoiceIds", processVars.get("selectedInvoiceIds"));
		
		return map;
	}
	
}
