package org.cancure.cpa.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class IDCardGenerator {

	public static void main(String[] args) throws DocumentException, MalformedURLException, IOException {
		Rectangle pagesize = new Rectangle(400f, 272f);
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
		canvas.saveState();
		canvas.beginText();
		canvas.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
		canvas.setLineWidth(1.5f);
		canvas.setRGBColorStroke(0x00, 0x00, 0x00);
		canvas.setRGBColorFill(0x00, 0x00, 0x00);
		BaseFont bf = BaseFont.createFont("src/main/webapp/fonts/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		canvas.setFontAndSize(bf, 20);
		canvas.setTextMatrix(73, 272-38);
		canvas.showText("CANCURE Foundation");
		canvas.endText();
		canvas.restoreState();

		Image cancureLog = Image.getInstance(imageLoc);
		cancureLog.setAbsolutePosition(285, 180);
		cancureLog.scaleToFit(100, 100);
		canvas.addImage(cancureLog);
		
		// step 5
		document.close();
	}
}
