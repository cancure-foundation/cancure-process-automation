package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.CampPatient;
import org.cancure.cpa.persistence.repository.CampPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CampPatientServiceImpl implements CampPatientService {
	@Autowired
	CampPatientRepository PatientCampRepo;
	@Override
	public CampPatient savePatientcamp(CampPatient patientcamp) {
		
	return PatientCampRepo.save(patientcamp);
		
	}

	@Override
	public Iterable<CampPatient> listPatientCamp() {
		
		return PatientCampRepo.findAll();
	}

	


}
