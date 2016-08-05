package org.cancure.cpa.controller;

import static org.cancure.cpa.common.Constants.PATIENT_REG_PROCESS_DEF_KEY;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.service.MyTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyWorkflowController {

	@Autowired
	private MyTasksService myTasksService;

	@RequestMapping("/tasks/my")
	public List<Map<String, String>> getPendingTasks(OAuth2Authentication auth) {
		List<String> roles = new ArrayList<>();
		auth.getAuthorities().forEach(x -> roles.add(x.getAuthority()));

		return myTasksService.getMyTasks(roles);

	}
	
	@RequestMapping("/tasks/role/{role}")
	public List<Map<String, String>> getPendingTasks(@PathVariable("role") String roles){
		if (roles != null) {
			String[] roleArray = roles.split("__");
			List<String> rolesList = new ArrayList<>();
			for (String r : roleArray){
				if (!r.isEmpty()){
					rolesList.add(r);
				}
			}
			
			return myTasksService.getMyTasks(rolesList);
		}
		
		return new ArrayList<>();
	}

	@RequestMapping("/tasks/{prn}")
	public Map<String, String> getTasks(@PathVariable("prn") String prn) {
		
		return myTasksService.getNextTask(prn, PATIENT_REG_PROCESS_DEF_KEY);

	}
	
	@RequestMapping("/tasks/history/{prn}")
	public Map<String, Object> getTaskHistory(@PathVariable("prn") String prn) {
		
		return myTasksService.getTaskHistory(prn, PATIENT_REG_PROCESS_DEF_KEY);

	}
}