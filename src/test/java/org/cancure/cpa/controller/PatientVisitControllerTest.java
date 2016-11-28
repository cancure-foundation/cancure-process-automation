package org.cancure.cpa.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.cancure.cpa.Application;
import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PatientVisitControllerTest {

	@Autowired
	private TaskService taskService;

	@Autowired
	PatientVisitController controller;
	
	@Test
	public void test() throws IOException {
		PatientVisitBean bean = new PatientVisitBean();
		bean.setPidn(1);
		bean.setAccountTypeId("5");
		bean.setAccountHolderId("1");
		bean.setTopupNeeded("TRUE");
		String res = controller.startPatientHospitalVisit(bean, getAuth("hpoc"));
		System.out.println(res);
	}
	
	@Test
	public void testMyTasks() throws Exception {
		
		List<Task> tasks = taskService.createTaskQuery()
				//.processDefinitionKey(Constants.PATIENT_HOSPITAL_VISIT_DEF_KEY)
				.includeProcessVariables().orderByTaskCreateTime().asc()
				.taskCandidateGroupIn(Arrays.asList("ROLE_SECRETARY", "ROLE_HOSPITAL_POC")).list();
		
		if (tasks != null){
			for (Task t : tasks){
				System.out.println(t);
			}
		}
		
		
	}

	private OAuth2Authentication getAuth(String login) {
    	Authentication authentication = new UsernamePasswordAuthenticationToken("cancure", "cancure");
    	OAuth2Authentication userAuthentication = new OAuth2Authentication(null, authentication);
        Map<String, String> map = new HashMap<>();
        map.put("username", login);
        userAuthentication.setDetails(map);
        OAuth2Authentication auth = new OAuth2Authentication(null, userAuthentication);
        return auth;
    }
}
