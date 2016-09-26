package org.cancure.cpa.controller.beans;

import java.util.List;

public class HpocHospitalBean {

    private Integer hospitalId;
    private List<Integer> hpocIdList ;
    private boolean status;
    
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
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
