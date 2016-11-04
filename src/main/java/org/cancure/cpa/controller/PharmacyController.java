package org.cancure.cpa.controller;

import org.cancure.cpa.persistence.entity.Pharmacy;
import org.cancure.cpa.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class PharmacyController {
	
	@Autowired
	PharmacyService pharmacyService;
	
	@RequestMapping(method=RequestMethod.POST, value="/pharmacy/save")
	public Pharmacy savePharmacy(@RequestBody Pharmacy pharmacy){
		
		return pharmacyService.savePharmacy(pharmacy);
	}
	
	@RequestMapping("/pharmacy/list")
    public Iterable<Pharmacy> listPharmacies(){
    	
    	return pharmacyService.listPharmacies();
    	
    }
    
    @RequestMapping("/pharmacy/{pharmacyId}")
    public Pharmacy gePharmacy(@PathVariable("pharmacyId") Integer pharmacyId) {
    	
    	return pharmacyService.gePharmacy(pharmacyId);
    	
    }

}
