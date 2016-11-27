package org.cancure.cpa.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.PatientVisitBean;
import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.service.PatientService;
import org.cancure.cpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PharmacyDispatchController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/pharmacydispatch/{pidn}", method = RequestMethod.GET)
	public String searchPatient(@PathVariable("pidn") String pidn, OAuth2Authentication auth) throws IOException {
	
		if (auth != null) {
			List<String> roles = new ArrayList<>();
			for (GrantedAuthority a : auth.getAuthorities()){
				roles.add(a.getAuthority());
			}
			
			String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
			UserBean user = userService.getUserByLogin(login);
			Integer userId = user.getId();
			
			return null;
		} else {
			throw new RuntimeException("Not logged in");
		}
	}
}
