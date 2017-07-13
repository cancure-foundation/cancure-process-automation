package org.cancure.cpa.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cancure.cpa.persistence.entity.User;

/**
 * Notifier to notify interested parties.
 * 
 */
public interface Notifier {

	void notify(Set<User> userSet, String messageId, Map<String, Object> values) throws Exception;

	void notify(Set<User> userSet, String messageId, Map<String, Object> values, List<String> attachmentPaths)
			throws Exception;
	
}
