package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLinkType;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.cancure.cpa.util.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;

/**
 * Notifies users when a task is created for them.
 * 
 */
public class NotifierService implements TaskListener {

	private List<Notifier> taskListeners = new ArrayList<>();
	
	public NotifierService(){
		taskListeners.add(new EmailNotifier());
		taskListeners.add(new SMSNotifier());
	}
	
	@Override
	public void notify(DelegateTask delegateTask) {
		
		TaskEntity task = (TaskEntity) delegateTask;
		Map vars = task.getVariables();

		String patName = (String)vars.get("patientName");
		Integer prn = (Integer)vars.get("prn");
		StringBuffer message = new StringBuffer("");
		message.append("Hi, <br><br>"
		        + "A task has been assigned to you.<br> "
		        + "<b>Patient Name : <b>" + patName + "<br>"
				+ "<b>PRN :</b> " + prn + "<br>"
				+ "<b>Task to do :</b> " + task.getName() + "<br><br>"
				+ "Visit <a href=http://www.cancure.in.net>www.cancure.in.net</a><br><br>"
				+ "Thanks, <br/>Cancure");
		new NotificationComponent().notify(message.toString(), null, task);
		
	}
}
