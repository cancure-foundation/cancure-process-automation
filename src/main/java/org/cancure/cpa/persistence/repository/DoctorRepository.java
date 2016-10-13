package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.Doctor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    @Query("Select d from Doctor d where d.enabled = true")
    public Iterable<Doctor> findAllActive();

    @Query("Select d from Doctor d join d.hospital h where h.hospitalId = ?1 and d.enabled = true")
    public List<Doctor> findByHospitalId(Integer hospitalId);
    
    public List<Doctor> findByUserId(Integer userId);
}