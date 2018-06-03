package org.cancure.cpa.service;

import org.cancure.cpa.controller.beans.DonationBean;
import org.springframework.stereotype.Component;

@Component
public class DonationServiceImpl implements DonationService {

	@Override
	public void saveDonation(DonationBean bean) {

	}

	@Override
	public void updateDonation(String orderId, String status, String paymentMode, String email, String statusMessage,
			String failureMessage, String amount, String trackingId) {

		
		
	}

}
