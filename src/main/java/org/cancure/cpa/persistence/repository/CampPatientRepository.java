package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.CampPatient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CampPatientRepository extends CrudRepository<CampPatient, Long> {

	List<CampPatient> findByCampId(Integer campId);
    
    @Query("select cp, tr from CampPatient cp left join cp.campPatientTestResults tr where cp.campId = ?1")
    List<Object[]> findCampPatients(Integer campId);
}
