package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.persistence.repository.CampPatientTestResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CampPatientTestResultsServiceImpl implements CampPatientTestResultsService{

	@Autowired
	CampPatientTestResultsRepository testresultsrepository;
	@Override
	public CampPatientTestResults saveTestResult(CampPatientTestResults testresult) {
		
		return testresultsrepository.save(testresult);
	}

	@Override
	public Iterable<CampPatientTestResults> listTestResults() {
		// TODO Auto-generated method stub
		return testresultsrepository.findAll();
	}


}
