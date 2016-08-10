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
        new File("d:\\Database\\" + id).mkdirs();
        List<PatientDocumentBean> temp = new ArrayList<>();
        temp = patientBean.getDocument();

        
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
        for (PatientDocumentBean a : temp) {
            PatientDocument patientDocument = new PatientDocument();
            if (a.getPatientFile() != null) {
                File file = new File("d:/Database/" + id + "/" + a.getPatientFile().getOriginalFilename());
                a.getPatientFile().transferTo(file);
                a.setDocPath("d:/Database/" + id + "/" + a.getPatientFile().getOriginalFilename());
            }
            BeanUtils.copyProperties(a, patientDocument);
            patientDocument.setPrn(id);
            patientDocumentRepo.save(patientDocument);
        }
        
	    return patientBean;
	}

	@Override
	public PatientBean get(Integer id) {
		Patient patient = patientRepo.findOne(id);
		PatientBean patientBean = new PatientBean();
		BeanUtils.copyProperties(patient, patientBean);
		return patientBean;
	}

	@Override
    public Iterable<Patient> searchByName(String name) {
        return patientRepo.findByNameContainingIgnoreCase("%" + name + "%");
    }
	
}
