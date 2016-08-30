package org.cancure.cpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.persistence.entity.Patient;
import org.cancure.cpa.persistence.entity.PatientDocument;
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
	    if (bean != null){
	        list.add(bean);
	    }
	    
	    return list;
	}
	
	@RequestMapping("/patient/search/{name}")
    public Iterable<Patient> getPatient(@PathVariable("name") String searchText){
        return patientService.searchByName(searchText);
    }

}