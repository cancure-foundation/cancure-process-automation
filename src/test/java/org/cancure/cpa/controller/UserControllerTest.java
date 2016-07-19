package org.cancure.cpa.controller;

import static org.junit.Assert.*;

import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.RoleRepository;
import org.cancure.cpa.persistence.repository.RoleRepositoryDummy;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.cancure.cpa.persistence.repository.UserRepositoryDummy;
import org.cancure.cpa.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class UserControllerTest {

	private UserController uc;
	
	@Before
	public void setup() {
		uc = new UserController();
		
		UserRepository userRepo = new UserRepositoryDummy();
		RoleRepository roleRepo = new RoleRepositoryDummy();
		
		UserServiceImpl userService = new UserServiceImpl();
		userService.setUserRepo(userRepo);
		userService.setRoleRepo(roleRepo);
		
		uc.userService = userService;
	}
	
	@Test
	public void testAddUser() {
		
		User user = new User();
		user.setEnabled(true);
		user.setLogin("admin");
		user.setName("Admin");
		
		uc.addUser(user);
		
		assertEquals(new Integer(1234), user.getId());
	}

	@Test
	public void testListUsers() {
		
		User user = new User();
		user.setEnabled(true);
		user.setLogin("admin");
		user.setName("Admin");
		uc.addUser(user);
		
		user = new User();
		user.setEnabled(false);
		user.setLogin("pc");
		user.setName("PC");
		uc.addUser(user);
		
		Iterable<User> list = uc.listUsers();
		
		int count=0;
		for (User u : list) {
			count++;
			assertTrue( u.getName().equals("Admin") || u.getName().equals("PC") );
		}
		
		assertEquals(2, count);
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
