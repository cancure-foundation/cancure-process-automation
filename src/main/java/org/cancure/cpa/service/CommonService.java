package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.InvestigatorType;
import org.cancure.cpa.persistence.entity.ListOfValues;

public interface CommonService {
	Iterable<InvestigatorType> getInvestigatorTypes();
	
	Iterable<ListOfValues> getListOfValues(String listName);
}
