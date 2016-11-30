package org.cancure.cpa.controller;

import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.LpocLab;
import org.cancure.cpa.service.LabService;
import org.cancure.cpa.service.LpocLabService;
import org.cancure.cpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LpocLabController {
    
    @Autowired
    private LpocLabService lpocLabService;

    @Autowired
    private UserService userService;

    @Autowired
    private LabService labService;
    
    @RequestMapping(value = "/link/lpoc/lab", method = RequestMethod.POST)
    public LpocLab linkLpocLab(@RequestBody LpocLab lpocLab) {

        return lpocLabService.saveLpocLab(lpocLab);
    }

    @RequestMapping("/list/lpoc/lab/{lab_id}")
    public List<UserBean> listLpocLab(@PathVariable("lab_id") Integer labId) {

        return lpocLabService.getLpocUsersFromLab(labId);
    }

    @RequestMapping("/list/lab/lpoc/{lpoc_id}")
    public LpocLab listLabLpoc(@PathVariable("lpoc_id") Integer lpocId) {

        return lpocLabService.getLabFromLpoc(lpocId);
    }
    
    @RequestMapping("/listAll/lpoc/Lab")
    public Map<String, Object> listAllLpocLab() {
        
        return lpocLabService.listAllLpocLab();
    }
}