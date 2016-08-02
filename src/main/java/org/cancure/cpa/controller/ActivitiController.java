package org.cancure.cpa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.cancure.cpa.service.PatientRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivitiController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private PatientRegistrationService patientRegistrationService;

	@RequestMapping(value = "/listTasks/{roleId}", headers = "Accept=application/json")
	public @ResponseBody Map<String, String> listTasks(
			@PathVariable String roleId) {

		return patientRegistrationService.getTaskListWithPatientIds(roleId);

	}

	@RequestMapping("/startPatientRegn/{patientId}")
	public String startPatientRegn(@PathVariable String patientId) {

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("patientName", "John Doe");
		variables.put("email", "john.doe@activiti.com");
		variables.put("phoneNumber", "123456789");
		variables.put("patientId", patientId);

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
	public @ResponseBody ArrayList<String> mbApprove(
			@PathVariable String patientId, @PathVariable String doctorId) {

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
	public @ResponseBody ArrayList<String> ecApprovalInd(
			@PathVariable String patientId, @PathVariable String ecId) {

		return patientRegistrationService.ecApprove(patientId, ecId);
	}

	@RequestMapping("/ecRejectInd/{patientId}/{ecId}")
	public @ResponseBody ArrayList<String> ecRejectInd(
			@PathVariable String patientId, @PathVariable String ecId) {

		return patientRegistrationService.ecReject(patientId, ecId);
	}

	// @RequestMapping("/ecApproval/{patientId}/{status}")
	// public String ecApproval(@PathVariable String patientId,
	// @PathVariable String status) {
	//
	// Map<String, Object> actVars = new HashMap<String, Object>();
	// actVars.put("ecApproval", status);
	// return patientRegistrationService.movePatientRegn(patientId, actVars);
	// }
}
