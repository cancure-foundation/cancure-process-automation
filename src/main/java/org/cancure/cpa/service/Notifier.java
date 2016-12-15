package org.cancure.cpa.service;

import java.util.Map;
import java.util.Set;

import org.cancure.cpa.persistence.entity.User;

/**
 * Notifier to notify interested parties.
 * 
 */
public interface Notifier {

	void notify(Set<User> userSet, String messageId, Map<String, Object> values) throws Exception;
	
}
