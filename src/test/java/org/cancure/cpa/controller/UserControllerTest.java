package org.cancure.cpa.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.cancure.cpa.Application;
import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.RoleRepository;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserControllerTest {

	@Autowired
	private UserController uc;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Test
	public void testAddUser() {
		
		userRepo.deleteAll();
		
		User user = new User();
		user.setEnabled(true);
		user.setPassword("1234");
		user.setLogin("admin");
		user.setName("Admin");
		
		User addedUser = uc.saveUser(user);
		
		assertEquals(addedUser.getEnabled(), user.getEnabled());
		assertTrue(addedUser.getEnabled());
		assertTrue(addedUser.getId() != null);
	}

	@Test
	public void testListUsers() {
		userRepo.deleteAll();
		
		User user = new User();
		user.setEnabled(true);
		user.setPassword("1234");
		user.setLogin("admin1");
		user.setName("Admin1");
		uc.saveUser(user);
		
		user = new User();
		user.setEnabled(false);
		user.setPassword("1234");
		user.setLogin("pc1");
		user.setName("PC1");
		uc.saveUser(user);
		
		Iterable<User> list = uc.listUsers();
		
		int count=0;
		for (User u : list) {
			count++;
			assertTrue( u.getName().equals("Admin1") || u.getName().equals("PC1") );
		}
		
		assertEquals(2, count);
	}

	@Test
	public void testUpdateUser() {
		
		userRepo.deleteAll();
		
		User user = new User();
		user.setEnabled(true);
		user.setPassword("1234");
		user.setLogin("admin1");
		user.setName("Admin1");
		
		User addedUser = uc.saveUser(user);
		Integer userId = addedUser.getId();
		
		User addedUserFull = uc.getUser(userId);
		
		// Create Role
		roleRepo.deleteAll();		
		Role role = new Role();
		role.setName("Admin");		
		Role savedRole = roleRepo.save(role);
		//---------------
		
		Set<Role> roles = new HashSet<>();
		roles.add(savedRole);
		addedUserFull.setRoles(roles);
		
		addedUserFull.setName("Modified");
		
		uc.saveUser(addedUserFull);
		
		User editedUser = uc.getUser(userId);
		
		assertEquals(userId, editedUser.getId());
		assertEquals(1, editedUser.getRoles().size());
		assertEquals("Admin", editedUser.getRoles().toArray(new Role[0])[0].getName());
		assertEquals("admin1", editedUser.getLogin());
		assertEquals("Modified", editedUser.getName());
	}

	@Test
	public void testListRoles() {
		roleRepo.deleteAll();
		
		Role role = new Role();
		role.setName("Admin");
		
		roleRepo.save(role);
		
		Iterable<Role> allRoles = uc.listRoles();
		int count=0;
		for (Role u : allRoles) {
			count++;
			assertTrue( u.getName().equals("Admin") );
		}
		
		assertEquals(1, count);
	}

}
