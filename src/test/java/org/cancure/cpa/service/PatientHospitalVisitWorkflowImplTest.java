package org.cancure.cpa.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cancure.cpa.Application;
import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitDocumentBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PatientHospitalVisitWorkflowImplTest {

	@Autowired
	private PatientHospitalVisitWorkflowServiceImpl PatientHospitalVisitWorkflowImpl;
	
	@Test
	public void testStartWorkflow() throws IOException {
		PatientVisitBean patientVisitBean = new PatientVisitBean();
		patientVisitBean.setAccountHolderId("1");
		patientVisitBean.setAccountTypeId("5");
		patientVisitBean.setPidn(10);
		
		List<PatientVisitDocumentBean> patientHospitalVisitDocumentBeanList = new ArrayList<>();
		PatientVisitDocumentBean bean1 = new PatientVisitDocumentBean();
		bean1.setAccountTypeId(3);
		bean1.setDocType("Lab Test Prescription");
		patientHospitalVisitDocumentBeanList.add(bean1);
		
		PatientVisitDocumentBean bean2 = new PatientVisitDocumentBean();
		bean2.setAccountTypeId(4);
		bean2.setDocType("Medicine Prescription");
		patientHospitalVisitDocumentBeanList.add(bean2);
		
		patientVisitBean.setPatientHospitalVisitDocumentBeanList(patientHospitalVisitDocumentBeanList);
		
		PatientHospitalVisitWorkflowImpl.startWorkflow(patientVisitBean, 1);
	}

}
