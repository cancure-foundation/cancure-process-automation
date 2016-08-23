package org.cancure.cpa.controller;

import java.util.HashMap;
import java.util.Map;

import org.cancure.cpa.persistence.entity.InvestigatorType;
import org.cancure.cpa.persistence.entity.ListOfValues;
import org.cancure.cpa.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

	@Autowired
	private CommonService commonService;
	
	@RequestMapping("/common/investigatorTypes")
	public Iterable<InvestigatorType> getInvestigatorTypes(){
		return commonService.getInvestigatorTypes();
	}
	
	@RequestMapping("/common/lov/{name}")
	public Iterable<ListOfValues> getListOfValues(@PathVariable("name") String name){
		return commonService.getListOfValues(name);
	}
	
	@RequestMapping("/common/lovs/{name}")
	public Map<String, Iterable> getListOfValuesMultiple(@PathVariable("name") String names){
		Map<String, Iterable> map = new HashMap<>();
		String[] namesList = names.split("_");
		for (String name : namesList) {
			map.put(name, commonService.getListOfValues(name));
		}
		return map;
	}
}
