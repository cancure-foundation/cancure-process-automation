package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitDocumentBean;
import org.cancure.cpa.controller.beans.PatientVisitForwardsBean;
import org.cancure.cpa.controller.beans.PharmacyDispatchHistoryBean;
import org.cancure.cpa.persistence.entity.AccountTypes;
import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.cancure.cpa.persistence.entity.LpocLab;
import org.cancure.cpa.persistence.entity.PatientApproval;
import org.cancure.cpa.persistence.entity.PatientVisit;
import org.cancure.cpa.persistence.entity.PatientVisitDocuments;
import org.cancure.cpa.persistence.entity.PatientVisitForwards;
import org.cancure.cpa.persistence.entity.PpocPharmacy;
import org.cancure.cpa.persistence.repository.ApprovalRepository;
import org.cancure.cpa.persistence.repository.InvoicesRepository;
import org.cancure.cpa.persistence.repository.PatientVisitForwardsRepository;
import org.cancure.cpa.persistence.repository.PatientVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PharmacyDispatchServiceImpl {

	@Autowired
	private InvoicesRepository invoicesRepository;
	
	@Autowired
	private PatientVisitForwardsRepository forwardsRepository;

	@Autowired
	private PatientService patientService;

	@Autowired
	private PatientVisitRepository patientVisitRepository;

	@Autowired
	private PpocPharmacyService ppocPharmacyService;

	@Autowired
	private LpocLabService lpocLabService;

	@Autowired
	private PatientVisitForwardsRepository patientVisitForwardsRepository;
	
	@Autowired
	private ApprovalRepository approvalRepository;

	public List<PatientVisitForwardsBean> searchForwards(Integer accountTypeId, Integer accountHolderId,
			String status) {

		List<PatientVisitForwards> forwards = forwardsRepository
				.findByAccountTypeIdAndAccountHolderIdAndStatus(accountTypeId, accountHolderId, status);

		List<PatientVisitForwardsBean> forwardsList = new ArrayList<>();

		if (forwards != null) {
			for (PatientVisitForwards fwd : forwards) {
				List<PatientBean> patient = patientService.searchByPidn(fwd.getPidn());
				if (patient != null && patient.size() == 1) {
					PatientVisitForwardsBean bean = new PatientVisitForwardsBean();
					bean.setDate(fwd.getDate());
					bean.setPidn(fwd.getPidn());
					bean.setPatient(patient.get(0));
					bean.setPatientVisitId(fwd.getPatientVisitId());

					forwardsList.add(bean);
				}
			}
		}

		return forwardsList;
	}

	public PharmacyDispatchHistoryBean searchForward(Integer patientVisitId, Integer myUserId) throws Exception {

		PharmacyDispatchHistoryBean historyBean = new PharmacyDispatchHistoryBean();
		
		// What type of user is this? PPOC or HPOC or LPOC?
		Integer accountTypeId;
		Integer accountHolderId;

		PpocPharmacy pharmacy = ppocPharmacyService.getPharmacyFromPpoc(myUserId);
		if (pharmacy != null) {
			// The user is a PPOC
			accountTypeId = 3; // Pharmacy
			accountHolderId = pharmacy.getPharmacyId();
		} else {
			LpocLab lab = lpocLabService.getLabFromLpoc(myUserId);
			accountTypeId = 4; // Pharmacy
			accountHolderId = lab.getLabId();
		}

		if (accountTypeId == null || accountHolderId == null) {
			throw new Exception("User " + myUserId + " is not a Pharma POC or Lab POC");
		}

		PatientVisit patientVisit = patientVisitRepository.findOne(patientVisitId);
		if (patientVisit != null) {
			PatientVisitBean patientVisitBean = transformPatientEntityToBean(patientVisit, accountTypeId);
			List<PatientVisitForwards> forwards = patientVisitForwardsRepository
					.findByAccountTypeIdAndAccountHolderIdAndPatientVisitId(accountTypeId, accountHolderId,
							patientVisitBean.getId().intValue());
			
			if (forwards != null && forwards.size() == 1) {
				historyBean.setPatientVisitForwards(forwards.get(0));
				historyBean.setPatientVisitBean(patientVisitBean);
			} else {
				// No forwards to this Partner. Just return.
				return historyBean;
			}
			
			// Now find pending amount and past approvals
			
			AccountTypes approvedForAccountType = new AccountTypes();
			approvedForAccountType.setId(accountTypeId);
			List<PatientApproval> patientApprovals = approvalRepository.findByPidnAndApprovedForAccountType(patientVisitBean.getPidn(), approvedForAccountType);
			if (patientApprovals != null && !patientApprovals.isEmpty()) {
				Double totalApprovals = 0d;
				historyBean.setPatientApprovals(patientApprovals);
				for (PatientApproval pa : patientApprovals) {
					totalApprovals += pa.getAmount();
				}
				
				Double totalInvoices = 0d;
				List<InvoicesEntity> invoicesList = invoicesRepository.findByPidnAndFromAccountTypeId(patientVisitBean.getPidn(), approvedForAccountType);
				if (invoicesList != null && !invoicesList.isEmpty()){
					historyBean.setInvoicesList(invoicesList);
					for (InvoicesEntity entity : invoicesList){
						totalInvoices += entity.getAmount();
					}
					
				}
				
				Double balance = totalApprovals - totalInvoices;
				historyBean.setBalance(balance);
			}
		}
		
		return historyBean;
	}
	
	// Transform and filter by account Type Id
	private PatientVisitBean transformPatientEntityToBean(PatientVisit patientVisit, Integer accountTypeId) {
		
		PatientVisitBean patientVisitBean = new PatientVisitBean();
		patientVisitBean.setDate(patientVisit.getDate());
		patientVisitBean.setPidn(patientVisit.getPidn());
		patientVisitBean.setAccountTypeId(patientVisit.getAccountTypes().getId().toString());
		patientVisitBean.setAccountHolderId(patientVisit.getAccountHolderId().toString());
		
		if (patientVisit.getPatientVisitDocumentsList() != null) {
			List<PatientVisitDocumentBean> patDocsBean = new ArrayList<>();
			for (PatientVisitDocuments patDocsEntity : patientVisit.getPatientVisitDocumentsList()) {
				
				// Add only the docs which this account type is allowed to see
				if (accountTypeId == patDocsEntity.getAccountTypes().getId()) { 
					PatientVisitDocumentBean patDocBean = new PatientVisitDocumentBean();
					patDocBean.setAccountTypeId(patDocsEntity.getAccountTypes().getId());
					patDocBean.setDocId(patDocsEntity.getDocId().longValue());
					patDocBean.setDocType(patDocsEntity.getDocType());
					
					patDocsBean.add(patDocBean);
				}
			}
			
			patientVisitBean.setPatientHospitalVisitDocumentBeanList(patDocsBean);
		}
		
		return patientVisitBean;
	}

}
