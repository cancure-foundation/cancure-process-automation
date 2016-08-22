package org.cancure.cpa.controller;

import org.cancure.cpa.persistence.entity.Hospital;
import org.cancure.cpa.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {

	@Autowired
	HospitalService hospitalService;

	@RequestMapping(method = RequestMethod.POST, value = "/hospital/save")
	public Hospital saveHospital(@RequestBody Hospital hospital) {
		return hospitalService.saveHospital(hospital);
	}

	@RequestMapping("/hospital/list")
	public Iterable<Hospital> listHospitals() {
		return hospitalService.listHospitals();
	}

	@RequestMapping("/hospital/{hospitalId}")
	public Hospital getHospital(@PathVariable("hospitalId") Integer hospitalId) {
		return hospitalService.getHospital(hospitalId);
	}
}
