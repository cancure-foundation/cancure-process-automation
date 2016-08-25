package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.RoleRepository;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	//@Autowired
	PasswordEncoder encoder = new BCryptPasswordEncoder();
   
	public User saveUser(User user) {
	    String encPass=encoder.encode(user.getPassword());
	    user.setPassword(encPass);
		userRepo.save(user);
		user.setPassword(null);
		return user;
	}
	
	@Override
	public User getUser(Integer id) {
		User user= userRepo.findOne(id);
		user.setPassword(null);
		return user;
	}

	public Iterable<User> listUsers() {
	    
	    Iterable<User> list = userRepo.findAll();
	    list.forEach( x -> x.setPassword(null));
	    return list;
	}

	public Iterable<Role> listRoles() {
		return roleRepo.findAll();
	}

	@Override
	public User getUserByLogin(String login) {
		User user= userRepo.findByLogin(login);
		user.setPassword(null);
		return user;
	}
	
	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public void setRoleRepo(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}

}
