package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.InvestigatorType;
import org.cancure.cpa.persistence.entity.ListOfValues;
import org.cancure.cpa.persistence.repository.InvestigatorTypeRepository;
import org.cancure.cpa.persistence.repository.ListOfValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonServiceImpl implements CommonService {

	@Autowired
	private InvestigatorTypeRepository investigatorTypeRepository;
	
	@Autowired
	private ListOfValuesRepository listOfValuesRepository;
	
	@Override
	public Iterable<InvestigatorType> getInvestigatorTypes() {
		return investigatorTypeRepository.findAll();
	}

	@Override
	public Iterable<ListOfValues> getListOfValues(String listName) {
		return listOfValuesRepository.findByName(listName);
	}

}
