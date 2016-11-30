package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.Lab;
import org.cancure.cpa.persistence.entity.LpocLab;
import org.cancure.cpa.persistence.repository.LpocLabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("lpocLabService")
public class LpocLabServiceImpl implements LpocLabService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private LpocLabRepository lpocLabRepo;
    
    @Autowired
    private LabService labService;
    
    @Override
    public LpocLab saveLpocLab(LpocLab lpocLab) {
        return lpocLabRepo.save(lpocLab);   
    }

    @Override
    public LpocLab getLabFromLpoc(Integer lpocId) {
        return lpocLabRepo.findByLpocId(lpocId);
    }

    @Override
    public List<UserBean> getLpocUsersFromLab(Integer labId) {
        
        List<UserBean> userBeans = new ArrayList<>();
        List<LpocLab> lpocLab = lpocLabRepo.findByLabId(labId);
        if (lpocLab != null && !lpocLab.isEmpty()) {
            for (LpocLab hh : lpocLab) {
                UserBean user = userService.getUser(hh.getLpocId());
                userBeans.add(user);
            }
        }
        return userBeans;
    }

    @Override
    public Map<String, Object> listAllLpocLab() {
 
        Map<Integer, List<UserBean>> parentMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Iterable<Lab> list = labService.listLabs();
        for (Lab lab : list) {
            List<UserBean> userBeanList = new ArrayList<>();
            userBeanList = getLpocUsersFromLab(lab.getLabId());
            parentMap.put(lab.getLabId(), userBeanList);
        }
        map.put("Labs", list);
        map.put("LabMappings", parentMap);
        return map;
    }

}