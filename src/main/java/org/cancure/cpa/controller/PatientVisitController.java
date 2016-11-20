package org.cancure.cpa.controller;

import java.io.IOException;

import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitForwardsMasterBean;
import org.cancure.cpa.controller.beans.PatientVisitHistoryBean;
import org.cancure.cpa.controller.beans.TopupStatusBean;
import org.cancure.cpa.service.PatientHospitalVisitWorkflowService;
import org.cancure.cpa.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientVisitController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PatientHospitalVisitWorkflowService service;

	@RequestMapping(value = "/patientvisit", method = RequestMethod.POST)
	public String startPatientHospitalVisit(PatientVisitBean patientHospitalVisitBean) throws IOException {
		String pvId = service.startWorkflow(patientHospitalVisitBean);
		return "{\"status\" : \"SUCCESS\",\"patientVisitId\" :" + pvId + "}";
	}

	@RequestMapping(value = "/patientvisit/topup", method = RequestMethod.POST)
	public String topUpApprovedAmount(TopupStatusBean topupBean) {
		String taskId = service.topUpApprovedAmount(topupBean);
		return "{\"status\" : \"SUCCESS\",\"taskId\" :" + taskId + "}";
	}
	
	@RequestMapping(value = "/patientvisit/partners", method = RequestMethod.POST)
	public String selectPartners(PatientVisitForwardsMasterBean masterBean) throws Exception {
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
}
