package org.cancure.cpa.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.cancure.cpa.persistence.entity.Camp;
import org.cancure.cpa.service.CampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CampController {

	
	@Autowired
	CampService campservice;

	@RequestMapping(method = RequestMethod.POST, value = "/camp")
	public Camp saveCamp(@RequestBody Camp camp) {
		camp.setPatientCount(0);
		return campservice.saveCamp(camp);
	}

	@RequestMapping(method = RequestMethod.GET, value="/camp/{month}/{year}")
	public Iterable<Camp> listCamp(@PathVariable("month") String monthStr, @PathVariable("year") String yearStr) throws Exception {
		Integer month = Integer.parseInt(monthStr);
		Integer year = Integer.parseInt(yearStr);
		
		if (month < 1 && month > 12) {
			throw new Exception("Month should be between 1 and 12");
		}
				
		if (year < 0) {
			throw new Exception("Enter a proper year");
		}
		
		return campservice.getCampsInAMonth(month, year);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/camp/{campId}")
	public Camp getCamp(@PathVariable("campId") Integer campId) {
		return campservice.getCamp(campId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/camp/report/{campId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadCampReport(@PathVariable("campId") Integer campId, HttpServletResponse response) throws IOException {
		
		HSSFWorkbook excel = campservice.generateCampReport(campId);
		returnMimeContent(campId, excel, response);
	}
	
	private void returnMimeContent(Integer campId, HSSFWorkbook excel, HttpServletResponse response) throws IOException {
    	
        response.setHeader("Content-Disposition", "attachment; filename=CampReport" + campId + ".xls");        
        response.setContentType("application/vnd.ms-excel");
        excel.write(response.getOutputStream());
        response.flushBuffer();
    }
}
