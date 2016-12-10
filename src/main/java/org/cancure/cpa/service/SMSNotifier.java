package org.cancure.cpa.service;

import java.util.Set;

import org.cancure.cpa.persistence.entity.User;

public class SMSNotifier implements Notifier {

	@Override
	public void notify(Set<User> userSet, String message) {
		
		for (User u : userSet) {
			System.out.println("########## Sending SMS to " + u.getPhone() + " with the message " + message);
		}
	}

}
