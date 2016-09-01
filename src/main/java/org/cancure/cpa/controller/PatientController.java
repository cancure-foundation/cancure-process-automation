package org.cancure.cpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.cancure.cpa.common.Constants.PATIENT_REG_PROCESS_DEF_KEY;
import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.persistence.entity.Patient;
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
	
	@RequestMapping("/patient/{id}")
	public List<PatientBean> getPatient(@PathVariable("id") Integer id){
		List<PatientBean> list = new ArrayList<PatientBean>();
		List<PatientDocument>document=new ArrayList<PatientDocument>();
		List<PatientDocumentBean>documentBean=new ArrayList<PatientDocumentBean>();
	    PatientBean bean = patientService.get(id);
	    document=patientDocumentService.findByTaskId(bean.getTaskId());
	    for(PatientDocument patientDocument:document){
	        PatientDocumentBean patientDocumentBean=new PatientDocumentBean();
	        BeanUtils.copyProperties(patientDocument, patientDocumentBean);
	        documentBean.add(patientDocumentBean);
	    }
	    bean.setDocument(documentBean);
	    Map<String, String> map=mytaskService.getNextTask(id.toString(), PATIENT_REG_PROCESS_DEF_KEY);
	    String nextTask=map.get("nextTask");
	    bean.setNextTask(nextTask);
	    if (bean != null){
	        list.add(bean);
	    }
	    
	    return list;
	}
	
	@RequestMapping("/patient/search/{name}")
    public List<PatientBean> getPatient(@PathVariable("name") String searchText){
	    List<PatientBean> patientBeanList= patientService.searchByName(searchText);
	    for(PatientBean patienBean:patientBeanList){
	        Map<String, String> map=mytaskService.getNextTask(patienBean.getPrn().toString(), PATIENT_REG_PROCESS_DEF_KEY);
	        String nextTask=map.get("nextTask");
	        patienBean.setNextTask(nextTask); 
	    }
	    return patientBeanList;
    }

}