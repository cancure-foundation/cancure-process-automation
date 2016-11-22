package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.controller.beans.UserSuperBean;
import org.cancure.cpa.persistence.entity.Doctor;
import org.cancure.cpa.persistence.entity.HpocHospital;
import org.cancure.cpa.persistence.entity.LpocLab;
import org.cancure.cpa.persistence.entity.PpocPharmacy;
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
    @Autowired
    DoctorService doctorService;

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    CommonService commonService;

    @Autowired
    PasswordNotifier passwordNotifier;

    @Autowired
    HpocHospitalService hpocHospitalService;

    @Autowired
    PpocPharmacyService ppocPharmacyService;
    
    @Autowired
    LpocLabService lpocLabService;
    
    @Transactional
    public UserSuperBean saveUser(UserSuperBean UserSuperBean) {

        if (UserSuperBean.getId() == null && userRepo.findByLogin(UserSuperBean.getLogin()) != null) {
            throw new RuntimeException("Login ID already exists");
        }
        User user = new User();
        BeanUtils.copyProperties(UserSuperBean, user);
        String password = "";
        if (user.getId() == null) {
            user.setFirstLog(true);
            password = commonService.generatePassword();
            String encPass = encoder.encode(password);
            user.setPassword(encPass);

        } else {
                User actualUser = userRepo.findOne(user.getId());
                user.setPassword(actualUser.getPassword());
        }
        User savedUser = userRepo.save(user);
        if (UserSuperBean.getDoctor() != null) {
            Doctor doc = UserSuperBean.getDoctor();
            doc.setUserId(savedUser.getId());
            doctorService.saveDoctor(doc);
        }
        if (UserSuperBean.getHospitalId() != null) {
            HpocHospital hpocHospital = new HpocHospital();
            hpocHospital.setHospitalId(UserSuperBean.getHospitalId());
            hpocHospital.setHpocId(savedUser.getId());
            hpocHospitalService.saveHpocHospital(hpocHospital);
        }
        if (UserSuperBean.getPharmacyId() != null) {
            PpocPharmacy ppocPharmacy = new PpocPharmacy();
            ppocPharmacy.setPharmacyId(UserSuperBean.getPharmacyId());
            ppocPharmacy.setPpocId(savedUser.getId());
            ppocPharmacyService.savePpocPharmacy(ppocPharmacy);
        }
        if (UserSuperBean.getLabId() != null) {
            LpocLab lpocLab = new LpocLab();
            lpocLab.setLabId(UserSuperBean.getLabId());
            lpocLab.setLpocId(savedUser.getId());
            lpocLabService.saveLpocLab(lpocLab);
        }
        if (!password.equals("")) {
            passwordNotifier.notify(user, password, false);
        }
        return UserSuperBean;
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

        User user = userRepo.findOne(id);
        user.setFirstLog(true);
        String password = commonService.generatePassword();
        String encPass = encoder.encode(password);
        user.setPassword(encPass);
        userRepo.save(user);
        passwordNotifier.notify(user, password, resetPassword);
        UserBean userBean = new UserBean();
        BeanUtils.copyProperties(user, userBean);
        return userBean;
    }

    @Override
    public String forgotPassword(UserBean userBean) {

        if (userRepo.findByLogin(userBean.getLogin()) == null
                || !userRepo.findByLogin(userBean.getLogin()).getEmail().equals(userBean.getEmail())) {
            return "{\"status\" : \"FAIL\"}";
        } else {
            User user = new User();
            user = userRepo.findByLogin(userBean.getLogin());
            user.setFirstLog(true);
            String password = commonService.generatePassword();
            String encPass = encoder.encode(password);
            user.setPassword(encPass);
            userRepo.save(user);
            passwordNotifier.notify(user, password, true);
            return "{\"status\" : \"SUCCESS\"}";
        }

    }

    @Override
    public String firstLogin(UserSuperBean UserSuperBean) {
       
        User user = new User();
        user = userRepo.findByLogin(UserSuperBean.getLogin());
        user.setFirstLog(false);
        user.setPassword(encoder.encode(UserSuperBean.getPassword()));
        userRepo.save(user);
        return "{\"status\" : \"SUCCESS\"}";
    }

}
