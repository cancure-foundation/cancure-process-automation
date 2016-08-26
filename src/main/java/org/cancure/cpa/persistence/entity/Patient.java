package org.cancure.cpa.persistence.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer prn;
    
    
    @Column(name="pidn",unique = true, nullable = false)
    private Integer pidn;
    
   
    private String name;

   
    private Date dob;
    
   
    private String gender;
      
    
    @Column(name="marital_status")
    private String maritalStatus;
    
    @Column(name="blood_group")
    private String bloodGroup;
    
    @Column(name="contact")
    private String contact;
    
    
    private String address;
    
    
    @Column(name="employment_status")
    private String employmentStatus;
    
    
    @Column(name="soleBreadWinner")
    private Boolean solebreadwinner;
    
    
    @Column(name="assets_owned")
    private String assetsOwned;
    
    
    @Column(name="bystander_name")
    private String bystanderName;
    
    
    @Column(name="bystander_contact")
    private String bystanderContact;
    
    
    @Column(name="bystander_relation")
    private String bystanderRelation;
    
    
    @Column(name="know_about_cancure")
    private String knowAboutCancure;
    
    
    @Column(name="recommendation_name")
    private String recommendationName;
    
    
    @Column(name="recommendation_type")
    private String recommendationType;
    
    
    @Column(name="doctor_name")
    private String doctorName;
    
    
    private String hospital;
    
    
    private String diagnosis;
    
    
    @Column(name="diagnosis_date")
    private Date diagnosisDate;
    
    
    @Column(name="doctor_comments")
    private String doctorComments ;
    
    
    @Column(name="type_of_support")
    private String typeOfSupport ;
    
    @Column(name="preliminary_exam_doctor_id")
    private Integer preliminaryExamDoctorId;
    
    @Column(name="preliminary_exam_hospital_id")
    private Integer preliminaryExamHospitalId;
    
    @OneToMany(mappedBy="familyPatient")
    private List<PatientFamily> patientFamily=new ArrayList<PatientFamily>();
    
    @OneToMany(mappedBy="patient")
     private List<SupportOrganisation> organisation=new ArrayList<SupportOrganisation>();
    
  //@OneToMany(mappedBy="patientDoc")
  //private List<PatientDocument> document=new ArrayList<PatientDocument>();
    
    /*@OneToMany(mappedBy="patientInvs")
    private List<PatientInvestigation> patientInvestigation=new ArrayList<PatientInvestigation>();*/

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

    public String getBloodGroup() {
        return bloodGroup;
    }

    
    public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
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

    public Integer getPreliminaryExamDoctorId() {
        return preliminaryExamDoctorId;
    }

    public void setPreliminaryExamDoctorId(Integer preliminaryExamDoctorId) {
        this.preliminaryExamDoctorId = preliminaryExamDoctorId;
    }

    public Integer getPreliminaryExamHospitalId() {
        return preliminaryExamHospitalId;
    }

    public void setPreliminaryExamHospitalId(Integer preliminaryExamHospitalId) {
        this.preliminaryExamHospitalId = preliminaryExamHospitalId;
    }

    public void setTypeOfSupport(String typeOfSupport) {
        this.typeOfSupport = typeOfSupport;
    }
    
    public List<PatientFamily> getPatientFamily() {
        return patientFamily;
    }

    public void setPatientFamily(List<PatientFamily> patientFamily) {
        this.patientFamily = patientFamily;
    }

    public List<SupportOrganisation> getOrganisation() {
        return organisation;
    }

    public void setOrganisation(List<SupportOrganisation> organisation) {
        this.organisation = organisation;
    }

 /*   public List<PatientDocument> getDocument() {
        return document;
    }

    public void setDocument(List<PatientDocument> document) {
        this.document = document;
    }
*/
    /*public List<PatientInvestigation> getPatientInvestigation() {
        return patientInvestigation;
    }

    public void setPatientInvestigation(List<PatientInvestigation> patientInvestigation) {
        this.patientInvestigation = patientInvestigation;
    }
*/
    
}
