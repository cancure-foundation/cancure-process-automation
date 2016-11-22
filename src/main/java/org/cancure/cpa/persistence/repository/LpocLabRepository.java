package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.LpocLab;
import org.springframework.data.repository.CrudRepository;

public interface LpocLabRepository extends CrudRepository<LpocLab, Integer> {

    List<LpocLab> findByLabId(Integer labId );
    
    LpocLab findByLpocId(Integer lpocId);
}
