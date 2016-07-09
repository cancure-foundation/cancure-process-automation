package org.cancure.cpa.persistence.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer prn;
    
    @NotEmpty
    @Column(name="pidn",unique = true, nullable = false)
    private Integer pidn;
    
    @NotEmpty
    private String name;

    @NotEmpty
    private Date dob;
    
    @NotEmpty
    private String gender;
    
    @NotEmpty
    private Integer age;
    
    @NotEmpty
    @Column(name="blood_group")
    private String bloodGroup;
    
    @NotEmpty
    @Column(name="contact")
    private String contact;
    
    @NotEmpty
    private String address;
    
    @NotEmpty
    @Column(name="employment_status")
    private String employmentStatus;
    
    @NotEmpty
    @Column(name="soleBreadWinner")
    private Boolean solebreadwinner;
    
    @NotEmpty
    @Column(name="assets_owned")
    private String assetsOwned;
    
    @NotEmpty
    @Column(name="photo_location")
    private String photoLocation;
    
    @NotEmpty
    @Column(name="bystander_name")
    private String bystanderName;
    
    @NotEmpty
    @Column(name="bystander_contact")
    private String bystanderContact;
    
    @NotEmpty
    @Column(name="bystander_relation")
    private String bystanderRelation;
    
    @NotEmpty
    @Column(name="know_about_cancure")
    private String knowAboutCancure;
    
    @NotEmpty
    @Column(name="recommendation_name")
    private String recommendationName;
    
    @NotEmpty
    @Column(name="recommendation_type")
    private String recommendationType;
    
    @NotEmpty
    @Column(name="doctor_name")
    private String doctorName;
    
    @NotEmpty
    private String hospital;
    
    @NotEmpty
    private String diagnosis;
    
    @NotEmpty
    @Column(name="diagnosis_date")
    private Date diagnosisDate;
    
    @NotEmpty
    @Column(name="doctor_comments")
    private String doctorComments ;
    
    @NotEmpty
    @Column(name="type_of_support")
    private String typeOfSupport ;
    
    @OneToMany(mappedBy="familyPatient")
    private Set<PatientFamily> patientFamily=new HashSet<PatientFamily>();
    
    @OneToMany(mappedBy="patient")
     private Set<SupportOrganisation> organisation=new HashSet<SupportOrganisation>();
    
    @OneToMany(mappedBy="patientDoc")
    private Set<PatientDocument> document=new HashSet<PatientDocument>();

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

    public void setDob(Date date) {
        this.dob = date;
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

    public Set<PatientFamily> getPatientFamily() {
        return patientFamily;
    }

    public void setPatientFamily(Set<PatientFamily> patientFamily) {
        this.patientFamily = patientFamily;
    }

    public Set<SupportOrganisation> getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Set<SupportOrganisation> organisation) {
        this.organisation = organisation;
    }

    public Set<PatientDocument> getDocument() {
        return document;
    }

    public void setDocument(Set<PatientDocument> document) {
        this.document = document;
    }

   
    
    
}
