package org.cancure.cpa.controller;

import java.sql.Date;

import org.cancure.cpa.persistence.entity.Camp;
import org.cancure.cpa.service.CampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CampController {

	
	@Autowired
	CampService campservice;

	@RequestMapping(method = RequestMethod.POST, value = "/camp")
	public Camp saveCamp(@RequestBody Camp camp) {
		camp.setCampDate(new Date(System.currentTimeMillis()));
		camp.setPatientCount(0);
		return campservice.saveCamp(camp);
	}

	@RequestMapping(method = RequestMethod.GET, value="/camp/{month}/{year}")
	public Iterable<Camp> listCamp(@PathVariable("month") String monthStr, @PathVariable("year") String yearStr) throws Exception {
		Integer month = Integer.parseInt(monthStr);
		Integer year = Integer.parseInt(yearStr);
		
		if (month < 1 && month > 12) {
			throw new Exception("Month should be between 1 and 12");
		}
				
		if (year < 0) {
			throw new Exception("Enter a proper year");
		}
		
		return campservice.getCampsInAMonth(month, year);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/camp/{campId}")
	public Camp getCamp(@PathVariable("campId") Integer campId) {
		return campservice.getCamp(campId);
	}
	
}
