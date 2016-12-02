package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.controller.beans.PatientVisitForwardsBean;
import org.cancure.cpa.controller.beans.PharmacyDispatchHistoryBean;
import org.cancure.cpa.controller.beans.PharmacyInvoiceBean;

public interface PharmacyDispatchService {

	public List<PatientVisitForwardsBean> searchForwards(Integer accountTypeId, Integer accountHolderId, String status);

	public PharmacyDispatchHistoryBean searchPharmacyDispatchHistory(Integer patientVisitId, Integer myUserId) throws Exception;
	
	public Integer saveInvoice(PharmacyInvoiceBean bean, Integer myUserId) throws Exception;

	public List<PatientVisitForwardsBean> searchForwardsByPidn(Integer pidn, Integer myUserId) throws Exception;
}
