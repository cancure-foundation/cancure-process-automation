package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Camp;
import org.cancure.cpa.persistence.repository.CampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CampServiceImpl implements CampService {

	@Autowired
	CampRepository campRepo;
	
	@Override
	public Camp saveCamp(Camp camp) {
		                      
		return campRepo.save(camp);
	}

	@Override
	public Iterable<Camp> listCamp() {
		// TODO Auto-generated method stub
		return campRepo.findAll();
	}

	@Override
	public Camp getCamp(Integer Camp_id) {
		// TODO Auto-generated method stub
		return campRepo.findOne(Camp_id);
	}

}
