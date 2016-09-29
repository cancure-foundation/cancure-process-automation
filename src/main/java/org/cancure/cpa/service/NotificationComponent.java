package org.cancure.cpa.service;

import java.util.ArrayList;
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
import org.springframework.context.ApplicationContext;

public class NotificationComponent {

	private UserRepository userRepository;

	private List<Notifier> taskListeners = new ArrayList<>();

	public NotificationComponent(){
		taskListeners.add(new EmailNotifier());
		taskListeners.add(new SMSNotifier());
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		userRepository = ctx.getBean(UserRepository.class);
	}

	public void notify(String message, String role, Task delegateTask) {

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

		sendNotification(userSet, message);

	}

	protected void sendNotification(Set<User> userSet, String message) {
		if (taskListeners != null && !taskListeners.isEmpty()) {
			for (Notifier notifier : taskListeners) {
				notifier.notify(userSet, message);
			}
		}
	}
}
