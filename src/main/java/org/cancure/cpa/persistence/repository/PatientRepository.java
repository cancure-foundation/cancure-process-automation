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

    public List<Patient>  findByAadharNo(String aadharNo);
    
    public List<Patient>  findByPidn(Integer pidn);
    
    @Modifying
    @Query("update Patient u set u.hospitalCostEstimate = ?1, u.medicalCostEstimate = ?2, u.patientType = ?3  where u.prn = ?4")
    public int updateCostEstimate(Integer hospitalCostEstimate,Integer medicalCostEstimate, String patientType, Integer prn);
    
    @Modifying
    @Query("update Patient u set u.hospitalCostApproved = ?1, u.medicalCostApproved = ?2 where u.prn = ?3")
    public int updateCostApproved(Integer hospitalCostApproved, Integer medicalCostApproved,  Integer prn);
    
    @Modifying
    @Query("update Patient u set u.mbapprovalViewedDoctors = ?1 where u.prn = ?2")
    public int updateMbApprovalViewedDoctors(String doctors,  Integer prn);
    
}