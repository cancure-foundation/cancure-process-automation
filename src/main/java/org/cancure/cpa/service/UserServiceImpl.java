package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.List;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.RoleRepository;
import org.cancure.cpa.persistence.repository.UserRepository;
import org.springframework.beans.BeanUtils;
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
    // @Autowired
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @Autowired
    CommonService commonService;

    @Autowired
    PasswordNotifier passwordNotifier;
    
    public UserBean saveUser(User user) {
        String password="";
        if (user.getId() == null) {
            user.setFirstLog(true);
            password=commonService.generatePassword();
            String encPass = encoder.encode(password);
            user.setPassword(encPass);
            
        } else {
            if (user.getFirstLog()) {
                if (user.getPassword() == null) {
                    throw new RuntimeException("User password cannot be empty");
                }
                String encPass = encoder.encode(user.getPassword());
                user.setPassword(encPass);
                user.setFirstLog(false);
            } else {
                if (user.getPassword() == null) {
                    User actualUser = userRepo.findOne(user.getId());
                    user.setPassword(actualUser.getPassword());
                } else {
                    String encPass = encoder.encode(user.getPassword());
                    user.setPassword(encPass);
                }
            }
        }

        userRepo.save(user);
        if(!password.equals("")){
        passwordNotifier.notify(user.getEmail(), password, user.getLogin(), false);
        }
        UserBean userBean = new UserBean();
        BeanUtils.copyProperties(user, userBean);
        return userBean;
    }

    @Override
    public UserBean getUser(Integer id) {
        User user = userRepo.findOne(id);
        UserBean userBean = new UserBean();
        BeanUtils.copyProperties(user, userBean);
        return userBean;
    }

    public Iterable<UserBean> listUsers() {

        Iterable<User> list = userRepo.findAll();
        List<UserBean> userBeans = new ArrayList<>();

        for (User usr : list) {
            UserBean userBean = new UserBean();
            BeanUtils.copyProperties(usr, userBean);
            userBeans.add(userBean);
        }

        return userBeans;
    }

    public Iterable<Role> listRoles() {
        return roleRepo.findAll();
    }

    @Override
    public UserBean getUserByLogin(String login) {
        User user = userRepo.findByLogin(login);
        UserBean userBean = new UserBean();
        BeanUtils.copyProperties(user, userBean);
        return userBean;
    }

    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void setRoleRepo(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Iterable<UserBean> listHPOCUsers() {
        Iterable<User> list = userRepo.findByUserRole("ROLE_HOSPITAL_POC");
        List<UserBean> userBeans = new ArrayList<>();

        for (User usr : list) {
            UserBean userBean = new UserBean();
            BeanUtils.copyProperties(usr, userBean);
            userBeans.add(userBean);
        }
        return userBeans;
    }

    @Override
    public UserBean resetPassword(Integer id, Boolean resetPassword) {
        
        User user=userRepo.findOne(id);
        user.setFirstLog(true);
        String password=commonService.generatePassword();
        String encPass = encoder.encode(password);
        user.setPassword(encPass);
        userRepo.save(user);
        passwordNotifier.notify(user.getEmail(), password, user.getLogin(), resetPassword);
        UserBean userBean = new UserBean();
        BeanUtils.copyProperties(user, userBean);
        return userBean;
    }

}
