/*package org.cancure.cpa.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cancure.cpa.Application;
import org.cancure.cpa.common.Constants;
import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentAndInvestigationBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientFamilyBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;
import org.cancure.cpa.controller.beans.SupportOrganisationBean;
import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.cancure.cpa.service.MyTasksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PatientWorkFlowControllerTest {
    
    @Autowired
    private PatientWorkFlowController pc;
    
    @Autowired
    private MyTasksService myTasksService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PatientRepository patientRepo;
    
    @Autowired
    private PatientFamilyRepository patientFamilyRepo;
    
    @Autowired
    private SupportOrganisationRepository SupportOrganisationRepo;
    
    @Autowired
    private PatientDocumentRepository PatientDocumentRepo;
    
    @Autowired
    private PatientInvestigationRepository PatientInvestigationRepo;
    
    
    private OAuth2Authentication getAuth() {
    	return getAuth("cancure");
    }
    
    private OAuth2Authentication getAuth(String login) {
    	Authentication authentication = new UsernamePasswordAuthenticationToken("cancure", "cancure");
    	OAuth2Authentication userAuthentication = new OAuth2Authentication(null, authentication);
        Map<String, String> map = new HashMap<>();
        map.put("username", login);
        userAuthentication.setDetails(map);
        OAuth2Authentication auth = new OAuth2Authentication(null, userAuthentication);
        return auth;
    }
    
    private void createRole(String username, String name, String password) {
    	Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setId(7); // Executive Committee
        roles.add(role);
        
        User user = new User();
        user.setLogin(username);
        user.setName(name);
        user.setPassword(password);
        user.setRoles(roles);
        user.setEnabled(true);
        try {
			userRepository.save(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    
    @Test
    public void testSave() throws Exception {

        PatientBean patient = new PatientBean();
        PatientFamilyBean patientFamily = new PatientFamilyBean();
        SupportOrganisationBean supportOrganisation = new SupportOrganisationBean();
        PatientDocumentBean patientDocument = new PatientDocumentBean();

        List<PatientFamilyBean> patientFamilyList = new ArrayList<PatientFamilyBean>();
        List<SupportOrganisationBean> organisationList = new ArrayList<SupportOrganisationBean>();
        List<PatientDocumentBean> documentList = new ArrayList<PatientDocumentBean>();

        patient.setName("Rahul");
        patient.setAddress("kochi");
        patient.setGender("male");
        patient.setSolebreadwinner(true);
        patient.setEmploymentStatus("Unemployed");

        patientFamily.setRelation("Father");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patientFamily = new PatientFamilyBean();
        patientFamily.setRelation("Mother");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patient.setPatientFamily(patientFamilyList);

        supportOrganisation.setName("Sunrise");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        supportOrganisation = new SupportOrganisationBean();
        supportOrganisation.setName("MedicalCare");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        patient.setOrganisation(organisationList);

        patientDocument.setDocCategory("AgeProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patient.setDocument(documentList);

        pc.save(patient);

        assertTrue(patient.getName().equals("Rahul"));
        assertTrue(patient.getGender().equals("male"));

        int count = 0;
        for (PatientFamilyBean p : patient.getPatientFamily()) {
            count++;
            assertTrue(p.getRelation().equals("Father") || p.getRelation().equals("Mother"));
        }
        assertEquals(2, count);

        assertTrue(patient.getOrganisation().get(0).getName().equals("Sunrise"));
        assertEquals(2, patient.getOrganisation().size());

        assertTrue(patient.getDocument().get(1).getDocCategory().equals("AddressProof"));
        assertEquals(2, patient.getDocument().size());
        
        //----------- Preliminary Examination ---------------------------------------
        
        Integer patientId = patient.getPrn();
        System.out.println("######### Patient Id is " + patientId);
        
        PatientDocumentBean preExamPatientDocument = new PatientDocumentBean();
        PatientInvestigationBean preExamPatientInvestigation=new PatientInvestigationBean(); 
        List<PatientDocumentBean> preExamDocumentList = new ArrayList<PatientDocumentBean>();
        preExamPatientDocument.setDocCategory("ScanReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        preExamPatientDocument = new PatientDocumentBean();
        preExamPatientDocument.setDocCategory("BloodTestReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        
        preExamPatientInvestigation.setComments("Blood Cancer");
        preExamPatientInvestigation.setInvestigatorType("Doctor");
        preExamPatientInvestigation.setInvestigatorId("1");
        preExamPatientInvestigation.setPrn(patientId + "");
        
        PatientDocumentAndInvestigationBean patientDocumentAndInvestigationBean = new PatientDocumentAndInvestigationBean();
        patientDocumentAndInvestigationBean.setPatientInvestigationBean(preExamPatientInvestigation);
        patientDocumentAndInvestigationBean.setPatientDocumentBean(preExamDocumentList);
        
        pc.saveExamination(patientDocumentAndInvestigationBean);
        
        Map<String, String> patRegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("BackgroundCheck", patRegNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bgPatientInvestigation=new PatientInvestigationBean(); 
        bgPatientInvestigation.setComments("Seems genuine");
        bgPatientInvestigation.setInvestigatorId("2");
        bgPatientInvestigation.setInvestigatorType("Program Coordinator");
        bgPatientInvestigation.setPrn(patientId + "");
        
        pc.saveBGC(bgPatientInvestigation, "PASS", getAuth());
        
        Map<String, String> bgNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", bgNextTask.get("nextTask"));
        
      //------------- MBDoctor Approval 1 -------------------------------
        PatientInvestigationBean mbPatientInvestigation=new PatientInvestigationBean(); 
        mbPatientInvestigation.setComments("Can be treated");
        mbPatientInvestigation.setInvestigatorId(3 + "");
        mbPatientInvestigation.setInvestigatorType("Doctor");
        mbPatientInvestigation.setPrn(patientId + "");
        pc.saveDoctorRecommendation(mbPatientInvestigation);
        
        Map<String, String> mb1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", mb1NextTask.get("nextTask"));
        
        //------------- MBDoctor Approval 2 -------------------------------
        PatientInvestigationBean mb2PatientInvestigation=new PatientInvestigationBean(); 
        mb2PatientInvestigation.setComments("Can be treated");
        mb2PatientInvestigation.setInvestigatorId(4 + "");
        mb2PatientInvestigation.setInvestigatorType("Doctor");
        mb2PatientInvestigation.setPrn(patientId + "");
        pc.saveDoctorRecommendation(mb2PatientInvestigation);
        
        Map<String, String> mb2NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Secretary Approval", mb2NextTask.get("nextTask"));
        
        //--------------- Secretary Approval --------------------------------
        PatientInvestigationBean secPatientInvestigation=new PatientInvestigationBean(); 
        secPatientInvestigation.setComments("Approving treatment");
        secPatientInvestigation.setInvestigatorId(5 + "");
        secPatientInvestigation.setInvestigatorType("Secretary");
        secPatientInvestigation.setPrn(patientId + "");
        pc.saveSecretaryRecommendation(secPatientInvestigation, "Approved", getAuth());
        
        Map<String, String> secNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Patient ID Card Generation", secNextTask.get("nextTask"));
        
        //--------------- PatientIDCardGeneration --------------------------------
        pc.savePatientIDCard(secPatientInvestigation);
        Map<String, String> patIdNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("", patIdNextTask.get("nextTask"));
        
        
    }
    
    @Test //bgc fail
    public void testBGCFail() throws IOException {

        PatientBean patient = new PatientBean();
        PatientFamilyBean patientFamily = new PatientFamilyBean();
        SupportOrganisationBean supportOrganisation = new SupportOrganisationBean();
        PatientDocumentBean patientDocument = new PatientDocumentBean();

        List<PatientFamilyBean> patientFamilyList = new ArrayList<PatientFamilyBean>();
        List<SupportOrganisationBean> organisationList = new ArrayList<SupportOrganisationBean>();
        List<PatientDocumentBean> documentList = new ArrayList<PatientDocumentBean>();

        patient.setName("Rahul");
        patient.setAddress("kochi");
        patient.setGender("male");
        patient.setSolebreadwinner(true);
        patient.setEmploymentStatus("Unemployed");

        patientFamily.setRelation("Father");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patientFamily = new PatientFamilyBean();
        patientFamily.setRelation("Mother");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patient.setPatientFamily(patientFamilyList);

        supportOrganisation.setName("Sunrise");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        supportOrganisation = new SupportOrganisationBean();
        supportOrganisation.setName("MedicalCare");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        patient.setOrganisation(organisationList);

        patientDocument.setDocCategory("AgeProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patient.setDocument(documentList);

        pc.save(patient);

        assertTrue(patient.getName().equals("Rahul"));
        assertTrue(patient.getGender().equals("male"));

        int count = 0;
        for (PatientFamilyBean p : patient.getPatientFamily()) {
            count++;
            assertTrue(p.getRelation().equals("Father") || p.getRelation().equals("Mother"));
        }
        assertEquals(2, count);

        assertTrue(patient.getOrganisation().get(0).getName().equals("Sunrise"));
        assertEquals(2, patient.getOrganisation().size());

        assertTrue(patient.getDocument().get(1).getDocCategory().equals("AddressProof"));
        assertEquals(2, patient.getDocument().size());
        
        //----------- Preliminary Examination ---------------------------------------
        
        Integer patientId = patient.getPrn();
        System.out.println("######### Patient Id is " + patientId);
        
        PatientDocumentBean preExamPatientDocument = new PatientDocumentBean();
        PatientInvestigationBean preExamPatientInvestigation=new PatientInvestigationBean(); 
        List<PatientDocumentBean> preExamDocumentList = new ArrayList<PatientDocumentBean>();
        preExamPatientDocument.setDocCategory("ScanReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        preExamPatientDocument = new PatientDocumentBean();
        preExamPatientDocument.setDocCategory("BloodTestReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        
        preExamPatientInvestigation.setComments("Blood Cancer");
        preExamPatientInvestigation.setInvestigatorType("Doctor");
        preExamPatientInvestigation.setInvestigatorId("1");
        preExamPatientInvestigation.setPrn(patientId + "");
        
        PatientDocumentAndInvestigationBean patientDocumentAndInvestigationBean = new PatientDocumentAndInvestigationBean();
        patientDocumentAndInvestigationBean.setPatientInvestigationBean(preExamPatientInvestigation);
        patientDocumentAndInvestigationBean.setPatientDocumentBean(preExamDocumentList);
        
        pc.saveExamination(patientDocumentAndInvestigationBean);
        
        Map<String, String> patRegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("BackgroundCheck", patRegNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bgPatientInvestigation=new PatientInvestigationBean(); 
        bgPatientInvestigation.setComments("Not Genuine");
        bgPatientInvestigation.setInvestigatorId("2");
        bgPatientInvestigation.setInvestigatorType("Program Coordinator");
        bgPatientInvestigation.setPrn(patientId + "");
        pc.saveBGC(bgPatientInvestigation,"FAIL", getAuth());
        
        Map<String, String> bgNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("", bgNextTask.get("nextTask"));
     
    }
    
    @Test  //1 MB reject and then secretary reject
    public void testMBApproval() throws IOException {

        PatientBean patient = new PatientBean();
        PatientFamilyBean patientFamily = new PatientFamilyBean();
        SupportOrganisationBean supportOrganisation = new SupportOrganisationBean();
        PatientDocumentBean patientDocument = new PatientDocumentBean();

        List<PatientFamilyBean> patientFamilyList = new ArrayList<PatientFamilyBean>();
        List<SupportOrganisationBean> organisationList = new ArrayList<SupportOrganisationBean>();
        List<PatientDocumentBean> documentList = new ArrayList<PatientDocumentBean>();

        patient.setName("Rahul");
        patient.setAddress("kochi");
        patient.setGender("male");
        patient.setSolebreadwinner(true);
        patient.setEmploymentStatus("Unemployed");

        patientFamily.setRelation("Father");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patientFamily = new PatientFamilyBean();
        patientFamily.setRelation("Mother");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patient.setPatientFamily(patientFamilyList);

        supportOrganisation.setName("Sunrise");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        supportOrganisation = new SupportOrganisationBean();
        supportOrganisation.setName("MedicalCare");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        patient.setOrganisation(organisationList);

        patientDocument.setDocCategory("AgeProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patient.setDocument(documentList);

        pc.save(patient);

        assertTrue(patient.getName().equals("Rahul"));
        assertTrue(patient.getGender().equals("male"));

        int count = 0;
        for (PatientFamilyBean p : patient.getPatientFamily()) {
            count++;
            assertTrue(p.getRelation().equals("Father") || p.getRelation().equals("Mother"));
        }
        assertEquals(2, count);

        assertTrue(patient.getOrganisation().get(0).getName().equals("Sunrise"));
        assertEquals(2, patient.getOrganisation().size());

        assertTrue(patient.getDocument().get(1).getDocCategory().equals("AddressProof"));
        assertEquals(2, patient.getDocument().size());
        
        //----------- Preliminary Examination ---------------------------------------
        
        Integer patientId = patient.getPrn();
        System.out.println("######### Patient Id is " + patientId);
        
        PatientDocumentBean preExamPatientDocument = new PatientDocumentBean();
        PatientInvestigationBean preExamPatientInvestigation=new PatientInvestigationBean(); 
        List<PatientDocumentBean> preExamDocumentList = new ArrayList<PatientDocumentBean>();
        preExamPatientDocument.setDocCategory("ScanReport " + patientId);
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        preExamPatientDocument = new PatientDocumentBean();
        preExamPatientDocument.setDocCategory("BloodTestReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        
        preExamPatientInvestigation.setComments("Blood Cancer");
        preExamPatientInvestigation.setInvestigatorType("Doctor");
        preExamPatientInvestigation.setInvestigatorId("1");
        preExamPatientInvestigation.setPrn(patientId + "");
        
        PatientDocumentAndInvestigationBean patientDocumentAndInvestigationBean = new PatientDocumentAndInvestigationBean();
        patientDocumentAndInvestigationBean.setPatientInvestigationBean(preExamPatientInvestigation);
        patientDocumentAndInvestigationBean.setPatientDocumentBean(preExamDocumentList);
        pc.saveExamination(patientDocumentAndInvestigationBean);
        
        Map<String, String> patRegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("BackgroundCheck", patRegNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bgPatientInvestigation=new PatientInvestigationBean(); 
        bgPatientInvestigation.setComments("Seems genuine " + patientId);
        bgPatientInvestigation.setInvestigatorId("2");
        bgPatientInvestigation.setInvestigatorType("Program Coordinator");
        bgPatientInvestigation.setPrn(patientId + "");
        pc.saveBGC(bgPatientInvestigation, "PASS", getAuth());
        
        Map<String, String> bgNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", bgNextTask.get("nextTask"));
        
      //------------- MBDoctor Approval 1 -------------------------------
        PatientInvestigationBean mbPatientInvestigation=new PatientInvestigationBean(); 
        mbPatientInvestigation.setComments("Can be treated " + patientId);
        mbPatientInvestigation.setInvestigatorId("3");
        mbPatientInvestigation.setInvestigatorType("Doctor");
        mbPatientInvestigation.setPrn(patientId + "");
        pc.saveDoctorRecommendation(mbPatientInvestigation);
        
        Map<String, String> mb1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", mb1NextTask.get("nextTask"));
        
        //------------- MBDoctor Approval 2 -------------------------------
        PatientInvestigationBean mb2PatientInvestigation=new PatientInvestigationBean(); 
        mb2PatientInvestigation.setComments("Cannot be treated");
        mb2PatientInvestigation.setInvestigatorId(4);
        mb2PatientInvestigation.setInvestigatorType("Doctor");
        mb2PatientInvestigation.setPrn(patientId);
        pc.saveDoctorRecommendation(mb2PatientInvestigation);
        
        Map<String, String> mb2NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("", mb2NextTask.get("nextTask"));
        
      //------------- MBDoctor Approval 3 -------------------------------
        PatientInvestigationBean mb3PatientInvestigation=new PatientInvestigationBean(); 
        mb3PatientInvestigation.setComments("Can be treated2 " + patientId);
        mb3PatientInvestigation.setInvestigatorId("4");
        mb3PatientInvestigation.setInvestigatorType("Doctor");
        mb3PatientInvestigation.setPrn(patientId + "");
        pc.saveDoctorRecommendation(mb3PatientInvestigation);
        
        Map<String, String> mb3NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Secretary Approval", mb3NextTask.get("nextTask"));
        
        //--------------- Secretary Approval --------------------------------
        PatientInvestigationBean secPatientInvestigation=new PatientInvestigationBean(); 
        secPatientInvestigation.setComments("Reject treatment " + patientId);
        secPatientInvestigation.setInvestigatorId("5");
        secPatientInvestigation.setInvestigatorType("Secretary");
        secPatientInvestigation.setPrn(patientId + "");
        pc.saveSecretaryRecommendation(secPatientInvestigation, "Reject", getAuth());
        
        Map<String, String> secNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("", secNextTask.get("nextTask"));
    }
    
    @Test //recommend to ec
    public void testSecretaryRecommend() throws Exception {

        PatientBean patient = new PatientBean();
        PatientFamilyBean patientFamily = new PatientFamilyBean();
        SupportOrganisationBean supportOrganisation = new SupportOrganisationBean();
        PatientDocumentBean patientDocument = new PatientDocumentBean();

        List<PatientFamilyBean> patientFamilyList = new ArrayList<PatientFamilyBean>();
        List<SupportOrganisationBean> organisationList = new ArrayList<SupportOrganisationBean>();
        List<PatientDocumentBean> documentList = new ArrayList<PatientDocumentBean>();

        patient.setName("Rahul");
        patient.setAddress("kochi");
        patient.setGender("male");
        patient.setSolebreadwinner(true);
        patient.setEmploymentStatus("Unemployed");

        patientFamily.setRelation("Father");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patientFamily = new PatientFamilyBean();
        patientFamily.setRelation("Mother");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patient.setPatientFamily(patientFamilyList);

        supportOrganisation.setName("Sunrise");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        supportOrganisation = new SupportOrganisationBean();
        supportOrganisation.setName("MedicalCare");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        patient.setOrganisation(organisationList);

        patientDocument.setDocCategory("AgeProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patient.setDocument(documentList);

        pc.save(patient);

        assertTrue(patient.getName().equals("Rahul"));
        assertTrue(patient.getGender().equals("male"));

        int count = 0;
        for (PatientFamilyBean p : patient.getPatientFamily()) {
            count++;
            assertTrue(p.getRelation().equals("Father") || p.getRelation().equals("Mother"));
        }
        assertEquals(2, count);

        assertTrue(patient.getOrganisation().get(0).getName().equals("Sunrise"));
        assertEquals(2, patient.getOrganisation().size());

        assertTrue(patient.getDocument().get(1).getDocCategory().equals("AddressProof"));
        assertEquals(2, patient.getDocument().size());
        
        createRole("ec1", "EC1", "password"); 
        createRole("ec2", "EC2", "password"); 
        createRole("ec3", "EC3", "password"); 
        createRole("ec4", "EC4", "password");
        
        //----------- Preliminary Examination ---------------------------------------
        
        Integer patientId = patient.getPrn();
        System.out.println("######### Patient Id is " + patientId);
        
        PatientDocumentBean preExamPatientDocument = new PatientDocumentBean();
        PatientInvestigationBean preExamPatientInvestigation=new PatientInvestigationBean(); 
        List<PatientDocumentBean> preExamDocumentList = new ArrayList<PatientDocumentBean>();
        preExamPatientDocument.setDocCategory("ScanReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        preExamPatientDocument = new PatientDocumentBean();
        preExamPatientDocument.setDocCategory("BloodTestReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        
        preExamPatientInvestigation.setComments("Blood Cancer");
        preExamPatientInvestigation.setInvestigatorType("Doctor");
        preExamPatientInvestigation.setInvestigatorId("1");
        preExamPatientInvestigation.setPrn(patientId + "");
        
        PatientDocumentAndInvestigationBean patientDocumentAndInvestigationBean = new PatientDocumentAndInvestigationBean();
        patientDocumentAndInvestigationBean.setPatientInvestigationBean(preExamPatientInvestigation);
        patientDocumentAndInvestigationBean.setPatientDocumentBean(preExamDocumentList);
        
        pc.saveExamination(patientDocumentAndInvestigationBean);
        
        Map<String, String> patRegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("BackgroundCheck", patRegNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bgPatientInvestigation=new PatientInvestigationBean(); 
        bgPatientInvestigation.setComments("Seems genuine");
        bgPatientInvestigation.setInvestigatorId("2");
        bgPatientInvestigation.setInvestigatorType("Program Coordinator");
        bgPatientInvestigation.setPrn(patientId + "");
        pc.saveBGC(bgPatientInvestigation, "PASS", getAuth());
        
        Map<String, String> bgNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", bgNextTask.get("nextTask"));
        
      //------------- MBDoctor Approval 1 -------------------------------
        PatientInvestigationBean mbPatientInvestigation=new PatientInvestigationBean(); 
        mbPatientInvestigation.setComments("Can be treated");
        mbPatientInvestigation.setInvestigatorId("3");
        mbPatientInvestigation.setInvestigatorType("Doctor");
        mbPatientInvestigation.setPrn(patientId + "");
        pc.saveDoctorRecommendation(mbPatientInvestigation);
        
        Map<String, String> mb1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", mb1NextTask.get("nextTask"));
        
        //------------- MBDoctor Approval 2 -------------------------------
        PatientInvestigationBean mb2PatientInvestigation=new PatientInvestigationBean(); 
        mb2PatientInvestigation.setComments("Can be treated");
        mb2PatientInvestigation.setInvestigatorId("4");
        mb2PatientInvestigation.setInvestigatorType("Doctor");
        mb2PatientInvestigation.setPrn(patientId + "");
        pc.saveDoctorRecommendation(mb2PatientInvestigation);
        
        Map<String, String> mb2NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Secretary Approval", mb2NextTask.get("nextTask"));
        
        //--------------- Secretary Approval --------------------------------
        PatientInvestigationBean secPatientInvestigation=new PatientInvestigationBean(); 
        secPatientInvestigation.setComments("Not Satisfied,Forward to EC");
        secPatientInvestigation.setInvestigatorId("5");
        secPatientInvestigation.setInvestigatorType("Secretary");
        secPatientInvestigation.setPrn(patientId + "");
        pc.saveSecretaryRecommendation(secPatientInvestigation, "Recommend", getAuth());
        
        Map<String, String> secNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("EC Approval", secNextTask.get("nextTask"));
        
        //--------------- EC Approval 1--------------------------------
        PatientInvestigationBean ec1PatientInvestigation=new PatientInvestigationBean(); 
        ec1PatientInvestigation.setComments("Approving treatment");
        ec1PatientInvestigation.setInvestigatorId("6");
        ec1PatientInvestigation.setInvestigatorType("Executive Committee");
        ec1PatientInvestigation.setPrn(patientId + "");
        pc.saveExecutiveBoardRecommendationAccept(ec1PatientInvestigation, getAuth("ec1"));
        
        Map<String, String> ec1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("EC Approval", ec1NextTask.get("nextTask"));
        
        //--------------- EC Approval 2--------------------------------
        PatientInvestigationBean ec2PatientInvestigation=new PatientInvestigationBean(); 
        ec2PatientInvestigation.setComments("Approving treatment");
        ec2PatientInvestigation.setInvestigatorId("7");
        ec2PatientInvestigation.setInvestigatorType("Executive Committee");
        ec2PatientInvestigation.setPrn(patientId + "");
        pc.saveExecutiveBoardRecommendationAccept(ec2PatientInvestigation, getAuth("ec2"));
        
        Map<String, String> ec2NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("EC Approval", ec2NextTask.get("nextTask"));
        
        
        //--------------- EC Approval 3--------------------------------
        PatientInvestigationBean ec3PatientInvestigation=new PatientInvestigationBean(); 
        ec3PatientInvestigation.setComments("Approving treatment");
        ec3PatientInvestigation.setInvestigatorId("8");
        ec3PatientInvestigation.setInvestigatorType("Executive Committee");
        ec3PatientInvestigation.setPrn(patientId + "");
        pc.saveExecutiveBoardRecommendationAccept(ec3PatientInvestigation, getAuth("ec3"));
        
        Map<String, String> ec3NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Patient ID Card Generation", ec3NextTask.get("nextTask"));
        
        //--------------- PatientIDCardGeneration --------------------------------
        pc.savePatientIDCard(ec3PatientInvestigation);
        Map<String, String> patIdNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("", patIdNextTask.get("nextTask"));
             
    }
    
    @Test //secretary send back to pc
    public void testSecretarySendBackToPc() throws IOException {

        PatientBean patient = new PatientBean();
        PatientFamilyBean patientFamily = new PatientFamilyBean();
        SupportOrganisationBean supportOrganisation = new SupportOrganisationBean();
        PatientDocumentBean patientDocument = new PatientDocumentBean();

        List<PatientFamilyBean> patientFamilyList = new ArrayList<PatientFamilyBean>();
        List<SupportOrganisationBean> organisationList = new ArrayList<SupportOrganisationBean>();
        List<PatientDocumentBean> documentList = new ArrayList<PatientDocumentBean>();

        patient.setName("Rahul");
        patient.setAddress("kochi");
        patient.setGender("male");
        patient.setSolebreadwinner(true);
        patient.setEmploymentStatus("Unemployed");

        patientFamily.setRelation("Father");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patientFamily = new PatientFamilyBean();
        patientFamily.setRelation("Mother");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patient.setPatientFamily(patientFamilyList);

        supportOrganisation.setName("Sunrise");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        supportOrganisation = new SupportOrganisationBean();
        supportOrganisation.setName("MedicalCare");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        patient.setOrganisation(organisationList);

        patientDocument.setDocCategory("AgeProof");
        patientDocument.setPrn(patient.getPrn()+"");
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(patient.getPrn()+"");
        documentList.add(patientDocument);
        patient.setDocument(documentList);

        pc.save(patient);

        assertTrue(patient.getName().equals("Rahul"));
        assertTrue(patient.getGender().equals("male"));

        int count = 0;
        for (PatientFamilyBean p : patient.getPatientFamily()) {
            count++;
            assertTrue(p.getRelation().equals("Father") || p.getRelation().equals("Mother"));
        }
        assertEquals(2, count);

        assertTrue(patient.getOrganisation().get(0).getName().equals("Sunrise"));
        assertEquals(2, patient.getOrganisation().size());

        assertTrue(patient.getDocument().get(1).getDocCategory().equals("AddressProof"));
        assertEquals(2, patient.getDocument().size());
        
        //----------- Preliminary Examination ---------------------------------------
        
        Integer patientId = patient.getPrn();
        System.out.println("######### Patient Id is " + patientId);
        
        PatientDocumentBean preExamPatientDocument = new PatientDocumentBean();
        PatientInvestigationBean preExamPatientInvestigation=new PatientInvestigationBean(); 
        List<PatientDocumentBean> preExamDocumentList = new ArrayList<PatientDocumentBean>();
        preExamPatientDocument.setDocCategory("ScanReport");
        preExamPatientDocument.setPrn(patientId+"");
        preExamDocumentList.add(preExamPatientDocument);
        preExamPatientDocument = new PatientDocumentBean();
        preExamPatientDocument.setDocCategory("BloodTestReport");
        preExamPatientDocument.setPrn(patientId+"");
        preExamDocumentList.add(preExamPatientDocument);
        
        preExamPatientInvestigation.setComments("Blood Cancer");
        preExamPatientInvestigation.setInvestigatorType("Doctor");
        preExamPatientInvestigation.setInvestigatorId("1");
        preExamPatientInvestigation.setPrn(patientId+"");
        
        PatientDocumentAndInvestigationBean patientDocumentAndInvestigationBean = new PatientDocumentAndInvestigationBean();
        patientDocumentAndInvestigationBean.setPatientInvestigationBean(preExamPatientInvestigation);
        patientDocumentAndInvestigationBean.setPatientDocumentBean(preExamDocumentList);
        pc.saveExamination(patientDocumentAndInvestigationBean);
        
        Map<String, String> patRegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("BackgroundCheck", patRegNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bgPatientInvestigation=new PatientInvestigationBean(); 
        bgPatientInvestigation.setComments("Seems genuine");
        bgPatientInvestigation.setInvestigatorId("2");
        bgPatientInvestigation.setInvestigatorType("Program Coordinator");
        bgPatientInvestigation.setPrn(patientId+"");
        pc.saveBGC(bgPatientInvestigation, "PASS",getAuth());
        
        Map<String, String> bgNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", bgNextTask.get("nextTask"));
        
      //------------- MBDoctor Approval 1 -------------------------------
        PatientInvestigationBean mbPatientInvestigation=new PatientInvestigationBean(); 
        mbPatientInvestigation.setComments("Can be treated");
        mbPatientInvestigation.setInvestigatorId("3");
        mbPatientInvestigation.setInvestigatorType("Doctor");
        mbPatientInvestigation.setPrn(patientId+"");
        pc.saveDoctorRecommendation(mbPatientInvestigation);
        
        Map<String, String> mb1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", mb1NextTask.get("nextTask"));
        
        //------------- MBDoctor Approval 2 -------------------------------
        PatientInvestigationBean mb2PatientInvestigation=new PatientInvestigationBean(); 
        mb2PatientInvestigation.setComments("Can be treated");
        mb2PatientInvestigation.setInvestigatorId("4");
        mb2PatientInvestigation.setInvestigatorType("Doctor");
        mb2PatientInvestigation.setPrn(patientId+"");
        pc.saveDoctorRecommendation(mb2PatientInvestigation);
        
        Map<String, String> mb2NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Secretary Approval", mb2NextTask.get("nextTask"));
        
        //--------------- Secretary Approval --------------------------------
        PatientInvestigationBean secPatientInvestigation=new PatientInvestigationBean(); 
        secPatientInvestigation.setComments("ReCheck BGC");
        secPatientInvestigation.setInvestigatorId("5");
        secPatientInvestigation.setInvestigatorType("Secretary");
        secPatientInvestigation.setPrn(patientId+"");
        pc.saveSecretaryRecommendation(secPatientInvestigation,"SendBackToPC",getAuth());
        
        Map<String, String> secNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Background Clarification", secNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bg1PatientInvestigation=new PatientInvestigationBean(); 
        bg1PatientInvestigation.setComments("Not Genuine");
        bg1PatientInvestigation.setInvestigatorId("10");
        bg1PatientInvestigation.setInvestigatorType("Program Coordinator");
        bg1PatientInvestigation.setPrn(patientId+"");
        pc.saveBackgrounCheckClarificationsFromPC(bg1PatientInvestigation, getAuth());
        
        Map<String, String> bg1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Secretary Approval", bg1NextTask.get("nextTask"));
        
        //--------------- Secretary Approval --------------------------------
        PatientInvestigationBean sec1PatientInvestigation=new PatientInvestigationBean(); 
        sec1PatientInvestigation.setComments("Reject treatment " + patientId);
        sec1PatientInvestigation.setInvestigatorId("5");
        sec1PatientInvestigation.setInvestigatorType("Secretary");
        sec1PatientInvestigation.setPrn(patientId + "");
        pc.saveSecretaryRecommendation(sec1PatientInvestigation, "Reject", getAuth());
        
        Map<String, String> sec1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("", sec1NextTask.get("nextTask"));
    }
    
    @Test // test preliminary Exam clarification
    public void testPreliminaryExamClarification() throws IOException  {
        PatientBean patient = new PatientBean();
        PatientFamilyBean patientFamily = new PatientFamilyBean();
        SupportOrganisationBean supportOrganisation = new SupportOrganisationBean();
        PatientDocumentBean patientDocument = new PatientDocumentBean();

        List<PatientFamilyBean> patientFamilyList = new ArrayList<PatientFamilyBean>();
        List<SupportOrganisationBean> organisationList = new ArrayList<SupportOrganisationBean>();
        List<PatientDocumentBean> documentList = new ArrayList<PatientDocumentBean>();

        patient.setName("Rahul");
        patient.setAddress("kochi");
        patient.setGender("male");
        patient.setSolebreadwinner(true);
        patient.setEmploymentStatus("Unemployed");

        patientFamily.setRelation("Father");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patientFamily = new PatientFamilyBean();
        patientFamily.setRelation("Mother");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patient.setPatientFamily(patientFamilyList);

        supportOrganisation.setName("Sunrise");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        supportOrganisation = new SupportOrganisationBean();
        supportOrganisation.setName("MedicalCare");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        patient.setOrganisation(organisationList);

        patientDocument.setDocCategory("AgeProof");
        patientDocument.setPrn(patient.getPrn()+"");
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(patient.getPrn()+"");
        documentList.add(patientDocument);
        patient.setDocument(documentList);

        pc.save(patient);

        assertTrue(patient.getName().equals("Rahul"));
        assertTrue(patient.getGender().equals("male"));

        int count = 0;
        for (PatientFamilyBean p : patient.getPatientFamily()) {
            count++;
            assertTrue(p.getRelation().equals("Father") || p.getRelation().equals("Mother"));
        }
        assertEquals(2, count);

        assertTrue(patient.getOrganisation().get(0).getName().equals("Sunrise"));
        assertEquals(2, patient.getOrganisation().size());

        assertTrue(patient.getDocument().get(1).getDocCategory().equals("AddressProof"));
        assertEquals(2, patient.getDocument().size());
        
        //----------- Preliminary Examination ---------------------------------------
        
        Integer patientId = patient.getPrn();
        System.out.println("######### Patient Id is " + patientId);
        
        PatientDocumentBean preExamPatientDocument = new PatientDocumentBean();
        PatientInvestigationBean preExamPatientInvestigation=new PatientInvestigationBean(); 
        List<PatientDocumentBean> preExamDocumentList = new ArrayList<PatientDocumentBean>();
        preExamPatientDocument.setDocCategory("ScanReport");
        preExamPatientDocument.setPrn(patientId+"");
        preExamDocumentList.add(preExamPatientDocument);
        preExamPatientDocument = new PatientDocumentBean();
        preExamPatientDocument.setDocCategory("BloodTestReport");
        preExamPatientDocument.setPrn(patientId+"");
        preExamDocumentList.add(preExamPatientDocument);
        
        preExamPatientInvestigation.setComments("Blood Cancer");
        preExamPatientInvestigation.setInvestigatorType("Doctor");
        preExamPatientInvestigation.setInvestigatorId("1");
        preExamPatientInvestigation.setPrn(patientId+"");
        
        PatientDocumentAndInvestigationBean patientDocumentAndInvestigationBean = new PatientDocumentAndInvestigationBean();
        patientDocumentAndInvestigationBean.setPatientInvestigationBean(preExamPatientInvestigation);
        patientDocumentAndInvestigationBean.setPatientDocumentBean(preExamDocumentList);
        pc.saveExamination(patientDocumentAndInvestigationBean);
        
        Map<String, String> patRegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("BackgroundCheck", patRegNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bgPatientInvestigation=new PatientInvestigationBean(); 
        bgPatientInvestigation.setComments("Seems genuine");
        bgPatientInvestigation.setInvestigatorId("2");
        bgPatientInvestigation.setInvestigatorType("Program Coordinator");
        bgPatientInvestigation.setPrn(patientId+"");
        pc.saveBGC(bgPatientInvestigation, "PASS",getAuth());
        
        Map<String, String> bgNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", bgNextTask.get("nextTask"));
        
      //------------- MBDoctor Approval 1 -------------------------------
        PatientInvestigationBean mbPatientInvestigation=new PatientInvestigationBean(); 
        mbPatientInvestigation.setComments("Can be treated");
        mbPatientInvestigation.setInvestigatorId("3");
        mbPatientInvestigation.setInvestigatorType("Doctor");
        mbPatientInvestigation.setPrn(patientId+"");
        pc.saveDoctorRecommendation(mbPatientInvestigation);
        
        Map<String, String> mb1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", mb1NextTask.get("nextTask"));
        
        //------------- MBDoctor Approval 2 -------------------------------
        PatientInvestigationBean mb2PatientInvestigation=new PatientInvestigationBean(); 
        mb2PatientInvestigation.setComments("Can be treated");
        mb2PatientInvestigation.setInvestigatorId("4");
        mb2PatientInvestigation.setInvestigatorType("Doctor");
        mb2PatientInvestigation.setPrn(patientId+"");
        pc.saveDoctorRecommendation(mb2PatientInvestigation);
        
        Map<String, String> mb2NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Secretary Approval", mb2NextTask.get("nextTask"));
        
        //--------------- Secretary Approval --------------------------------
        PatientInvestigationBean secPatientInvestigation=new PatientInvestigationBean(); 
        secPatientInvestigation.setComments("ReCheck BGC");
        secPatientInvestigation.setInvestigatorId("5");
        secPatientInvestigation.setInvestigatorType("Secretary");
        secPatientInvestigation.setPrn(patientId+"");
        pc.saveSecretaryRecommendation(secPatientInvestigation,"prelimExamClarificationReqd",getAuth());
        
        Map<String, String> secNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Preliminary Examination Clarification", secNextTask.get("nextTask")); 
        
        //-------------- PreliminaryExamination--------------------------------
        PatientDocumentBean pre1ExamPatientDocument = new PatientDocumentBean();
        PatientInvestigationBean pre1ExamPatientInvestigation=new PatientInvestigationBean(); 
        List<PatientDocumentBean> pre1ExamDocumentList = new ArrayList<PatientDocumentBean>();
        pre1ExamPatientDocument.setDocCategory("SugarReport");
        pre1ExamPatientDocument.setPrn(patientId+"");
        pre1ExamDocumentList.add(pre1ExamPatientDocument);
        pre1ExamPatientDocument = new PatientDocumentBean();
        pre1ExamPatientDocument.setDocCategory("DiagnosisReport");
        pre1ExamPatientDocument.setPrn(patientId+"");
        pre1ExamDocumentList.add(pre1ExamPatientDocument);
        
        pre1ExamPatientInvestigation.setComments("Blood Cancer");
        pre1ExamPatientInvestigation.setInvestigatorType("Doctor");
        pre1ExamPatientInvestigation.setInvestigatorId("1");
        pre1ExamPatientInvestigation.setPrn(patientId+"");
        
        pc.savePreliminaryExamClarification(pre1ExamPatientInvestigation);
        
        Map<String, String> pat1RegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", pat1RegNextTask.get("nextTask"));
        
        //------------- MBDoctor Approval 1 -------------------------------
        PatientInvestigationBean mb1PatientInvestigation=new PatientInvestigationBean(); 
        mb1PatientInvestigation.setComments("Can be treated");
        mb1PatientInvestigation.setInvestigatorId("3");
        mb1PatientInvestigation.setInvestigatorType("Doctor");
        mb1PatientInvestigation.setPrn(patientId+"");
        pc.saveDoctorRecommendation(mb1PatientInvestigation);
        
        Map<String, String> mb3NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", mb3NextTask.get("nextTask"));
        
        //------------- MBDoctor Approval 2 -------------------------------
        PatientInvestigationBean mb3PatientInvestigation=new PatientInvestigationBean(); 
        mb3PatientInvestigation.setComments("Can be treated");
        mb3PatientInvestigation.setInvestigatorId("4");
        mb3PatientInvestigation.setInvestigatorType("Doctor");
        mb3PatientInvestigation.setPrn(patientId+"");
        pc.saveDoctorRecommendation(mb3PatientInvestigation);
        
        Map<String, String> mb4NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Secretary Approval", mb4NextTask.get("nextTask"));
        
        //--------------- Secretary Approval --------------------------------
        PatientInvestigationBean sec1PatientInvestigation=new PatientInvestigationBean(); 
        sec1PatientInvestigation.setComments("ReCheck BGC");
        sec1PatientInvestigation.setInvestigatorId("5");
        sec1PatientInvestigation.setInvestigatorType("Secretary");
        sec1PatientInvestigation.setPrn(patientId+"");
        pc.saveSecretaryRecommendation(sec1PatientInvestigation,"Reject",getAuth());
        
        Map<String, String> sec1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("", sec1NextTask.get("nextTask")); 
    }
    
    @Test // test one ec reject
    public void testECReject() throws Exception {

        PatientBean patient = new PatientBean();
        PatientFamilyBean patientFamily = new PatientFamilyBean();
        SupportOrganisationBean supportOrganisation = new SupportOrganisationBean();
        PatientDocumentBean patientDocument = new PatientDocumentBean();

        List<PatientFamilyBean> patientFamilyList = new ArrayList<PatientFamilyBean>();
        List<SupportOrganisationBean> organisationList = new ArrayList<SupportOrganisationBean>();
        List<PatientDocumentBean> documentList = new ArrayList<PatientDocumentBean>();

        patient.setName("Rahul");
        patient.setAddress("kochi");
        patient.setGender("male");
        patient.setSolebreadwinner(true);

        patient.setEmploymentStatus("Unemployed");

        patientFamily.setRelation("Father");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patientFamily = new PatientFamilyBean();
        patientFamily.setRelation("Mother");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patient.setPatientFamily(patientFamilyList);

        supportOrganisation.setName("Sunrise");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        supportOrganisation = new SupportOrganisationBean();
        supportOrganisation.setName("MedicalCare");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        patient.setOrganisation(organisationList);

        patientDocument.setDocCategory("AgeProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patient.setDocument(documentList);

        pc.save(patient);

        assertTrue(patient.getName().equals("Rahul"));
        assertTrue(patient.getGender().equals("male"));

        int count = 0;
        for (PatientFamilyBean p : patient.getPatientFamily()) {
            count++;
            assertTrue(p.getRelation().equals("Father") || p.getRelation().equals("Mother"));
        }
        assertEquals(2, count);

        assertTrue(patient.getOrganisation().get(0).getName().equals("Sunrise"));
        assertEquals(2, patient.getOrganisation().size());

        assertTrue(patient.getDocument().get(1).getDocCategory().equals("AddressProof"));
        assertEquals(2, patient.getDocument().size());
        
        createRole("ec1", "EC1", "password"); 
        createRole("ec2", "EC2", "password"); 
        createRole("ec3", "EC3", "password"); 
        createRole("ec4", "EC4", "password"); 
        
        //----------- Preliminary Examination ---------------------------------------
        
        Integer patientId = patient.getPrn();
        System.out.println("######### Patient Id is " + patientId);
        
        PatientDocumentBean preExamPatientDocument = new PatientDocumentBean();
        PatientInvestigationBean preExamPatientInvestigation=new PatientInvestigationBean(); 
        List<PatientDocumentBean> preExamDocumentList = new ArrayList<PatientDocumentBean>();
        preExamPatientDocument.setDocCategory("ScanReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        preExamPatientDocument = new PatientDocumentBean();
        preExamPatientDocument.setDocCategory("BloodTestReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        
        preExamPatientInvestigation.setComments("Blood Cancer");
        preExamPatientInvestigation.setInvestigatorType("Doctor");
        preExamPatientInvestigation.setInvestigatorId("1");
        preExamPatientInvestigation.setPrn(patientId + "");
        
        PatientDocumentAndInvestigationBean patientDocumentAndInvestigationBean = new PatientDocumentAndInvestigationBean();
        patientDocumentAndInvestigationBean.setPatientInvestigationBean(preExamPatientInvestigation);
        patientDocumentAndInvestigationBean.setPatientDocumentBean(preExamDocumentList);
        pc.saveExamination(patientDocumentAndInvestigationBean);
        
        Map<String, String> patRegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("BackgroundCheck", patRegNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bgPatientInvestigation=new PatientInvestigationBean(); 
        bgPatientInvestigation.setComments("Seems genuine");
        bgPatientInvestigation.setInvestigatorId("2");
        bgPatientInvestigation.setInvestigatorType("Program Coordinator");
        bgPatientInvestigation.setPrn(patientId + "");
        pc.saveBGC(bgPatientInvestigation, "PASS", getAuth());
        
        Map<String, String> bgNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", bgNextTask.get("nextTask"));
        
      //------------- MBDoctor Approval 1 -------------------------------
        PatientInvestigationBean mbPatientInvestigation=new PatientInvestigationBean(); 
        mbPatientInvestigation.setComments("Can be treated");
        mbPatientInvestigation.setInvestigatorId("3");
        mbPatientInvestigation.setInvestigatorType("Doctor");
        mbPatientInvestigation.setPrn(patientId + "");
        pc.saveDoctorRecommendation(mbPatientInvestigation);
        
        Map<String, String> mb1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", mb1NextTask.get("nextTask"));
        
        //------------- MBDoctor Approval 2 -------------------------------
        PatientInvestigationBean mb2PatientInvestigation=new PatientInvestigationBean(); 
        mb2PatientInvestigation.setComments("Can be treated");
        mb2PatientInvestigation.setInvestigatorId("4");
        mb2PatientInvestigation.setInvestigatorType("Doctor");
        mb2PatientInvestigation.setPrn(patientId + "");
        pc.saveDoctorRecommendation(mb2PatientInvestigation);
        
        Map<String, String> mb2NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Secretary Approval", mb2NextTask.get("nextTask"));
        
        //--------------- Secretary Approval --------------------------------
        PatientInvestigationBean secPatientInvestigation=new PatientInvestigationBean(); 
        secPatientInvestigation.setComments("Not Satisfied,Forward to EC");
        secPatientInvestigation.setInvestigatorId("5");
        secPatientInvestigation.setInvestigatorType("Secretary");
        secPatientInvestigation.setPrn(patientId + "");
        pc.saveSecretaryRecommendation(secPatientInvestigation, "Recommend", getAuth());
        
        Map<String, String> secNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("EC Approval", secNextTask.get("nextTask"));
        
        //--------------- EC Approval 1--------------------------------
        PatientInvestigationBean ec1PatientInvestigation=new PatientInvestigationBean(); 
        ec1PatientInvestigation.setComments("Approving treatment");
        ec1PatientInvestigation.setInvestigatorId("3");
        ec1PatientInvestigation.setInvestigatorType("Executive Committee");
        ec1PatientInvestigation.setPrn(patientId + "");
        pc.saveExecutiveBoardRecommendationAccept(ec1PatientInvestigation, getAuth("ec1"));
        
        Map<String, String> ec1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("EC Approval", ec1NextTask.get("nextTask"));
        
        //--------------- EC Approval 2--------------------------------
        PatientInvestigationBean ec2PatientInvestigation=new PatientInvestigationBean(); 
        ec2PatientInvestigation.setComments("Approving treatment");
        ec2PatientInvestigation.setInvestigatorId("4");
        ec2PatientInvestigation.setInvestigatorType("Executive Committee");
        ec2PatientInvestigation.setPrn(patientId + "");
        pc.saveExecutiveBoardRecommendationAccept(ec2PatientInvestigation, getAuth("ec2"));
        
        Map<String, String> ec2NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("EC Approval", ec2NextTask.get("nextTask"));
        
        
        //--------------- EC Approval 3--------------------------------
        PatientInvestigationBean ec3PatientInvestigation=new PatientInvestigationBean(); 
        ec3PatientInvestigation.setComments("Rejecting treatment");
        ec3PatientInvestigation.setInvestigatorId("5");
        ec3PatientInvestigation.setInvestigatorType("Executive Committee");
        ec3PatientInvestigation.setPrn(patientId + "");
        pc.saveExecutiveBoardRecommendationReject(ec3PatientInvestigation, getAuth("ec3"));
        
        Map<String, String> ec3NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("EC Approval", ec3NextTask.get("nextTask"));
        
        
        //--------------- EC Approval 4--------------------------------
        PatientInvestigationBean ec4PatientInvestigation=new PatientInvestigationBean(); 
        ec4PatientInvestigation.setComments("Approving treatment by EC4");
        ec4PatientInvestigation.setInvestigatorId("6");
        ec4PatientInvestigation.setInvestigatorType("Executive Committee");
        ec4PatientInvestigation.setPrn(patientId + "");
        pc.saveExecutiveBoardRecommendationAccept(ec4PatientInvestigation, getAuth("ec4"));
        
        Map<String, String> ec4NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("Patient ID Card Generation", ec4NextTask.get("nextTask"));
        
        //--------------- PatientIDCardGeneration --------------------------------
        pc.savePatientIDCard(ec4PatientInvestigation);
        Map<String, String> patIdNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("", patIdNextTask.get("nextTask"));
             
    }

    @Test
    public void testSaveTillBgCheck() throws IOException {

        PatientBean patient = new PatientBean();
        PatientFamilyBean patientFamily = new PatientFamilyBean();
        SupportOrganisationBean supportOrganisation = new SupportOrganisationBean();
        PatientDocumentBean patientDocument = new PatientDocumentBean();

        List<PatientFamilyBean> patientFamilyList = new ArrayList<PatientFamilyBean>();
        List<SupportOrganisationBean> organisationList = new ArrayList<SupportOrganisationBean>();
        List<PatientDocumentBean> documentList = new ArrayList<PatientDocumentBean>();

        patient.setName("Rahul");
        patient.setAddress("kochi");
        patient.setGender("male");
        patient.setSolebreadwinner(true);
        patient.setEmploymentStatus("Unemployed");

        patientFamily.setRelation("Father");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patientFamily = new PatientFamilyBean();
        patientFamily.setRelation("Mother");
        patientFamily.setStatus("Dependent");
        patientFamily.setPrn(patient.getPrn());
        patientFamilyList.add(patientFamily);
        patient.setPatientFamily(patientFamilyList);

        supportOrganisation.setName("Sunrise");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        supportOrganisation = new SupportOrganisationBean();
        supportOrganisation.setName("MedicalCare");
        supportOrganisation.setPrn(patient.getPrn());
        organisationList.add(supportOrganisation);
        patient.setOrganisation(organisationList);

        patientDocument.setDocCategory("AgeProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(patient.getPrn() + "");
        documentList.add(patientDocument);
        patient.setDocument(documentList);

        pc.save(patient);

        assertTrue(patient.getName().equals("Rahul"));
        assertTrue(patient.getGender().equals("male"));

        int count = 0;
        for (PatientFamilyBean p : patient.getPatientFamily()) {
            count++;
            assertTrue(p.getRelation().equals("Father") || p.getRelation().equals("Mother"));
        }
        assertEquals(2, count);

        assertTrue(patient.getOrganisation().get(0).getName().equals("Sunrise"));
        assertEquals(2, patient.getOrganisation().size());

        assertTrue(patient.getDocument().get(1).getDocCategory().equals("AddressProof"));
        assertEquals(2, patient.getDocument().size());
        
        //----------- Preliminary Examination ---------------------------------------
        
        Integer patientId = patient.getPrn();
        System.out.println("######### Patient Id is " + patientId);
        
        PatientDocumentBean preExamPatientDocument = new PatientDocumentBean();
        PatientInvestigationBean preExamPatientInvestigation=new PatientInvestigationBean(); 
        List<PatientDocumentBean> preExamDocumentList = new ArrayList<PatientDocumentBean>();
        preExamPatientDocument.setDocCategory("ScanReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        preExamPatientDocument = new PatientDocumentBean();
        preExamPatientDocument.setDocCategory("BloodTestReport");
        preExamPatientDocument.setPrn(patientId + "");
        preExamDocumentList.add(preExamPatientDocument);
        
        preExamPatientInvestigation.setComments("Blood Cancer");
        preExamPatientInvestigation.setInvestigatorType("Doctor");
        preExamPatientInvestigation.setInvestigatorId("1");
        preExamPatientInvestigation.setPrn(patientId + "");
        
        PatientDocumentAndInvestigationBean patientDocumentAndInvestigationBean = new PatientDocumentAndInvestigationBean();
        patientDocumentAndInvestigationBean.setPatientInvestigationBean(preExamPatientInvestigation);
        patientDocumentAndInvestigationBean.setPatientDocumentBean(preExamDocumentList);
        pc.saveExamination(patientDocumentAndInvestigationBean);
        
        Map<String, String> patRegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("BackgroundCheck", patRegNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bgPatientInvestigation=new PatientInvestigationBean(); 
        bgPatientInvestigation.setComments("Seems genuine");
        bgPatientInvestigation.setInvestigatorId("2");
        bgPatientInvestigation.setInvestigatorType("Program Coordinator");
        bgPatientInvestigation.setPrn(patientId + "");
        pc.saveBGC(bgPatientInvestigation, "PASS", getAuth());
        
        Map<String, String> bgNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MB Doctor Approval", bgNextTask.get("nextTask"));   
        
    }
    
    @Test
    public void testSaveExamination() throws IOException{
        
        PatientDocumentBean patientDocument = new PatientDocumentBean();
        PatientInvestigationBean patientInvestigation=new PatientInvestigationBean(); 
        List<PatientDocumentBean> documentList = new ArrayList<PatientDocumentBean>();
        patientDocument.setDocCategory("AgeProof");
        patientDocument.setPrn(1);
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(1);
        documentList.add(patientDocument);
        
        patientInvestigation.setComments("Blood Cancer");
        patientInvestigation.setInvestigatorType("Doctor");
        patientInvestigation.setInvestigatorId(1);
        patientInvestigation.setPrn(1);
        
        pc.saveExamination(patientInvestigation, documentList);
        
        assertTrue(patientInvestigation.getComments().equals("Blood Cancer"));
        
        assertTrue(documentList.get(1).getDocCategory().equals("AddressProof"));
        assertEquals(2, documentList.size());    
    }
    @Test
    public void testSaveBGC() throws IOException {
        PatientInvestigationBean patientInvestigation=new PatientInvestigationBean(); 
        patientInvestigation.setComments("Blood Cancer");
        patientInvestigation.setInvestigatorId(1);
        patientInvestigation.setPrn(1);
        pc.saveBGC(patientInvestigation, "PASS");
        assertTrue(patientInvestigation.getComments().equals("Blood Cancer"));
        
    }
    @Test
    public void testSaveDoctorRecommendation() throws IOException {
        PatientInvestigationBean patientInvestigation=new PatientInvestigationBean(); 
        patientInvestigation.setComments("Blood Cancer");
        patientInvestigation.setInvestigatorId(1);
        patientInvestigation.setPrn(1);
        pc.saveDoctorRecommendation(patientInvestigation);
        assertTrue(patientInvestigation.getComments().equals("Blood Cancer"));
    }
    @Test
    public void testSaveSecretaryRecommendation() throws IOException{
        PatientInvestigationBean patientInvestigation=new PatientInvestigationBean(); 
        patientInvestigation.setComments("Blood Cancer");
        patientInvestigation.setInvestigatorId(1);
        patientInvestigation.setPrn(1);
        pc.saveSecretaryRecommendation(patientInvestigation, "PASS");
        assertTrue(patientInvestigation.getComments().equals("Blood Cancer"));
    }
    @Test
    public void testSaveExecutiveBoardRecommendationAccept() throws IOException{
        PatientInvestigationBean patientInvestigation=new PatientInvestigationBean(); 
        patientInvestigation.setComments("Blood Cancer");
        patientInvestigation.setInvestigatorId(1);
        patientInvestigation.setPrn(1);
        pc.saveExecutiveBoardRecommendationAccept(patientInvestigation);
        assertTrue(patientInvestigation.getComments().equals("Blood Cancer"));
    }
    @Test
    public void testSaveExecutiveBoardRecommendationReject() throws IOException {
        PatientInvestigationBean patientInvestigation=new PatientInvestigationBean(); 
        patientInvestigation.setComments("Blood Cancer");
        patientInvestigation.setInvestigatorId(1);
        patientInvestigation.setPrn(1);
        pc.saveExecutiveBoardRecommendationReject(patientInvestigation);
        assertTrue(patientInvestigation.getComments().equals("Blood Cancer"));
    }
    @Test
    public void testSavePatientIDCard() throws IOException{
        PatientInvestigationBean patientInvestigation=new PatientInvestigationBean(); 
        patientInvestigation.setComments("Blood Cancer");
        patientInvestigation.setInvestigatorId(1);
        patientInvestigation.setPrn(1);
        pc.savePatientIDCard(patientInvestigation);
        assertTrue(patientInvestigation.getComments().equals("Blood Cancer"));
    }
    
}*/