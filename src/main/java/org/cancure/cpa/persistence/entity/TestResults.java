package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="test_results")
public class TestResults {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "test_result_id")
    private Integer id;
    
    private String comment;
    
    @Column(name="test_id")
    private Integer testId; 
    
    private String docPath;
    
    @Column(name = "patient_camp_id")
    private Integer patientCampId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public Integer getPatientCampId() {
        return patientCampId;
    }

    public void setPatientCampId(Integer patientCampId) {
        this.patientCampId = patientCampId;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }
    
}
