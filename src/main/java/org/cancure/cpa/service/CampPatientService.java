package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.CampPatient;

public interface CampPatientService {
	
 CampPatient savePatientcamp(CampPatient patientcamp);
 Iterable<CampPatient> listPatientCamp();


}
