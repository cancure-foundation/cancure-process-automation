package org.cancure.cpa.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.service.EmailNotifier;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContactUsController {

	@RequestMapping(value = "/contactus", method = {RequestMethod.POST, RequestMethod.GET})
	public String contactus(HttpServletRequest request) {
		boolean error = false;
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String click = request.getParameter("click");
		String message = request.getParameter("message");
		
		String errName = null;
		if (StringUtils.isEmpty(name)) {
			errName = "Name is required";
			request.setAttribute("errName", errName);
			error = true;
		}
		
		String errEmail = null;
		if (StringUtils.isEmpty(email)) {
			errEmail = "Email is required";
			error = true;
		} else if (!new EmailValidator().isValid(email, null)) {
			errEmail = "Please enter a valid email address";
			error = true;
		}
		request.setAttribute("errEmail", errEmail);
		
		String errMessage = null;
		if (StringUtils.isBlank(message)) {
			errMessage = "Message is required";
			request.setAttribute("errMessage", errMessage);
			error = true;
		}
		
		if (!error) {
			Set<User> userSet = new HashSet<>();
			User user = new User();
			user.setEmail("secretary@cancure.in");
			userSet.add(user);
			
			String result = "Thank You For Sending Mail! We will be in touch with you shortly";
			
			Map<String, Object> values = new HashMap<>();
			values.put("name", name);
			values.put("email", email);
			values.put("phone", phone);
			values.put("click", click);
			values.put("message", message);
			values.put("EmailSubject", "Cancure Contact Form");
			
			try {
				new EmailNotifier().notify(userSet, "ContactUs", values);
			} catch (Exception e) {
				result = "Sorry there was an error sending your message. Please try again later. " + e.getMessage();
				e.printStackTrace();
			}
			request.setAttribute("result", result);
			request.setAttribute("status", "success");
		}
		
		return "contact";
	}
}
