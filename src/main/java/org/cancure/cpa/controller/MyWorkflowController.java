package org.cancure.cpa.controller;

import static org.cancure.cpa.common.Constants.PATIENT_REG_PROCESS_DEF_KEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.service.MyTasksService;
import org.cancure.cpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyWorkflowController {

	@Autowired
	private MyTasksService myTasksService;
	
	@Autowired
    private UserService userService;

	@RequestMapping("/tasks/my")
	public Map<String, List<Map<String, String>>> getPendingTasks(OAuth2Authentication auth) {
		if (auth != null) {
			List<String> roles = new ArrayList<>();
			for (GrantedAuthority a : auth.getAuthorities()){
				roles.add(a.getAuthority());
			}
			
			String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
			UserBean user = userService.getUserByLogin(login);
			Integer userId = user.getId();
			
			return myTasksService.getMyTasks(roles, userId);
		} else {
			throw new RuntimeException("Not logged in");
		}
	}
	
	@RequestMapping("/tasks/role/{role}")
	public Map<String, List<Map<String, String>>> getPendingTasks(@PathVariable("role") String roles){
		if (roles != null) {
			String[] roleArray = roles.split("__");
			List<String> rolesList = new ArrayList<>();
			for (String r : roleArray){
				if (!r.isEmpty()){
					rolesList.add(r);
				}
			}
			
			return myTasksService.getMyTasks(rolesList, null);
		}
		
		return new HashMap<>();
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