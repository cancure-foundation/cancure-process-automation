package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

	private UserRepository userRepository;
	private List<Notifier> taskListeners = new ArrayList<>();
	
	public NotifierService(){
		taskListeners.add(new EmailNotifier());
		taskListeners.add(new SMSNotifier());
		
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		userRepository = ctx.getBean(UserRepository.class);
	}
	
	@Override
	public void notify(DelegateTask delegateTask) {
		TaskEntity task = (TaskEntity) delegateTask;
		String assignee = task.getAssignee();
		List<IdentityLinkEntity> identityLinks = task.getIdentityLinks();

		//Object var = task.getVariable("preliminaryExamHospitalId");
		
		StringBuffer message = new StringBuffer("");
		message.append("You have a new assigned task. <br/><br/>");
		message.append(task.getName());
		
		Set<User> userSet = new HashSet<>();
		
		for (IdentityLinkEntity link : identityLinks) {
			if (link.getType().equals(IdentityLinkType.CANDIDATE)) {
				
				if (link.isGroup()) {
					String role = link.getGroupId();
					Iterable<User> userList = userRepository.findByUserRole(role);
					userList.forEach( x -> userSet.add(x));
				}
				
			}
		}
		
		sendNotification(userSet, message.toString());
	}
	
	protected void sendNotification(Set<User> userSet, String message) {
		if (taskListeners != null && !taskListeners.isEmpty()) {
			for (Notifier notifier : taskListeners) {
				notifier.notify(userSet, message);
			}
		}
	}
}
