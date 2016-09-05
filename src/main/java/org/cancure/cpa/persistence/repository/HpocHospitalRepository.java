package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.HpocHospital;
import org.springframework.data.repository.CrudRepository;

public interface HpocHospitalRepository extends CrudRepository<HpocHospital, Integer> {

    List<HpocHospital> findByHospitalId(Integer hospitalId );
    
    HpocHospital findByHpocId(Integer hpocId);
}
