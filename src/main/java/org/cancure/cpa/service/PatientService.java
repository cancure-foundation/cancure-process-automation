package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Patient;

public interface PatientService {

	String save(Patient p);
	
	Patient get(Integer id);
	
}
