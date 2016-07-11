package org.cancure.cpa.controller;

import org.cancure.cpa.persistence.entity.Patient;
import org.cancure.cpa.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@RequestMapping("/patient/{id}")
	public Patient getPatient(@PathVariable("id") Integer id){
		return patientService.get(id);
	}
	
	@RequestMapping(value="/patient/save", method=RequestMethod.POST)
	public String save(Patient patient) {
		return patientService.save(patient);
	}
}
