package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.CampPatient;
import org.springframework.data.repository.CrudRepository;

public interface CampPatientRepository extends CrudRepository<CampPatient, Integer> {

    List<CampPatient> findByCampId(Integer campId); 
}
