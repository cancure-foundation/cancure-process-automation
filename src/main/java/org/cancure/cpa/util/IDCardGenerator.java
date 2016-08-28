package org.cancure.cpa.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.service.PatientDocumentService;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;

public class IDCardGenerator {

	@Autowired
    private PatientDocumentService patientDocumentService;
	
	public static void main(String[] args) throws Exception {
		
		PatientBean bean = new PatientBean();
		bean.setName("Ann Maria");
		bean.setDob(new Date());
		bean.setContact("98374837273");
		bean.setAddress("sdf dsfs dfs, sdf sdf sddsf");
		bean.setBloodGroup("A+");
		bean.setGender("Male");
		List<PatientDocumentBean> list = new ArrayList<>();
		PatientDocumentBean profile = new PatientDocumentBean();
		profile.setDocCategory("Profile");
		profile.setDocId(67);
		profile.setDocPath("/34/67_14CG24709.jpg");
		list.add(profile);
		bean.setDocument(list);
		
		new IDCardGenerator().generateCard(bean);
	}

	private void generateCard(PatientBean patient) throws Exception {
	
		Rectangle pagesize = new Rectangle(400f, 245f);
		Document document = new Document(pagesize, 10f, 72f, 108f, 180f);

		String imageLoc = "src/main/webapp/images/logo.jpg";
		//String imageLoc = "C:\\Users\\sudo\\cancure\\matureman1-512.png";
		// C:\Users\sudo\git\cancure-process-automation\src\main\webapp\images
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("C:\\Users\\sudo\\cancure\\cancuepdf.pdf"));
		// step 3
		document.open();
		PdfContentByte canvas = writer.getDirectContent();
		
		printText(canvas, 137, 245-32, "CANCURE Foundation", 20, 1.5f);
		printText(canvas, 137, 245-48, "Regd. Office: 60/3285, Benrub, P. K. Devoor Road, ", 9, 0.2f);
		printText(canvas, 204, 245-63, "Perumanoor, Cochin -15 ", 9, 0.2f);
			
		printText(canvas, 141, 245-138, "Name : ", 10, 0.2f);
		printText(canvas, 199, 245-138, patient.getName(), 12, 1f);
		
		printText(canvas, 154, 245-157, "DOB : ", 10, 0.2f);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		printText(canvas, 199, 245-157, sdf.format(patient.getDob()), 12, 1f);
		
		/*printText(canvas, 48, 272-143, "Gender", 10, 0.2f);
		printText(canvas, 48, 272-159, patient.getGender(), 15, 1f);
		*/
		/*PatientDocument patDoc = patientDocumentService.findOne(id);
        String filePath = fileSavePath + patDoc.getDocPath();
        InputStream is = new FileInputStream(filePath);
        */

		String basePath = "C:\\Users\\sudo\\cancure\\upload";
		String docPath = null;
		List<PatientDocumentBean> patDocs = patient.getDocument();
		for (PatientDocumentBean patDoc : patDocs) {
			if (patDoc.getDocCategory().equals("Profile")) {
				docPath = patDoc.getDocPath();
			}
		}
		
		if (docPath != null) {
			String filePath = basePath + "/" +  docPath;
			printImage(canvas, 10, 245 - 232, 114, 126, filePath);
		}
		
		printImage(canvas, 10, 245-96, 100, 100, imageLoc);
		printTransparentImage(canvas, 127, 245-232, 273, 226, imageLoc);
		
		// step 5
		document.close();
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
	
	private void printTransparentImage(PdfContentByte canvas, float x, float y, float scalex, float scaley, String imageLoc)
			throws MalformedURLException, IOException, DocumentException {
		Image cancureLog = Image.getInstance(imageLoc);
		canvas.saveState();
		PdfGState state = new PdfGState();
		state.setFillOpacity(0.05f);
		canvas.setGState(state);
		cancureLog.setAbsolutePosition(x, y);
		cancureLog.scaleToFit(scalex, scaley);
		canvas.addImage(cancureLog);
		canvas.restoreState();
	}
	
	private void printText(PdfContentByte canvas, float x, float y, String text, float fontSize, float lineWidth) throws DocumentException, IOException {
		canvas.saveState();
		canvas.beginText();
		canvas.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
		canvas.setCharacterSpacing(1f);
		canvas.setLineWidth(lineWidth);
		canvas.setRGBColorStroke(0x00, 0x00, 0x00);
		canvas.setRGBColorFill(0x00, 0x00, 0x00);
		BaseFont bf = BaseFont.createFont("src/main/webapp/fonts/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		canvas.setFontAndSize(bf, fontSize);
		canvas.setTextMatrix(x, y);
		canvas.showText(text);
		canvas.endText();
		canvas.restoreState();
	}
}
