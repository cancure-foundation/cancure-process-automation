package org.cancure.cpa.service;

import java.util.Set;

import org.cancure.cpa.persistence.entity.User;

public class SMSNotifier implements Notifier {

	@Override
	public void notify(Set<User> userSet, String message) {
		
		System.out.println("########## Sending SMS to ?? with the message " + message);
	}

}
