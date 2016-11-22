package org.cancure.cpa.controller;

import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.persistence.entity.PpocPharmacy;
import org.cancure.cpa.service.PharmacyService;
import org.cancure.cpa.service.PpocPharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PpocPharmacyController {
    
    @Autowired
    private PpocPharmacyService ppocPharmacyService;
    
    @RequestMapping(value = "/link/ppoc/pharmacy", method = RequestMethod.POST)
    public PpocPharmacy linkPpocPharmacy(@RequestBody PpocPharmacy ppocPharmacy) {

        return ppocPharmacyService.savePpocPharmacy(ppocPharmacy);
    }

    @RequestMapping("/list/ppoc/pharmacy/{pharmacy_id}")
    public List<UserBean> listPpocPharmacy(@PathVariable("pharmacy_id") Integer pharmacyId) {

        return ppocPharmacyService.getPpocUsersFromPharmacy(pharmacyId);
    }

    @RequestMapping("/list/pharmacy/ppoc/{ppoc_id}")
    public PpocPharmacy listPharmacyPpoc(@PathVariable("ppoc_id") Integer ppocId) {

        return ppocPharmacyService.getPharmacyFromPpoc(ppocId);
    }
    
    @RequestMapping("/listAll/ppoc/pharmacy")
    public Map<String, Object> listAllPpocPharmacy() {
        
        return ppocPharmacyService.listAllPpocPharmacy();
    }
}
