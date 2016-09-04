package org.cancure.cpa.service;

import java.util.Set;

import org.activiti.engine.ActivitiException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.util.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

public class EmailNotifier implements Notifier {

	@Override
	public void notify(Set<User> userSet, String message) {
		
		if (userSet == null || userSet.isEmpty()){
			return;
		}
		
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		
		Environment env = context.getBean(Environment.class);
		String enabled = env.getProperty("email.enabled");
		
		if (!Boolean.valueOf(enabled)){
			return;
		}
		
		String host = env.getProperty("email.server");
		String port = env.getProperty("email.port");
		String from = env.getProperty("email.from");
		
		
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
			
			email.send();
		} catch (EmailException e) {
			throw new ActivitiException("Could not send e-mail:" + e.getMessage(), e);
		}
	}

}
