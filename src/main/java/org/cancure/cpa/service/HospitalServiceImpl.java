package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Hospital;
import org.cancure.cpa.persistence.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hospitalService")
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	HospitalRepository hospitalRepo;

	@Override
	public Hospital saveHospital(Hospital hospital) {
		if (hospital.getHospitalId() == null) {
			hospital.setEnabled(true);
		}
		return hospitalRepo.save(hospital);
	}

	@Override
	public Iterable<Hospital> listHospitals() {
		return hospitalRepo.findAllActive();
	}

	@Override
	public Hospital getHospital(Integer id) {
		return hospitalRepo.findOne(id);
	}
}
