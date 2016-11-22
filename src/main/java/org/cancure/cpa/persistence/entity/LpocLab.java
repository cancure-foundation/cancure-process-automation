package org.cancure.cpa.persistence.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LpocLab {
    @Id
    @Column(name="lpoc_id")
    private Integer lpocId;
    
    @Column(name="lab_id")
    private Integer labId;

    public Integer getLpocId() {
        return lpocId;
    }

    public void setLpocId(Integer lpocId) {
        this.lpocId = lpocId;
    }

    public Integer getLabId() {
        return labId;
    }

    public void setLabId(Integer labId) {
        this.labId = labId;
    }
    
}
