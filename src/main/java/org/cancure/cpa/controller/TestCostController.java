package org.cancure.cpa.controller;


import org.cancure.cpa.persistence.entity.TestCost;
import org.cancure.cpa.service.TestCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCostController {
    
    @Autowired
    private TestCostService testCostService;
    
    @RequestMapping(value="/testCost/save", method=RequestMethod.POST)
    public String save(TestCost testCost) {
        return testCostService.save(testCost);
    }
       
    @RequestMapping("/testCost/list")
    public Iterable<TestCost> listTestCosts() {
        return testCostService.listTestCosts();
    }
}
