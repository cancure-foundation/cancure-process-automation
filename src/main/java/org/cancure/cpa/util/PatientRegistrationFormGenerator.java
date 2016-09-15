package org.cancure.cpa.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

import org.cancure.cpa.controller.beans.PatientBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class PatientRegistrationFormGenerator {

	public void generateRegistrationForm(PatientBean patient) throws Exception {

		// Convert java bean to Map representation
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> props = mapper.convertValue(patient, Map.class);
		
		// Process freemarker template to create HTML
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setClassForTemplateLoading(this.getClass(), "/");
		Template template = cfg.getTemplate("templates/PatientRegistrationForm.ftl");
		
		Writer out = new StringWriter();
		template.process(props, out);
		
		// Convert HTML to PDF using itext
		OutputStream file = new FileOutputStream(new File("C:\\Users\\sudo\\cancure\\regForm.pdf"));
	    Document document = new Document();
	    PdfWriter writer = PdfWriter.getInstance(document, file);
	    document.open();
	    InputStream is = new ByteArrayInputStream(out.toString().getBytes());
	    XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
	    document.close();
	    file.close();
	}

	public static void main(String args[]) throws Exception{
		PatientBean bean = new PatientBean();
		bean.setName("John Doe");
		bean.setBloodGroup("AB+");
		bean.setDob(new Date());
		bean.setGender("M");
		
		new PatientRegistrationFormGenerator().generateRegistrationForm(bean);
	}
}
