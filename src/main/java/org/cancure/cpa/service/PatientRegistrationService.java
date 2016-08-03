package org.cancure.cpa.service;

import static org.cancure.cpa.common.Constants.PATIENT_REG_PROCESS_DEF_KEY;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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
	
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	private static final String approvedEcVarKey = "approvedEcs";
	private static final String rejectedEcVarKey = "rejectedEcs";
	private static final String mbDocApprovedVarKey = "mbDocApproved";
	private static final String MAX_BGCHECK_TIME = "P30D";
	private static final String MAX_ECAPPROVAL_TIME = "P30D";
	private static final String PATIENT_ID_STR = "patientId";
	private static final String NOT_APPROVED_STR = "NotApproved";
	private static final String APPROVED_STR = "Approved";
	private static final String REJECTED_STR = "Rejected";
	private static final String COMPLETED_STR = "Completed";
	private static final int MAX_EC_COUNT = 5;
	private static final int MIN_EC_COUNT = 2;

	public String getMaxBgCheckTime() {
		return MAX_BGCHECK_TIME;
	}

	public String getMaxEcApprovalTime() {
		return MAX_ECAPPROVAL_TIME;
	}

	public String startPatientRegnProcess(Map<String, Object> variables,
			String patientId) {

		ProcessInstance procInst = runtimeService.startProcessInstanceByKey(
				PATIENT_REG_PROCESS_DEF_KEY, patientId, variables);
		return "Started Proc instance! :: " + procInst.getId();
	}

	public Map<String, String> getTaskListWithPatientIds(String roleId) {
		Map<String, String> taskMap = new HashMap<String, String>();
		List<Task> taskList = taskService.createTaskQuery()
				.taskCandidateGroup(roleId).includeProcessVariables().list();

		for (Task task : taskList) {
			System.out.println(task);
			String patientId = (String) task.getProcessVariables().get(
					PATIENT_ID_STR);
			taskMap.put(patientId, task.getId());
		}
		return taskMap;

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
		return COMPLETED_STR;
	}

	public ArrayList<String> mbApprove(String patientId, String doctorId) {

		Task taskData = findTask(patientId);

		HashMap<String, Object> actVars = new HashMap<String, Object>();
		String executionId = taskData.getExecutionId();

		@SuppressWarnings("unchecked")
		ArrayList<String> mbApprovedDocs = (ArrayList<String>) runtimeService
				.getVariable(executionId, mbDocApprovedVarKey);
		if (mbApprovedDocs == null) {
			mbApprovedDocs = new ArrayList<String>();
			mbApprovedDocs.add(doctorId);

		} else if (!mbApprovedDocs.contains(doctorId)) {
			mbApprovedDocs.add(doctorId);
		}

		actVars.put(mbDocApprovedVarKey, mbApprovedDocs);
		taskService.complete(taskData.getId(), actVars);
		return mbApprovedDocs;
	}

	public String approvalCountCheck(String patientId) {

		logger.info("approvalCountCheck ...");

		Task taskData = findTask(patientId);

		@SuppressWarnings("unchecked")
		ArrayList<String> mbApprovedDocs = (ArrayList<String>) runtimeService
				.getVariable(taskData.getExecutionId(), mbDocApprovedVarKey);

		logger.info("Length: " + mbApprovedDocs.size());
		if (mbApprovedDocs.size() > 1) {
			return APPROVED_STR;
		} else {
			return NOT_APPROVED_STR;
		}
	}

	public ArrayList<String> ecApprove(String patientId, String ecId) {

		Task taskData = findTask(patientId);
		HashMap<String, Object> actVars = new HashMap<String, Object>();
		String executionId = taskData.getExecutionId();

		@SuppressWarnings("unchecked")
		ArrayList<String> approvedEcs = (ArrayList<String>) runtimeService
				.getVariable(executionId, approvedEcVarKey);
		if (approvedEcs == null) {
			approvedEcs = new ArrayList<String>();
			approvedEcs.add(ecId);

		} else if (!approvedEcs.contains(ecId)) {
			approvedEcs.add(ecId);
		}
		actVars.put(approvedEcVarKey, approvedEcs);
		taskService.complete(taskData.getId(), actVars);
		return approvedEcs;
	}

	public ArrayList<String> ecReject(String patientId, String ecId) {

		Task taskData = findTask(patientId);
		HashMap<String, Object> actVars = new HashMap<String, Object>();
		String executionId = taskData.getExecutionId();

		@SuppressWarnings("unchecked")
		ArrayList<String> rejectedEcs = (ArrayList<String>) runtimeService
				.getVariable(executionId, rejectedEcVarKey);
		if (rejectedEcs == null) {
			rejectedEcs = new ArrayList<String>();
			rejectedEcs.add(ecId);
		} else if (!rejectedEcs.contains(ecId)) {
			rejectedEcs.add(ecId);
		}
		actVars.put(rejectedEcVarKey, rejectedEcs);
		taskService.complete(taskData.getId(), actVars);
		return rejectedEcs;
	}

	public String ecApprovalCountCheck(String patientId) {

		logger.info("ecApprovalCountCheck ...");

		String approvalStatus = NOT_APPROVED_STR;
		Task taskData = findTask(patientId);

		@SuppressWarnings("unchecked")
		ArrayList<String> approvedEcs = (ArrayList<String>) runtimeService
				.getVariable(taskData.getExecutionId(), approvedEcVarKey);
		if (approvedEcs != null && approvedEcs.size() > MIN_EC_COUNT) {
			approvalStatus = APPROVED_STR;
		} else {
			@SuppressWarnings("unchecked")
			ArrayList<String> rejectedEcs = (ArrayList<String>) runtimeService
					.getVariable(taskData.getExecutionId(), rejectedEcVarKey);
			if (rejectedEcs != null
					&& rejectedEcs.size() >= (MAX_EC_COUNT - MIN_EC_COUNT)) {
				approvalStatus = REJECTED_STR;
			}
		}

		return approvalStatus;
	}

	public String updatePartners(String patientId) {

		logger.info("updatePartners ...");

		return COMPLETED_STR;
	}

	public Task findTask(String patientId) {
		ProcessInstance procInsts = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(PATIENT_REG_PROCESS_DEF_KEY)
				.processInstanceBusinessKey(patientId).singleResult();
		Task taskData = taskService.createTaskQuery()
				.processInstanceId(procInsts.getProcessInstanceId())
				.singleResult();

		return taskData;
	}
}
