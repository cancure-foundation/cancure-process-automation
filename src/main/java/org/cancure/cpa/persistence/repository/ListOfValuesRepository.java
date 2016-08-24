package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.ListOfValues;
import org.springframework.data.repository.CrudRepository;

public interface ListOfValuesRepository extends CrudRepository<ListOfValues, Integer> {
	
	Iterable<ListOfValues> findByName(String name);
	
}