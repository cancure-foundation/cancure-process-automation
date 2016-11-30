package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.Pharmacy;
import org.cancure.cpa.persistence.entity.PpocPharmacy;
import org.cancure.cpa.persistence.repository.PpocPharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ppocPharmacyService")
public class PpocPharmacyServiceImpl implements PpocPharmacyService {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PpocPharmacyRepository ppocPharmacyRepo;
    
    @Autowired
    private PharmacyService pharmacyService;
    @Override
    public PpocPharmacy savePpocPharmacy(PpocPharmacy ppocPharmacy) {
        
        return ppocPharmacyRepo.save(ppocPharmacy);    
    }

    @Override
    public PpocPharmacy getPharmacyFromPpoc(Integer ppocId) {
        
        return ppocPharmacyRepo.findByPpocId(ppocId);
    }

    @Override
    public List<UserBean> getPpocUsersFromPharmacy(Integer pharmacyId) {

        List<UserBean> userBeans = new ArrayList<>();
        List<PpocPharmacy> ppocPharmacy = ppocPharmacyRepo.findByPharmacyId(pharmacyId);
        if (ppocPharmacy != null && !ppocPharmacy.isEmpty()) {
            for (PpocPharmacy hh : ppocPharmacy) {
                UserBean user = userService.getUser(hh.getPpocId());
                userBeans.add(user);
            }
        }
        return userBeans;
    }

    @Override
    public Map<String, Object> listAllPpocPharmacy() {
        Map<Integer, List<UserBean>> parentMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Iterable<Pharmacy> list = pharmacyService.listPharmacies();
        for (Pharmacy pharmacy : list) {
            List<UserBean> userBeanList = new ArrayList<>();
            userBeanList = getPpocUsersFromPharmacy(pharmacy.getPharmacyId());
            parentMap.put(pharmacy.getPharmacyId(), userBeanList);
        }
        map.put("Pharmacies", list);
        map.put("PharmacyMappings", parentMap);
        return map;
    }



}