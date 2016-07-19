package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.RoleRepository;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;

	public User addUser(User user) {
		return userRepo.save(user);
	}

	public Iterable<User> listUsers() {
		return userRepo.findAll();
	}

	public Iterable<Role> listRoles() {
		return roleRepo.findAll();
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public void setRoleRepo(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}
	
}
