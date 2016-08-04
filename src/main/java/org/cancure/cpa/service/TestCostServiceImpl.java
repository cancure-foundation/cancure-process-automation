package org.cancure.cpa.service;

import javax.transaction.Transactional;

import org.cancure.cpa.persistence.entity.TestCost;
import org.cancure.cpa.persistence.repository.TestCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("testCostService")
public class TestCostServiceImpl implements TestCostService {
    
    @Autowired
    private TestCostRepository testCostRepo;
    
    @Override
    @Transactional
    public String save(TestCost p) {
        testCostRepo.save(p);
        return "success";
    }

    @Override
    public Iterable<TestCost> listTestCosts() {
        return testCostRepo.findAll();
    }

}
