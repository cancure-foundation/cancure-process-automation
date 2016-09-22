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
	
	@Autowired
	private HospitalService hospitalService;

    @RequestMapping(value = "/link/hpoc/hospital", method = RequestMethod.POST)
    public HpocHospital linkHpocHospital(@RequestBody HpocHospitalBean hpocHospitalBean) {
        HpocHospital hpocHospital = new HpocHospital();
        List<Integer> hpocIdList = hpocHospitalBean.getHpocIdList();
        for(Integer hpocId:hpocIdList){
            if (hpocHospitalService.getHospitalFromHpoc(hpocId) != null) {

                    throw new RuntimeException("HPOC already mapped");
                
            } else {
               
                    hpocHospital.setHospitalId(hpocHospitalBean.getHospitalId());
                    hpocHospital.setHpocId(hpocId);
                    hpocHospitalService.saveHpocHospital(hpocHospital);

            }
        }
        return hpocHospital;
    }

	@RequestMapping("/list/hpoc/hospital/{hospital_id}")
	public List<UserBean> listHpocHospital(@PathVariable("hospital_id") Integer hospitalId) {
		return hpocHospitalService.getHpocUsersFromHospital(hospitalId);
	}
	
	@RequestMapping("/listAll/hpoc/hospital")
	public Map<String,Object> listAllHpocHospital() {
	    Map<Integer,List<UserBean>> parentMap=new HashMap<>();
	    Map<String,Object> map=new HashMap<>();
	    Iterable<Hospital> list=hospitalService.listHospitals();
	    for (Hospital hospital : list){
	        List<UserBean> userBeanList=new ArrayList<>();
	        userBeanList=hpocHospitalService.getHpocUsersFromHospital(hospital.getHospitalId());
	        parentMap.put(hospital.getHospitalId(),userBeanList);
	    }
	    map.put("Hospitals", list);
	    map.put("HospitalMappings",parentMap);
	    return map;
	}

}
