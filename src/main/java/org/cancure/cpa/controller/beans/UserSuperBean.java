package org.cancure.cpa.controller.beans;

import java.util.HashSet;
import java.util.Set;

import org.cancure.cpa.persistence.entity.Doctor;
import org.cancure.cpa.persistence.entity.Role;

public class UserSuperBean {

	private Integer id;

	private String name;

	private String login;

	private Set<Role> roles = new HashSet<Role>();
	
	private Boolean enabled;
	
	private String email;
	
	private String password;
	
	private Boolean firstLog;
	
	private Doctor doctor;
	
	private Integer hospitalId;
	
	private Integer pharmacyId;
	
	private Integer labId;

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public Boolean getFirstLog() {
        return firstLog;
    }

    public void setFirstLog(Boolean firstLog) {
        this.firstLog = firstLog;
    }

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Integer getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Integer pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Integer getLabId() {
        return labId;
    }

    public void setLabId(Integer labId) {
        this.labId = labId;
    }

    
}
