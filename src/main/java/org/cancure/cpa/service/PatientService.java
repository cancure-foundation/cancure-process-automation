package org.cancure.cpa.service;

import java.io.IOException;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.persistence.entity.Patient;

public interface PatientService {

    PatientBean save(PatientBean p) throws IOException;
	
	PatientBean get(Integer id);

	Iterable<Patient> searchByName(String name);
	
	
	
}
