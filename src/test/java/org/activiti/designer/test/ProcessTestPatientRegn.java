package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;

import javax.sql.DataSource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.cancure.cpa.Application;
import org.cancure.cpa.service.PatientRegistrationService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
@IntegrationTest
public class ProcessTestPatientRegn {

	private static final String doctorId = "200";
	private static String patientId = "100";

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private DataSource datasource;

	@Autowired
	private PatientRegistrationService patientRegistrationService;

	@Before
	public void startInst() throws SQLException {
		Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("patientName", "John Doe" + patientId);
        variables.put("email", "john.doe@activiti.com");
        variables.put("phoneNumber", "123456789");
        variables.put("prn", patientId);
		patientRegistrationService
				.startPatientRegnProcess(variables, patientId);
		
		System.out.println("Database: " + datasource.getConnection().getMetaData().getDatabaseProductName());
		System.out.println("Database: " + datasource.getConnection());
	}

	@Test
	public void ruleUsageExample() throws InterruptedException {

		Map<String, Object> activitiVars = new HashMap<String, Object>();
		patientRegistrationService.movePatientRegn(patientId, null);
		printTasks();
		activitiVars.put("bgCheck", "PASS");
		patientRegistrationService.movePatientRegn(patientId, activitiVars);
		printTasks();
		patientRegistrationService.mbApprove(patientId, "200");
		printTasks();
		patientRegistrationService.mbApprove(patientId, "201");
		printTasks();
		activitiVars.put("secApproval", "Approved");
		patientRegistrationService.movePatientRegn(patientId, activitiVars);
		printTasks();
		patientRegistrationService.ecApprove(patientId, "401");			
		printTasks();
		patientRegistrationService.ecApprove(patientId, "402");		
		patientRegistrationService.ecApprove(patientId, "403");		
		activitiVars.put("ecApproval", "Approved");
		patientRegistrationService.movePatientRegn(patientId, activitiVars);
		patientRegistrationService.movePatientRegn(patientId, null);
	}
	
	@Test
	public void ruleUsageExample2() throws InterruptedException {

		Map<String, Object> activitiVars = new HashMap<String, Object>();
		patientRegistrationService.movePatientRegn(patientId, null);
		activitiVars.put("bgCheck", "PASS");
		patientRegistrationService.movePatientRegn(patientId, activitiVars);
		patientRegistrationService.mbApprove(patientId, "200");
		patientRegistrationService.mbApprove(patientId, "201");
		activitiVars.put("secApproval", "prelimExamClarificationReqd");
		patientRegistrationService.movePatientRegn(patientId, activitiVars);
		patientRegistrationService.mbApprove(patientId, "200");
		patientRegistrationService.mbApprove(patientId, "201");
		activitiVars.put("secApproval", "prelimExamClarificationReqd");
		patientRegistrationService.movePatientRegn(patientId, activitiVars);
		patientRegistrationService.mbApprove(patientId, "200");
		patientRegistrationService.mbApprove(patientId, "201");		
		activitiVars.put("secApproval", "Recommend");
		patientRegistrationService.ecApprove(patientId, "401");			
		patientRegistrationService.ecApprove(patientId, "402");		
		patientRegistrationService.ecApprove(patientId, "403");		
		activitiVars.put("ecApproval", "Approved");
		patientRegistrationService.movePatientRegn(patientId, activitiVars);
		patientRegistrationService.movePatientRegn(patientId, null);
	}

	public void printTasks() {
		List<Task> taskData = taskService.createTaskQuery().list();
		System.out.println("Tasks::::: ");
		for (Task task : taskData) {
			System.out.println("-----" + task.getProcessInstanceId() + " - "
					+ task.getExecutionId() + " - " + task.getId() + " - "
					+ task.getName() + " - " + task.getDescription());
		}

	}
}