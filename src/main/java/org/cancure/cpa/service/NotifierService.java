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
		message.append("Hi, <br>A task has been assigned to you. <br>Patient Name : " + patName + 
				"<br>PRN : " + prn + "<br>Task to do : " + task.getName() + 
				"<br>Thanks, <br/>Cancure");
		new NotificationComponent().notify(message.toString(), null, task);
		
	}
}
