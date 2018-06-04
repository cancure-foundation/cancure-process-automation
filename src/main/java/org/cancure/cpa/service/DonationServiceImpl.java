package org.cancure.cpa.service;

import org.cancure.cpa.controller.beans.DonationBean;
import org.cancure.cpa.persistence.entity.Donation;
import org.cancure.cpa.persistence.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DonationServiceImpl implements DonationService {

	@Autowired
	DonationRepository donationRepository;
	
	@Override
	public void saveDonation(DonationBean bean) {
		Donation don = new Donation();
		don.setTransactionId(bean.getTid());
		don.setProductName(bean.getProduct_name());
		don.setProductQuantity(bean.getProduct_quantity());
		don.setProductAmount(bean.getAmount());
		don.setPayerFname(bean.getBilling_name());
		don.setMessage(bean.getNotesValue());
		don.setPayerAddress(bean.getBilling_address());
		don.setPayerCity(bean.getBilling_city());
		don.setPayerState(bean.getBilling_state());
		don.setPayerZip(bean.getBilling_zip());
		don.setPayerCountry(bean.getBilling_country());
		don.setOrganisation(bean.getOrg());
		don.setDesignation(bean.getDesig());
		don.setMobile(bean.getBilling_tel());
		don.setTelo(bean.getTelo());
		don.setTelr(bean.getTelr());
		don.setPayerEmail(bean.getBilling_email());
		don.setStatus("Pending");
		don.setOrderId(bean.getOrder_id());
		
		donationRepository.save(don);
	}

	@Override
	public void updateDonation(String orderId, String orderStatus, String paymentMode, String email, String statusMessage,
			String failureMessage, String status, String amount, String trackingId) {

		Donation don = donationRepository.findByOrderId(orderId);
		if (don != null) {
			don.setOrderStatus(orderStatus);
			don.setPaymentMode(paymentMode);
			don.setPayerEmail(email);
			don.setStatusMessage(statusMessage);
			don.setFailureMessage(failureMessage);
			don.setStatus(status);
			don.setProductAmount(amount);
			don.setTrackingId(trackingId);
			donationRepository.save(don);
		}
	}

}
