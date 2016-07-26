package org.cancure.cpa.service;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientRegistrationService {

	private static Logger logger = Logger
			.getLogger(PatientRegistrationService.class);
	private static String procDefKey = "patientRegn";

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	public String startPatientRegnProcess(Map<String, Object> variables,
			String patientId) {

		ProcessInstance procInst = runtimeService.startProcessInstanceByKey(
				procDefKey, patientId, variables);
		return "Started Proc instance! :: " + procInst.getId();
	}

	public String movePatientRegn(String patientId,
			Map<String, Object> activitiVars) {

		logger.info("movePatientRegn ...");
		String taskId = findTask(patientId).getId();
		logger.info("Moving Task Id ..." + taskId);
		taskService.complete(taskId, activitiVars);

		return "Moved Task Id: " + taskId;
	}

	public String performClosureTasks(String patientId) {

		logger.info("performClosureTasks ...");
		String taskId = findTask(patientId).getId();
		logger.info("Moving Task Id ..." + taskId);
		return "Completed";
	}

	public String approvalCountCheck(String patientId) {

		logger.info("approvalCountCheck ...");

		Task taskData = findTask(patientId);

		String mbApprovedDocs = (String) runtimeService.getVariable(
				taskData.getExecutionId(), "mbDocApproved");

		logger.info("Length: " + mbApprovedDocs.split("\\|").length);
		if (mbApprovedDocs.split("\\|").length > 1) {
			return "Approved";
		} else {
			return "NotYetApproved";
		}
	}

	public String updatePartners(String patientId) {

		logger.info("updatePartners ...");

		return "Completed";
	}

	public String mbApprove(String patientId, String doctorId) {

		Task taskData = findTask(patientId);

		HashMap<String, Object> actVars = new HashMap<String, Object>();
		String executionId = taskData.getExecutionId();
		String mbApprovedDocs = (String) runtimeService.getVariable(
				executionId, "mbDocApproved");
		if (mbApprovedDocs == null) {
			mbApprovedDocs = doctorId;
		} else if (!mbApprovedDocs.contains(doctorId)) {
			mbApprovedDocs = mbApprovedDocs + "|" + doctorId;
		}
		actVars.put("mbDocApproved", mbApprovedDocs);
		taskService.complete(taskData.getId(), actVars);
		return mbApprovedDocs;
	}

	public String ecApprove(String patientId, String ecId) {

		Task taskData = findTask(patientId);
		HashMap<String, Object> actVars = new HashMap<String, Object>();
		String executionId = taskData.getExecutionId();
		String approvedEcs = (String) runtimeService.getVariable(executionId,
				"approvedEcs");
		if (approvedEcs == null) {
			approvedEcs = ecId;
		} else if (!approvedEcs.contains(ecId)) {
			approvedEcs = approvedEcs + "|" + ecId;
		}
		actVars.put("approvedEcs", approvedEcs);
		taskService.complete(taskData.getId(), actVars);
		return approvedEcs;
	}

	public String ecApprovalCountCheck(String patientId) {

		logger.info("ecApprovalCountCheck ...");

		Task taskData = findTask(patientId);

		String approvedEcs = (String) runtimeService.getVariable(
				taskData.getExecutionId(), "approvedEcs");
		if (approvedEcs.split("\\|").length > 2) {
			return "Approved";
		} else {
			return "NotApproved";
		}
	}

	private Task findTask(String patientId) {
		ProcessInstance procInsts = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(procDefKey)
				.processInstanceBusinessKey(patientId).singleResult();
		Task taskData = taskService.createTaskQuery()
				.processInstanceId(procInsts.getProcessInstanceId())
				.singleResult();

		return taskData;
	}
}
