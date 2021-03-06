package org.cancure.cpa.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.cancure.cpa.persistence.entity.CampPatientTestResults;
import org.cancure.cpa.persistence.entity.PatientBills;
import org.cancure.cpa.persistence.entity.PatientDocument;
import org.cancure.cpa.persistence.entity.PatientVisitDocuments;
import org.cancure.cpa.service.CampPatientTestResultsService;
import org.cancure.cpa.service.PatientBillService;
import org.cancure.cpa.service.PatientDocumentService;
import org.cancure.cpa.service.PatientVisitDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PropertySource("classpath:application.properties")
public class FileController {

    @Value("${spring.files.save.path}")
    private String fileSavePath;
    
    @Autowired
    private Environment env;
    
    @Autowired
    private PatientDocumentService patientDocumentService;
    
    @Autowired
    private PatientVisitDocumentService patientVisitDocumentService;
    
    @Autowired
    private PatientBillService patientBillService;
    
    @Autowired
    private CampPatientTestResultsService campPatientTestResultsService;

    @RequestMapping(value = "/files/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getFile(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {

        PatientDocument patDoc = patientDocumentService.findOne(id);
        String filePath = fileSavePath + patDoc.getDocPath();
        returnMimeContent(filePath, response);
    }
    
    private void returnMimeContent(String filePath, HttpServletResponse response) throws IOException {
    	InputStream is = new FileInputStream(filePath);
        String fileExtension= filePath.substring(filePath.lastIndexOf(".") + 1);
        String fileAbsoluteName = filePath.substring(filePath.lastIndexOf("/") + 1);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileAbsoluteName);        
        String mimeType= URLConnection.guessContentTypeFromName(filePath);
        if (mimeType == null) {
            mimeType = env.getProperty("file.mimetype." + fileExtension);
        }
        
        if (mimeType != null) {
            response.setContentType(mimeType);
        }
        
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }
    
    @RequestMapping("/files/idcard/{prn}")
    public void getIDCard(@PathVariable("prn") String prn, HttpServletResponse response) throws IOException {
    	String filePath = fileSavePath + "/" + prn + "/IDCard"+ prn + ".pdf";
    	returnMimeContent(filePath, response);
    }
    
    @RequestMapping("/files/patientvisit/{id}")
    public void getPatientVisitFiles(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        
        PatientVisitDocuments patVisitDoc = patientVisitDocumentService.getPatientVisitDocuments(id);
        String filePath = fileSavePath + patVisitDoc.getDocPath();
        returnMimeContent(filePath, response);
    }
    
    @RequestMapping("/files/patientbills/{id}")
    public void getPatientBills(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        
        PatientBills patBills = patientBillService.getPatientBills(id);
        String filePath = fileSavePath + patBills.getPartnerBillPath();
        returnMimeContent(filePath, response);
    }
    
    @RequestMapping("/files/testresult/{id}")
    public void getCampTestResults(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        
        CampPatientTestResults campPatientTestResults = campPatientTestResultsService.getCampPatientTestResults(id);
        String filePath = fileSavePath + campPatientTestResults.getTestResultPath();
        returnMimeContent(filePath, response);
    }
}
