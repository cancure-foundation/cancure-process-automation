package org.cancure.cpa.controller;

import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.persistence.entity.Hospital;
import org.cancure.cpa.service.CampPatientTestResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CampPatientTestResultsController {
@Autowired
CampPatientTestResultsService camppatienttestesultsservice;
	
@RequestMapping(method=RequestMethod.POST,value="CampPatientTestResult/save")
public CampPatientTestResults saveCampPatientTestResults(@RequestBody CampPatientTestResults camppatienttestesults)
{
	return camppatienttestesultsservice.saveTestResult(camppatienttestesults);
}
@RequestMapping(method=RequestMethod.POST,value="CampPatientTestResult/get")
public Iterable<CampPatientTestResults>  getCampPatientTestResults()
{
	return camppatienttestesultsservice.listTestResults();
}
}
