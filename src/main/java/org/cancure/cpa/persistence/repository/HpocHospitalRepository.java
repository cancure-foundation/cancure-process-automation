package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.HpocHospital;
import org.springframework.data.repository.CrudRepository;

public interface HpocHospitalRepository extends CrudRepository<HpocHospital, Integer> {

    HpocHospital findByHospitalId(Integer hospitalId );
    
    HpocHospital findByHpocId(Integer hpocId);
}
