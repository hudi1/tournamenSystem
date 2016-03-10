package org.tahom.processor.callable;

import org.tahom.processor.service.finalStanding.dto.FinalStandingDto;
import org.tahom.processor.service.finalStanding.dto.FinalStandingPageDto;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class FinalStandingPdfCallable extends AbstractPdfCallable<FinalStandingPageDto> {

	public FinalStandingPdfCallable(String path, FinalStandingPageDto dto) {
		super(path, dto);
	}

	private static final String FILE_TYPE = "finalStanding";

	@Override
	protected void processDocument(Document document, FinalStandingPageDto finalStandingsDto, PdfWriter writer)
	        throws Exception {
		PdfPTable pdfTable = new PdfPTable(3);
		pdfTable.setLockedWidth(true);
		pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfTable.setTotalWidth(400);
		pdfTable.setWidths(new float[] { 2f, 7f, 3f });

		for (FinalStandingDto finalStanding : finalStandingsDto.getFinalStandings()) {
			pdfTable.addCell(createLeftAlignSmallCell(finalStanding.getRank() + "."));
			pdfTable.addCell(createLeftAlignSmallCell(finalStanding.getName()));
			pdfTable.addCell(createLeftAlignSmallCell(finalStanding.getClub()));
		}

		document.add(pdfTable);
	}

	@Override
	protected void initDocument(Document document) {
		document.open();
		document.newPage();
	}

	@Override
	protected String getFileType() {
		return FILE_TYPE;
	}
}