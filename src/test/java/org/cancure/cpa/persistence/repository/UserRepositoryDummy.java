package org.cancure.cpa.persistence.repository;

import java.util.ArrayList;
import java.util.List;

import org.cancure.cpa.persistence.entity.User;

public class UserRepositoryDummy implements UserRepository {

	private List<User> list = new ArrayList<>();
	
	@Override
	public <S extends User> S save(S entity) {
		entity.setId(1234);
		list.add(entity);
		return entity;
	}

	@Override
	public <S extends User> Iterable<S> save(Iterable<S> entities) {
		int i=0;
		for (S s : entities){
			s.setId(i++);
			list.add(s);
		}
		return (Iterable<S>) list;
	}

	@Override
	public User findOne(Long id) {
		for (User u : list){
			if (u.getId().equals(id)){
				return u;
			}
		}
		return null;
	}

	@Override
	public boolean exists(Long id) {
		for (User u : list){
			if (u.getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterable<User> findAll() {
		return list;
	}

	@Override
	public Iterable<User> findAll(Iterable<Long> ids) {
		return list;
	}

	@Override
	public long count() {
		return list.size();
	}

	@Override
	public void delete(Long id) {
		for (int i=0; i < list.size(); i ++){
			if (list.get(i).getId().equals(id)){
				list.remove(i);
				return;
			}
		}
	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends User> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		list.clear();
	}

	@Override
	public User findByLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

}
