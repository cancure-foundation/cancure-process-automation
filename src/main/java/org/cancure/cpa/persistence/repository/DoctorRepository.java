package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.Doctor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

	@Query("Select d from Doctor d where d.enabled = true")
	public Iterable<Doctor> findAllActive();
	
}