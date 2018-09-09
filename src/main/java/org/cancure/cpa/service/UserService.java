package org.cancure.cpa.service;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.controller.beans.UserSuperBean;
import org.cancure.cpa.persistence.entity.Role;


public interface UserService {

    UserSuperBean saveUser(UserSuperBean user);
	Iterable<UserBean> listUsers();
	Iterable<Role> listRoles();
	UserBean getUser(Integer id);
	UserBean getUserByLogin(String login);
    Iterable<UserBean> listHPOCUsers();
    UserBean resetPassword(Integer id, Boolean resetPassword);
    String forgotPassword(UserBean userBean);
    String firstLogin(UserSuperBean user);
	UserBean savePushId(Integer id, String pushId);
}
