package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.persistence.entity.User;


public interface UserService {

	User saveUser(User user);
	Iterable<User> listUsers();
	Iterable<Role> listRoles();
	User getUser(Integer id);
	
}
