package org.cancure.cpa.controller;

import java.util.HashMap;
import java.util.Map;

import org.cancure.cpa.persistence.entity.InvestigatorType;
import org.cancure.cpa.persistence.entity.ListOfValues;
import org.cancure.cpa.persistence.entity.Settings;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.service.CommonService;
import org.cancure.cpa.service.PasswordNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

	@Autowired
	private CommonService commonService;
	@Autowired
	private PasswordNotifier passwordNotifier;
	
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
	
	@RequestMapping("/common/settings/{id}")
	public Settings getSetting(@PathVariable("id") Integer id){
		return commonService.findSettingsById(id);
	}
	
	@RequestMapping("/common/settings/save")
	public Settings saveSetting(@RequestBody Settings setting){
		return commonService.saveSetting(setting);
	}
	
	@RequestMapping("/common/settings")
	public Iterable<Settings> getAllSettings(){
		return commonService.findAllSettings();
	}
	
	@RequestMapping("/common/testemail")
	public Map<String, String> testEmail(){
		Map<String, String> map = new HashMap<>();
		User user;
		try {
			
			user = new User();
			user.setEmail("dantis@msn.com");
			user.setName("Dantis P S");
			user.setLogin("dantis");
			passwordNotifier.notify(user, "dantis@cancure", false);
			map.put("status dominic", "success");
			System.out.println("Send to dantis");
			
		} catch (Exception e) {
			map.put("status dominic", "failed");
			map.put("message", e.toString());
		}
	
		
		return map;
	}
}
