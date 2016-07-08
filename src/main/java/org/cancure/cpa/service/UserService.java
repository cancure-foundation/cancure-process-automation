package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.User;


public interface UserService {

	User addUser(User user);
	public Iterable<User> listUsers();


}
