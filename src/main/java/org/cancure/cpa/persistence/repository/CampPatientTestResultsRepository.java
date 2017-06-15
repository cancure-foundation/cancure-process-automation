package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.springframework.data.repository.CrudRepository;

public interface CampPatientTestResultsRepository extends CrudRepository<CampPatientTestResults, Integer>  {

    List<CampPatientTestResults> findByCampPatientId(Integer campPatientId);
}
