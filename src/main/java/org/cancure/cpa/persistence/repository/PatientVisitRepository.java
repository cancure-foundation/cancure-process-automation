package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.PatientVisit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PatientVisitRepository extends CrudRepository<PatientVisit, Integer>  {
	
	@Query("from PatientVisit p where pidn = ?1 and status = 'open' ")
	public List<PatientVisit> findOpenWorkflowsOfPatient(Integer pidn);

}
