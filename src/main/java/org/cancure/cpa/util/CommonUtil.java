package org.cancure.cpa.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.cancure.cpa.controller.beans.PatientFamilyBean;

public class CommonUtil {

    public static int getAge(Date dob) {
        if (dob != null) {
            Calendar calDOB = Calendar.getInstance();
            calDOB.setTime(dob);
            Calendar calNow = Calendar.getInstance();
            calNow.setTime(new java.util.Date());
            int ageYr = (calNow.get(Calendar.YEAR) - calDOB.get(Calendar.YEAR));
            int ageMo = (calNow.get(Calendar.MONTH) - calDOB.get(Calendar.MONTH));
            if (ageMo < 0) {
                ageYr--;
            }
            return ageYr;
        } else {
            return 0;
        }
    }
    
    public static long getTotalIncome(List<PatientFamilyBean> patientFamily){
        long totalIncome=(long)0;
        for(PatientFamilyBean temp:patientFamily){
            totalIncome=totalIncome+(temp.getIncome()+temp.getOtherIncome());            
        }
        return totalIncome;
        
    }
    
    public static String formatDate(Date date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    	return sdf.format(date);
    }
    
    public static String formatDateSimple(Date date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    	return sdf.format(date);
    }
    
    public static String generateUID(Date date, Integer patientCount, Integer campId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String uid =  sdf.format(date) + campId+ String.format("%04d", patientCount+1);
        return uid;
              
    }
}
