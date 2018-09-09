package org.cancure.cpa.service;

import static org.cancure.cpa.util.PDFGenUtil.printImage;
import static org.cancure.cpa.util.PDFGenUtil.printText;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.cancure.cpa.controller.beans.DonationBean;
import org.cancure.cpa.persistence.entity.Donation;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.DonationRepository;
import org.cancure.cpa.util.CurrencyUtil;
import org.cancure.cpa.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class DonationServiceImpl implements DonationService {

	@Value("${spring.files.save.path}")
	private String fileSavePath;

	@Autowired
	DonationRepository donationRepository;
	
	@Override
	public void saveDonation(DonationBean bean) {
		Donation don = new Donation();
		don.setTransactionId(bean.getTid());
		don.setProductName(bean.getProduct_name());
		don.setProductQuantity(1);
		don.setProductAmount(bean.getAmount());
		don.setPayerFname(bean.getBilling_name());
		don.setMessage(bean.getNotesValue());
		don.setPayerAddress(bean.getBilling_address());
		don.setPayerCity(bean.getBilling_city());
		don.setPayerState(bean.getBilling_state());
		don.setPayerZip(bean.getBilling_zip());
		don.setPayerCountry(bean.getBilling_country());
		don.setOrganisation(bean.getOrg());
		don.setDesignation(bean.getDesig());
		don.setMobile(bean.getBilling_tel());
		don.setTelo(bean.getTelo());
		don.setTelr(bean.getTelr());
		don.setPayerEmail(bean.getBilling_email());
		don.setStatus("Pending");
		don.setOrderId(bean.getOrder_id());
		
		donationRepository.save(don);
	}

	@Override
	public Donation updateDonation(String orderId, String orderStatus, String paymentMode, String email, String statusMessage,
			String failureMessage, String status, String amount, String trackingId) throws Exception {

		Donation don = donationRepository.findByOrderId(orderId);
		if (don != null) {
			don.setOrderStatus(orderStatus);
			don.setPaymentMode(paymentMode);
			don.setPayerEmail(email);
			don.setStatusMessage(statusMessage);
			don.setFailureMessage(failureMessage);
			don.setStatus(status);
			don.setProductAmount(amount);
			don.setTrackingId(trackingId);
			donationRepository.save(don);
		}
		
		return don;
	}
	
	@Override
	public void notifyUser(Donation don, ServletContext servletContext) throws Exception {
		try {
			File receipt = generateReceipt(don, servletContext);
			User user = new User();
			user.setEmail(don.getPayerEmail());
			Set<User> userSet = new HashSet<>();
			userSet.add(user);
			
			Map<String, Object> values = new HashMap<>();
			values.put("name", don.getPayerFname());
			values.put("amount", don.getProductAmount());
			
			if (receipt != null) {
				List<String> filePaths = new ArrayList<>();
				filePaths.add(receipt.getAbsolutePath());
				
				new EmailNotifier().notify(userSet, "DonationThanks", values, filePaths);
			}
		} catch (Exception e) {
			Log.getLogger().error("Exception while notifying user ", e);
		}
		
	}
	
	private File generateReceipt(Donation don, ServletContext servletContext) {
		File f = null;
		try {
			Rectangle pagesize = new Rectangle(785f, 345f);
			Document document = new Document(pagesize, 10f, 72f, 108f, 180f);

			String imageLoc = "images/logo.jpg";
			// String imageLoc = "C:/Users/sudo/git/cancure-process-automation/src/main/webapp/images/logo.jpg";
			// C:\Users\sudo\git\cancure-process-automation\src\main\webapp\images
			// step 2
			f = new File(fileSavePath + "/receipts/Receipt" + don.getPayId() + ".pdf");
			//f = new File("C:\\Users\\sudo\\cancure\\Receipt.pdf");
			
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(f));
			// step 3
			document.open();
			PdfContentByte canvas = writer.getDirectContent();
			BaseFont bf = null;
			try {
			    String fontPath = servletContext.getRealPath("/fonts/FreeSans.ttf");
				bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, 
		                  BaseFont.EMBEDDED);
			} catch (Exception e) {
				bf = BaseFont.createFont(BaseFont.HELVETICA,BaseFont.CP1257, 
		                  BaseFont.EMBEDDED);
			}
			
			printText(bf, canvas, 337, 345 - 45, "CANCURE FOUNDATION", 10, 1f);
			printText(bf, canvas, 337, 345 - 59, "(Regn No. EKM/TC/169/2015)", 10, 1f);
			printText(bf, canvas, 337, 345 - 73, "Regd Office", 10, 1f);
			printText(bf, canvas, 400, 345 - 73, ": 60/3285, Benrub, P.K. Deevor Road", 10, 0.2f);
			printText(bf, canvas, 337, 345 - 88, "Perumanoor, Cochin-682 015 | PAN: AABTC7227J", 10, 0.2f);
			printText(bf, canvas, 337, 345 - 102, "Tel. 98460 31667, 93877 53451, 70 25 00 3333", 10, 0.2f);
			printText(bf, canvas, 337, 345 - 115, "Email: info@cancure.in; URL: www.cancure.in", 10, 0.2f);
			
			printText(bf, canvas, 367, 345 - 147, "RECEIPT", 13, 1.2f);
			printText(bf, canvas, 12, 345 - 168, "Receipt No: " + don.getPayId() , 10, 0.2f);
			printText(bf, canvas, 662, 345 - 168, "Date : " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()), 10, 0.2f);
			printText(bf, canvas, 12, 345 - 202, "Received with thanks from ", 10, 0.2f);
			printText(bf, canvas, 148, 345 - 202, don.getPayerFname(), 10, 0.8f);
			printText(bf, canvas, 12, 345 - 218, "the amount of Rs. ", 10, 0.2f);
			printText(bf, canvas, 102, 345 - 218, don.getProductAmount() + " (" + CurrencyUtil.convertToIndianCurrency(don.getProductAmount()) + ")", 10, 0.8f);
			printText(bf, canvas, 12, 345 - 233, "by cash /cheque_no __________________. dated ________________ drawn on _____________________", 10, 0.2f);
			printText(bf, canvas, 12, 345 - 248, "towards ___________________________________________.", 10, 0.2f);
			
			printText(bf, canvas, 642, 345 - 269, "For Cancure Foundation", 10, 0.2f);
			printText(bf, canvas, 740, 345 - 285, "Sd /-", 10, 0.2f);
			printText(bf, canvas, 657, 345 - 300, "Authorised Signatory", 10, 0.2f);
			
			printText(bf, canvas, 12, 345 - 327, "Donations to Cancure Foundation are exempt u/s 80G vide order no. CIT(E) CHN/80G/155/2014-15 dated 25-08-2015 with unique Regn. No. AABTC7227J/09/15-16/S-0038/80G ", 8, 0.1f);
			printText(bf, canvas, 12, 345 - 335, "and shall be valid in perpetuity", 8, 0.1f);
			
			//String logoFile = servletContext.getRealPath("/images/logo.jpg");
			String logoFile = imageLoc;
			Image cancureLog = Image.getInstance(logoFile);
			printImage(canvas, 17, 345 - 100, 100, 100, cancureLog);
			
			document.close();
			
		} catch (Exception e) {
			Log.getLogger().error("Exception while generating PDF Donation receipt.", e);
		} 
		
		return f;
	}
	/*
	public static void main(String[] args) {
		new DonationServiceImpl().generateReceipt(null, null);
	}
*/
}
