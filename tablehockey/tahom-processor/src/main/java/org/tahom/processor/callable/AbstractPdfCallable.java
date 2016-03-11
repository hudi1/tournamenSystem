package org.tahom.processor.callable;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.wicket.util.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.PdfTournamentException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class AbstractPdfCallable<I> implements Callable<File> {

	protected final Logger logger = LoggerFactory.getLogger(AbstractPdfCallable.class);

	protected final String UNDEFINED_PLAYER = "---";
	protected final String UNDEFINED_FIELD = "-";

	private static Font defaultFont;

	private String path;
	private I dto;

	static {
		defaultFont = FontFactory.getFont(BaseFont.HELVETICA, BaseFont.CP1250);
	}

	public AbstractPdfCallable(String path, I dto) {
		this.path = path;
		this.dto = dto;
	}

	@Override
	public File call() throws Exception {
		return createFile(path, dto);
	}

	private File createFile(String path, I input) {
		Document document = new Document(PageSize.A4, 5, 5, 5, 5);
		File file = null;
		try {
			file = new File(getFileName(path));
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			initDocument(document);
			processDocument(document, input, writer);
			closePdfDocument(document, writer);
		} catch (Exception e) {
			deleteFile(file);
			logger.error("Error during creating pdf file", e);
			throw new PdfTournamentException(e.getMessage());
		} finally {
			if (file != null) {
				file.deleteOnExit();
			}
		}
		return file;
	}

	protected abstract void processDocument(Document document, I input, PdfWriter writer) throws Exception;

	protected abstract void initDocument(Document document);

	protected abstract String getFileType();

	protected String getFileName(String path) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyyHH-mm-ss");
		String fileName = path + getFileType() + df.format(new Date()) + ".pdf";
		return fileName;
	}

	protected PdfPCell createRightTopBorderSmallCell(String text) {
		PdfPCell cell = createCell(text);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT + Rectangle.TOP);
		return cell;
	}

	protected PdfPCell createRightBorderCell(String text) {
		PdfPCell cell = createCell(text);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT);
		return cell;
	}

	protected PdfPCell createCenterAlignBorderCell(String text) {
		PdfPCell cell = createCell(text);
		cell.setBorder(Rectangle.BOX);
		cell.setNoWrap(true);
		return cell;
	}

	protected PdfPCell createCenterAlignBorderSmallCell(String text) {
		PdfPCell cell = createCell(text, 10);
		cell.setBorder(Rectangle.BOX);
		cell.setNoWrap(true);
		return cell;
	}

	protected PdfPCell createBorderCell(String text) {
		PdfPCell cell = createCell(text);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.BOX);
		return cell;
	}

	protected PdfPCell createLeftAlignCell(String text) {
		PdfPCell cell = createCell(text);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setNoWrap(true);
		return cell;
	}

	protected PdfPCell createLeftAlignSmallCell(String text) {
		PdfPCell cell = createCell(text, 10);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setNoWrap(true);
		return cell;
	}

	protected PdfPCell createLeftAlignBorderSmallCell(String text) {
		PdfPCell cell = createCell(text, 10);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.BOX);
		cell.setNoWrap(true);
		return cell;
	}

	protected PdfPCell createCell(String text) {
		return createCell(text, Font.DEFAULTSIZE);
	}

	protected PdfPCell createSmallCell(String text) {
		return createCell(text, 10);
	}

	protected PdfPCell createCell(String text, int size) {
		PdfPCell cell = new PdfPCell(new Phrase(text, new Font(defaultFont.getBaseFont(), size)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}

	protected PdfPCell createEmptyCell() {
		return createCell(" ");
	}

	protected PdfPCell createSmallEmptyCell() {
		return createCell(" ", 10);
	}

	protected PdfPCell createHalfEmptyCell() {
		return createCell(" ", 5);
	}

	protected PdfPCell createEmptyTopCell() {
		PdfPCell cell = createCell(" ");
		cell.setBorder(Rectangle.TOP);
		return cell;
	}

	protected PdfPCell createTopBorderCell(String string) {
		PdfPCell cell = createCell(string);
		cell.setBorder(Rectangle.TOP);
		return cell;
	}

	protected <K, V> LinkedHashMap<K, V> subMap(LinkedHashMap<K, V> map, int fromIndex, int toIndex) {
		LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();

		int i = 0;
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (i >= fromIndex && i <= toIndex) {
				result.put(entry.getKey(), entry.getValue());
			}
			i++;
		}
		return result;
	}

	protected Document initPdfDocument(Document document) {
		document.open();
		return document;
	}

	protected void deleteFile(File file) {
		if (file != null) {
			Files.remove(file);
		}
	}

	protected void closePdfDocument(Document document, PdfWriter writer) {
		if (document.getPageNumber() == 0) {
			writer.setPageEmpty(false);
			document.newPage();
		}
		document.close();
	}

}