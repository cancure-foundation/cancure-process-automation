package org.cancure.cpa.controller;

import java.util.Calendar;
import java.util.Map;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@RequestMapping("/news/{page}")
	public String what(@PathVariable("page") String page, @RequestParam Map<String,String> allRequestParams, Map<String, Object> model) {
		model.put("year", Calendar.getInstance().get(Calendar.YEAR));
		model.putAll(allRequestParams);
		return page;
	}
	
	public static void main(String args[]) {
		System.out.println(new EmailValidator().isValid("d@s.com", null));
	}
}
