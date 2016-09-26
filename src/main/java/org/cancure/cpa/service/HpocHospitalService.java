package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.HpocHospital;

public interface HpocHospitalService {

    HpocHospital saveHpocHospital(HpocHospital hpocHospital);
    
    HpocHospital getHospitalFromHpoc(Integer hpocId);

	List<UserBean> getHpocUsersFromHospital(Integer hospitalId);

    void deleteHpocHospital(Integer hospitalId);
}
