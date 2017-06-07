package org.cancure.cpa.controller.beans;

import org.springframework.web.multipart.MultipartFile;

public class TestResultsBean {

    private Integer id;
    private String comment;
    private Integer testId;
    private String docPath;
    private MultipartFile testFile;    
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

    public MultipartFile getTestFile() {
        return testFile;
    }

    public void setTestFile(MultipartFile testFile) {
        this.testFile = testFile;
    }

    
}
