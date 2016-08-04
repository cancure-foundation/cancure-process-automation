package org.cancure.cpa.service;


import org.cancure.cpa.persistence.entity.Lab;
import org.cancure.cpa.persistence.repository.LabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("labService")
public class LabServiceImpl implements LabService{
   
    @Autowired
    LabRepository labRepo;
    
    @Transactional
    @Override
    public String save(Lab lab) {
        labRepo.save(lab);
        return "Success";
    }
    
    @Override
    public Lab get(Integer labId){
        return labRepo.findOne(labId);
    }
    
    public Iterable<Lab> listLabs() {
        return labRepo.findAll();
    }

   
}
