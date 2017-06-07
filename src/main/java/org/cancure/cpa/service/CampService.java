package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Camp;

public interface CampService {
    Camp saveCamp(Camp camp);

    Iterable<Camp> listCamp();

    Camp getCamp(Integer Camp_id);
}
