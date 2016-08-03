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
@Table(name= "patient_family")
public class PatientFamily {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="family_member_id")
    private Integer familyMemberId ;
    
    @NotEmpty
    private String relation ;

    @NotEmpty
    private Integer age;
    
    @NotEmpty
    private String status;
    
    @NotEmpty
    private Long income;
    
    @NotEmpty
    @Column(name="other_income")
    private Long otherIncome ;
   
    @JsonIgnore
    @ManyToOne (fetch = FetchType.LAZY) 
    @JoinColumn(name="prn")
    private Patient familyPatient;

    public Integer getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(Integer familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public Long getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(Long otherIncome) {
        this.otherIncome = otherIncome;
    }

    public Patient getFamilyPatient() {
        return familyPatient;
    }

    public void setFamilyPatient(Patient familyPatient) {
        this.familyPatient = familyPatient;
    }

   
    
}
