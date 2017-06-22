package org.cancure.cpa.service;

import java.util.Calendar;
import java.util.Date;

import org.cancure.cpa.persistence.entity.Camp;
import org.cancure.cpa.persistence.repository.CampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CampServiceImpl implements CampService {

    @Autowired
    CampRepository campRepo;

    @Override
    public Camp saveCamp(Camp camp) {

        return campRepo.save(camp);
    }

    @Override
    public Iterable<Camp> getCampsInAMonth(int month, int year) {
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.clear();
    	calendar.set(Calendar.MONTH, month-1);
    	calendar.set(Calendar.YEAR, year);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	Date startDate = calendar.getTime();
    	
    	calendar.add(Calendar.MONTH, 1);
    	Date endDate = calendar.getTime();
    	
    	return campRepo.getCampsInAMonth(startDate, endDate);
    }

    @Override
    public Camp getCamp(Integer Camp_id) {
        // TODO Auto-generated method stub
        return campRepo.findOne(Camp_id);
    }

    @Transactional
    @Override
    public void updatePatientCount(Integer campId) {
        
        Integer patientCount = (campRepo.findOne(campId).getPatientCount())+1;
        campRepo.updatePatientCount(patientCount, campId);
        
    }

}
