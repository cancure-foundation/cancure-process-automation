package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.PpocPharmacy;
import org.springframework.data.repository.CrudRepository;

public interface PpocPharmacyRepository extends CrudRepository<PpocPharmacy, Integer> {

    List<PpocPharmacy> findByPharmacyId(Integer pharmacyId );
    
    PpocPharmacy findByPpocId(Integer ppocId);
}