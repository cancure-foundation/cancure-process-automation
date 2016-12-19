package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cancure.cpa.persistence.entity.InvoicesEntity;
import org.cancure.cpa.persistence.entity.Patient;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.PatientRepository;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.cancure.cpa.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceNotificationComponent {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PatientRepository patientRepo;
	
	public void notifySecretary(InvoicesEntity entity, String accountHolderName) {
		Integer pidn = entity.getPidn();
		Set<User> userSet = new HashSet<>();
		Iterable<User> userList = userRepository.findByUserRole("ROLE_SECRETARY");
		for (User u : userList) {
			userSet.add(u);
		}
		
		Patient pat = patientRepo.findByPidn(pidn).get(0);
		String patName = pat.getName();
		
		Map<String, Object> values = new HashMap<>();
        values.put("pidn", pidn);
        values.put("patName", patName);
        values.put("accountHolderName", accountHolderName);
        values.put("amount", entity.getAmount());
        values.put("date", entity.getDate());
		
		
		try {
			sendNotification(userSet, "InvoiceSecretaryNotification", values);
		} catch (Exception e) {
			Log.getLogger().error("Error sending notification", e);
		}
	}

	protected void sendNotification(Set<User> userSet, String messageId, Map<String, Object> values) throws Exception {
		
		try {
			new EmailNotifier().notify(userSet, messageId, values);
			new SMSNotifier().notify(userSet, messageId, values);
		} catch (Exception e) {
			Log.getLogger().error("Exception while notification", e);
		}
	}

}
