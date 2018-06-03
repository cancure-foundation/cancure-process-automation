package org.cancure.cpa.controller;

import java.net.URLDecoder;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.cancure.cpa.controller.beans.DonationBean;
import org.cancure.cpa.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ccavenue.security.AesCryptUtil;

@Controller
public class DonationController {

	@Value("${merchant.data}")
	private String MERCHANT_DATA;

	@Value("${working_key}")
	private String WORKING_KEY;

	@Value("${access.code}")
	private String ACCESS_CODE;

	@Autowired
	private DonationService donationSevice;

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

		// Insert into DB
		donationSevice.saveDonation(model);

		return "ccavRequestHandler";
	}

	@RequestMapping(value = "/donateResponseHandler", method = {RequestMethod.POST, RequestMethod.GET})
	public String paymentResponse(HttpServletRequest request) {
		
		String encResp= request.getParameter("encResp");
		AesCryptUtil aesUtil=new AesCryptUtil(WORKING_KEY);
		String decResp = aesUtil.decrypt(encResp);
		StringTokenizer tokenizer = new StringTokenizer(decResp, "&");
		Hashtable hs=new Hashtable();
		String pair=null, pname=null, pvalue=null;
		
		int i=0;
		String orderStatus = "";
		String orderId = "";
		String paymentMode = "";
		String trackingId = "";
		String amount = "";
		String failureMessage = "";
		String statusMessage = "";
		String status = "";
		String email = "";
		
		while (tokenizer.hasMoreTokens()) {
			pair = (String)tokenizer.nextToken();
			if(pair!=null) {
				StringTokenizer strTok=new StringTokenizer(pair, "=");
				pname=""; pvalue="";
				if(strTok.hasMoreTokens()) {
					pname=(String)strTok.nextToken();
					if(strTok.hasMoreTokens())
						pvalue=(String)strTok.nextToken();
					hs.put(pname, URLDecoder.decode(pvalue));
					
					if (i == 0) {
						orderId = pvalue;
					} else if (i == 3) {
						orderStatus = pvalue;
					} else if (i == 5) {
						paymentMode = pvalue;
					} else if (i == 1) {
						trackingId = pvalue;
					} else if (i == 10) {
						amount = pvalue;
					} else if (i == 4) {
						failureMessage = pvalue;
					} else if (i == 8) {
						statusMessage = pvalue;
					} else if (i == 7) {
						status = pvalue;
					} else if (i == 18) {
						email = pvalue;
					}
				}
			}
			i++;
		}
		
		request.setAttribute("paymentResultMap", hs);
		request.setAttribute("orderStatus", orderStatus);
		
		
		donationSevice.updateDonation(orderId, orderStatus, paymentMode, email, statusMessage, failureMessage, amount, trackingId);
		
		
		if ("Success".equals(orderStatus)) {
			//generateReceipt();
		
		} else if ("Aborted".equals(orderStatus)) {
			
		} else if ("Failure".equals(orderStatus)) {
			
		} else {
			//Security Error. Illegal access detected
			
		}
		
		
		return "ccavResponseHandler";
	}

	public static void main(String args[]) {
		System.out.println(new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date())
				+ (new java.util.Random().nextInt(9632 - 1234) + 1234));
	}
}
