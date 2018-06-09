package org.cancure.cpa.service;

import javax.servlet.ServletContext;

import org.cancure.cpa.controller.beans.DonationBean;
import org.cancure.cpa.persistence.entity.Donation;

public interface DonationService {

	void saveDonation(DonationBean bean);

	Donation updateDonation(String orderId, String orderStatus, String paymentMode, String email, String statusMessage,
			String failureMessage, String status, String amount, String trackingId) throws Exception;

	void notifyUser(Donation don, ServletContext servletContext) throws Exception;

}
