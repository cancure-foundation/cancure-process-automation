package org.cancure.cpa.service;

import java.util.ArrayList;
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
	
	public void notifyHpoc(List<UserBean> hpocList, Integer pidn) {
		Set<User> userSet = new HashSet<>();
		
		for (UserBean ub : hpocList){
			User targetBean = new User();
			BeanUtils.copyProperties(ub, targetBean);
			userSet.add(targetBean);
		}
		
		Patient pat = patientRepo.findByPidn(pidn).get(0);
		String patName = pat.getName();
		
		StringBuilder message = new StringBuilder();
		message.append("<div style='border : 2px solid #f4961c;'>"
                + "<div style='background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;'>"
                + "Cancure Foundation</div>"
                + "<div style='padding:15px;color: #222d32;font-weight:500;'> "
                + "Hi, <br><br>"
                + "<b>The following task been assigned to you.</b> <br> <br>"
                + "<table border=1 style='border-collapse: collapse;'>"
                + "<tr>"
                + "<th style='padding:4px 8px;'> PIDN</th>"
                + "<th style='padding:4px 8px;'> Patient Name</th>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding:4px 8px;'>"+ pidn +"</td>"
                + "<td style='padding:4px 8px;'>"+ patName +"</td>"
                + "</tr>"
                + "</table><br>"
                + "Visit <a href='www.cancure.in.net'>www.cancure.in.net</a> <br> <br>"
                + "<b>Thanks,</b> <br>"
                + "Admin"
                + "</div>"
                + "</div>");
		
		sendNotification(userSet, "monis to do", null);
	}
	
	public void notifySecretary(Integer pidn) {
	
		Set<User> userSet = new HashSet<>();
		Iterable<User> userList = userRepository.findByUserRole("ROLE_SECRETARY");
		for (User u : userList) {
			userSet.add(u);
		}
		
		Patient pat = patientRepo.findByPidn(pidn).get(0);
		String patName = pat.getName();
		
		StringBuilder message = new StringBuilder();
		message.append("<div style='border : 2px solid #f4961c;'>"
                + "<div style='background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;'>"
                + "Cancure Foundation</div>"
                + "<div style='padding:15px;color: #222d32;font-weight:500;'> "
                + "Hi, <br><br>"
                + "<b>You have received a request for approving additional amount to a patient.</b> <br> <br>"
                + "<table border=1 style='border-collapse: collapse;'>"
                + "<tr>"
                + "<th style='padding:4px 8px;'> PIDN</th>"
                + "<th style='padding:4px 8px;'> Patient Name</th>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding:4px 8px;'>"+ pidn +"</td>"
                + "<td style='padding:4px 8px;'>"+ patName +"</td>"
                + "</tr>"
                + "</table><br>"
                + "Visit <a href='www.cancure.in.net'>www.cancure.in.net</a> <br> <br>"
                + "<b>Thanks,</b> <br>"
                + "Admin"
                + "</div>"
                + "</div>");
		
		sendNotification(userSet, "monis to do", null);
	}

	protected void sendNotification(Set<User> userSet, String messageId, Map<String, Object> values ) {
		try {
			new EmailNotifier().notify(userSet, messageId, null);
			new SMSNotifier().notify(userSet, messageId, null);
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
			
			StringBuilder message = new StringBuilder();
			message.append("<div style='border : 2px solid #f4961c;'>"
	                + "<div style='background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;'>"
	                + "Cancure Foundation</div>"
	                + "<div style='padding:15px;color: #222d32;font-weight:500;'> "
	                + "Hi, <br><br>"
	                + "<b>A patient has been referred to you.</b> <br> <br>"
	                + "<table border=1 style='border-collapse: collapse;'>"
	                + "<tr>"
	                + "<th style='padding:4px 8px;'> PIDN</th>"
	                + "<th style='padding:4px 8px;'> Patient Name</th>"
	                + "</tr>"
	                + "<tr>"
	                + "<td style='padding:4px 8px;'>"+ pidn +"</td>"
	                + "<td style='padding:4px 8px;'>"+ patName +"</td>"
	                + "</tr>"
	                + "</table><br>"
	                + "Visit <a href='www.cancure.in.net'>www.cancure.in.net</a> <br> <br>"
	                + "<b>Thanks,</b> <br>"
	                + "Admin"
	                + "</div>"
	                + "</div>");
			
			sendNotification(userSet, "monis to do", null);
			
		}
	}
}
