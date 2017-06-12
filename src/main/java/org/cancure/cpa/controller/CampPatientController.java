package org.cancure.cpa.controller;

import org.cancure.cpa.persistence.entity.CampPatient;
import org.cancure.cpa.service.CampPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CampPatientController {

	@Autowired
	CampPatientService patientcampservice;
	
	@RequestMapping(method=RequestMethod.POST,value="camppatient/save")
	public CampPatient saveCampPatient(@RequestBody CampPatient patientcamp)
	{
		return patientcampservice.savePatientcamp(patientcamp);
	}
	@RequestMapping(method=RequestMethod.POST,value="camppatient/get")
	
	public Iterable<CampPatient> listCampPatient() {
		return patientcampservice.listPatientCamp();
	}
	

}
