package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.CampPatientTestResults;

public interface CampPatientTestResultsService {
	
	CampPatientTestResults saveTestResult(CampPatientTestResults testresult);

	Iterable<CampPatientTestResults> listTestResults();

	
}
