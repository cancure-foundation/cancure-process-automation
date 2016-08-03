package org.cancure.cpa.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyTasksServiceImpl implements MyTasksService {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private HistoryService historyService;
	
	@Override
	public List<Map<String, String>> getMyTasks(List<String> roles) {

		List<Map<String, String>> list = new ArrayList<>();

		if (roles == null || roles.size() < 1) {
			return list;
		}

		List<Task> tasks = taskService.createTaskQuery().includeProcessVariables().orderByTaskCreateTime().asc()
				.taskCandidateGroupIn(roles).list();

		list = extractTaskAttributes(tasks);

		return list;
	}

	private List<Map<String, String>> extractTaskAttributes(List<Task> tasks) {
		List<Map<String, String>> list = new ArrayList<>();
		for (Task t : tasks) {
			list.add(extractOneTask(t));
		}
		
		return list;
	}
	
	private Map<String, Object> extractHistoryTaskAttributes(List<HistoricTaskInstance> tasks) {
		Map<String, Object> parentMap = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		parentMap.put("patient", "John Doe");
		
		Map taskMap = new HashMap<>();
		
		for (HistoricTaskInstance t : tasks) {
			Map<String, String> map = new HashMap<>();
			map.put("executionId", t.getExecutionId());
			if (t.getCreateTime() != null) {
				map.put("createTime", sdf.format(t.getCreateTime()));
			}
			if (t.getEndTime() != null) {
				map.put("endTime",  sdf.format(t.getEndTime()));
			}
			map.put("id", t.getId());
			map.put("nextTask", t.getName());
			map.put("description", t.getDescription());
			Map<String, Object> processVars = t.getProcessVariables();
			Object patientId = processVars.get("prn");
			Object patientName = processVars.get("patientName");
			if (patientId != null) {
				map.put("prn", patientId.toString());
			}
			
			if (patientId != null){
				map.put("patientName", patientName.toString());
			}

			taskMap.put(t.getName(), map);
		}
		
		parentMap.put("tasks", taskMap);
		return parentMap;
	}
	
	private Map<String, String> extractOneTask(Task t){
		Map<String, String> map = new HashMap<>();
		map.put("executionId", t.getExecutionId());
		map.put("createTime", t.getCreateTime() != null ? t.getCreateTime().toString() : null);
		map.put("id", t.getId());
		map.put("nextTask", t.getName());
		Map<String, Object> processVars = t.getProcessVariables();
		Object patientId = processVars.get("prn");
		Object patientName = processVars.get("patientName");
		if (patientId != null) {
			map.put("prn", patientId.toString());
		}
		
		if (patientId != null){
			map.put("patientName", patientName.toString());
		}
		
		return map;
	}

    public Map<String, String> getNextTask(String patientId, String processKey) {
        ProcessInstance procInsts = runtimeService.createProcessInstanceQuery().processDefinitionKey(processKey)
                .processInstanceBusinessKey(patientId).singleResult();

        if (procInsts == null) { // No active executions. Check history.
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processDefinitionKey(processKey).processInstanceBusinessKey(patientId).singleResult();

            if (historicProcessInstance == null) {
                return new HashMap();
            } else {
                Map<String, String> nextTaskMap = new HashMap<>();
                nextTaskMap.put("nextTask", "");
                nextTaskMap.put("endTime", historicProcessInstance.getEndTime().toString());
                nextTaskMap.put("description", historicProcessInstance.getDescription());
                return nextTaskMap;
            }
        } else {

            Task taskData = taskService.createTaskQuery().processInstanceId(procInsts.getProcessInstanceId())
                    .singleResult();

            return extractOneTask(taskData);
        }
    }
	
	public Map<String, Object> getTaskHistory(String patientId, String processKey) {
		ProcessInstance procInsts = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(processKey)
				.processInstanceBusinessKey(patientId).singleResult();
			
		String processInstanceId;
		
		if (procInsts == null) { // No active executions. Check history.
		    HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
		    .processDefinitionKey(processKey)
		    .processInstanceBusinessKey(patientId).singleResult();
		    
		    if (historicProcessInstance == null) {
		        return new HashMap();
		    } else {
		        processInstanceId = historicProcessInstance.getSuperProcessInstanceId();
		    }
		    
		} else {
		    processInstanceId = procInsts.getProcessInstanceId();
		}
		
		
		List<HistoricTaskInstance> taskHistory = historyService.createHistoricTaskInstanceQuery()
			.processInstanceId(processInstanceId).includeProcessVariables()
			.orderByTaskCreateTime().asc().list();
		
		return extractHistoryTaskAttributes(taskHistory);
		
	}
	
	/*public List<Map<String, String>> getPatientWorkflowStatus(String prn) {
		List<Task> tasks = taskService.createTaskQuery().includeProcessVariables().processInstanceBusinessKey(prn)
				.processDefinitionKey("patientRegn").list();
		return extractTaskAttributes(tasks);
	}*/
}
