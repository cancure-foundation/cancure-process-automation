package org.cancure.cpa.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientBean;
import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.service.PatientService;
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

	@Autowired
	private PatientService patientService;

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

		Rectangle pagesize = new Rectangle(400f, 245f);
		Document document = new Document(pagesize, 10f, 72f, 108f, 180f);

		String imageLoc = "src/main/webapp/images/logo.jpg";
		// String imageLoc = "C:\\Users\\sudo\\cancure\\matureman1-512.png";
		// C:\Users\sudo\git\cancure-process-automation\src\main\webapp\images
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(fileSavePath + "/" + patient.getPrn() + "/IDCard" + patient.getPrn() + ".pdf"));
		// step 3
		document.open();
		PdfContentByte canvas = writer.getDirectContent();

		printText(canvas, 137, 245 - 32, "CANCURE Foundation", 20, 1.5f);
		printText(canvas, 137, 245 - 48, "Regd. Office: 60/3285, Benrub, P. K. Devoor Road, ", 9, 0.2f);
		printText(canvas, 204, 245 - 63, "Perumanoor, Cochin -15 ", 9, 0.2f);
		printText(canvas, 180, 245 - 77, "7025 00 33 33, 9846 031 667 ", 9, 0.2f);
		printText(canvas, 155, 245 - 91, "www.cancure.in", 9, 0.2f);
		printText(canvas, 291, 245 - 91, "info@cancure.in", 9, 0.2f);
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

		String basePath = "C:\\Users\\sudo\\cancure\\upload";
		String docPath = null;
		List<PatientDocumentBean> patDocs = patient.getDocument();
		for (PatientDocumentBean patDoc : patDocs) {
			if (patDoc.getDocCategory().equals("Profile")) {
				docPath = patDoc.getDocPath();
			}
		}

		if (docPath != null) {
			String filePath = basePath + "/" + docPath;
			printImage(canvas, 10, 245 - 232, 114, 126, filePath);
		}

		printImage(canvas, 10, 245 - 96, 100, 100, imageLoc);
		printTransparentImage(canvas, 127, 245 - 232, 273, 226, imageLoc);

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

	private void printTransparentImage(PdfContentByte canvas, float x, float y, float scalex, float scaley,
			String imageLoc) throws MalformedURLException, IOException, DocumentException {
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

	private void printText(PdfContentByte canvas, float x, float y, String text, float fontSize, float lineWidth)
			throws DocumentException, IOException {
		canvas.saveState();
		canvas.beginText();
		canvas.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
		canvas.setCharacterSpacing(0.5f);
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
}
