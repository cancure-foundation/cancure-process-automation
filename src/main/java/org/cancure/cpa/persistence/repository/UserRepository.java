package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByLogin(String login);

	@Query("Select u from User u join u.roles r where r.name = ?1 ")
	public List<User> findByUserRole(String role);

	@Modifying
    @Query("update User u set u.pushId = ?1 where u.id = ?2")
    public User updatePushId(String pushId, Integer id);
}

    
