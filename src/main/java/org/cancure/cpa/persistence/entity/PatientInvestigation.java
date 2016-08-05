package org.cancure.cpa.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="patient_investigation")
public class PatientInvestigation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="investigation_id")
    private Integer investigationId ;
    
    
    
    @Column(name="investigator_type")
    private String investigatorType;

   
    @Column(name="investigator_id")
    private Integer investigatorId;
    
    
    @Column(name="investigation_date")
    private Date investigationDate;
    
    
    private String comments;
    
    private String status;
    
    @Column(name="task_id")
    private String taskId;
    
    /*@JsonIgnore
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="prn")
    private Patient patientInvs;*/
    
    @Column(name="prn")
    private Integer prn;

    public Integer getInvestigationId() {
        return investigationId;
    }

    public void setInvestigationId(Integer investigationId) {
        this.investigationId = investigationId;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    /*public Patient getPatientInvs() {
        return patientInvs;
    }

    public void setPatientInvs(Patient patientInvs) {
        this.patientInvs = patientInvs;
    }
    */
    
}
