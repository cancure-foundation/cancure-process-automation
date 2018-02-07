package org.cancure.cpa.controller;

import java.util.Map;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.controller.beans.UserSuperBean;
import org.cancure.cpa.persistence.entity.Role;
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
	public UserSuperBean saveUser(@RequestBody UserSuperBean user) {
		return userService.saveUser (user);
	}

	@RequestMapping("/user/list")
	public Iterable<UserBean> listUsers() {
		return userService.listUsers ();
	}
	
	@RequestMapping("/user/{id}")
	public UserBean getUser(@PathVariable("id") Integer id) {
		return userService.getUser(id);
	}
	
	@RequestMapping("/user/login/{login}")
	public UserBean getUserByLogin(@PathVariable("login") String login) {
		return userService.getUserByLogin(login);
	}
	
	@RequestMapping("/user/whoami")
	public UserBean getUserByLogin(OAuth2Authentication auth) {
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
	
	@RequestMapping("/user/hpoc")
	public Iterable<UserBean> listHPOCUsers() {
	    return userService.listHPOCUsers();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/user/resetpassword/{id}", consumes = "application/json")
    public UserBean resetPassword(@PathVariable("id") Integer id) {
        return userService.resetPassword(id,true);
    }
	
	@RequestMapping(method= RequestMethod.POST, value="/user/forgotpassword")
	public String forgotPassword(@RequestBody UserBean userBean){
	    return userService.forgotPassword(userBean);
	}
	
	@RequestMapping(method= RequestMethod.POST, value="/user/firstlogin/save")
    public String firstLogin(@RequestBody UserSuperBean user){
        return userService.firstLogin(user);
    }
	
	@RequestMapping(method= RequestMethod.POST, value="/user/pushid/save/{id}/{pushId}")
    public UserBean savePushId(@PathVariable("id") Integer id, @PathVariable("pushId") String pushId){
        return userService.savePushId(id, pushId);
    }

}