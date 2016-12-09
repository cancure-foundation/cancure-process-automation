package org.cancure.cpa.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.cancure.cpa.common.Constants;
import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.persistence.entity.Doctor;
import org.cancure.cpa.persistence.entity.HpocHospital;
import org.cancure.cpa.persistence.entity.PatientDocument;
import org.cancure.cpa.persistence.entity.PatientInvestigation;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.DoctorRepository;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.springframework.beans.BeanUtils;
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
    private HpocHospitalService hpocHospitalService;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private DoctorRepository doctorRepository;
	
	@Override
	public Map<String, List<Map<String, String>>> getMyTasks(List<String> roles, Integer myUserId) {

		Map<String, List<Map<String, String>>> allProcessMap = new HashMap<>();
		
		List<Map<String, String>> list = new ArrayList<>();

		if (roles == null || roles.isEmpty()) {
			return allProcessMap;
		}

		List<Task> tasks = taskService.createTaskQuery()
				.processDefinitionKey(Constants.PATIENT_REG_PROCESS_DEF_KEY)
				.includeProcessVariables().orderByTaskCreateTime().asc()
				.taskCandidateGroupIn(roles).list();

		if (myUserId != null) {
			tasks = filterTasksForRoles(roles, tasks, myUserId);
		}
		list = extractTaskAttributes(tasks);

		allProcessMap.put("PATIENT_REG_PROCESS_DEF_KEY", list);
		
		List<Map<String, String>> hospitalVisitList = new ArrayList<>();
		List<Task> hospitalVisitTasks = taskService.createTaskQuery()
				.processDefinitionKey(Constants.PATIENT_HOSPITAL_VISIT_DEF_KEY)
				.includeProcessVariables().orderByTaskCreateTime().asc()
				.taskCandidateGroupIn(roles).list();

		if (myUserId != null) {
			hospitalVisitTasks = filterHospitalVisitTasksForRoles(roles, hospitalVisitTasks, myUserId);
		}
		
		hospitalVisitList = extractHospitalVisitTaskAttributes(hospitalVisitTasks);
		
		allProcessMap.put("PATIENT_HOSPITAL_VISIT_DEF_KEY", hospitalVisitList);
		return allProcessMap;
	}

	private List<Map<String, String>> extractHospitalVisitTaskAttributes(List<Task> hospitalVisitTasks) {
		List<Map<String, String>> list = new ArrayList<>();
		for (Task t : hospitalVisitTasks) {
			list.add(extractOneHospitalVisitTask(t));
		}
		
		return list;
	}

	private Map<String, String> extractOneHospitalVisitTask(Task t) {
		Map<String, String> map = new HashMap<>();
		map.put("executionId", t.getExecutionId());
		map.put("createTime", t.getCreateTime() != null ? t.getCreateTime().toString() : null);
		map.put("id", t.getId());
		map.put("nextTask", t.getName());
		map.put("nextTaskKey" ,t.getTaskDefinitionKey());
		Map<String, Object> processVars = t.getProcessVariables();
		Object patientId = processVars.get("prn");
		String taskKey = t.getTaskDefinitionKey();
		
		PatientBean patientBean = null;
		
		Object patientName = processVars.get("patientName");
		Object pidn = processVars.get("pidn");
		if (patientId != null) {
			map.put("prn", patientId.toString());
		}
		
		if (patientName != null){
			map.put("patientName", patientName.toString());
		}
		
		if (pidn != null){
			map.put("pidn", pidn.toString());
		}
		Object patientVisitId = processVars.get("patientVisitId");
		if (patientVisitId != null){
			map.put("patientVisitId", patientVisitId.toString());
		}
		
		return map;
	}

	private List<Task> filterTasksForRoles(List<String> roles, List<Task> tasks, Integer myUserId) {
		if (roles.contains("ROLE_HOSPITAL_POC")){
			List<Task> filteredTasks = new ArrayList<>();
			for (Task task: tasks){
				//if ("Preliminary Examination".equals(task.getName())){
				if ("preliminaryExamination".equals(task.getTaskDefinitionKey())){
					Map<String, Object> processVars = task.getProcessVariables();
					Object preliminaryExamHospitalId = processVars.get("preliminaryExamHospitalId");
					if (preliminaryExamHospitalId != null){
						HpocHospital hpocHosMapping = hpocHospitalService.getHospitalFromHpoc(myUserId);
						// Show to a HPOC only if the Task is assigned to his hospital.
						if (hpocHosMapping != null && hpocHosMapping.getHospitalId() == Integer.parseInt(preliminaryExamHospitalId.toString())){
							filteredTasks.add(task);
						}
					} else {
						// If the task is not assigned to a specific hospital, show it to all.
						filteredTasks.add(task);
					}
				} else {
					filteredTasks.add(task);
				}
			}
			
			return filteredTasks;
			
		} else {
			return tasks;
		}
	}
	
	private List<Task> filterHospitalVisitTasksForRoles(List<String> roles, List<Task> tasks, Integer myUserId) {
		if (roles.contains("ROLE_HOSPITAL_POC")){
			List<Task> filteredTasks = new ArrayList<>();
			for (Task task: tasks){
				if ("selectPharmacy".equals(task.getTaskDefinitionKey())){
					Map<String, Object> processVars = task.getProcessVariables();
					Object hospitalId = processVars.get("hospitalId");
					if (hospitalId != null){
						HpocHospital hpocHosMapping = hpocHospitalService.getHospitalFromHpoc(myUserId);
						// Show to a HPOC only if the Task is assigned to his hospital.
						if (hpocHosMapping != null && hpocHosMapping.getHospitalId() == Integer.parseInt(hospitalId.toString())){
							filteredTasks.add(task);
						}
					}
				} else {
					filteredTasks.add(task);
				}
			}
			
			return filteredTasks;
			
		} else {
			return tasks;
		}
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		PatientBean patient=patientService.get(Integer.parseInt(patientID));
		
		//Map taskMap = new HashMap<>();
		List taskList = new ArrayList<>();
		
		String nextTask = null;
		String nextTaskKey = null;
		
		Collections.sort(tasks, new Comparator<HistoricTaskInstance>(){

			@Override
			public int compare(HistoricTaskInstance x, HistoricTaskInstance y) {
				int value = x.getCreateTime().compareTo(y.getCreateTime());
				if (value == 0){
					try {
						value = Integer.parseInt(x.getId()) - Integer.parseInt(y.getId());
					} catch (NumberFormatException e) {
						value = x.getId().compareTo(y.getId());
					}
					return value;
				} else {
					return value;
				}
			}
			
		});
		
		int i=0,flag=0;
		for (HistoricTaskInstance t : tasks) {
            Map<String, Object> map = new HashMap<>();
		    //if(t.gettName().equals("Patient Registration")){
            if ("patientRegistration".equals(t.getTaskDefinitionKey())) {
		        map.put("patient",patient);
		    }
			map.put("sequenceNo", i++);
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
			List<PatientDocumentBean> patientDocumentBeanList=new ArrayList<PatientDocumentBean>();
	        //if(t.getName().equals("Patient Registration")){
	        if ("patientRegistration".equals(t.getTaskDefinitionKey())) {	 
	             for(PatientDocument patientDocument:patientDocuments){
	                 PatientDocumentBean patientDocumentBean=new PatientDocumentBean();
	                 BeanUtils.copyProperties(patientDocument, patientDocumentBean);
	                 patientDocumentBean.setPrn(patientID);
	                 patientDocumentBeanList.add(patientDocumentBean);
	             }
	             patient.setDocument(patientDocumentBeanList);   
	             map.put("patient",patient);
	        }
			map.put("id", t.getId());
			map.put("nextTask", t.getName());
			map.put("nextTaskKey", t.getTaskDefinitionKey());
			map.put("description", t.getDescription());
			//if(!t.getName().equals("Patient Registration")){
			if (!"patientRegistration".equals(t.getTaskDefinitionKey())) {
			    map.put("documents", toMap(patientDocuments));
			    map.put("investigation", toMap(patientInvestigation));
			}						
			Map<String, Object> processVars = t.getProcessVariables();
			Object patientId = processVars.get("prn");
			Object patientName = patient.getName();
			if (patientId != null) {
				map.put("prn", patientId.toString());
			}
			
			if (patientId != null){
				map.put("patientName", patientName.toString());
			}
			taskList.add(map);
			
			if (t.getEndTime() == null) {
				nextTask = t.getName();
				nextTaskKey = t.getTaskDefinitionKey();
				String taskId = t.getId();
				List<IdentityLink> roleList = taskService.getIdentityLinksForTask(taskId);
				List<String> roleNames = new ArrayList<>();
				for (IdentityLink role : roleList) {
					roleNames.add(role.getGroupId());
				}
				parentMap.put("Owner", roleNames);
			}
			//if(t.getName().equals("Patient ID Card Generation")){
			if ("patientIdCardGeneration".equals(t.getTaskDefinitionKey())) {
			    flag=1;
			}
		}

        if (nextTask != null || flag==1) {
            parentMap.put("nextTask", nextTask);
            parentMap.put("nextTaskKey", nextTaskKey);
        } else {
            parentMap.put("nextTask", "Rejected");
            parentMap.put("nextTaskKey", "Rejected");
        }
		parentMap.put("tasks", taskList);
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
        case "Executive Committee":
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
		map.put("nextTaskKey" ,t.getTaskDefinitionKey());
		Map<String, Object> processVars = t.getProcessVariables();
		Object patientId = processVars.get("prn");
		String taskKey = t.getTaskDefinitionKey();
		
		PatientBean patientBean = null;
		
		//if(!t.getName().equals("Preliminary Examination")){
		if (!taskKey.equals("preliminaryExamination")) {
			patientBean = patientService.get((Integer)patientId);
			map.put("hospitalCostEstimate", valueOf(patientBean.getHospitalCostEstimate()));
			map.put("medicalCostEstimate", valueOf(patientBean.getMedicalCostEstimate()));									
		}
		//if(t.getName().equals("Secretary Approval") || t.getName().equals("EC Approval")){
		if (taskKey.equals("secretaryApproval") || taskKey.equals("ecApproval")) {
			if (patientBean == null) {
				patientBean = patientService.get((Integer)patientId);
			}
		    map.put("hospitalCostApproved", valueOf(patientBean.getHospitalCostApproved()));
            map.put("medicalCostApproved", valueOf(patientBean.getMedicalCostApproved()));
		}
		Object patientName = processVars.get("patientName");
		Object pidn = processVars.get("pidn");
		if (patientId != null) {
			map.put("prn", patientId.toString());
		}
		
		if (patientName != null){
			map.put("patientName", patientName.toString());
		}
		
		if (pidn != null){
			map.put("pidn", pidn.toString());
		}
				
		return map;
	}

    private String valueOf(Integer hospitalCostEstimate) {
		if (hospitalCostEstimate == null) {
			return "0";
		} else {
			return hospitalCostEstimate.toString();
		}
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
                nextTaskMap.put("nextTaskKey", "");
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
