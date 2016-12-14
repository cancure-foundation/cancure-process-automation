package org.cancure.cpa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.cancure.cpa.Application;
import org.cancure.cpa.controller.beans.PatientApprovalBean;
import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.TopupStatusBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PatientVisitControllerTest {

	@Autowired
	private TaskService taskService;

	@Autowired
	PatientVisitController controller;
	
	@Test
	public void test() throws Exception {
		Integer pidn = 2;
		PatientVisitBean bean = new PatientVisitBean();
		bean.setPidn(pidn);
		bean.setAccountTypeId("5");
		bean.setAccountHolderId("1");
		bean.setTopupNeeded("TRUE");
		String res = controller.startPatientHospitalVisit(bean, getAuth("lissiehpoc"));
		ObjectMapper mapper = new ObjectMapper();
		Map map = mapper.readValue(res, HashMap.class);
		Integer patientVisitId = (Integer)map.get("patientVisitId");
		
		System.out.println(res);
		
		TopupStatusBean topupBean = new TopupStatusBean();
		topupBean.setPidn(2);
		topupBean.setStatus("TRUE");
		topupBean.setPatientVisitId(patientVisitId);
		List<PatientApprovalBean> patientApproval = new ArrayList<>();
		PatientApprovalBean appBean = new PatientApprovalBean();
		appBean.setAmount(10000d);
		appBean.setApprovedForAccountTypeId(3);
		appBean.setPatientVisitId(patientVisitId);
		appBean.setPidn(pidn + "");
		patientApproval.add(appBean);
		topupBean.setPatientApproval(patientApproval);
		
		String status = controller.topUpApprovedAmount(topupBean);
		System.out.println("%% TOPUP Approval done - " + status);
		
		
		/*PatientVisitForwardsMasterBean masterBean = new PatientVisitForwardsMasterBean();
		List<PatientVisitForwardsBean> pvfList = new ArrayList<>();
		PatientVisitForwardsBean pvfBean = new PatientVisitForwardsBean();
		pvfBean.setAccountHolderId(1);
		pvfBean.setAccountTypeId(3);
		pvfBean.setPatientVisitId(patientVisitId);
		pvfBean.setPidn(pidn);
		pvfList.add(pvfBean);
		masterBean.setPatientVisitForwards(pvfList);
		status = controller.selectPartners(masterBean);
		System.out.println("$$$$$$$$$ Select partners done " + status);*/
		
		
	}
	
	/*@Test
	public void testMyTasks() throws Exception {
		
		List<Task> tasks = taskService.createTaskQuery()
				//.processDefinitionKey(Constants.PATIENT_HOSPITAL_VISIT_DEF_KEY)
				.includeProcessVariables().orderByTaskCreateTime().asc()
				.taskCandidateGroupIn(Arrays.asList("ROLE_SECRETARY", "ROLE_HOSPITAL_POC")).list();
		
		if (tasks != null){
			for (Task t : tasks){
				System.out.println(t);
			}
		}
		
		
	}*/

	private OAuth2Authentication getAuth(String login) {
    	Authentication authentication = new UsernamePasswordAuthenticationToken("cancure", "cancure");
    	OAuth2Authentication userAuthentication = new OAuth2Authentication(null, authentication);
        Map<String, String> map = new HashMap<>();
        map.put("username", login);
        userAuthentication.setDetails(map);
        OAuth2Authentication auth = new OAuth2Authentication(null, userAuthentication);
        return auth;
    }
	
	public static void main(String args[]) throws Exception {
		String s =  "{\"status\" : \"SUCCESS\",\"patientVisitId\" :" + 1234+ "}";
		ObjectMapper mapper = new ObjectMapper();
		Map map = mapper.readValue(s, HashMap.class);
	   	System.out.println("#########" + map.get("patientVisitId"));
	}
}
