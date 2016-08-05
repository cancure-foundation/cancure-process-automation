package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.TestCost;

public interface TestCostService {

    public String save(TestCost p);
    public Iterable<TestCost> listTestCosts();
}
