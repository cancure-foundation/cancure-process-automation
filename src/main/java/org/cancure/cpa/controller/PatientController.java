package org.cancure.cpa.controller;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@RequestMapping("/patient/{id}")
	public PatientBean getPatient(@PathVariable("id") Integer id){
		return patientService.get(id);
	}
	


}
