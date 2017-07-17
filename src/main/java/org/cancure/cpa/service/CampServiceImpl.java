package org.cancure.cpa.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.cancure.cpa.controller.beans.CampPatientBean;
import org.cancure.cpa.controller.beans.CampPatientTestResultsBean;
import org.cancure.cpa.persistence.entity.Camp;
import org.cancure.cpa.persistence.entity.CampPatient;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.persistence.repository.CampPatientRepository;
import org.cancure.cpa.persistence.repository.CampRepository;
import org.cancure.cpa.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CampServiceImpl implements CampService {

    @Autowired
    CampRepository campRepo;
    
    @Autowired
    CampPatientRepository campPatientRepo;

    @Override
    public Camp saveCamp(Camp camp) {

        return campRepo.save(camp);
    }

    @Override
    public Iterable<Camp> getCampsInAMonth(int month, int year) {
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.clear();
    	calendar.set(Calendar.MONTH, month-1);
    	calendar.set(Calendar.YEAR, year);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	Date startDate = calendar.getTime();
    	
    	calendar.add(Calendar.MONTH, 1);
    	Date endDate = calendar.getTime();
    	
    	return campRepo.getCampsInAMonth(startDate, endDate);
    }

    @Override
    public Camp getCamp(Integer Camp_id) {
        return campRepo.findOne(Camp_id);
    }
    
    @Override
    public HSSFWorkbook generateCampReport(Integer campId) {
    	Camp camp = campRepo.findOne(campId);
    	if (camp == null) {
    		return null;
    	}
    	
    	List<Object[]> campPatients = campPatientRepo.findCampPatients(camp.getCampId());
    	
    	HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Report");
        
        HSSFRow rowhead = sheet.createRow((short)0);
        rowhead.createCell(0).setCellValue("Camp Name");
        rowhead.createCell(1).setCellValue(camp.getCampName());
        rowhead.createCell(2).setCellValue("Date");
        rowhead.createCell(3).setCellValue(CommonUtil.formatDateSimple(camp.getCampDate()));
        rowhead.createCell(4).setCellValue("Venue");
        rowhead.createCell(5).setCellValue(camp.getCampPlace());
        
        HSSFRow row = sheet.createRow((short)1);
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        style.setFont(font);
        
        row.createCell(0).setCellValue("UID");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Age");
        row.createCell(3).setCellValue("Gender");
        row.createCell(4).setCellValue("Mobile");
        row.createCell(5).setCellValue("Tests Conducted");
        row.createCell(6).setCellValue("Results");
        setRowStyle(row, style, 6);
        
        if (campPatients != null) {
        	Map<Long, CampPatientBean> map = new HashMap<>();
        	short index = 2;
        	for (Object[] campList : campPatients) {
        		CampPatient cp = (CampPatient)campList[0];
        		CampPatientTestResults cpTr = (CampPatientTestResults)campList[1];
        		
        		CampPatientBean cpBean = map.get(cp.getCampPatientId());
        		if (cpBean == null) {
        			cpBean = new CampPatientBean();
        			BeanUtils.copyProperties(cp, cpBean);
        			map.put(cp.getCampPatientId(), cpBean);
        		}
        		
        		if (cpTr != null) {
        			CampPatientTestResultsBean cpTrBean = new CampPatientTestResultsBean();
        			BeanUtils.copyProperties(cpTr, cpTrBean);
        			cpBean.getCampPatientTestResultsBean().add(cpTrBean);
        		}
        	}
        	
        	for (CampPatientBean cpb : map.values()) {
        		HSSFRow dataRow = sheet.createRow(index++);
        		dataRow.createCell(0).setCellValue(cpb.getUid());
        		dataRow.createCell(1).setCellValue(cpb.getName());
        		dataRow.createCell(2).setCellValue(cpb.getAge());
        		dataRow.createCell(3).setCellValue(cpb.getGender());
        		dataRow.createCell(4).setCellValue(cpb.getPhone());
        		dataRow.createCell(5).setCellValue( flattenTestNames(cpb.getCampPatientTestResultsBean()));
        		dataRow.createCell(6).setCellValue(flattenTestResults(cpb.getCampPatientTestResultsBean()));
        	}
        }
        
        return workbook;
    }

    private void setRowStyle(HSSFRow row, CellStyle style, int count) {
		for (int i=0; i <= count; i++) {
			row.getCell(i).setCellStyle(style);
		}
	}

	private String flattenTestNames(List<CampPatientTestResultsBean> testResults) {
    	if (testResults != null) {
    		StringBuilder tests = new StringBuilder(); 
    		for (CampPatientTestResultsBean cptr: testResults) {
    			tests.append(cptr.getTestName()).append(", ");
    		}
    		
    		return tests.substring(0, tests.length()-2);
    	}
    	
    	return "";
    }
    
    private String flattenTestResults(List<CampPatientTestResultsBean> testResults) {
    	if (testResults != null) {
    		StringBuilder tests = new StringBuilder();
    		for (CampPatientTestResultsBean cptr: testResults) {
    			tests.append(cptr.getTestResultText()).append("\n");
    		}
    		
    		return tests.substring(0, tests.length()-1);
    	}
    	
    	return "";
    }

    @Transactional
    @Override
    public void updatePatientCount(Integer campId) {
        
        Integer patientCount = (campRepo.findOne(campId).getPatientCount())+1;
        campRepo.updatePatientCount(patientCount, campId);
        
    }
}
