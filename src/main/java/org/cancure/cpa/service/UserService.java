package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.persistence.entity.User;


public interface UserService {

	public User addUser(User user);
	public Iterable<User> listUsers();
	public User updateUser(User user);
	public Iterable<Role> listRoles();


}
