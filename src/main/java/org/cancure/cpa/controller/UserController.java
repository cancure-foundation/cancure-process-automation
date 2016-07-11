package org.cancure.cpa.controller;

import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/user/save")
	public User addUser(@RequestBody User user) {
		return userService.addUser (user);
	}

	@RequestMapping("/user/list")
	public Iterable<User> listUsers() {
		return userService.listUsers ();
	}

	@RequestMapping("/user/update")
	public User updateUser(@RequestBody User user) {
		return userService.updateUser (user);

	}

	@RequestMapping("/roles")
	public Iterable<Role> listRoles() {
		return userService.listRoles ();
	}

}
