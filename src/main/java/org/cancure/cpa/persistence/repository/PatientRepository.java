package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.Patient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Integer> {

    public List<Patient> findByNameContainingIgnoreCase(String name);
 
    @Modifying
    @Query("update Patient u set u.taskId = ?1 where u.prn = ?2")
    public int updateTaskId(String taskId, Integer prn);
    
    @Modifying
    @Query("update Patient u set u.pidn = ?1 where u.prn = ?2")
    public int updatePidn(Integer pidn, Integer prn);

    public Boolean findByAadharNo(Long aadharNo);
}