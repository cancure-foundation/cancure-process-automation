package org.cancure.cpa.controller.beans;

public class PatientFamilyBean {
    
    private Integer prn;
    private String relation ;
    private Integer age;
    private String status;
    private Long income;
    private Long otherIncome;
    
    public Integer getPrn() {
        return prn;
    }
    public void setPrn(Integer prn) {
        this.prn = prn;
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
    
}
