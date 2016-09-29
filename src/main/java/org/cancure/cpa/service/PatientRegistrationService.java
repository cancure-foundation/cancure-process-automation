package org.cancure.cpa.service;

import static org.cancure.cpa.common.Constants.PATIENT_REG_PROCESS_DEF_KEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.cancure.cpa.persistence.entity.Settings;
import org.cancure.cpa.persistence.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PatientRegistrationService {

	private static Logger logger = Logger
			.getLogger(PatientRegistrationService.class);
	
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private SettingsRepository settingsRepository;
	
	@Autowired
	private TaskService taskService;

	private static final String approvedEcVarKey            = "approvedEcs";
	private static final String rejectedEcVarKey            = "rejectedEcs";
	private static final String mbDocApprovedVarKey         = "mbDocApproved";
	private static final String mbApprovedLoopCountVar      = "mbApprovedLoopCount";
	private static final String PATIENT_ID_STR              = "patientId";
	private static final String NOT_APPROVED_STR            = "NotApproved";
	private static final String APPROVED_STR                = "Approved";
	private static final String REJECTED_STR                = "Rejected";
	private static final String COMPLETED_STR               = "Completed";
	
	@Value("${activiti.patientReg.count.maxEcApproval}")
	private int MAX_EC_COUNT;
	
	@Value("${activiti.patientReg.count.minEcApproval}")
	private int MIN_EC_COUNT;

	/*
	@Value("${activiti.patientReg.timeout.bgCheck}")
	private String maxBgCheckTime;
	
	@Value("${activiti.patientReg.timeout.ecApproval}")
	private String maxEcApprovalTime;
*/
	public String getMaxBgCheckTime() {
		return getSettingsInHours(7);
	}

	public String getMaxEcApprovalTime() {
		return getSettingsInHours(8);
	}
	
	public String getBgCheckTimeCycle(){
		return "R/" + getSettingsInHours(9);
	}
	
	public String getSecretaryApprovalTimeCycle(){
		return "R/" + getSettingsInHours(11);
	}
	
	public String getMbDoctorApprovalTimeCycle(){
		return "R/" + getSettingsInHours(10);
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

		return taskId;
	}

	public String performClosureTasks(String patientId) {

		logger.info("performClosureTasks ...");
		TaskEntity task = (TaskEntity)findTask(patientId);
		String taskId = task.getId();
		logger.info("Moving Task Id ..." + taskId);
		
		Map vars = task.getVariables();
		String patName = (String)vars.get("patientName");
		Integer prn = (Integer)vars.get("prn");
		StringBuffer message = new StringBuffer("");
		message.append("Hi, <br>A person's workflow has been closed. <br>Patient Name : " + patName + 
				"<br>PRN : " + prn + "<br>" + 
				"<br>Thanks, <br/>Cancure");
		
		new NotificationComponent().notify(message.toString(), null, task);
		
		return COMPLETED_STR;
	}
	
	public String reminderTask(String patientId) {
		// Send reminder notifications
		TaskEntity task = (TaskEntity)findTask(patientId);
		String taskId = task.getId();
		logger.info("Moving Task Id ..." + taskId);
		
		Map vars = task.getVariables();
		String patName = (String)vars.get("patientName");
		Integer prn = (Integer)vars.get("prn");
		StringBuffer message = new StringBuffer("");
		message.append("Hi, <br>This is a reminder to take action on a person's workflow. "
				+ "<br>Patient Name : " + patName + 
				"<br>PRN : " + prn + "<br>" + 
				"<br>Task to do : " + task.getName() + 
				"<br>Thanks, <br/>Cancure");
		
		new NotificationComponent().notify(message.toString(), null, task);
		return COMPLETED_STR;
	}

	public String mbApprove(String patientId, String doctorId) {

		logger.info("mbApprove ..." + doctorId);

		Task taskData = findTask(patientId);
		String taskId=taskData.getId();

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
		return taskId;
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
	
	public String resetDocApprovalData(String patientId) {

		logger.info("resetDocApprovalData ...");

		Task taskData = findTask(patientId);
		String executionId = taskData.getExecutionId();
		
		reloadMbApproval(executionId, mbDocApprovedVarKey);

		return COMPLETED_STR;
	}
	
	public String ecApprove(String patientId, String ecId) {

		logger.info("ecApprove ..." + ecId);
		
		Task taskData = findTask(patientId);
		String taskId= taskData.getId();
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
		return taskId;
	}

	public String ecReject(String patientId, String ecId) {

		logger.info("ecReject ..." + ecId);
		
		Task taskData = findTask(patientId);
		String taskId= taskData.getId();
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
		return taskId;
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
	
	public void reloadMbApproval(String executionId, String key)
	{
		@SuppressWarnings("unchecked")
		ArrayList<String> mbApprovedDocs = (ArrayList<String>) runtimeService
				.getVariable(executionId, key);

		Integer mbApprovedLoopCount = (Integer) runtimeService
				.getVariable(executionId, mbApprovedLoopCountVar);
		if(mbApprovedLoopCount == null)
		{
			mbApprovedLoopCount = 0;
		}

		logger.info("mbApprovedDocs: " + mbApprovedDocs);
		logList(mbApprovedDocs, "mbDoc");
		
		String newKey = key + ++mbApprovedLoopCount; 
		
		runtimeService.setVariable(executionId, newKey, mbApprovedDocs);
		runtimeService.setVariable(executionId, key, null);
		runtimeService.setVariable(executionId, mbApprovedLoopCountVar, mbApprovedLoopCount);

		@SuppressWarnings("unchecked")
		ArrayList<String> mbApprovedDocs2 = (ArrayList<String>) runtimeService
				.getVariable(executionId, key);		
		logger.info("mbApprovedDocs: " + mbApprovedDocs2);
		logList(mbApprovedDocs2, "mbDoc");
	}
	
	public <T> void logList(List<T> listData, String varName)
	{
		logger.info("listData: " + listData);
		if(listData != null)
		{
			for (T value : listData) {
				logger.info(varName + value);
			}
		}			
	}
}

