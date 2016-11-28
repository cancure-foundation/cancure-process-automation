package org.cancure.cpa.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.activiti.engine.task.Task;
import org.cancure.cpa.controller.beans.PatientApprovalBean;
import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitDocumentBean;
import org.cancure.cpa.controller.beans.PatientVisitForwardsBean;
import org.cancure.cpa.controller.beans.PatientVisitHistoryBean;
import org.cancure.cpa.controller.beans.TopupStatusBean;
import org.cancure.cpa.persistence.entity.AccountTypes;
import org.cancure.cpa.persistence.entity.HpocHospital;
import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.cancure.cpa.persistence.entity.PatientApproval;
import org.cancure.cpa.persistence.entity.PatientVisit;
import org.cancure.cpa.persistence.entity.PatientVisitDocuments;
import org.cancure.cpa.persistence.entity.PatientVisitForwards;
import org.cancure.cpa.persistence.entity.Pharmacy;
import org.cancure.cpa.persistence.repository.ApprovalRepository;
import org.cancure.cpa.persistence.repository.InvoicesRepository;
import org.cancure.cpa.persistence.repository.PatientVisitForwardsRepository;
import org.cancure.cpa.persistence.repository.PatientVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PatientHospitalVisitWorkflowServiceImpl implements PatientHospitalVisitWorkflowService {

	@Autowired
	private PatientHospitalVisitService patientHospitalVisitService;

	@Autowired
	private PatientVisitRepository patientVisitRepository;

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private ApprovalRepository approvalRepository;

	@Autowired
	private InvoicesRepository invoicesRepository;
	
	@Autowired
	private PatientVisitForwardsRepository patientVisitForwardsRepository;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
    private HpocHospitalService hpocHospitalService;
	
	@Value("${spring.files.save.path}")
	private String fileSavePath;

	@Override
	@Transactional
	public String startWorkflow(PatientVisitBean patientVisitBean, Integer myUserId) throws IOException {

		Integer pidn = patientVisitBean.getPidn();
		List<PatientBean> patientInDbList = patientService.searchByPidn(pidn);
		PatientBean patientInDb = patientInDbList.get(0);
		
		// Find the hospital of this HPOC.
		HpocHospital hpocHosMapping = hpocHospitalService.getHospitalFromHpoc(myUserId);
		Integer hospitalId = hpocHosMapping.getHospitalId();
		
		PatientVisit patientVisit = transformPatientBeanToEntity(patientVisitBean);

		patientVisitRepository.save(patientVisit);

		savePatientDocs(patientVisit, patientVisitBean);

		
		Map<String, Object> variables = new HashMap<>();
		variables.put("pidn", pidn);
		variables.put("patientName", patientInDb.getName());
		variables.put("patientVisitId", patientVisit.getId());
		variables.put("hospitalId", hospitalId);

		patientHospitalVisitService.startPatientHospitalVisitWorkflow(variables, pidn + "", patientVisit.getId());

		// Move to next task
		Map<String, Object> activitiVars = new HashMap<String, Object>();
		activitiVars.put("topupNeeded", patientVisitBean.getTopupNeeded());
		String taskId = patientHospitalVisitService.moveToNextTask(pidn + "", patientVisit.getId(), activitiVars);

		patientVisit.setTaskId(taskId);
		patientVisitRepository.save(patientVisit);

		return patientVisit.getId() + "";
	}

	private void savePatientDocs(PatientVisit patientVisit, PatientVisitBean patientVisitBean) throws IOException {

		List<PatientVisitDocuments> docList = patientVisit.getPatientVisitDocumentsList();

		if (docList != null && !docList.isEmpty()) {
			Integer patientVisitId = patientVisit.getId();
			new File(fileSavePath + "/patientVisit/" + patientVisitId).mkdirs();
			for (int i = 0; i < docList.size(); i++) {

				PatientVisitDocuments patDocs = docList.get(i);
				String originalFileName = patientVisitBean.getPatientHospitalVisitDocumentBeanList().get(i)
						.getPatientVisitFile().getOriginalFilename();

				String docPath = "/patientVisit/" + patientVisitId + "/" + patDocs.getDocId() + "_" + originalFileName;

				File file = new File(fileSavePath + docPath);
				patientVisitBean.getPatientHospitalVisitDocumentBeanList().get(i).getPatientVisitFile()
						.transferTo(file);
				patDocs.setDocPath(docPath);

			}
		}
	}

	private PatientVisit transformPatientBeanToEntity(PatientVisitBean patientVisitBean) {
		PatientVisit patientVisit = new PatientVisit();
		patientVisit.setDate(new Timestamp(System.currentTimeMillis()));
		patientVisit.setPidn(patientVisitBean.getPidn());
		patientVisit.setAccountTypes(new AccountTypes(5, "Hospital"));
		patientVisit.setAccountHolderId(Integer.parseInt(patientVisitBean.getAccountHolderId()));

		if (patientVisitBean.getPatientHospitalVisitDocumentBeanList() != null) {
			List<PatientVisitDocuments> patientVisitDocList = new ArrayList<>();
			for (PatientVisitDocumentBean bean : patientVisitBean.getPatientHospitalVisitDocumentBeanList()) {
				PatientVisitDocuments patientVisitDocumentBean = new PatientVisitDocuments();
				AccountTypes actType = new AccountTypes();
				actType.setId(bean.getAccountTypeId());
				patientVisitDocumentBean.setAccountTypes(actType);
				patientVisitDocumentBean.setDocType(bean.getDocType());
				patientVisitDocumentBean.setPatientVisit(patientVisit);
				patientVisitDocList.add(patientVisitDocumentBean);
			}

			patientVisit.setPatientVisitDocumentsList(patientVisitDocList);
		}

		return patientVisit;
	}

	@Override
	@Transactional
	public String topUpApprovedAmount(TopupStatusBean topupBean) {
		// Save topups for Pharma and Lab
		Integer pidn = topupBean.getPidn();
		Integer patientVisitId = topupBean.getPatientVisitId();
		String topupApproved = topupBean.getStatus();

		if ("TRUE".equals(topupApproved)) {
			List<PatientApprovalBean> approvalBean = topupBean.getPatientApproval();
			if (approvalBean != null && !approvalBean.isEmpty()) {
				for (PatientApprovalBean b : approvalBean) {
					PatientApproval approval = new PatientApproval();
					approval.setAmount(b.getAmount());
					approval.setDate(new Timestamp(System.currentTimeMillis()));
					approval.setPatientVisitId(patientVisitId);
					approval.setPidn(pidn);
					AccountTypes approvedForAccountType = new AccountTypes();
					approvedForAccountType.setId(b.getApprovedForAccountTypeId());
					approval.setApprovedForAccountType(approvedForAccountType);

					approvalRepository.save(approval);
				}
			}
		}

		// Move to next task
		Map<String, Object> activitiVars = new HashMap<String, Object>();
		activitiVars.put("topupApproved", topupApproved);
		String taskId = patientHospitalVisitService.moveToNextTask(pidn + "", patientVisitId, activitiVars);
		return taskId;
	}

	@Override
	@Transactional
	public String selectPartners(List<PatientVisitForwardsBean> forwardList) throws Exception {
		Integer pidn = null;
		Integer patientVisitId = null;
		if (forwardList != null && !forwardList.isEmpty()) {
			for (PatientVisitForwardsBean forward : forwardList) {
				pidn = forward.getPidn();
				patientVisitId = forward.getPatientVisitId();
				// save referred Pharma.
				PatientVisitForwards fwd = new PatientVisitForwards();
				fwd.setAccountHolderId(forward.getAccountHolderId());
				fwd.setDate(new Timestamp(System.currentTimeMillis()));
				fwd.setPatientVisitId(forward.getPatientVisitId());
				AccountTypes actType = new AccountTypes();
				actType.setId(forward.getAccountTypeId());
				fwd.setAccountTypeId(actType);
				fwd.setPidn(forward.getPidn());
				fwd.setStatus("open");
				
				patientVisitForwardsRepository.save(fwd);
			}
		} else {
			throw new Exception("No forwards present");
		}

		return patientHospitalVisitService.moveToNextTask(pidn.toString() + "", patientVisitId, null);
	}

	@Override
	public PatientVisitHistoryBean selectPatient(String pidnString) {
		Integer pidn = Integer.parseInt(pidnString);
		List<PatientBean> list = new ArrayList<PatientBean>();
		list = patientService.searchByPidn(pidn);
		if (list != null && !list.isEmpty()) {
			PatientVisitHistoryBean historyBean = new PatientVisitHistoryBean();
			PatientBean patientBean = list.get(0);
			historyBean.setPatientBean(patientBean);
			
			List<PatientApproval> patientApprovals = approvalRepository.findByPidn(pidn);
			if (patientApprovals != null && !patientApprovals.isEmpty()) {
				historyBean.setPatientApprovals(patientApprovals);
				List<InvoicesEntity> invoicesList = invoicesRepository.findByPidn(pidn);
				if (invoicesList != null && !invoicesList.isEmpty()){
					for (InvoicesEntity entity : invoicesList){
						entity.setFromAccountHolderName(getPartnerName(entity.getFromAccountTypeId().getId(), entity.getFromAccountHolderId()));
					}
					historyBean.setInvoicesList(invoicesList);
				}
			}
			
			return historyBean;
		}
		
		return null;
	}


	@Override
	public PatientVisitHistoryBean searchByPatientVisitId(String patientVisitId) {
		PatientVisit patientVisitBean = patientVisitRepository.findOne(Integer.parseInt(patientVisitId));
		Integer pidn = patientVisitBean.getPidn();
		
		Map<String, String> task = patientHospitalVisitService.findTask(pidn + "_" + patientVisitBean.getId());
		
		PatientVisitHistoryBean bean = selectPatient(pidn.toString());
		bean.setPatientVisit(patientVisitBean);
		bean.setTask(task);
		return bean;
	}

	
	private String getPartnerName(Integer fromAccountTypeId, Integer fromAccountHolderId) {
		if (fromAccountTypeId == 3) { //Pharmacy
			Pharmacy phar = pharmacyService.getPharmacy(fromAccountHolderId);
			if (phar == null){
				return "Unknown Pharmacy";
			}
			return phar.getName();
		}
		
		return "";
	}

}