package org.cancure.cpa.controller;


import org.cancure.cpa.persistence.entity.Lab;
import org.cancure.cpa.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LabController {
    @Autowired
    private LabService labService;
    
    @RequestMapping(value="/lab/save", method=RequestMethod.POST)
    public String save(Lab lab) {
        return labService.save(lab);
    }
    
    @RequestMapping("/lab/{id}")
    public Lab getLab(@PathVariable("id") Integer labId){
        return labService.get(labId);
    }
    
    @RequestMapping("/lab/list")
    public Iterable<Lab> listLabs() {
        return labService.listLabs();
    }
}
