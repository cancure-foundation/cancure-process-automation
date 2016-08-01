package org.cancure.cpa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.*;
import org.cancure.cpa.service.PatientRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivitiController {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private PatientRegistrationService patientRegistrationService;

	// @RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	/*@RequestMapping("/start100/{patientId}")
	public String hello(@PathVariable String patientId) {

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("applicantName", "John Doe");
		variables.put("email", "john.doe@activiti.com");
		variables.put("phoneNumber", "123456789");
		variables.put("prn", patientId);
		ProcessInstance procInst = runtimeService.startProcessInstanceByKey(
				"activitiReview", patientId, variables);

		List<ProcessInstance> procInsts = runtimeService
				.createProcessInstanceQuery().list();
		System.out.println("Deployment Idssss ---");
		for (ProcessInstance proc : procInsts) {
			System.out.println("Deployment Id ---" + proc.getDeploymentId());
		}

		return "Started Proc instance! :: " + procInst.getId();
	}
	
	@RequestMapping("/move100/{patientId}")
	public String move100(@PathVariable String patientId) {
		patientRegistrationService.movePatientRegn(patientId, null);
		return "Done";
	}*/

		

	@RequestMapping("/startPatientRegn/{patientId}")
	public String startPatientRegn(@PathVariable String patientId) {

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("patientName", "John Doe");
		variables.put("email", "john.doe@activiti.com");
		variables.put("phoneNumber", "123456789");
		variables.put("prn", patientId);

		return patientRegistrationService.startPatientRegnProcess(variables,
				patientId);
	}

	@RequestMapping("/movePatientRegn/{patientId}")
	public String movePatientRegn(@PathVariable String patientId) {

		return patientRegistrationService.movePatientRegn(patientId, null);
	}

	@RequestMapping("/backgroundCheck/{patientId}/{status}")
	public String backgroundCheck(@PathVariable String patientId,
			@PathVariable String status) {

		Map<String, Object> actVars = new HashMap<String, Object>();
		actVars.put("bgCheck", status);
		return patientRegistrationService.movePatientRegn(patientId, actVars);
	}
	
	@RequestMapping("/mbApprove/{patientId}/{doctorId}")
	public String mbApprove(@PathVariable String patientId,
			@PathVariable String doctorId) {

		return patientRegistrationService.mbApprove(patientId, doctorId);
	}

	@RequestMapping("/secApproval/{patientId}/{status}")
	public String secApproval(@PathVariable String patientId,
			@PathVariable String status) {

		Map<String, Object> actVars = new HashMap<String, Object>();
		actVars.put("secApproval", status);
		return patientRegistrationService.movePatientRegn(patientId, actVars);
	}

	@RequestMapping("/ecApprovalInd/{patientId}/{ecId}")
	public String ecApprovalInd(@PathVariable String patientId,
			@PathVariable String ecId) {

		return patientRegistrationService.ecApprove(patientId, ecId);
	}

	@RequestMapping("/ecApproval/{patientId}/{status}")
	public String ecApproval(@PathVariable String patientId,
			@PathVariable String status) {

		Map<String, Object> actVars = new HashMap<String, Object>();
		actVars.put("ecApproval", status);
		return patientRegistrationService.movePatientRegn(patientId, actVars);
	}
}
