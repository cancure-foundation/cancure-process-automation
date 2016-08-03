package org.cancure.cpa.controller.beans;

import java.util.Date;



public class PatientInvestigationBean {

    private Integer prn;
    private String investigatorType;
    private Integer investigatorId;
    private Date investigationDate;
    private String status; 
    private String comments;

    public String getInvestigatorType() {
        return investigatorType;
    }

    public void setInvestigatorType(String investigatorType) {
        this.investigatorType = investigatorType;
    }

    public Integer getInvestigatorId() {
        return investigatorId;
    }

    public void setInvestigatorId(Integer investigatorId) {
        this.investigatorId = investigatorId;
    }

    public Date getInvestigationDate() {
        return investigationDate;
    }

    public void setInvestigationDate(Date investigationDate) {
        this.investigationDate = investigationDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getPrn() {
        return prn;
    }

    public void setPrn(Integer prn) {
        this.prn = prn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    


}
