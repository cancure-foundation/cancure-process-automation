package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.PatientApprovalBean;
import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.PatientVisitDocumentBean;
import org.cancure.cpa.controller.beans.PatientVisitForwardsBean;
import org.cancure.cpa.controller.beans.PharmacyDispatchHistoryBean;
import org.cancure.cpa.controller.beans.PharmacyInvoiceBean;
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
public class PharmacyDispatchServiceImpl implements PharmacyDispatchService {

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

	// This is a fishing expedition. May not be a good idea.
	@Override
	public List<PatientVisitForwardsBean> searchForwards(Integer accountTypeId, Integer accountHolderId,
			String status) {

		AccountTypes approvedForAccountType = new AccountTypes();
		approvedForAccountType.setId(accountTypeId);
		
		List<PatientVisitForwards> forwards = forwardsRepository
				.findByAccountTypeIdAndAccountHolderIdAndStatus(approvedForAccountType, accountHolderId, status);

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
	
	@Override
	public List<PatientVisitForwardsBean> searchForwardsByPidn(Integer pidn, Integer myUserId) throws Exception {

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
			accountTypeId = 4; // Lab
			accountHolderId = lab.getLabId();
		}

		if (accountTypeId == null || accountHolderId == null) {
			throw new Exception("User " + myUserId + " is not a Pharma POC or Lab POC");
		}
				
		AccountTypes approvedForAccountType = new AccountTypes();
		approvedForAccountType.setId(accountTypeId);
		
		List<PatientVisitForwards> forwards = forwardsRepository
				.findByAccountTypeIdAndAccountHolderIdAndPidn(approvedForAccountType, accountHolderId, pidn);

		List<PatientVisitForwardsBean> forwardsList = new ArrayList<>();

		if (forwards != null && !forwards.isEmpty()) {
			// All are same patient
			List<PatientBean> patient = patientService.searchByPidn(pidn);
			
			for (PatientVisitForwards fwd : forwards) {
				
				PatientVisitForwardsBean bean = new PatientVisitForwardsBean();
				bean.setDate(fwd.getDate());
				bean.setPidn(fwd.getPidn());
				bean.setPatient(patient.get(0));
				bean.setPatientVisitId(fwd.getPatientVisitId());

				forwardsList.add(bean);
				
			}
		}

		return forwardsList;
	}
	

	@Override
	@Transactional
	public PharmacyDispatchHistoryBean searchPharmacyDispatchHistory(Integer patientVisitId, Integer myUserId) throws Exception {

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
			accountTypeId = 4; // Lab
			accountHolderId = lab.getLabId();
		}

		if (accountTypeId == null || accountHolderId == null) {
			throw new Exception("User " + myUserId + " is not a Pharma POC or Lab POC");
		}

		PatientVisit patientVisit = patientVisitRepository.findOne(patientVisitId);
		if (patientVisit != null) {
			AccountTypes approvedForAccountType = new AccountTypes();
			approvedForAccountType.setId(accountTypeId);
			
			PatientVisitBean patientVisitBean = transformPatientEntityToBean(patientVisit, accountTypeId);
			List<PatientVisitForwards> forwards = patientVisitForwardsRepository
					.findByAccountTypeIdAndAccountHolderIdAndPatientVisitId(approvedForAccountType, accountHolderId,
							patientVisitBean.getId().intValue());
			
			if (forwards != null && forwards.size() == 1) {
				
				PatientVisitForwards fwdEntity = forwards.get(0);
				
				PatientVisitForwardsBean fwdBean = new PatientVisitForwardsBean();
				fwdBean.setAccountHolderId(fwdEntity.getAccountHolderId());
				fwdBean.setAccountTypeId(fwdEntity.getAccountTypeId().getId());
				fwdBean.setAccountTypeName(fwdEntity.getAccountTypeId().getName());
				fwdBean.setDate(fwdEntity.getDate());
				fwdBean.setId(fwdEntity.getId());
				fwdBean.setPidn(fwdEntity.getPidn());
				fwdBean.setPatientVisitId(fwdEntity.getPatientVisitId());
				
				historyBean.setPatientVisitForwards(fwdBean);
				historyBean.setPatientVisitBean(patientVisitBean);
			} else {
				// No forwards to this Partner. Just return.
				return historyBean;
			}
			
			List<PatientBean> patient = patientService.searchByPidn(patientVisit.getPidn());
			if (patient != null && patient.size() == 1) {
				historyBean.setPatient(patient.get(0));
			}
			
			// Now find pending amount and past approvals
			
			
			List<PatientApproval> patientApprovals = approvalRepository.findByPidnAndApprovedForAccountType(patientVisitBean.getPidn(), approvedForAccountType);
			if (patientApprovals != null && !patientApprovals.isEmpty()) {
				Double totalApprovals = 0d;
				List<PatientApprovalBean> paBeanList = new ArrayList<>();
				
				for (PatientApproval pa : patientApprovals) {
					
					PatientApprovalBean paBean = new PatientApprovalBean();
					paBean.setAmount(pa.getAmount());
					paBean.setApprovedForAccountTypeId(pa.getApprovedForAccountType().getId());
					paBean.setApprovedForAccountTypeName(pa.getApprovedForAccountType().getName());
					paBean.setDate(pa.getDate());
					paBean.setExpiryDate(pa.getExpiryDate());
					paBean.setId(pa.getId());
					paBean.setPatientVisitId(pa.getPatientVisitId());
					paBean.setPidn(pa.getPidn().toString());
					//paBean.setStatus(pa.get);
					
					paBeanList.add(paBean);
					
					totalApprovals += pa.getAmount();
				}
				
				historyBean.setPatientApprovals(paBeanList);
				
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
		patientVisitBean.setId(patientVisit.getId().longValue());
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

	@Transactional
	public Integer saveInvoice(PharmacyInvoiceBean bean, Integer myUserId) throws Exception {
		
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
			accountTypeId = 4; // Lab
			accountHolderId = lab.getLabId();
		}

		if (accountTypeId == null || accountHolderId == null) {
			throw new Exception("User " + myUserId + " is not a Pharma POC or Lab POC");
		}
		
		InvoicesEntity entity = new InvoicesEntity();
		entity.setAmount(bean.getAmount());
		entity.setBalanceAmount(bean.getAmount());
		entity.setPartnerBillAmount(bean.getPartnerBillAmount());
		entity.setPartnerBillNo(bean.getPartnerBillNo());
		entity.setComments(bean.getComments());
		entity.setPidn(bean.getPidn());
		entity.setStatus("open");
		
		entity.setToAccountHolderId(1); // To cancure
		AccountTypes toAccountType = new AccountTypes();
		toAccountType.setId(1);
		entity.setToAccountTypeId(toAccountType);
		
		entity.setFromAccountHolderId(accountHolderId);
		AccountTypes approvedForAccountType = new AccountTypes();
		approvedForAccountType.setId(accountTypeId);
		entity.setFromAccountTypeId(approvedForAccountType);
		
		entity = invoicesRepository.save(entity);
		
		return entity.getId();
	}
}
