package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.persistence.entity.PatientVisitForwards;
import org.cancure.cpa.persistence.repository.PatientVisitForwardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PharmacyDispatchServiceImpl {

	@Autowired
	private PatientVisitForwardsRepository forwardsRepository;
	
	public void searchPatient(String pidn) {
		
		List<PatientVisitForwards> forwards = forwardsRepository.findByPidn(pidn);
		
	}
	
}
