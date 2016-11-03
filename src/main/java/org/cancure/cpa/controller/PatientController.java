package org.cancure.cpa.controller;

import static org.cancure.cpa.common.Constants.PATIENT_REG_PROCESS_DEF_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.persistence.entity.PatientDocument;
import org.cancure.cpa.service.MyTasksService;
import org.cancure.cpa.service.PatientDocumentService;
import org.cancure.cpa.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PatientDocumentService patientDocumentService;
	
	@Autowired
	private MyTasksService mytaskService;
	
	@RequestMapping("/patient/search/{key}/{value}")
	public List<PatientBean> getPatient(@PathVariable("key") String key, @PathVariable("value") String value){
	    List<PatientBean> list = new ArrayList<PatientBean>();
	    if(key.equals("prn")){
	        List<PatientDocument>document=new ArrayList<PatientDocument>();
	        List<PatientDocumentBean>documentBean=new ArrayList<PatientDocumentBean>();
	        PatientBean bean = patientService.get(Integer.parseInt(value));
	        if (bean == null) {
	            return Collections.emptyList();
	        }
	        document=patientDocumentService.findByTaskId(bean.getTaskId());
	        for(PatientDocument patientDocument:document){
	            PatientDocumentBean patientDocumentBean=new PatientDocumentBean();
	            BeanUtils.copyProperties(patientDocument, patientDocumentBean);
	            documentBean.add(patientDocumentBean);
	        }
	        bean.setDocument(documentBean);
	        Map<String, String> map=mytaskService.getNextTask(value, PATIENT_REG_PROCESS_DEF_KEY);
	        String nextTask=map.get("nextTask");
	        bean.setNextTask(nextTask);
	        list.add(bean); 
		}else if(key.equals("name")){
		    list = patientService.searchByName(value);
	        for(PatientBean patienBean:list){
	            Map<String, String> map=mytaskService.getNextTask(patienBean.getPrn().toString(), PATIENT_REG_PROCESS_DEF_KEY);
	            String nextTask=map.get("nextTask");
	            patienBean.setNextTask(nextTask); 
	        }
		}else if(key.equals("pidn")){
		    list = patientService.searchByPidn(Integer.parseInt(value));
		    for(PatientBean patienBean:list){
                Map<String, String> map=mytaskService.getNextTask(patienBean.getPrn().toString(), PATIENT_REG_PROCESS_DEF_KEY);
                String nextTask=map.get("nextTask");
                patienBean.setNextTask(nextTask);
		    }
		}else{
		    list = patientService.searchByAadhar(value); 
	          for(PatientBean patienBean:list){
	                Map<String, String> map=mytaskService.getNextTask(patienBean.getPrn().toString(), PATIENT_REG_PROCESS_DEF_KEY);
	                String nextTask=map.get("nextTask");
	                patienBean.setNextTask(nextTask);
	          }
		}	    	    
	    return list;
	}
	
}