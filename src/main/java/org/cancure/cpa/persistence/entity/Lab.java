package org.cancure.cpa.persistence.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

@Table(name="lab")
@Entity
public class Lab {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="lab_id")
    private Integer labId;
    
    @NotEmpty
    private String name;
    
    @NotEmpty
    private String address;
    
    @NotEmpty
    private String contact;
    
    private String enabled;
    
    public Integer getLabId() {
        return labId;
    }

    public void setLabId(Integer labId) {
        this.labId = labId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

 


  
    
}
