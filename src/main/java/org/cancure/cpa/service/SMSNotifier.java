package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cancure.cpa.SMSSender;
import org.cancure.cpa.persistence.entity.Settings;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.SettingsRepository;
import org.cancure.cpa.util.ApplicationContextProvider;
import org.cancure.cpa.util.Log;
import org.cancure.cpa.util.TemplateUtil;
import org.springframework.context.ApplicationContext;

public class SMSNotifier implements Notifier {

	@Override
	public void notify(Set<User> userSet, String messageId, Map<String, Object> values) throws Exception {
	
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		SettingsRepository settingsRepo = context.getBean(SettingsRepository.class);
		
		/*Environment env = context.getBean(Environment.class);
		
		String enabled = env.getProperty("sms.enabled");
		*/
		
		Settings smsEnabled = settingsRepo.findOne(29); // SMS Enabled?
		if (!Boolean.valueOf(smsEnabled.getValue())){
			return;
		}
		
		
		List<Settings> settingsTemplateList = settingsRepo.findByDisplayName(messageId + "_sms");
		if (settingsTemplateList == null || settingsTemplateList.isEmpty()) {
			Log.getLogger().error("SMS template not found for " + messageId + "_sms.");
			return;
		}
		
		String message = TemplateUtil.process(settingsTemplateList.get(0).getValue(), values);
		
		List<String> phoneList = new ArrayList<>();
		for (User u : userSet) {
			//System.out.println("########## Sending SMS to " + u.getPhone() + " with the message " + message);
			String phoneNo = u.getPhone();
			if (phoneNo != null) {
				if (!phoneNo.startsWith("91")) {
					phoneList.add("91" + phoneNo);
				} else {
					phoneList.add(phoneNo);
				}
			}
		}
		
		Settings smsUsernameSetting = settingsRepo.findOne(30);
		Settings smsHashSetting = settingsRepo.findOne(31);
		
		if (!phoneList.isEmpty()) {
			String phoneCSV = StringUtils.join(phoneList, ",");
			new SMSSender(phoneCSV, message, smsUsernameSetting.getValue(), smsHashSetting.getValue()).start();
		}
	}

}
