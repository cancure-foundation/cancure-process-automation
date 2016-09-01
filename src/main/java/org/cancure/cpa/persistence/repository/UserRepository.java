package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByLogin(String login);

    @Query("Select u from User u join u.roles r where r.name = 'ROLE_HOSPITAL_POC'")
    public List<User> findByUserRole(); 
	}

