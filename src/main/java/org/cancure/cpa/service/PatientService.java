package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Patient;

public interface PatientService {

	public String save(Patient p);
	
	public Patient get(Integer id);
	
}
