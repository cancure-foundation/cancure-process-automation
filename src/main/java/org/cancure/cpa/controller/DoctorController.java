package org.cancure.cpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cancure.cpa.controller.beans.DoctorBean;
import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.Doctor;
import org.cancure.cpa.persistence.entity.HpocHospital;
import org.cancure.cpa.persistence.entity.Role;
import org.cancure.cpa.service.DoctorService;
import org.cancure.cpa.service.HpocHospitalService;
import org.cancure.cpa.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @Autowired
    private HpocHospitalService hpocHospitalService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/doctor/save", method = RequestMethod.POST)
    public String saveDoctor(@RequestBody Doctor doctor) {
        System.out.println(doctor.getName());
        doctor.setEnabled(true);
        doctorService.saveDoctor(doctor);
        return "{\"status\" : \"SUCCESS\"}";
    }

    @RequestMapping("/doctor/list")
    public Iterable<Doctor> listDoctors() {
        return doctorService.listDoctors();
    }

    @RequestMapping("/doctor/{doctor_id}")
    public Doctor getDoctor(@PathVariable("doctor_id") Integer doctor_id) {
        return doctorService.getDoctor(doctor_id);
    }

    @RequestMapping("/doctor/user/{user_id}")
    public Doctor getDoctorByUserId(@PathVariable("user_id") Integer userId) {
        return doctorService.getDoctorByUserId(userId);
    }

    @RequestMapping("/doctor/delete/{doctor_id}")
    public Doctor updateDoctor(@PathVariable("doctor_id") Integer doctor_id) {
        return doctorService.updateDoctor(doctor_id);
    }

    @RequestMapping("/hospital/doctor/list/{hospital_id}")
    public List<Doctor> listHospitalDoctors(@PathVariable("hospital_id") Integer hospitalId) {
        return doctorService.listHospitalDoctors(hospitalId);
    }

    @RequestMapping("/doctor/hpoclist")
    public List<DoctorBean> listHpocDoctors(OAuth2Authentication auth) {
        Integer userId = null;
        List<DoctorBean> doctorBeanList = new ArrayList<>();
        Boolean  isDoctor = false;
        if (auth != null) {
            String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
            UserBean user = userService.getUserByLogin(login);
            userId = user.getId();
            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                if (role.getName().equals("ROLE_DOCTOR")) {
                    isDoctor = true;
                }
            }
            if (!isDoctor) {
                HpocHospital hpocHospital = hpocHospitalService.getHospitalFromHpoc(userId);
                List<Doctor> DoctorList = doctorService.listHospitalDoctors(hpocHospital.getHospitalId());

                for (Doctor doctor : DoctorList) {
                    DoctorBean doctorBean = new DoctorBean();
                    BeanUtils.copyProperties(doctor, doctorBean);
                    doctorBeanList.add(doctorBean);
                }
            } else {
                Doctor doctor = doctorService.getDoctorByUserId(userId);
                DoctorBean doctorBean = new DoctorBean();
                BeanUtils.copyProperties(doctor, doctorBean);
                doctorBeanList.add(doctorBean);
            }
        } else {
            throw new RuntimeException("Not logged in");
        }

        return doctorBeanList;
    }

}
