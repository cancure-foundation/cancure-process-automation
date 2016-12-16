package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.Patient;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.PatientRepository;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.cancure.cpa.util.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientHospitalVisitNotificationComponent {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PatientRepository patientRepo;
	
	@Autowired
	private PpocPharmacyService ppocService;
	
	@Autowired
	private LpocLabService lpocService;
	
	private List<Notifier> taskListeners = new ArrayList<>();
	
	public void notifySecretary(Integer pidn) {
	
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
        		
		sendNotification(userSet, "PatientHospitalVisitSecretaryNotification", values);
	}

	protected void sendNotification(Set<User> userSet, String messageId, Map<String, Object> values ) {
		try {
			new EmailNotifier().notify(userSet, messageId, values);
			new SMSNotifier().notify(userSet, messageId, values);
		} catch (Exception e) {
			Log.getLogger().error("Exception while notification", e);
		}
	}

	public void notifyPartner(Integer accountTypeId, Integer accountHolderId, Integer pidn) {
		List<UserBean> userList = null;
		if (accountTypeId == 3) {
			userList = ppocService.getPpocUsersFromPharmacy(accountHolderId);
		} else if (accountTypeId == 4) { // Lab
			userList = lpocService.getLpocUsersFromLab(accountHolderId);
		} else {
			// Unsupported
		}
		
		if (userList != null) {
			Set<User> userSet = new HashSet<>();
			for (UserBean ub : userList){
				User targetBean = new User();
				BeanUtils.copyProperties(ub, targetBean);
				userSet.add(targetBean);
			}
			
			Patient pat = patientRepo.findByPidn(pidn).get(0);
			String patName = pat.getName();
			
			Map<String, Object> values = new HashMap<>();
	        values.put("pidn", pidn);
	        values.put("patName", patName);
	        
			sendNotification(userSet, "PatientHospitalVisitPartnerNotification", values);
			
		}
	}
}
