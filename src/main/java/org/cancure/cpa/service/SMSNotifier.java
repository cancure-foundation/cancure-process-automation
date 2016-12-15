package org.cancure.cpa.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cancure.cpa.persistence.entity.Settings;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.SettingsRepository;
import org.cancure.cpa.util.ApplicationContextProvider;
import org.cancure.cpa.util.Log;
import org.cancure.cpa.util.TemplateUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

public class SMSNotifier implements Notifier {

	@Override
	public void notify(Set<User> userSet, String messageId, Map<String, Object> values) throws Exception {
	
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		
		Environment env = context.getBean(Environment.class);
		
		String enabled = env.getProperty("sms.enabled");
		
		if (!Boolean.valueOf(enabled)){
			return;
		}
		
		SettingsRepository settingsRepo = context.getBean(SettingsRepository.class);
		List<Settings> settingsTemplateList = settingsRepo.findByDisplayName(messageId + "_sms");
		if (settingsTemplateList == null || settingsTemplateList.isEmpty()) {
			Log.getLogger().error("Email template not found for " + messageId + "_sms.");
			return;
		}
		
		String message = TemplateUtil.process(settingsTemplateList.get(0).getValue(), values);
		
		for (User u : userSet) {
			System.out.println("########## Sending SMS to " + u.getPhone() + " with the message " + message);
		}
	}

}
