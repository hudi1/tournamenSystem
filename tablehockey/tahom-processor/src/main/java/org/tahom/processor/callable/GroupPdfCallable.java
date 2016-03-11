package org.tahom.processor.callable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.processor.service.group.dto.GroupPageDto;
import org.tahom.processor.service.participant.dto.ParticipantDto;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GroupPdfCallable extends AbstractPdfCallable<GroupPageDto> {

	public GroupPdfCallable(String path, GroupPageDto dto) {
		super(path, dto);
	}

	private static final String FILE_TYPE = "group";

	@Override
	protected String getFileName(String path) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyyHH-mm-ss");
		String fileName = path + FILE_TYPE + df.format(new Date()) + ".pdf";
		return fileName;
	}

	@Override
	protected void processDocument(Document document, GroupPageDto groupPageDto, PdfWriter writer) throws Exception {
		List<ParticipantDto> players = groupPageDto.getParticipants();

		PdfPTable pdfTable = new PdfPTable(2 + players.size() + 3);

		pdfTable.setKeepTogether(true);
		pdfTable.setSplitLate(false);
		pdfTable.setSplitRows(false);
		pdfTable.setTotalWidth(800);
		pdfTable.setLockedWidth(true);

		float[] widths = new float[2 + players.size() + 3];
		widths[0] = 1.5f;
		widths[1] = 5f;
		for (int i = 2; i < widths.length; i++) {
			widths[i] = 1.5f;
		}
		widths[2 + players.size()] = 2.5f;
		widths[2 + players.size() + 1] = 1.75f;

		pdfTable.setWidths(widths);

		pdfTable.addCell(createCenterAlignBorderCell(""));
		pdfTable.addCell(createCenterAlignBorderCell("name"));

		for (int i = 0; i < players.size(); i++) {
			pdfTable.addCell(createCenterAlignBorderSmallCell(i + 1 + ""));
		}

		pdfTable.addCell(createCenterAlignBorderSmallCell("score"));
		pdfTable.addCell(createCenterAlignBorderSmallCell("points"));
		pdfTable.addCell(createCenterAlignBorderSmallCell("rank"));

		for (ParticipantDto participant : players) {
			if (participant.getRank() != null) {
				pdfTable.addCell(createCenterAlignBorderSmallCell(participant.getRank().toString()));
			} else {
				pdfTable.addCell(createCenterAlignBorderSmallCell(""));
			}
			pdfTable.addCell(createCenterAlignBorderSmallCell(participant.getShortName()));

			for (GameDto game : participant.getGames()) {
				if (game.getResult() != null) {
					pdfTable.addCell(createCenterAlignBorderSmallCell(game.getResult().toString()));
				} else {
					pdfTable.addCell(createCenterAlignBorderSmallCell(""));
				}
			}

			pdfTable.addCell(createCenterAlignBorderSmallCell(participant.getScore().toString()));
			pdfTable.addCell(createCenterAlignBorderSmallCell(participant.getPoints().toString()));
			if (participant.getRank() != null) {
				pdfTable.addCell(createCenterAlignBorderSmallCell(participant.getRank().toString()));
			} else {
				pdfTable.addCell(createCenterAlignBorderSmallCell(""));
			}
		}

		document.add(new Paragraph("Group: " + groupPageDto.getGroup().getName()));
		document.add(new Paragraph(""));
		document.add(pdfTable);
	}

	@Override
	protected void initDocument(Document document) {
		document.open();
		document.setPageSize(PageSize.A4.rotate());
		document.newPage();
	}

	@Override
	protected String getFileType() {
		return FILE_TYPE;
	}
}
