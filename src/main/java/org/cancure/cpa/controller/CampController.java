package org.cancure.cpa.controller;

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

	@RequestMapping(method = RequestMethod.POST, value = "/camp/save")
	public Camp saveCamp(@RequestBody Camp camp) {
		return campservice.saveCamp(camp);
	}

	@RequestMapping("/camp/list")
	public Iterable<Camp> listCamp() {
		return campservice.listCamp();
	}

	@RequestMapping("/camp/{campId}")
	public Camp getCamp(@PathVariable("campId") Integer campId) {
		return campservice.getCamp(campId);
	}
	
}
