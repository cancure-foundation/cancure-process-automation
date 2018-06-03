package org.cancure.cpa.service;

import org.cancure.cpa.controller.beans.DonationBean;

public interface DonationService {

	void saveDonation(DonationBean bean);

	void updateDonation(String orderId, String status, String paymentMode, String email, String statusMessage,
			String failureMessage, String amount, String trackingId);

}
