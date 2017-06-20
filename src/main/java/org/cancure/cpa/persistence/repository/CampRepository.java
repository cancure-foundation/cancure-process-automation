package org.cancure.cpa.persistence.repository;

import java.util.Date;
import java.util.List;

import org.cancure.cpa.persistence.entity.Camp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CampRepository extends CrudRepository<Camp, Integer> {

	@Query("from Camp c where campDate >= ?1 and campDate < ?2")
	public List<Camp> getCampsInAMonth(Date startDate, Date endDate);
	
}
