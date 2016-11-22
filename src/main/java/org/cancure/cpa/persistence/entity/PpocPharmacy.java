package org.cancure.cpa.persistence.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PpocPharmacy {
    @Id
    @Column(name="ppoc_id")
    private Integer ppocId;
    
    @Column(name="pharmacy_id")
    private Integer pharmacyId;

    public Integer getPpocId() {
        return ppocId;
    }

    public void setPpocId(Integer ppocId) {
        this.ppocId = ppocId;
    }

    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Integer pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
    
}
