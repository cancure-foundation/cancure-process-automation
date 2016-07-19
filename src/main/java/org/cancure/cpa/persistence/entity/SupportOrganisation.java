package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "support_organisations")
public class SupportOrganisation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="org_id")
    private Integer orgId;
    
    @NotEmpty
    private String name;
    
    @NotEmpty
    @Column(name="amount_rec")
    private Integer amountRec;
    
    @JsonIgnore
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="prn")
    private Patient patient;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmountRec() {
        return amountRec;
    }

    public void setAmountRec(Integer amountRec) {
        this.amountRec = amountRec;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
   

   

    
    }
    
    
    
    

