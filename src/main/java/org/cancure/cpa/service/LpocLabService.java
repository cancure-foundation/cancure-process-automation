package org.cancure.cpa.service;

import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.LpocLab;

public interface LpocLabService {

    LpocLab saveLpocLab(LpocLab lpocLab);
    
    LpocLab getLabFromLpoc(Integer lpocId);

    List<UserBean> getLpocUsersFromLab(Integer labId);
    
    public Map<String, Object> listAllLpocLab();
}
