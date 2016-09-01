package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.HpocHospital;
import org.cancure.cpa.persistence.repository.HpocHospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hpocHospitalService")
public class HpocHospitalServiceImpl implements HpocHospitalService {

    @Autowired
    HpocHospitalRepository hpocHospitalRepo;
    
    @Override
    public HpocHospital saveHpocHospital(HpocHospital hpocHospital) {
       
        return hpocHospitalRepo.save(hpocHospital);
    }

    @Override
    public HpocHospital getHpocFromHospital(Integer hospitalId) {

        return hpocHospitalRepo.findByHospitalId(hospitalId);
    }

    @Override
    public HpocHospital getHospitalFromHpoc(Integer hpocId) {
       
        return hpocHospitalRepo.findByHpocId(hpocId);
    }

}
