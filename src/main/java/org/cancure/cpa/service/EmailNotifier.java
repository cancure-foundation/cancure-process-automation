package org.cancure.cpa.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.ActivitiException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.cancure.cpa.persistence.entity.Settings;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.SettingsRepository;
import org.cancure.cpa.util.ApplicationContextProvider;
import org.cancure.cpa.util.Log;
import org.cancure.cpa.util.TemplateUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

public class EmailNotifier implements Notifier {

	@Override
	public void notify(Set<User> userSet, String messageId, Map<String, Object> values) throws Exception {
		
		if (userSet == null || userSet.isEmpty()){
			Log.getLogger().warn("User set is empty. No one to email or SMS.");
			return;
		}
		
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		
		Environment env = context.getBean(Environment.class);
		String enabled = env.getProperty("email.enabled");
		
		if (!Boolean.valueOf(enabled)){
			return;
		}
		
		SettingsRepository settingsRepo = context.getBean(SettingsRepository.class);
		List<Settings> settingsTemplateList = settingsRepo.findByDisplayName(messageId + "_email");
		if (settingsTemplateList == null || settingsTemplateList.isEmpty()) {
			Log.getLogger().error("Email template not found for " + messageId + "_email.");
			return;
		}
		
		String message = TemplateUtil.process(settingsTemplateList.get(0).getValue(), values);
		
		String host = env.getProperty("email.server");
		String port = env.getProperty("email.port");
		String from = env.getProperty("email.from");
		String password = env.getProperty("email.password");
		
		HtmlEmail email = new HtmlEmail();
		try {
			email.setHtmlMsg(message);
			for (User user : userSet){
				email.addTo(user.getEmail());
			}
			email.setSubject("Cancure Notification");
			email.setHtmlMsg(message);
			email.setHostName(host);
			email.setSmtpPort(Integer.parseInt(port));
			email.setFrom(from);
			if(from!=null && password!=null){
			email.setAuthentication(from, password);
			}
			email.send();
		} catch (EmailException e) {
			Log.getLogger().error(e);
			throw new ActivitiException("Could not send e-mail:" + e.getMessage(), e);
		}
	}

}
