package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HpocHospital {

    @Id
    @Column(name="hpoc_id")
    private Integer hpocId;
    
    @Column(name="hospital_id")
    private Integer hospitalId;

    public Integer getHpocId() {
        return hpocId;
    }

    public void setHpocId(Integer hpocId) {
        this.hpocId = hpocId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }
    
    
}
