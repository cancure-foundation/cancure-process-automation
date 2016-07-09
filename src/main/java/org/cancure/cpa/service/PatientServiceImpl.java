package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Patient;
import org.cancure.cpa.persistence.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("patientService")
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepo;
	
	@Transactional
	@Override
	public String save(Patient patient) {
		patientRepo.save(patient);
		return "Success";
	}

	@Override
	public Patient get(Integer id) {
		return patientRepo.findOne(id);
	}

}
