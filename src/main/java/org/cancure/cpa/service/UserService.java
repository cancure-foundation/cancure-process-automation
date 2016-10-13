package org.cancure.cpa.service;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.persistence.entity.User;


public interface UserService {

	UserBean saveUser(User user);
	Iterable<UserBean> listUsers();
	Iterable<Role> listRoles();
	UserBean getUser(Integer id);
	UserBean getUserByLogin(String login);
    Iterable<UserBean> listHPOCUsers();
    UserBean resetPassword(Integer id, Boolean resetPassword);
    String forgotPassword(UserBean userBean);
}
