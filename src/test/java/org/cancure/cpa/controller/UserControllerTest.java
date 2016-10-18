package org.cancure.cpa.controller;

import org.cancure.cpa.Application;
import org.cancure.cpa.persistence.repository.RoleRepository;
import org.cancure.cpa.persistence.repository.UserRepository;
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
	
	/*@Test
	public void testAddUser() {
		
		userRepo.deleteAll();
		
		User user = new User();
		user.setEnabled(true);
		user.setPassword("1234");
		user.setLogin("admin");
		user.setName("Admin");
		
		UserBean addedUser = uc.saveUser(user);
		
		assertEquals(addedUser.getEnabled(), user.getEnabled());
		assertTrue(addedUser.getEnabled());
		assertTrue(addedUser.getId() != null);
	}*/

	/*@Test
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
		
		Iterable<UserBean> list = uc.listUsers();
		
		int count=0;
		for (UserBean u : list) {
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
		
		UserBean addedUser = uc.saveUser(user);
		Integer userId = addedUser.getId();
		
		UserBean addedUserFull = uc.getUser(userId);
		
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
		
		User userAdded = new User();
		BeanUtils.copyProperties(addedUserFull, userAdded);
		uc.saveUser(userAdded);
		
		UserBean editedUser = uc.getUser(userId);
		
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
	}*/

}
