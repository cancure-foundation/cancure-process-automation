package org.cancure.cpa.service;

import java.util.Set;

import org.activiti.engine.ActivitiException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.cancure.cpa.persistence.entity.User;

public class EmailNotifier implements Notifier {

	@Override
	public void notify(Set<User> userSet, String message) {
		/*HtmlEmail email = new HtmlEmail();
		try {
			email.setHtmlMsg(message);
			email.addTo(user.getEmail()); 
			email.setSubject("Activiti Assignment Notification");
			email.setFrom("noreply@deltafaucet.com");
 
			setMailServerProperties(email);
 
			email.send();
		} catch (EmailException e) {
			throw new ActivitiException("Could not send e-mail:" + e.getMessage(), e);
		}*/
	}

}
