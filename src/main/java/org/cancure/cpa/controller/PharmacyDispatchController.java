package org.cancure.cpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cancure.cpa.controller.beans.PatientBillsBean;
import org.cancure.cpa.controller.beans.PatientVisitForwardDetailsBean;
import org.cancure.cpa.controller.beans.PatientVisitForwardsBean;
import org.cancure.cpa.controller.beans.PharmacyDispatchHistoryBean;
import org.cancure.cpa.controller.beans.PharmacyInvoiceBean;
import org.cancure.cpa.controller.beans.UserBean;
import org.cancure.cpa.service.PharmacyDispatchService;
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
	private PharmacyDispatchService pharmacyDispatchService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/pharmacyforwards/{patientVisitId}", method = RequestMethod.GET)
	public PharmacyDispatchHistoryBean searchPatient(@PathVariable("patientVisitId") String patientVisitId,
			OAuth2Authentication auth) throws Exception {

		if (auth != null) {
			List<String> roles = new ArrayList<>();
			for (GrantedAuthority a : auth.getAuthorities()) {
				roles.add(a.getAuthority());
			}

			String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
			UserBean user = userService.getUserByLogin(login);
			Integer userId = user.getId();

			return pharmacyDispatchService.searchPharmacyDispatchHistory(Integer.parseInt(patientVisitId), userId);

		} else {
			throw new RuntimeException("Not logged in");
		}
	}

	@RequestMapping(value = "/pharmacydispatch/{pidn}", method = RequestMethod.GET)
	public PatientVisitForwardDetailsBean searchPatientForward(@PathVariable("pidn") String pidn, OAuth2Authentication auth)
			throws Exception {

		if (auth != null) {
			List<String> roles = new ArrayList<>();
			for (GrantedAuthority a : auth.getAuthorities()) {
				roles.add(a.getAuthority());
			}

			String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
			UserBean user = userService.getUserByLogin(login);
			Integer userId = user.getId();

			return pharmacyDispatchService.searchForwardsByPidn(Integer.parseInt(pidn), userId);

		} else {
			throw new RuntimeException("Not logged in");
		}
	}

	@RequestMapping(value = "/pharmacydispatch", method = RequestMethod.POST)
	public String saveInvoice(PharmacyInvoiceBean pharmacyInvoiceBean, OAuth2Authentication auth)
			throws Exception {

		if (auth != null) {
			List<String> roles = new ArrayList<>();
			for (GrantedAuthority a : auth.getAuthorities()) {
				roles.add(a.getAuthority());
			}

			String login = (String) ((Map) auth.getUserAuthentication().getDetails()).get("username");
			UserBean user = userService.getUserByLogin(login);
			Integer userId = user.getId();

			Integer id = pharmacyDispatchService.saveInvoice(pharmacyInvoiceBean, userId);
			return "{\"status\" : \"SUCCESS\" , \"invoiceId\" : " + id + "}";

		} else {
			throw new RuntimeException("Not logged in");
		}
	}
}
