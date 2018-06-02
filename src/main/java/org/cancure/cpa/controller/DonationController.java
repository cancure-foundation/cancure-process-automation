package org.cancure.cpa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.cancure.cpa.controller.beans.DonationBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DonationController {
	
	@Value("${merchant.data}")
	private String MERCHANT_DATA;
	
	@Value("${working_key}")
	private String WORKING_KEY;
	
	@Value("${access.code}")
	private String ACCESS_CODE;
	
	
	@RequestMapping(value = "/donate", method = RequestMethod.POST)
	public String payment(@Valid DonationBean model, BindingResult bindingResult, HttpServletRequest request) {
		
		if (bindingResult.hasErrors()) {
            return "donate";
        }
		
		model.setMerchant_id(MERCHANT_DATA);
		model.setWorkingKey(WORKING_KEY);
		model.setAccessCode(ACCESS_CODE);
		
		request.setAttribute("accessCode", ACCESS_CODE);
		request.setAttribute("workingKey", WORKING_KEY);
		
		//Insert into DB
		
		
		
		return "ccavRequestHandler";
	}
	
	public static void main(String args[]) {
		System.out.println(new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date()) + (new java.util.Random().nextInt(9632 - 1234) + 1234));
	}
}
