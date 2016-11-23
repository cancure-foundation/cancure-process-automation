package org.cancure.cpa.persistence.repository;


import org.cancure.cpa.persistence.entity.Pharmacy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PharmacyRepository extends CrudRepository<Pharmacy, Integer>{
	

	@Query("Select d from Pharmacy d where d.enabled = true")
	public Iterable<Pharmacy> findAllActive();
	

}
