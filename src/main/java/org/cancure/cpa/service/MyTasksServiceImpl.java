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
import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.persistence.entity.Doctor;
import org.cancure.cpa.persistence.entity.PatientDocument;
import org.cancure.cpa.persistence.entity.PatientInvestigation;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.DoctorRepository;
import org.cancure.cpa.persistence.repository.UserRepository;
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
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PatientInvestigationService patientInvestigationService;
	
	@Autowired
	private PatientDocumentService patientDocumentService;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private DoctorRepository doctorRepository;
	
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
	
	private Map<String, Object> extractHistoryTaskAttributes(List<HistoricTaskInstance> tasks,String patientID) {
		Map<String, Object> parentMap = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		PatientBean patient=patientService.get(Integer.parseInt(patientID));
		parentMap.put("patient", toMap(patient));
		
		Map taskMap = new HashMap<>();
		
		String nextTask = null;
		
		for (HistoricTaskInstance t : tasks) {
			Map<String, Object> map = new HashMap<>();
			//List<PatientInvestigationBean> patientInvestigationBean=new ArrayList<>();
			//patientInvestigationBean=patient.getPatientInvestigation();
			map.put("executionId", t.getExecutionId());
			if (t.getCreateTime() != null) {
				map.put("createTime", sdf.format(t.getCreateTime()));
			}
			if (t.getEndTime() != null) {
				map.put("endTime",  sdf.format(t.getEndTime()));
			}
			/*for(PatientInvestigationBean temp:patientInvestigationBean){
			    PatientInvestigation patientInvestigation=patientInvestigationService.findByTask_id(t.getId());			    
			}*/
			PatientInvestigation patientInvestigation=patientInvestigationService.findByTaskId(t.getId());  
			List<PatientDocument> patientDocuments=patientDocumentService.findByTaskId(t.getId());
			map.put("id", t.getId());
			map.put("nextTask", t.getName());
			map.put("description", t.getDescription());
			map.put("documents", toMap(patientDocuments));
			map.put("investigation", toMap(patientInvestigation));
			
			Map<String, Object> processVars = t.getProcessVariables();
			Object patientId = processVars.get("prn");
			Object patientName = patient.getName();
			if (patientId != null) {
				map.put("prn", patientId.toString());
			}
			
			if (patientId != null){
				map.put("patientName", patientName.toString());
			}

			if ("MBDoctorApproval".equals(t.getName()) || "ECApproval".equals(t.getName())) {
				
				List<Object> existingObject = (List)taskMap.get(t.getName());
				if (existingObject != null) {
					existingObject.add(map);
				} else {
					List<Object> listOfTask = new ArrayList<>();
					listOfTask.add(map);
					taskMap.put(t.getName(), listOfTask);
				}
				
			} else {
				taskMap.put(t.getName(), map);
			}
			
			if (t.getEndTime() == null) {
				nextTask = t.getName();
			}
			
		}
		
		if (nextTask != null) {
			parentMap.put("nextTask", nextTask);
		}
		parentMap.put("tasks", taskMap);
		return parentMap;
	}
	
	private List<Map<String, String>> toMap(List<PatientDocument> patientDocuments) {
	    List<Map<String, String>> list = new ArrayList<>();
	    if (patientDocuments != null) {
	        for (PatientDocument doc : patientDocuments) {
	            Map<String, String> map = new HashMap<>();
	            map.put("docCategory", doc.getDocCategory());
	            map.put("docType", doc.getDocType());
	            map.put("docId", doc.getDocId().toString());
	            list.add(map);
	        }
	    }
	    
	    return list;
	}
	
    private Map<String, String> toMap(PatientBean patient) {
        Map<String, String> map = new HashMap<>();
        if (patient != null) {
        	map.put("prn", patient.getPrn().toString());
            map.put("name", patient.getName());
            map.put("address", patient.getAddress());
            map.put("contact", patient.getContact());
            map.put("employmentStatus", patient.getEmploymentStatus());
			map.put("solebreadwinner", patient.getSolebreadwinner() != null
					? String.valueOf(patient.getSolebreadwinner()) : Boolean.FALSE.toString());
            map.put("assetsOwned", patient.getAssetsOwned());
            map.put("gender", patient.getGender());
            map.put("typeOfSupport", patient.getTypeOfSupport());
        }

        return map;
    }
    
    private Map<String, String> toMap(PatientInvestigation patientInvestigation) {
        Map<String, String> map = new HashMap<>();
        if (patientInvestigation != null) {
            map.put("comments", patientInvestigation.getComments());
            map.put("investigatorType", patientInvestigation.getInvestigatorType());
            map.put("status", patientInvestigation.getStatus());
            map.put("investigationDate", patientInvestigation.getInvestigationDate().toString());

            Integer investigatorId = patientInvestigation.getInvestigatorId();
            if (investigatorId != null) {
                map.put("investigatorId", investigatorId + "");
                map.put("investigatorName",
                        getInvestigator(patientInvestigation.getInvestigatorType(), investigatorId));
            }
        }

        return map;
    }


    private String getInvestigator(String investigatorType, Integer investigatorId) {

        String investigatorName = "";
        switch (investigatorType) {
        case "Doctor":
        	Doctor doc = doctorRepository.findOne(investigatorId);
        	if (doc != null) {
        		investigatorName = doc.getName();
        	} else {
        		investigatorName = "N/A";
        	}
             
            break;
        case "Program Coordinator":
            investigatorName = findInvestigator(investigatorId);
            break;
        case "Secretary":
            investigatorName = findInvestigator(investigatorId);
            break;
        default:
            investigatorName = "n/a";
        }

        return investigatorName;
    }
    
    private String findInvestigator(Integer investigatorId) {
        User user = userRepository.findOne(investigatorId);
        return user == null? "" : user.getName();
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
		
		if (patientName != null){
			map.put("patientName", patientName.toString());
		}
		
		return map;
	}

    public Map<String, String> getNextTask(String patientId, String processKey) {
        ProcessInstance procInsts = runtimeService.createProcessInstanceQuery().processDefinitionKey(processKey)
                .processInstanceBusinessKey(patientId).singleResult();

        if (procInsts == null) { // No active executions. Check history.
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processDefinitionKey(processKey).processInstanceBusinessKey(patientId).includeProcessVariables().singleResult();

            if (historicProcessInstance == null) {
                return new HashMap();
            } else {
                Map<String, String> nextTaskMap = new HashMap<>();
                nextTaskMap.put("nextTask", "");
                nextTaskMap.put("endTime", historicProcessInstance.getEndTime().toString());
                nextTaskMap.put("description", historicProcessInstance.getDescription());
                nextTaskMap.put("prn", patientId);
                
                Map<String, Object> processVars = historicProcessInstance.getProcessVariables();
        		Object patientName = processVars.get("patientName");
        		if (patientName != null){
        			nextTaskMap.put("patientName", patientName.toString());
        		}
        		
                return nextTaskMap;
            }
        } else {

            Task taskData = taskService.createTaskQuery().processInstanceId(procInsts.getProcessInstanceId())
            		.includeProcessVariables().singleResult();

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
		        processInstanceId = historicProcessInstance.getId();
		    }
		    
		} else {
		    processInstanceId = procInsts.getProcessInstanceId();
		}
		
		
		List<HistoricTaskInstance> taskHistory = historyService.createHistoricTaskInstanceQuery()
			.processInstanceId(processInstanceId).includeProcessVariables()
			.orderByTaskCreateTime().asc().list();
		
		return extractHistoryTaskAttributes(taskHistory,patientId);
		
	}
	
	/*public List<Map<String, String>> getPatientWorkflowStatus(String prn) {
		List<Task> tasks = taskService.createTaskQuery().includeProcessVariables().processInstanceBusinessKey(prn)
				.processDefinitionKey("patientRegn").list();
		return extractTaskAttributes(tasks);
	}*/
}
