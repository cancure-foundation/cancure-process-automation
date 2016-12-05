package org.cancure.cpa.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.persistence.entity.PatientDocument;
import org.cancure.cpa.persistence.repository.SettingsRepository;
import org.cancure.cpa.service.PatientDocumentService;
import org.cancure.cpa.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class IDCardGenerator {

    //@Autowired
    private ServletContext servletContext;
    
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private SettingsRepository settingsRepository;

	@Autowired
	private PatientDocumentService patientDocumentService;
	   
	@Value("${spring.files.save.path}")
	private String fileSavePath;

	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * PatientBean bean = new PatientBean(); bean.setName("Ann Maria");
	 * bean.setDob(new Date()); bean.setContact("98374837273"); bean.setAddress(
	 * "Manakkakadavu house, Malikampeedika, Aluva, Ernakulam PIN 6899434");
	 * bean.setBloodGroup("A+"); bean.setGender("Male");
	 * List<PatientDocumentBean> list = new ArrayList<>(); PatientDocumentBean
	 * profile = new PatientDocumentBean(); profile.setDocCategory("Profile");
	 * profile.setDocId(67); profile.setDocPath("/34/67_14CG24709.jpg");
	 * list.add(profile); bean.setDocument(list);
	 * 
	 * new IDCardGenerator().generateCard(bean); }
	 */
	public void generateCard(Integer id) throws Exception {

		PatientBean patient = patientService.get(id);
		List<PatientDocument>documentList=new ArrayList<PatientDocument>();
		List<PatientDocumentBean>documentBean=new ArrayList<PatientDocumentBean>();
		documentList=patientDocumentService.findByTaskId(patient.getTaskId());
	        for(PatientDocument patientDocument:documentList){
	            PatientDocumentBean patientDocumentBean=new PatientDocumentBean();
	            BeanUtils.copyProperties(patientDocument, patientDocumentBean);
	            documentBean.add(patientDocumentBean);
	        }
	    patient.setDocument(documentBean);
		Rectangle pagesize = new Rectangle(400f, 245f);
		Document document = new Document(pagesize, 10f, 72f, 108f, 180f);

		String imageLoc = "images/logo.jpg";
		// String imageLoc = "C:\\Users\\sudo\\cancure\\matureman1-512.png";
		// C:\Users\sudo\git\cancure-process-automation\src\main\webapp\images
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(fileSavePath + "/" + patient.getPrn() + "/IDCard" + patient.getPrn() + ".pdf"));
		// step 3
		document.open();
		PdfContentByte canvas = writer.getDirectContent();

		printText(canvas, 137, 245 - 32, getSetting(1), 20, 1.5f);
		printText(canvas, 137, 245 - 48, getSetting(2), 9, 0.2f);
		printText(canvas, 204, 245 - 63, getSetting(3), 9, 0.2f);
		printText(canvas, 180, 245 - 77, getSetting(4), 9, 0.2f);
		printText(canvas, 155, 245 - 91, getSetting(5), 9, 0.2f);
		printText(canvas, 291, 245 - 91, getSetting(6), 9, 0.2f);
		drawLine(canvas, 129, 245 - 106, 397, 245 - 106);

		printText(canvas, 147, 245 - 120, "PIDN : ", 10, 0.2f);
		printText(canvas, 199, 245 - 120, patient.getPidn() + "", 11, 0.8f);
		printText(canvas, 141, 245 - 138, "Name : ", 10, 0.2f);
		printText(canvas, 199, 245 - 138, patient.getName(), 11, 0.8f);

		printText(canvas, 154, 245 - 157, "DOB : ", 10, 0.2f);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		printText(canvas, 199, 245 - 157, sdf.format(patient.getDob()), 11, 0.8f);
		printText(canvas, 291, 245 - 157, "Gender : ", 10, 0.2f);
		printText(canvas, 343, 245 - 157, patient.getGender(), 11, 0.8f);

		printText(canvas, 133, 245 - 176, "Address : ", 10, 0.2f);
		String address = patient.getAddress();
		int length = patient.getAddress().length();
		printText(canvas, 199, 245 - 176, address.substring(0, length > 28 ? 28 : length), 11, 0.4f);
		if (length > 28) {
			printText(canvas, 199, 245 - 192, address.substring(28, length > 56 ? 56 : length), 11, 0.4f);
		}
		if (length > 56) {
			printText(canvas, 199, 245 - 210, address.substring(56, length), 11, 0.4f);
		}

		printText(canvas, 143, 245 - 225, "Phone : ", 10, 0.2f);
		printText(canvas, 199, 245 - 225, patient.getContact(), 11, 0.8f);

		printText(canvas, 134, 245 - 238, "Blood Group : ", 10, 0.2f);
		printText(canvas, 219, 245 - 238, patient.getBloodGroup(), 11, 0.8f);

		/*
		 * printText(canvas, 48, 272-143, "Gender", 10, 0.2f); printText(canvas,
		 * 48, 272-159, patient.getGender(), 15, 1f);
		 */
		/*
		 * PatientDocument patDoc = patientDocumentService.findOne(id); String
		 * filePath = fileSavePath + patDoc.getDocPath(); InputStream is = new
		 * FileInputStream(filePath);
		 */

		String docPath = null;
		List<PatientDocumentBean> patDocs = patient.getDocument();
		for (PatientDocumentBean patDoc : patDocs) {
			if (patDoc.getDocCategory().equals("Profile Image")) {
				docPath = patDoc.getDocPath();
			}
		}

		if (docPath != null) {
			String filePath = fileSavePath + docPath;
			printImage(canvas, 10, 245 - 232, 114, 126, filePath);
		}

		String logoFile = servletContext.getRealPath("/images/logo.jpg");
		Image cancureLog = Image.getInstance(logoFile);
		printImage(canvas, 10, 245 - 96, 100, 100, cancureLog);
		printTransparentImage(canvas, 127, 245 - 232, 273, 226, cancureLog);

		// step 5
		document.close();
	}

	private void printImage(PdfContentByte canvas, float x, float y, float scalex, float scaley, Image cancureLog)
			throws MalformedURLException, IOException, DocumentException {
		canvas.saveState();
		cancureLog.setAbsolutePosition(x, y);
		cancureLog.scaleToFit(scalex, scaley);
		canvas.addImage(cancureLog);
		canvas.restoreState();
	}
	
	private void printImage(PdfContentByte canvas, float x, float y, float scalex, float scaley, String imageLoc)
			throws MalformedURLException, IOException, DocumentException {
		canvas.saveState();
		Image cancureLog = Image.getInstance(imageLoc);
		cancureLog.setAbsolutePosition(x, y);
		cancureLog.scaleToFit(scalex, scaley);
		canvas.addImage(cancureLog);
		canvas.restoreState();
	}

	private void printTransparentImage(PdfContentByte canvas, float x, float y, float scalex, float scaley,
			Image cancureLog) throws MalformedURLException, IOException, DocumentException {
		canvas.saveState();
		PdfGState state = new PdfGState();
		state.setFillOpacity(0.05f);
		canvas.setGState(state);
		cancureLog.setAbsolutePosition(x, y);
		cancureLog.scaleToFit(scalex, scaley);
		canvas.addImage(cancureLog);
		canvas.restoreState();
	}

	private void printText(PdfContentByte canvas, float x, float y, String text, float fontSize, float lineWidth)
			throws DocumentException, IOException {
		canvas.saveState();
		canvas.beginText();
		canvas.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
		canvas.setCharacterSpacing(0.5f);
		canvas.setLineWidth(lineWidth);
		canvas.setRGBColorStroke(0x00, 0x00, 0x00);
		canvas.setRGBColorFill(0x00, 0x00, 0x00);
		BaseFont bf = null;
		try {
		    String fontPath = servletContext.getRealPath("/fonts/FreeSans.ttf");
			bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, 
	                  BaseFont.EMBEDDED);
		} catch (Exception e) {
			bf = BaseFont.createFont(BaseFont.HELVETICA,BaseFont.CP1257, 
	                  BaseFont.EMBEDDED);
		}
		
		canvas.setFontAndSize(bf, fontSize);
		canvas.setTextMatrix(x, y);
		canvas.showText(text);
		canvas.endText();
		canvas.restoreState();
	}

	private void drawLine(PdfContentByte canvas, float fromx, float fromy, float tox, float toy) {
		canvas.saveState();
		canvas.setLineWidth(1f);
		canvas.setRGBColorStroke(0x00, 0x00, 0x00);
		canvas.setRGBColorFill(0x00, 0x00, 0x00);
		canvas.moveTo(fromx, fromy);
		canvas.lineTo(tox, toy);
		canvas.stroke();
		canvas.restoreState();
	}
	
	private String getSetting(Integer id){
		return settingsRepository.findOne(id).getValue();
	}
}
