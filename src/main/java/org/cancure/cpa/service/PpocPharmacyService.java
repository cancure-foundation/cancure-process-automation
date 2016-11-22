package org.cancure.cpa.service;

import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.PpocPharmacy;

public interface PpocPharmacyService {
    PpocPharmacy savePpocPharmacy(PpocPharmacy ppocPharmacy);
    
    PpocPharmacy getPharmacyFromPpoc(Integer ppocId);

    List<UserBean> getPpocUsersFromPharmacy(Integer pharmacyId);
    
    public Map<String, Object> listAllPpocPharmacy();
}
