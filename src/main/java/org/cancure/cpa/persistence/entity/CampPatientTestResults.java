package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="camp_patient_test_results")
public class CampPatientTestResults {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "test_result_id")
    private Integer id;
    
    @Column(name="test_result_text")
    private String testResultText;
    
    @Column(name="test_name")
    private String testName; 
    
    @Column(name="test_result_path")
    private String testResultPath;
    
    @Column(name = "camp_patient_id")
    private Integer campPatientId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestResultText() {
        return testResultText;
    }

    public void setTestResultText(String testResultText) {
        this.testResultText = testResultText;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestResultPath() {
        return testResultPath;
    }

    public void setTestResultPath(String testResultPath) {
        this.testResultPath = testResultPath;
    }

    public Integer getCampPatientId() {
        return campPatientId;
    }

    public void setCampPatientId(Integer campPatientId) {
        this.campPatientId = campPatientId;
    }
    
}
