package org.cancure.cpa.service;

import static org.cancure.cpa.common.Constants.PATIENT_HOSPITAL_VISIT_DEF_KEY;

import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.cancure.cpa.persistence.entity.Settings;
import org.cancure.cpa.persistence.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientHospitalVisitService {

	private static Logger logger = Logger.getLogger(PatientHospitalVisitService.class);

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SettingsRepository settingsRepository;
	
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

	public String startPatientHospitalVisitWorkflow(Map<String, Object> variables, String pidn) {

		ProcessInstance procInst = runtimeService.startProcessInstanceByKey(PATIENT_HOSPITAL_VISIT_DEF_KEY, pidn,
				variables);
		logger.debug("Started patient hospital visit workflow for patient with pidn = " + pidn);
		return procInst.getId();
	}

	public String moveToNextTask(String pidn, Map<String, Object> activitiVars) {

		String taskId = findTask(pidn).getId();
		logger.info("Moving Task Id ..." + taskId);
		// Complete current task
		taskService.complete(taskId, activitiVars);
		return taskId;
	}

	private Task findTask(String pidn) {
		ProcessInstance procInsts = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(PATIENT_HOSPITAL_VISIT_DEF_KEY).processInstanceBusinessKey(pidn)
				.singleResult();
		Task taskData = taskService.createTaskQuery().processInstanceId(procInsts.getProcessInstanceId())
				.singleResult();

		return taskData;
	}
}
