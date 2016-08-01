package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByLogin(String login);
	}