package org.cancure.cpa.controller;

import static org.junit.Assert.assertEquals;

import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.RoleRepository;
import org.cancure.cpa.persistence.repository.RoleRepositoryDummy;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.cancure.cpa.persistence.repository.UserRepositoryDummy;
import org.cancure.cpa.service.UserServiceImpl;
import org.junit.Test;

public class UserControllerTest {

	@Test
	public void testAddUser() {
		UserController uc = new UserController();
		
		UserRepository userRepo = new UserRepositoryDummy();
		RoleRepository roleRepo = new RoleRepositoryDummy();
		
		UserServiceImpl userService = new UserServiceImpl();
		userService.setUserRepo(userRepo);
		userService.setRoleRepo(roleRepo);
		
		uc.userService = userService;
		
		User user = new User();
		user.setEnabled(true);
		user.setLogin("admin");
		user.setName("Admin");
		
		uc.addUser(user);
		
		assertEquals(new Integer(1234), user.getId());
	}

	@Test
	public void testListUsers() {
		//fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() {
		//fail("Not yet implemented");
	}

	@Test
	public void testListRoles() {
		//fail("Not yet implemented");
	}

}
