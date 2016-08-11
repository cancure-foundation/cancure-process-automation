package org.cancure.cpa.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;
import org.cancure.cpa.persistence.entity.PatientDocument;
import org.cancure.cpa.persistence.entity.PatientInvestigation;
import org.cancure.cpa.persistence.repository.PatientDocumentRepository;
import org.cancure.cpa.persistence.repository.PatientInvestigationRepository;
import org.cancure.cpa.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("PatientInvestigationService")
public class PatientInvestigationServiceImpl implements PatientInvestigationService {
    @Autowired
    PatientInvestigationRepository patientInvestigationRepo;
    
    @Autowired
    PatientDocumentRepository patientDocumentRepo; 
    
    @Autowired
    private FileUtil fileUtil;
    
    @Value("${spring.files.save.path}")
    private String fileSavePath;
    
   
    @Override
    public String savePatientInvestigation(PatientInvestigationBean patientInvestigationBean,String status) throws IOException {
        PatientInvestigation bean = new PatientInvestigation();
        BeanUtils.copyProperties(patientInvestigationBean, bean);
        if (patientInvestigationBean.getPrn() != null){
        	bean.setPrn(Integer.parseInt(patientInvestigationBean.getPrn()));
        }
        if (patientInvestigationBean.getInvestigatorId() != null) {
        	bean.setInvestigationId(Integer.parseInt(patientInvestigationBean.getInvestigatorId()));
        }
        bean.setStatus(status);
        bean.setInvestigationDate(new Date());
        patientInvestigationRepo.save(bean);
        return "Success";
    }
    
    @Transactional
    @Override
    public String savePatientDocument(List<PatientDocumentBean> patientDocumentBeans) throws IOException {
        if (patientDocumentBeans == null || patientDocumentBeans.size() == 0) {
            return "Success";
        }
        
        for (PatientDocumentBean patDocBean : patientDocumentBeans) {
            PatientDocument bean = new PatientDocument();
            BeanUtils.copyProperties(patDocBean, bean);
            
            if (patDocBean.getPrn() != null){
            	bean.setPrn(Integer.parseInt(patDocBean.getPrn()));
            }
            
            String storePath = null;
            if (patDocBean.getPatientFile() != null) {
                storePath = fileSavePath + "/" +  patDocBean.getPatientFile().getOriginalFilename(); 
                bean.setDocPath(storePath);
            }
            
            patientDocumentRepo.save(bean);
            
            if (patDocBean.getPatientFile() != null) {
                fileUtil.saveFile(storePath, patDocBean.getPatientFile());
            }
        }
        
        return "Success";
    }
    
    @Transactional
    @Override
    public String savePatientExamination(PatientInvestigationBean patientInvestigationBean,
            List<PatientDocumentBean> patientDocumentBean) throws IOException {
        
        savePatientInvestigation(patientInvestigationBean,null);
        savePatientDocument(patientDocumentBean);
        
        return "Success";
    }

    @Override
    public PatientInvestigation findByTaskId(String taskId) {
        
        PatientInvestigation patientInvestigation=patientInvestigationRepo.findByTaskId(taskId);
        return patientInvestigation;
    }

}

