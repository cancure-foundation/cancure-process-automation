package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.persistence.entity.Doctor;



public interface DoctorService {

	Doctor saveDoctor(Doctor doctor);
	Iterable<Doctor> listDoctors();
	Doctor getDoctor(Integer doctor_id);
	Doctor updateDoctor(Integer doctor_id);
    List<Doctor> listHospitalDoctors(Integer hospitalId);
	
	
}