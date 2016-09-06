package org.cancure.cpa.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientFamilyBean;
import org.cancure.cpa.controller.beans.SupportOrganisationBean;
import org.cancure.cpa.persistence.entity.Patient;
import org.cancure.cpa.persistence.entity.PatientDocument;
import org.cancure.cpa.persistence.entity.PatientFamily;
import org.cancure.cpa.persistence.entity.SupportOrganisation;
import org.cancure.cpa.persistence.repository.PatientDocumentRepository;
import org.cancure.cpa.persistence.repository.PatientFamilyRepository;
import org.cancure.cpa.persistence.repository.PatientRepository;
import org.cancure.cpa.persistence.repository.SupportOrganisationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component("patientService")
public class PatientServiceImpl implements PatientService {
    
    @Autowired
	private PatientRepository patientRepo;
	
	@Autowired
	private PatientDocumentRepository patientDocumentRepo;
	
	@Autowired
	private SupportOrganisationRepository supportOrganisationRepo;
	
	@Autowired
	private PatientFamilyRepository patientFamilyRepo;
	
	@Value("${spring.files.save.path}")
    private String fileSavePath;
	
	
    @Transactional
	@Override
	public PatientBean save(PatientBean patientBean) throws  IOException {
        
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientBean, patient);
        patientRepo.save(patient);  
        patientBean.setPrn(patient.getPrn());
        
        List<PatientFamilyBean> temp4 = new ArrayList<>();
        temp4 = patientBean.getPatientFamily();

        List<SupportOrganisationBean> temp2 = new ArrayList<>();
        temp2 = patientBean.getOrganisation();

        Integer id = patient.getPrn();
        new File(fileSavePath + "/" + id).mkdirs();
        List<PatientDocumentBean> patientDocumentList = new ArrayList<>();
        patientDocumentList = patientBean.getDocument();

        
        for (PatientFamilyBean c : temp4) {

            PatientFamily patientFamily = new PatientFamily();
            BeanUtils.copyProperties(c, patientFamily);          
            patientFamily.setFamilyPatient(patient);
            patientFamilyRepo.save(patientFamily);
        }
        
        for (SupportOrganisationBean b : temp2) {
            SupportOrganisation supportOrganisation = new SupportOrganisation();
            BeanUtils.copyProperties(b, supportOrganisation);
            supportOrganisation.setPatient(patient);
            supportOrganisationRepo.save(supportOrganisation);
        }
        
        for (PatientDocumentBean patientDocBean : patientDocumentList) {
            PatientDocument patientDocument = new PatientDocument();
            
            //Save patient document only if File is present.
            if (patientDocBean.getPatientFile() != null) {
            	BeanUtils.copyProperties(patientDocBean, patientDocument);
                patientDocument.setPrn(id);
                patientDocumentRepo.save(patientDocument);
                
                Integer docId = patientDocument.getDocId();
                
                File file = new File(fileSavePath + "/" + id + "/" + docId + "_" + patientDocBean.getPatientFile().getOriginalFilename());
                patientDocBean.getPatientFile().transferTo(file);
                String docPath = "/" + id + "/" + docId + "_" + patientDocBean.getPatientFile().getOriginalFilename();
                patientDocBean.setDocPath(docPath);
                patientDocBean.setDocId(docId);
                patientDocument.setDocPath(docPath);
                patientDocumentRepo.save(patientDocument);
            }
            
        }
	    return patientBean;
	}
    
    @Transactional
    @Override
    public void savePatientDocuments(List<PatientDocumentBean> patientDocuments) {
        
        for (PatientDocumentBean bean : patientDocuments){
            PatientDocument patientDocument = new PatientDocument();
            BeanUtils.copyProperties(bean, patientDocument);
            patientDocument.setPrn(Integer.parseInt(bean.getPrn()));
            patientDocumentRepo.save(patientDocument);
        }
        
    }

	@Override
	public PatientBean get(Integer id) {
		Patient patient = patientRepo.findOne(id);
		if (patient == null) {
			return null;
		}
		PatientBean patientBean = new PatientBean();
		BeanUtils.copyProperties(patient, patientBean);
		return patientBean;
	}

	@Override
    public List<PatientBean> searchByName(String name) {
	    List<Patient> patientList= patientRepo.findByNameContainingIgnoreCase("%" + name + "%");
	    List<PatientBean> patientBeanList=new ArrayList<>();
	    for(Patient patient:patientList){
	        PatientBean patientBean=new PatientBean();
	        BeanUtils.copyProperties(patient, patientBean);
	        patientBeanList.add(patientBean);
	    }
	    return patientBeanList;
    }
	
	@Override
	public int updateTaskId(String taskId,Integer id){
	    return patientRepo.updateTaskId(taskId, id);
	}

	@Override
	public int updatePidn(Integer pidn, Integer prn) {
		return patientRepo.updatePidn(pidn, prn);
	}
	
}