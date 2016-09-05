package org.cancure.cpa.controller.beans;

import java.util.List;

public class HpocHospitalBean {

    private Integer hospitalId;
    private List<Integer> hpocIdList ;
    public Integer getHospitalId() {
        return hospitalId;
    }
    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }
    public List<Integer> getHpocIdList() {
        return hpocIdList;
    }
    public void setHpocIdList(List<Integer> hpocIdList) {
        this.hpocIdList = hpocIdList;
    }
    
}
