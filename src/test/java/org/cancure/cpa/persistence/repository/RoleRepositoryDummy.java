package org.cancure.cpa.persistence.repository;

import java.util.Arrays;

import org.cancure.cpa.persistence.entity.Role;

public class RoleRepositoryDummy implements RoleRepository {

	@Override
	public <S extends Role> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Role> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Role> findAll() {
		Role r1 = new Role();
		r1.setId(1);
		r1.setName("PC");
		
		Role r2 = new Role();
		r2.setId(2);
		r2.setName("Admin");
		
		return Arrays.asList(r1, r2);
	}

	@Override
	public Iterable<Role> findAll(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Role entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Role> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

}
