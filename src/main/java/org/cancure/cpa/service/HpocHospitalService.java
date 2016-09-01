package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.persistence.entity.HpocHospital;

public interface HpocHospitalService {

    HpocHospital saveHpocHospital(HpocHospital hpocHospital);
    
    HpocHospital getHpocFromHospital(Integer hospitalId);
    
    HpocHospital getHospitalFromHpoc(Integer hpocId);
}
