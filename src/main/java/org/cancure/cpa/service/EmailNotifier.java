package org.cancure.cpa.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.ActivitiException;
import org.apache.commons.mail.EmailAttachment;
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
		notify(userSet, messageId, values, null);
	}
	
	@Override
	public void notify(Set<User> userSet, String messageId, Map<String, Object> values, List<String> attachmentPaths) throws Exception {
		
		if (userSet == null || userSet.isEmpty()){
			Log.getLogger().warn("User set is empty. No one to email or SMS.");
			return;
		}
		
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		
		SettingsRepository settingsRepo = context.getBean(SettingsRepository.class);
		String enabled = settingsRepo.findOne(33).getValue(); //'Email Enabled
		
		if (!Boolean.valueOf(enabled)){
			return;
		}
		
		List<Settings> settingsTemplateList = settingsRepo.findByDisplayName(messageId + "_email");
		if (settingsTemplateList == null || settingsTemplateList.isEmpty()) {
			Log.getLogger().error("Email template not found for " + messageId + "_email.");
			return;
		}
		
		String message = TemplateUtil.process(settingsTemplateList.get(0).getValue(), values);
				
		String host = settingsRepo.findOne(34).getValue(); //email.server
		String port = settingsRepo.findOne(35).getValue(); //Email Port
		String from = settingsRepo.findOne(36).getValue(); //Email From
		String password = settingsRepo.findOne(37).getValue(); //Email Password
		
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
			
			if (attachmentPaths != null) {
				attachFiles(email, attachmentPaths);
			}
			
			email.send();
		} catch (EmailException e) {
			Log.getLogger().error(e);
			throw new ActivitiException("Could not send e-mail:" + e.getMessage(), e);
		}
	}

	private void attachFiles(HtmlEmail email, List<String> attachmentPaths) throws EmailException {

		for (String path : attachmentPaths) {
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(path);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			File file = new File(path);
			String aName = file.getName();
			attachment.setDescription(aName);
			attachment.setName(aName);
			email.attach(attachment);
		}
		
	}

}
