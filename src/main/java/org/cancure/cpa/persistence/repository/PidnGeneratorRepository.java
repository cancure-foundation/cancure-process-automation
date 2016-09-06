package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.PidnGenerator;
import org.springframework.data.repository.CrudRepository;

public interface PidnGeneratorRepository extends CrudRepository<PidnGenerator, Integer> {

}