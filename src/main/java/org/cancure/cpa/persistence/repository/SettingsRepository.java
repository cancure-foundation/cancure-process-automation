package org.cancure.cpa.persistence.repository;

import org.cancure.cpa.persistence.entity.Settings;
import org.springframework.data.repository.CrudRepository;

public interface SettingsRepository extends CrudRepository<Settings, Integer> {

	public Iterable<Settings> findAllByOrderByIdAsc();
	
}