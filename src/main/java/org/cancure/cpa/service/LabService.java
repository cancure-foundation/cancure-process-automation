package org.cancure.cpa.service;


import org.cancure.cpa.persistence.entity.Lab;

public interface LabService {

    public Lab save(Lab p);
    public Lab get(Integer labId);
    public Iterable<Lab> listLabs();
}
