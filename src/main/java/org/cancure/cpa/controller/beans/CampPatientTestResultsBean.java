package org.cancure.cpa.controller.beans;

import org.springframework.web.multipart.MultipartFile;

public class CampPatientTestResultsBean {

    private Integer id;
    private String testResultText;
    private String testName; 
    private String testResultPath;
    private MultipartFile testFile;
    private Long campPatientId;

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

    public MultipartFile getTestFile() {
        return testFile;
    }

    public void setTestFile(MultipartFile testFile) {
        this.testFile = testFile;
    }

    public Long getCampPatientId() {
        return campPatientId;
    }

    public void setCampPatientId(Long campPatientId) {
        this.campPatientId = campPatientId;
    }       
}
