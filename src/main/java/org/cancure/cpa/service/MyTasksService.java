package org.cancure.cpa.service;

import java.util.List;
import java.util.Map;

public interface MyTasksService {

	/**
	 * Return the tasks pending in this role's queue.
	 * @param role
	 * @return
	 */
	Map<String, List<Map<String, String>>> getMyTasks(List<String> roles, Integer myUserId);
	
	Map<String, String> getNextTask(String prn, String processKey);
	
	Map<String, Object> getTaskHistory(String patientId, String processKey);
}
