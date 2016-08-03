package org.cancure.cpa.controller.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientBean {

    private Integer prn;
    private Integer pidn;
    private String name;
    private Date dob;
    private String gender;
    private Integer age;
    private String bloodGroup;
    private String contact;
    private String address;
    private String employmentStatus;
    private Boolean solebreadwinner;
    private String assetsOwned;
    private String photoLocation;
    private String bystanderName;
    private String bystanderContact;
    private String bystanderRelation;
    private String knowAboutCancure;
    private String recommendationName;
    private String recommendationType;
    private String doctorName;
    private String hospital;
    private String diagnosis;
    private Date diagnosisDate;
    private String doctorComments ;
    private String typeOfSupport ;
    private List<PatientFamilyBean> patientFamily=new ArrayList<PatientFamilyBean>();
    private List<SupportOrganisationBean> organisation=new ArrayList<SupportOrganisationBean>();
    private List<PatientDocumentBean> document=new ArrayList<PatientDocumentBean>();
    private List<PatientInvestigationBean> patientInvestigation=new ArrayList<PatientInvestigationBean>();
    
    public Integer getPrn() {
        return prn;
    }
    public void setPrn(Integer prn) {
        this.prn = prn;
    }
    public Integer getPidn() {
        return pidn;
    }
    public void setPidn(Integer pidn) {
        this.pidn = pidn;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmploymentStatus() {
        return employmentStatus;
    }
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
    public Boolean getSolebreadwinner() {
        return solebreadwinner;
    }
    public void setSolebreadwinner(Boolean solebreadwinner) {
        this.solebreadwinner = solebreadwinner;
    }
    public String getAssetsOwned() {
        return assetsOwned;
    }
    public void setAssetsOwned(String assetsOwned) {
        this.assetsOwned = assetsOwned;
    }
    public String getPhotoLocation() {
        return photoLocation;
    }
    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }
    public String getBystanderName() {
        return bystanderName;
    }
    public void setBystanderName(String bystanderName) {
        this.bystanderName = bystanderName;
    }
    public String getBystanderContact() {
        return bystanderContact;
    }
    public void setBystanderContact(String bystanderContact) {
        this.bystanderContact = bystanderContact;
    }
    public String getBystanderRelation() {
        return bystanderRelation;
    }
    public void setBystanderRelation(String bystanderRelation) {
        this.bystanderRelation = bystanderRelation;
    }
    public String getKnowAboutCancure() {
        return knowAboutCancure;
    }
    public void setKnowAboutCancure(String knowAboutCancure) {
        this.knowAboutCancure = knowAboutCancure;
    }
    public String getRecommendationName() {
        return recommendationName;
    }
    public void setRecommendationName(String recommendationName) {
        this.recommendationName = recommendationName;
    }
    public String getRecommendationType() {
        return recommendationType;
    }
    public void setRecommendationType(String recommendationType) {
        this.recommendationType = recommendationType;
    }
    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getHospital() {
        return hospital;
    }
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    public Date getDiagnosisDate() {
        return diagnosisDate;
    }
    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }
    public String getDoctorComments() {
        return doctorComments;
    }
    public void setDoctorComments(String doctorComments) {
        this.doctorComments = doctorComments;
    }
    public String getTypeOfSupport() {
        return typeOfSupport;
    }
    public void setTypeOfSupport(String typeOfSupport) {
        this.typeOfSupport = typeOfSupport;
    }
    public List<PatientFamilyBean> getPatientFamily() {
        return patientFamily;
    }
    public void setPatientFamily(List<PatientFamilyBean> patientFamily) {
        this.patientFamily = patientFamily;
    }
    public List<SupportOrganisationBean> getOrganisation() {
        return organisation;
    }
    public void setOrganisation(List<SupportOrganisationBean> organisation) {
        this.organisation = organisation;
    }
    public List<PatientDocumentBean> getDocument() {
        return document;
    }
    public void setDocument(List<PatientDocumentBean> document) {
        this.document = document;
    }
    public List<PatientInvestigationBean> getPatientInvestigation() {
        return patientInvestigation;
    }
    public void setPatientInvestigation(List<PatientInvestigationBean> patientInvestigation) {
        this.patientInvestigation = patientInvestigation;
    } 
    
}
