package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.InvestigatorType;
import org.cancure.cpa.persistence.entity.ListOfValues;
import org.cancure.cpa.persistence.entity.Settings;

public interface CommonService {
	Iterable<InvestigatorType> getInvestigatorTypes();
	
	Iterable<ListOfValues> getListOfValues(String listName);
	
	Iterable<Settings> findAllSettings();
	
	Settings saveSetting(Settings setting);
	
	Settings findSettingsById(Integer id);
}
