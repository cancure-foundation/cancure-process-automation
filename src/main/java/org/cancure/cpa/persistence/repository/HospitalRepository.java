package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.Hospital;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HospitalRepository extends CrudRepository<Hospital, Integer> {

	@Query("Select d from Hospital d where d.enabled = true")
	public Iterable<Hospital> findAllActive();
	
}