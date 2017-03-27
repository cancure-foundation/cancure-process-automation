package org.cancure.cpa.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.cancure.cpa.util.ApplicationContextProvider;
import org.cancure.cpa.util.Log;
import org.springframework.context.ApplicationContext;

public class NotificationComponent {

	public void notify(String message, Map<String, Object> values, String role, Task delegateTask) {
		
		try {
			ApplicationContext context = ApplicationContextProvider.getApplicationContext();
			
			UserRepository userRepository = context.getBean(UserRepository.class);

			TaskEntity task = (TaskEntity) delegateTask;
			String assignee = task.getAssignee();
			List<IdentityLinkEntity> identityLinks = task.getIdentityLinks();

			Map vars = task.getVariables();

			Set<User> userSet = new HashSet<>();

			if (role != null) {
				Iterable<User> userList = userRepository.findByUserRole(role);
				for (User u : userList) {
					userSet.add(u);
				}
			} else {

				// If present, mail only assignee.
				Object var = task.getVariable("assignee");
				if (var != null) {
					//Remove the variable. Otherwise it will be available for the next tasks too.
					task.removeVariable("assignee");
					
					String assignees = var.toString();
					String[] assigneesCsv = assignees.split(",");
					for (String id : assigneesCsv) {
						userSet.add(userRepository.findOne(Integer.parseInt(id)));
					}

				} else {
					for (IdentityLinkEntity link : identityLinks) {
						if (link.getType().equals(IdentityLinkType.CANDIDATE)) {

							if (link.isGroup()) {
								String roleOfGroup = link.getGroupId();
								Iterable<User> userList = userRepository.findByUserRole(roleOfGroup);
								for (User u : userList) {
									userSet.add(u);
								}
							}
						}
					}
				}
			}

			sendNotification(userSet, message, values);
		} catch (Exception e) {
			Log.getLogger().error("Exception while notifying ", e );
		}

	}

	protected void sendNotification(Set<User> userSet, String messageId, Map<String, Object> values) throws Exception {
		try {
			new EmailNotifier().notify(userSet, messageId, values);
			new SMSNotifier().notify(userSet, messageId, values);
		} catch (Exception e) {
			Log.getLogger().error("Exception while notification", e);
		}
	}
}
