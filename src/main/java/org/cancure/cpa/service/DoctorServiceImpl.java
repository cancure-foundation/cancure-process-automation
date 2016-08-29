package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.persistence.entity.Doctor;
import org.cancure.cpa.persistence.repository.DoctorRepository;
import org.cancure.cpa.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("doctorService")
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepository doctorRepo;
	@Autowired
	RoleRepository roleRepo;

	@Override

	public Doctor saveDoctor(Doctor doctor) {
		if (doctor.getDoctorId() == null){
			doctor.setEnabled(true);
		}
		return doctorRepo.save(doctor);
	}

	@Override
	public Doctor getDoctor(Integer doctor_id) {
		return doctorRepo.findOne(doctor_id);
	}

	@Override
	public Iterable<Doctor> listDoctors() {
		return doctorRepo.findAllActive();
	}

	@Override
	public Doctor updateDoctor(Integer doctor_id) {
		Doctor doc = doctorRepo.findOne(doctor_id);
		doc.setEnabled(false);
		return doctorRepo.save(doc);
	}

    @Override
    public List<Doctor> listHospitalDoctors(Integer hospitalId) {
        return doctorRepo.findByHospitalId(hospitalId);
    }
}
