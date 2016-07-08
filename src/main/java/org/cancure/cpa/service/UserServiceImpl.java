package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	public Iterable<User> listUsers() {
		return userRepo.findAll();
	}


}
