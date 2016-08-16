package org.cancure.cpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.persistence.entity.Patient;
import org.cancure.cpa.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@RequestMapping("/patient/{id}")
	public List<PatientBean> getPatient(@PathVariable("id") Integer id){
		List<PatientBean> list = new ArrayList<PatientBean>();
	    PatientBean bean = patientService.get(id);
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