package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.InvestigatorType;

public interface CommonService {
	Iterable<InvestigatorType> getInvestigatorTypes();	
}
