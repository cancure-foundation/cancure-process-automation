package org.cancure.cpa.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.cancure.cpa.controller.beans.DonationBean;
import org.cancure.cpa.persistence.entity.Donation;
import org.cancure.cpa.persistence.repository.SettingsRepository;
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

	@Autowired
	private SettingsRepository settingsRepository;

	@Autowired
	private DonationService donationSevice;

	@RequestMapping(value = "/donateform", method = RequestMethod.GET)
	public String paymentForm(HttpServletRequest request) {

		String redirectUrl = settingsRepository.findOne(41).getValue();
		String cancelUrl = settingsRepository.findOne(42).getValue();
		
		request.setAttribute("redirectUrl", redirectUrl);
		request.setAttribute("cancelUrl", cancelUrl);
		
		return "donate";
	}
	
	@RequestMapping(value = "/donate", method = RequestMethod.POST)
	public String payment(@Valid DonationBean model, BindingResult bindingResult, HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			return "donate";
		}

		String merchantId = settingsRepository.findOne(43).getValue();
		String accessCode = settingsRepository.findOne(44).getValue();
		String workingKey = settingsRepository.findOne(45).getValue();
		
		model.setMerchant_id(merchantId);
		model.setWorkingKey(workingKey);
		model.setAccessCode(accessCode);

		request.setAttribute("accessCode", accessCode);
		request.setAttribute("workingKey", workingKey);

		if (model.getProduct_name() == null) {
			model.setProduct_name("Donation");
		}
		// Insert into DB
		donationSevice.saveDonation(model);

		return "ccavRequestHandler";
	}
	
	/**
	 * This end point is only for testing payments using ccAvenue and not for production use.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mockCCAvenue", method = {RequestMethod.POST, RequestMethod.GET})
	public String mockCCAvenue(HttpServletRequest request) throws Exception {
	
		String workingKey = settingsRepository.findOne(45).getValue();
		AesCryptUtil aesUtil=new AesCryptUtil(workingKey);
		String encResp= request.getParameter("encRequest");
		
		String resp = aesUtil.decrypt(encResp);
		String[] keyVals = resp.split("&");
		HashMap<String, String> map = new HashMap<>();
		for (String kv : keyVals) {
			String[] param = kv.split("=");
			if (param.length == 2) {
				map.put(param[0], URLDecoder.decode(param[1]));
			}
		}
		
		String value = "orderId=" + map.get("order_id") + 
				"&trackingId=" + map.get("tid") + 
				"&x=y&orderStatus=Success&failureMessage=xyz&paymentMode=online&six=6&status=Success&statusMessage=howdy&nine=9&amount="
				+ map.get("amount") + "&eleven=11&twelve=12&thirteen=13&fourteen=14&fifteen=15&sixteen=16&seventeen=17&email=" + map.get("billing_email");
		String encStr = aesUtil.encrypt(value);
		
		return "redirect:/donateResponseHandler?encResp=" + encStr;
	}

	@RequestMapping(value = "/donateResponseHandler", method = {RequestMethod.POST, RequestMethod.GET})
	public String paymentResponse(HttpServletRequest request) throws Exception {
		
		String workingKey = settingsRepository.findOne(45).getValue();
		String encResp= request.getParameter("encResp");
		AesCryptUtil aesUtil=new AesCryptUtil(workingKey);
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
		
		
		Donation don = donationSevice.updateDonation(orderId, orderStatus, paymentMode, email, statusMessage, failureMessage, status, amount, trackingId);
		
		if ("Success".equals(orderStatus)) {
			donationSevice.notifyUser(don, request.getServletContext());
		
		} else if ("Aborted".equals(orderStatus)) {
			
		} else if ("Failure".equals(orderStatus)) {
			
		} else {
			//Security Error. Illegal access detected
			
		}
		
		
		return "ccavResponseHandler";
	}

	/*public static void main(String args[]) {
		System.out.println(new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date())
				+ (new java.util.Random().nextInt(9632 - 1234) + 1234));
	}*/
}
