package org.cancure.cpa.util;

import java.util.Calendar;
import java.util.Date;

public class CommonUtil {
    
    public int getAge(Date dob){
        Calendar calDOB = Calendar.getInstance();
        calDOB.setTime(dob);
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(new java.util.Date());
        int ageYr = (calNow.get(Calendar.YEAR) - calDOB.get(Calendar.YEAR));
        int ageMo = (calNow.get(Calendar.MONTH) - calDOB.get(Calendar.MONTH));
        if (ageMo < 0)
        {
        ageYr--;
        }
        return ageYr;
    }

}
