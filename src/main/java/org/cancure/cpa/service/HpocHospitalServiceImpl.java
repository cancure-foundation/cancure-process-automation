package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.List;

import org.cancure.cpa.controller.beans.HpocHospitalBean;
import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.HpocHospital;
import org.cancure.cpa.persistence.repository.HpocHospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hpocHospitalService")
public class HpocHospitalServiceImpl implements HpocHospitalService {

	@Autowired
	private UserService userService;
	
    @Autowired
    private HpocHospitalRepository hpocHospitalRepo;
    
    @Override
    public HpocHospital saveHpocHospital(HpocHospital hpocHospital) {
       
        return hpocHospitalRepo.save(hpocHospital);
    }
    
    @Override
    public List<UserBean> getHpocUsersFromHospital(Integer hospitalId) {
    	List<UserBean> userBeans = new ArrayList<>();
    	List<HpocHospital> hpocHospital = hpocHospitalRepo.findByHospitalId(hospitalId);
    	if (hpocHospital != null && !hpocHospital.isEmpty()) {
			for (HpocHospital hh : hpocHospital) {
				UserBean user = userService.getUser(hh.getHpocId());
				userBeans.add(user);
			}
		}
    	return userBeans;
    }

    @Override
    public HpocHospital getHospitalFromHpoc(Integer hpocId) {
       
        return hpocHospitalRepo.findByHpocId(hpocId);
    }

    @Override
    public void deleteHpocHospital(Integer hospitalId) {
          
        List<UserBean> userBeans=getHpocUsersFromHospital(hospitalId);
        for(UserBean user:userBeans){
            hpocHospitalRepo.delete(user.getId());
        }
        
    }

    @Override
    public HpocHospital hpocHospitalMapping(HpocHospitalBean hpocHospitalBean) {
        HpocHospital hpocHospital = new HpocHospital();
        List<Integer> hpocIdList = hpocHospitalBean.getHpocIdList();
        int count=0;
        if(hpocIdList.size() ==0){
            deleteHpocHospital(hpocHospitalBean.getHospitalId());
            return null;
        }else{
        for(Integer hpocId:hpocIdList){
            if (getHospitalFromHpoc(hpocId) == null || getHospitalFromHpoc(hpocId).getHospitalId()==hpocHospitalBean.getHospitalId()) {

                if(count==0 && hpocHospitalBean.isStatus()){
                    deleteHpocHospital(hpocHospitalBean.getHospitalId());
                    count++;
                }
                
                hpocHospital.setHospitalId(hpocHospitalBean.getHospitalId());
                hpocHospital.setHpocId(hpocId);
                saveHpocHospital(hpocHospital);
                
            } else {
               
                throw new RuntimeException("HPOC already mapped");
                    

            }
        }
        return hpocHospital;
        }
    }

}
