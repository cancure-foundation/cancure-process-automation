package org.cancure.cpa.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.Application;
import org.cancure.cpa.common.Constants;
import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientFamilyBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;
import org.cancure.cpa.controller.beans.SupportOrganisationBean;
import org.cancure.cpa.persistence.entity.Patient;
import org.cancure.cpa.persistence.entity.PatientFamily;
import org.cancure.cpa.persistence.repository.PatientDocumentRepository;
import org.cancure.cpa.persistence.repository.PatientFamilyRepository;
import org.cancure.cpa.persistence.repository.PatientInvestigationRepository;
import org.cancure.cpa.persistence.repository.PatientRepository;
import org.cancure.cpa.persistence.repository.SupportOrganisationRepository;
import org.cancure.cpa.service.MyTasksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PatientWorkFlowControllerTest {
    
    @Autowired
    private PatientWorkFlowController pc;
    
    @Autowired
    private MyTasksService myTasksService;
    
    /*@Autowired
    private PatientRepository patientRepo;
    
    @Autowired
    private PatientFamilyRepository patientFamilyRepo;
    
    @Autowired
    private SupportOrganisationRepository SupportOrganisationRepo;
    
    @Autowired
    private PatientDocumentRepository PatientDocumentRepo;
    
    @Autowired
    private PatientInvestigationRepository PatientInvestigationRepo;
    */
    
    @Test
    public void testSave() throws IOException {

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
        patient.setAge(23);
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
        patientDocument.setPrn(patient.getPrn());
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(patient.getPrn());
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
        preExamPatientDocument.setPrn(patientId);
        preExamDocumentList.add(preExamPatientDocument);
        preExamPatientDocument = new PatientDocumentBean();
        preExamPatientDocument.setDocCategory("BloodTestReport");
        preExamPatientDocument.setPrn(patientId);
        preExamDocumentList.add(preExamPatientDocument);
        
        preExamPatientInvestigation.setComments("Blood Cancer");
        preExamPatientInvestigation.setInvestigatorType("Doctor");
        preExamPatientInvestigation.setInvestigatorId(1);
        preExamPatientInvestigation.setPrn(patientId);
        
        pc.saveExamination(preExamPatientInvestigation, preExamDocumentList);
        
        Map<String, String> patRegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("BackgroundCheck", patRegNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bgPatientInvestigation=new PatientInvestigationBean(); 
        bgPatientInvestigation.setComments("Seems genuine");
        bgPatientInvestigation.setInvestigatorId(2);
        bgPatientInvestigation.setInvestigatorType("Program Coordinator");
        bgPatientInvestigation.setPrn(patientId);
        pc.saveBGC(bgPatientInvestigation, "PASS");
        
        Map<String, String> bgNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MBDoctorApproval", bgNextTask.get("nextTask"));
        
      //------------- MBDoctor Approval 1 -------------------------------
        PatientInvestigationBean mbPatientInvestigation=new PatientInvestigationBean(); 
        mbPatientInvestigation.setComments("Can be treated");
        mbPatientInvestigation.setInvestigatorId(3);
        mbPatientInvestigation.setInvestigatorType("Doctor");
        mbPatientInvestigation.setPrn(patientId);
        pc.saveDoctorRecommendation(mbPatientInvestigation);
        
        Map<String, String> mb1NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MBDoctorApproval", mb1NextTask.get("nextTask"));
        
        //------------- MBDoctor Approval 2 -------------------------------
        PatientInvestigationBean mb2PatientInvestigation=new PatientInvestigationBean(); 
        mb2PatientInvestigation.setComments("Can be treated");
        mb2PatientInvestigation.setInvestigatorId(4);
        mb2PatientInvestigation.setInvestigatorType("Doctor");
        mb2PatientInvestigation.setPrn(patientId);
        pc.saveDoctorRecommendation(mb2PatientInvestigation);
        
        Map<String, String> mb2NextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("SecretaryApproval", mb2NextTask.get("nextTask"));
        
        //--------------- Secretary Approval --------------------------------
        PatientInvestigationBean secPatientInvestigation=new PatientInvestigationBean(); 
        secPatientInvestigation.setComments("Approving treatment");
        secPatientInvestigation.setInvestigatorId(5);
        secPatientInvestigation.setInvestigatorType("Secretary");
        secPatientInvestigation.setPrn(patientId);
        pc.saveSecretaryRecommendation(secPatientInvestigation, "Approved");
        
        Map<String, String> secNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("PatientIDCardGeneration", secNextTask.get("nextTask"));
        
        //--------------- PatientIDCardGeneration --------------------------------
        pc.savePatientIDCard(patientId);
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
        patient.setAge(23);
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
        patientDocument.setPrn(patient.getPrn());
        documentList.add(patientDocument);
        patientDocument = new PatientDocumentBean();
        patientDocument.setDocCategory("AddressProof");
        patientDocument.setPrn(patient.getPrn());
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
        preExamPatientDocument.setPrn(patientId);
        preExamDocumentList.add(preExamPatientDocument);
        preExamPatientDocument = new PatientDocumentBean();
        preExamPatientDocument.setDocCategory("BloodTestReport");
        preExamPatientDocument.setPrn(patientId);
        preExamDocumentList.add(preExamPatientDocument);
        
        preExamPatientInvestigation.setComments("Blood Cancer");
        preExamPatientInvestigation.setInvestigatorType("Doctor");
        preExamPatientInvestigation.setInvestigatorId(1);
        preExamPatientInvestigation.setPrn(patientId);
        
        pc.saveExamination(preExamPatientInvestigation, preExamDocumentList);
        
        Map<String, String> patRegNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("BackgroundCheck", patRegNextTask.get("nextTask"));
        
        //------------- Background Check -------------------------------
        PatientInvestigationBean bgPatientInvestigation=new PatientInvestigationBean(); 
        bgPatientInvestigation.setComments("Seems genuine");
        bgPatientInvestigation.setInvestigatorId(2);
        bgPatientInvestigation.setInvestigatorType("Program Coordinator");
        bgPatientInvestigation.setPrn(patientId);
        pc.saveBGC(bgPatientInvestigation, "PASS");
        
        Map<String, String> bgNextTask = myTasksService.getNextTask(patientId + "", Constants.PATIENT_REG_PROCESS_DEF_KEY);
        assertEquals("MBDoctorApproval", bgNextTask.get("nextTask"));   
        
    }
    
    
   /* @Test
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
    }*/
    
}
