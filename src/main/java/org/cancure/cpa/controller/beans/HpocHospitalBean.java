package org.cancure.cpa.controller.beans;

import java.util.List;

public class HpocHospitalBean {

    private Integer hospitalId;
    private Integer hpocId ;
    private boolean status;
    
    public Integer getHospitalId() {
        return hospitalId;
    }
    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public Integer getHpocId() {
        return hpocId;
    }
    public void setHpocId(Integer hpocId) {
        this.hpocId = hpocId;
    }
    
}
