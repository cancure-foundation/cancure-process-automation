package org.cancure.cpa.controller;

import java.security.Principal;
import java.util.Map;

import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.POST, value = "/user/save", consumes = "application/json")
	public User saveUser(@RequestBody User user) {
		return userService.saveUser (user);
	}

	@RequestMapping("/user/list")
	public Iterable<User> listUsers() {
		return userService.listUsers ();
	}
	
	@RequestMapping("/user/{id}")
	public User getUser(@PathVariable("id") Integer id) {
		return userService.getUser(id);
	}
	
	@RequestMapping("/user/login/{login}")
	public User getUserByLogin(@PathVariable("login") String login) {
		return userService.getUserByLogin(login);
	}
	
	@RequestMapping("/user/whoami")
	public User getUserByLogin(OAuth2Authentication auth) {
		if (auth != null) {
			String login = (String)((Map)auth.getUserAuthentication().getDetails()).get("username");
			return userService.getUserByLogin(login);
		} else {
			throw new RuntimeException("Not logged in");
		}
	}

	@RequestMapping("/roles")
	public Iterable<Role> listRoles() {
		return userService.listRoles ();
	}

}
