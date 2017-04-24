package org.cancure.cpa.service;

import static org.cancure.cpa.common.Constants.IN_PATIENT_HOSPITAL_VISIT_DEF_KEY;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.cancure.cpa.persistence.entity.PatientVisit;
import org.cancure.cpa.persistence.entity.Settings;
import org.cancure.cpa.persistence.repository.PatientVisitRepository;
import org.cancure.cpa.persistence.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InPatientHospitalVisitService {

	private static Logger logger = Logger.getLogger(InPatientHospitalVisitService.class);

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SettingsRepository settingsRepository;
	
	@Autowired
	private PatientVisitRepository patientVisitRepository;
	
	public String getMaxTopupApprovalWaitTime(){
		return getSettingsInHours(13);
	}
	
	public String getMaxSelectPharmaTime(){
		return getSettingsInHours(13);
	}

	/**
	 * Return time in ISO 8601 Format (Duration).
	 * Duration calculated in hours.
	 * @param id
	 * @return
	 */
	private String getSettingsInHours(Integer id){
		Settings sett = settingsRepository.findOne(id);
		return sett.getValue();
	}

	public String startInPatientHospitalVisitWorkflow(Map<String, Object> variables, String pidn, Integer patientVisitId) {

		ProcessInstance procInst = runtimeService.startProcessInstanceByKey(IN_PATIENT_HOSPITAL_VISIT_DEF_KEY, pidn + "_" + patientVisitId,
				variables);
		logger.debug("Started patient hospital visit workflow for patient with pidn = " + pidn);
		return procInst.getId();
	}

	public String moveToNextTask(String pidn, Integer patientVisitId, Map<String, Object> activitiVars) {

		String taskId = findTask(pidn + "_" + patientVisitId).get("id");
		logger.info("Moving Task Id ..." + taskId);
		// Complete current task
		taskService.complete(taskId, activitiVars);
		return taskId;
	}
	
	@Transactional
	public void performClosureTasks(String pidn, Integer patientVisitId){
		
		PatientVisit patVisit = patientVisitRepository.findOne(patientVisitId);
		patVisit.setStatus("closed");
		patientVisitRepository.save(patVisit);
		
		logger.info("Closing task for PIDN ..." + pidn + " and patientVisitId " + patientVisitId);
	}

	public Map<String, String> findTask(String pidnAndPatientVisitId) {
		ProcessInstance procInsts = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(IN_PATIENT_HOSPITAL_VISIT_DEF_KEY).processInstanceBusinessKey(pidnAndPatientVisitId)
				.singleResult();
		Task taskData = taskService.createTaskQuery().processInstanceId(procInsts.getProcessInstanceId())
				.singleResult();
		//taskData.get
		return extractOneTask(taskData);
	}
	
	private Map<String, String> extractOneTask(Task t){
		Map<String, String> map = new HashMap<>();
		map.put("id", t.getId());
		map.put("taskDefinitionKey", t.getTaskDefinitionKey());
		map.put("name", t.getName());
		return map;
	}
	
}
