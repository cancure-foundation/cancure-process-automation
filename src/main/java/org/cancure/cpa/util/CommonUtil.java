package org.cancure.cpa.util;

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

}
