package org.cancure.cpa.service;

import java.io.IOException;

import org.cancure.cpa.controller.beans.PatientBean;

public interface PatientService {

    PatientBean save(PatientBean p) throws IOException;
	
	PatientBean get(Integer id);
	
	
	
}
