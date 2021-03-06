package org.cancure.cpa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.cancure.cpa.util.Log;

/**
 * An API to send SMS using http://api.textlocal.in API
 * Documentation at : http://api.textlocal.in/docs/sendsms
 */
public class SMSSender extends Thread {
	
	private String numbersCSV;
	private String smsMessage;
	private String smsUsername;
	private String smsHash;
	
	public SMSSender(String numbersCSV, String smsMessage, String smsUsername, String smsHash){
		this.numbersCSV = numbersCSV;
		this.smsMessage = smsMessage;
		this.smsUsername = smsUsername;
		this.smsHash = smsHash;
	}
	
	@Override
	public void run() {
		sendSms();
	}
	
	
	private String sendSms() {
		try {
			// Construct data
			String user = "username=" + smsUsername;
			String hash = "&hash=" + smsHash;
			String message = "&message=" + smsMessage;
			String sender = "&sender=" + "TXTLCL";
			String numbers = "&numbers=" + numbersCSV; // "918123456789";

			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("http://api.textlocal.in/send/?").openConnection();
			String data = user + hash + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuilder stringBuffer = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();

			return stringBuffer.toString();
		} catch (Exception e) {
			Log.getLogger().error("Error while sending SMS", e);
			return "Error " + e;
		}
	}
}
