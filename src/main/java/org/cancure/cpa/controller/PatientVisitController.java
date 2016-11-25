package org.cancure.cpa.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitForwardsMasterBean;
import org.cancure.cpa.controller.beans.PatientVisitHistoryBean;
import org.cancure.cpa.controller.beans.TopupStatusBean;
import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.service.PatientHospitalVisitWorkflowService;
import org.cancure.cpa.service.PatientService;
import org.cancure.cpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientVisitController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private PatientHospitalVisitWorkflowService service;

	@RequestMapping(value = "/patientvisit", method = RequestMethod.POST)
	public String startPatientHospitalVisit(@RequestBody PatientVisitBean patientHospitalVisitBean, OAuth2Authentication auth) throws IOException {
		if (auth != null) {
			List<String> roles = new ArrayList<>();
			for (GrantedAuthority a : auth.getAuthorities()){
				roles.add(a.getAuthority());
			}
			
			String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
			UserBean user = userService.getUserByLogin(login);
			Integer userId = user.getId();
			
			String pvId = service.startWorkflow(patientHospitalVisitBean, userId);
			return "{\"status\" : \"SUCCESS\",\"patientVisitId\" :" + pvId + "}";
		} else {
			throw new RuntimeException("Not logged in");
		}
		
	}

	@RequestMapping(value = "/patientvisit/topup", method = RequestMethod.POST)
	public String topUpApprovedAmount(@RequestBody TopupStatusBean topupBean) {
		String taskId = service.topUpApprovedAmount(topupBean);
		return "{\"status\" : \"SUCCESS\",\"taskId\" :" + taskId + "}";
	}
	
	@RequestMapping(value = "/patientvisit/partners", method = RequestMethod.POST)
	public String selectPartners(@RequestBody  PatientVisitForwardsMasterBean masterBean) throws Exception {
		String taskId = service.selectPartners(masterBean.getPatientVisitForwards());
		return "{\"status\" : \"SUCCESS\",\"taskId\" :" + taskId + "}";
	}
	
	@RequestMapping(value = "/patientvisit/patient/{pidn}", method = RequestMethod.GET)
	public PatientVisitHistoryBean searchPatientVisit(@PathVariable("pidn") String pidn){
		
		PatientVisitHistoryBean bean = service.selectPatient(pidn);
		if (bean == null){
			bean = new PatientVisitHistoryBean();
		}
		
		return bean;
	}
	
	@RequestMapping(value = "/patientvisit/{patientVisitId}", method = RequestMethod.GET)
	public PatientVisitHistoryBean searchPatientVisitById(@PathVariable("patientVisitId") String patientVisitId){
		
		PatientVisitHistoryBean bean = service.searchByPatientVisitId(patientVisitId);
		if (bean == null){
			bean = new PatientVisitHistoryBean();
		}
		
		return bean;
	}
}
