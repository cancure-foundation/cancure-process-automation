package org.cancure.cpa.controller;

import org.cancure.cpa.persistence.entity.Doctor;

import org.cancure.cpa.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoctorController {

	@Autowired
	DoctorService doctorService;

	@RequestMapping(method = RequestMethod.POST, value = "/doctor/save", consumes = "application/json")
	public Doctor saveDoctor(@RequestBody Doctor doctor) {
		return doctorService.saveDoctor(doctor);
	}

	@RequestMapping("/doctor/list")
	public Iterable<Doctor> listDoctors() {
		return doctorService.listDoctors();
	}

	@RequestMapping("/doctor/{doctor_id}")
	public Doctor getDoctor(@PathVariable("doctor_id") Integer doctor_id) {
		return doctorService.getDoctor(doctor_id);
	}

	@RequestMapping("/doctor/delete/{doctor_id}")
	public Doctor updateDoctor(@PathVariable("doctor_id") Integer doctor_id) {
		return doctorService.updateDoctor(doctor_id);
	}
}
