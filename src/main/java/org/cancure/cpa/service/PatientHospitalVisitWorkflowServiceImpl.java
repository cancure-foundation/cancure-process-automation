package org.cancure.cpa.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.PatientApprovalBean;
import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitDocumentBean;
import org.cancure.cpa.controller.beans.PatientVisitForwardsBean;
import org.cancure.cpa.controller.beans.PatientVisitHistoryBean;
import org.cancure.cpa.controller.beans.TopupStatusBean;
import org.cancure.cpa.controller.beans.UserBean;
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
	
	@Autowired
	private PatientHospitalVisitNotificationComponent notifier;
	
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
		patientVisitBean.setAccountHolderId(hospitalId + "");
		
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

		if ("TRUE".equals(patientVisitBean.getTopupNeeded())) {
			notifier.notifySecretary(pidn);
		}
		
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
		AccountTypes at = new AccountTypes();
		at.setId(5);
		at.setName("Hospital");
		patientVisit.setAccountTypes(at);
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
					AccountTypes at = new AccountTypes();
					at.setId(b.getApprovedForAccountTypeId());
					approval.setApprovedForAccountType(at);

					approvalRepository.save(approval);
				}
			}
		}

		// Move to next task
		Map<String, Object> activitiVars = new HashMap<String, Object>();
		activitiVars.put("topupApproved", topupApproved);
		String taskId = patientHospitalVisitService.moveToNextTask(pidn + "", patientVisitId, activitiVars);
		PatientVisit patVisit = patientVisitRepository.findOne(patientVisitId);
		
		Integer hospitalId = patVisit.getAccountHolderId();
		List<UserBean> hpocList = hpocHospitalService.getHpocUsersFromHospital(hospitalId);
		notifier.notifyHpoc(hpocList, pidn);
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

		String taskId = patientHospitalVisitService.moveToNextTask(pidn.toString() + "", patientVisitId, null);
		
		// Notify
		for (PatientVisitForwardsBean forward : forwardList) {
			pidn = forward.getPidn();
			notifier.notifyPartner(forward.getAccountTypeId(), forward.getAccountHolderId(), pidn);
		}
		
		return taskId;
	}

	@Override
	@Transactional
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
	@Transactional
	public PatientVisitHistoryBean searchByPatientVisitId(String patientVisitId) {
		PatientVisit patientVisitEntity = patientVisitRepository.findOne(Integer.parseInt(patientVisitId));
		Integer pidn = patientVisitEntity.getPidn();
		
		Map<String, String> task = patientHospitalVisitService.findTask(pidn + "_" + patientVisitEntity.getId());
		PatientVisitBean patVisitBean = transformPatientVisitEntityToBean(patientVisitEntity);
		PatientVisitHistoryBean bean = selectPatient(pidn.toString());
		bean.setPatientVisit(patVisitBean);
		bean.setTask(task);
		return bean;
	}

	
	private PatientVisitBean transformPatientVisitEntityToBean(PatientVisit entity) {
		PatientVisitBean bean = new PatientVisitBean();
		bean.setAccountHolderId(entity.getAccountHolderId().toString());
		bean.setAccountTypeId(entity.getAccountTypes().getId().toString());
		bean.setDate(entity.getDate());
		bean.setId(entity.getId().longValue());
		
		List<PatientVisitDocuments> docList = entity.getPatientVisitDocumentsList();
		if (docList != null && !docList.isEmpty()) {
			List<PatientVisitDocumentBean> docBeanList = new ArrayList<>();
			for (PatientVisitDocuments doc : docList) {
				PatientVisitDocumentBean docBean = new PatientVisitDocumentBean();
				docBean.setAccountTypeId(doc.getAccountTypes().getId());
				docBean.setDocId(doc.getDocId().longValue());
				docBean.setDocPath(doc.getDocPath());
				docBean.setDocType(doc.getDocType());
				
				docBeanList.add(docBean);
			}
			
			bean.setPatientHospitalVisitDocumentBeanList(docBeanList);
		}
		
		
		bean.setPidn(entity.getPidn());
		bean.setTaskId(entity.getTaskId());
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
