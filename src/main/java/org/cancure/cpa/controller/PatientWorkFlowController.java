package org.cancure.cpa.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentAndInvestigationBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.service.PatientRegistrationWorkflowService;
import org.cancure.cpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientWorkFlowController {
    
    @Autowired
    private PatientRegistrationWorkflowService patientRegistrationWorkflowService;   
  
    @Autowired
	private UserService userService;

    @RequestMapping(value="/patientregistration/patient/save", method=RequestMethod.POST)
    public String save(PatientBean patientbean) throws IOException {
        patientRegistrationWorkflowService.registerPatient(patientbean);
        return "{\"status\" : \"SUCCESS\"}";
    }
    
    @RequestMapping(value= "/patientregistration/preliminaryexamination/save" , method=RequestMethod.POST)
    public String saveExamination(PatientDocumentAndInvestigationBean patientDocumentAndInvestigationBean) throws IOException {
    	
    	PatientInvestigationBean patientInvestigationBean = patientDocumentAndInvestigationBean.getPatientInvestigationBean();
    	List<PatientDocumentBean> patientDocumentBean = patientDocumentAndInvestigationBean.getPatientDocumentBean();
    	
    	patientInvestigationBean.setInvestigatorType("Doctor");
    	
        patientRegistrationWorkflowService.preliminaryExamination(patientInvestigationBean,patientDocumentBean);
        return "{\"status\" : \"SUCCESS\"}";
    }
    
	@RequestMapping(value = "/patientregistration/backgroundcheck/save/{status}", method = RequestMethod.POST)
	public String saveBGC(PatientInvestigationBean patientInvestigationBean, @PathVariable("status") String status,
			OAuth2Authentication auth) throws IOException {
		
		Integer userId = null;
		if (auth != null) {
			String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
			User user = userService.getUserByLogin(login);
			userId = user.getId();
		} else {
			throw new RuntimeException("Not logged in");
		}

		patientInvestigationBean.setInvestigatorId(userId.toString());
		patientInvestigationBean.setInvestigatorType("Program Coordinator");
		patientRegistrationWorkflowService.backGroundCheck(patientInvestigationBean, status);
		return "{\"status\" : \"SUCCESS\"}";
	}
    
    @RequestMapping(value= "/patientregistration/mbdoctorrecommendation/save", method=RequestMethod.POST)
    public String saveDoctorRecommendation(PatientInvestigationBean patientInvestigationBean) throws IOException {
    	
    	patientInvestigationBean.setInvestigatorType("Doctor");
        patientRegistrationWorkflowService.doctorRecommendation(patientInvestigationBean);
        return "{\"status\" : \"SUCCESS\"}";
    }

	@RequestMapping(value = "/patientregistration/secretaryrecommendation/save/{status}", method = RequestMethod.POST)
	public String saveSecretaryRecommendation(PatientInvestigationBean patientInvestigationBean,
			@PathVariable("status") String status, OAuth2Authentication auth) throws IOException {
	
    	Integer userId = null;
		if (auth != null) {
			String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
			User user = userService.getUserByLogin(login);
			userId = user.getId();
		} else {
			throw new RuntimeException("Not logged in");
		}
		
		patientInvestigationBean.setInvestigatorId(userId.toString());
    	patientInvestigationBean.setInvestigatorType("Secretary");
        patientRegistrationWorkflowService.secretaryRecommendation(patientInvestigationBean,status); 
        return "{\"status\" : \"SUCCESS\"}";
    }
    
   @RequestMapping(value= "patientregistration/executiveboardrecommendation/accept/save", method=RequestMethod.POST)
    public String saveExecutiveBoardRecommendationAccept(PatientInvestigationBean patientInvestigationBean, OAuth2Authentication auth) throws IOException {
	   Integer userId = null;
		if (auth != null) {
			String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
			User user = userService.getUserByLogin(login);
			userId = user.getId();
		} else {
			throw new RuntimeException("Not logged in");
		}
		
		patientInvestigationBean.setInvestigatorId(userId.toString());
    	patientInvestigationBean.setInvestigatorType("Executive Committee");
        patientRegistrationWorkflowService.executiveBoardRecommendationAccept(patientInvestigationBean); 
        return "{\"status\" : \"SUCCESS\"}";
    }
   
   @RequestMapping(value= "patientregistration/executiveboardrecommendation/reject/save", method=RequestMethod.POST)
   public String saveExecutiveBoardRecommendationReject(PatientInvestigationBean patientInvestigationBean, OAuth2Authentication auth) throws IOException {
	   Integer userId = null;
		if (auth != null) {
			String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
			User user = userService.getUserByLogin(login);
			userId = user.getId();
		} else {
			throw new RuntimeException("Not logged in");
		}
		
		patientInvestigationBean.setInvestigatorId(userId.toString());
		patientInvestigationBean.setInvestigatorType("Executive Committee");
   	
        patientRegistrationWorkflowService.executiveBoardRecommendationReject(patientInvestigationBean); 
        return "{\"status\" : \"SUCCESS\"}";
   }
   
   @RequestMapping(value= "patientregistration/Patientidcard/{prn}")
   public String savePatientIDCard(@PathVariable("prn") Integer prn) throws IOException {
        patientRegistrationWorkflowService.patientIDCard(prn);
        return "{\"status\" : \"SUCCESS\"}";
   }
    
}
