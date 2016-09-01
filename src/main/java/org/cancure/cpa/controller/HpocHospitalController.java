package org.cancure.cpa.controller;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.HpocHospital;
import org.cancure.cpa.service.HpocHospitalService;
import org.cancure.cpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HpocHospitalController {

    @Autowired
    private HpocHospitalService hpocHospitalService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("/link/hpoc/hospital/{hpoc_id}/{hospital_id}")
    public HpocHospital linkHpocHospital(@PathVariable("hpoc_id") Integer hpocId,@PathVariable("hospital_id") Integer hospitalId){
        HpocHospital hpocHospital=new HpocHospital();
        hpocHospital.setHpocId(hpocId);
        hpocHospital.setHospitalId(hospitalId);
        return hpocHospitalService.saveHpocHospital(hpocHospital);
        
    }
    
    @RequestMapping("/list/hpoc/hospital/{hospital_id}")
    public UserBean listHpocHospital(@PathVariable("hospital_id") Integer hospitalId){
        HpocHospital hpocHospital= hpocHospitalService.getHpocFromHospital(hospitalId);
        UserBean user=new UserBean();
        user=userService.getUser(hpocHospital.getHpocId());
        return user;
    }
    
}
