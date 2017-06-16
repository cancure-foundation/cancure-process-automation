package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Camp;

public interface CampService {
    Camp saveCamp(Camp camp);

    Camp getCamp(Integer Camp_id);

	Iterable<Camp> getCampsInAMonth(int month, int year);
}
