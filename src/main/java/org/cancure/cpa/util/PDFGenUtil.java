package org.cancure.cpa.util;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.ServletContext;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;

public class PDFGenUtil {
	
	public static void printImage(PdfContentByte canvas, float x, float y, float scalex, float scaley, Image cancureLog)
			throws MalformedURLException, IOException, DocumentException {
		canvas.saveState();
		cancureLog.setAbsolutePosition(x, y);
		cancureLog.scaleToFit(scalex, scaley);
		canvas.addImage(cancureLog);
		canvas.restoreState();
	}
	
	public static void printImage(PdfContentByte canvas, float x, float y, float scalex, float scaley, String imageLoc)
			throws MalformedURLException, IOException, DocumentException {
		canvas.saveState();
		Image cancureLog = Image.getInstance(imageLoc);
		cancureLog.setAbsolutePosition(x, y);
		cancureLog.scaleToFit(scalex, scaley);
		canvas.addImage(cancureLog);
		canvas.restoreState();
	}

	public static void printTransparentImage(PdfContentByte canvas, float x, float y, float scalex, float scaley,
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

	public static void printText(BaseFont bf, PdfContentByte canvas, float x, float y, String text, float fontSize, float lineWidth)
			throws DocumentException, IOException {
		canvas.saveState();
		canvas.beginText();
		canvas.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
		canvas.setCharacterSpacing(0.5f);
		canvas.setLineWidth(lineWidth);
		canvas.setRGBColorStroke(0x00, 0x00, 0x00);
		canvas.setRGBColorFill(0x00, 0x00, 0x00);
		
		canvas.setFontAndSize(bf, fontSize);
		canvas.setTextMatrix(x, y);
		canvas.showText(text);
		canvas.endText();
		canvas.restoreState();
	}

	public static void drawLine(PdfContentByte canvas, float fromx, float fromy, float tox, float toy) {
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
