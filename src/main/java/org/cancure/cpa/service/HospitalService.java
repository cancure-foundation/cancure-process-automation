package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Hospital;

public interface HospitalService {

	Hospital saveHospital(Hospital doctor);

	Iterable<Hospital> listHospitals();

	Hospital getHospital(Integer doctor_id);

}