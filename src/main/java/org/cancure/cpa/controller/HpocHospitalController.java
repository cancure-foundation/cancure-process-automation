package org.cancure.cpa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.HpocHospitalBean;
import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.Hospital;
import org.cancure.cpa.persistence.entity.HpocHospital;
import org.cancure.cpa.service.HospitalService;
import org.cancure.cpa.service.HpocHospitalService;
import org.cancure.cpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HpocHospitalController {

    @Autowired
    private HpocHospitalService hpocHospitalService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/link/hpoc/hospital", method = RequestMethod.POST)
    public HpocHospital linkHpocHospital(@RequestBody HpocHospitalBean hpocHospitalBean) {

        return hpocHospitalService.hpocHospitalMapping(hpocHospitalBean);
    }

    @RequestMapping("/list/hpoc/hospital/{hospital_id}")
    public List<UserBean> listHpocHospital(@PathVariable("hospital_id") Integer hospitalId) {

        return hpocHospitalService.getHpocUsersFromHospital(hospitalId);
    }

    @RequestMapping("/list/hospital/hpoc/{hpoc_id}")
    public HpocHospital listHospitalHpoc(@PathVariable("hpoc_id") Integer hpocId) {

        return hpocHospitalService.getHospitalFromHpoc(hpocId);
    }
    
    @RequestMapping("/listAll/hpoc/hospital")
    public Map<String, Object> listAllHpocHospital() {
        
        return hpocHospitalService.listAllHpocHospital();
    }

}
