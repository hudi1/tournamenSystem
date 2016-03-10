package org.tahom.processor.callable;

import java.util.List;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.processor.service.group.dto.GroupPageDto;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class SchedulePdfCallable extends AbstractPdfCallable<GroupPageDto> {

	public SchedulePdfCallable(String path, GroupPageDto dto) {
		super(path, dto);
	}

	private static final String FILE_TYPE = "schedule";

	@Override
	protected void processDocument(Document document, GroupPageDto groupPageDto, PdfWriter writer) throws Exception {
		List<GameDto> schedule = groupPageDto.getSchedule();

		PdfContentByte canvas = writer.getDirectContent();
		ColumnText column = new ColumnText(canvas);
		float[][] x = { { 0, 210 }, { 210, 420 }, { 420, 630 }, { 630, 840 } };

		column.addElement(createTable(schedule));
		int count = 0;
		int status = ColumnText.START_COLUMN;
		while (ColumnText.hasMoreText(status)) {
			column.setSimpleColumn(x[count][0], 5, x[count][1], 590);
			status = column.go();
			if (++count > 3) {
				count = 0;
				document.newPage();
			}
		}
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

	private PdfPTable createTable(List<GameDto> schedule) throws Exception {
		PdfPTable pdfTable = new PdfPTable(6);

		pdfTable.setKeepTogether(true);
		pdfTable.setSplitLate(false);
		pdfTable.setSplitRows(false);

		pdfTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		pdfTable.setHeaderRows(0);
		pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfTable.setTotalWidth(210);
		pdfTable.setLockedWidth(true);
		pdfTable.setWidths(new float[] { 2f, 15f, 2f, 1f, 2f, 0.5f });
		int round = 0;

		// TODO kde vopchat nazov skupiny
		for (GameDto game : schedule) {
			if (game.getRound() != round) {
				addRoundTableRow(pdfTable, game.getRound().toString());
				round++;
			}

			addPlayerTableRow(pdfTable, game);
		}
		return pdfTable;
	}

	private void addPlayerTableRow(PdfPTable pdfTable, GameDto game) {

		pdfTable.addCell(createLeftAlignSmallCell(game.getHockey().toString() + "."));

		String homeParticipant;
		String awayParticipant;

		if (game.getPlayerName() != null) {
			homeParticipant = game.getPlayerName().split(" ", 2)[0].charAt(0) + ". "
			        + game.getPlayerName().split(" ", 2)[1];
		} else {
			homeParticipant = UNDEFINED_PLAYER;
		}

		if (game.getOpponentName() != null) {
			awayParticipant = game.getOpponentName().split(" ", 2)[0].charAt(0) + ". "
			        + game.getOpponentName().split(" ", 2)[1];
		} else {
			awayParticipant = UNDEFINED_PLAYER;
		}
		pdfTable.addCell(createLeftAlignSmallCell(homeParticipant + " - " + awayParticipant));
		pdfTable.addCell(createBorderCell("  "));
		pdfTable.addCell(createCell(":"));
		pdfTable.addCell(createBorderCell("  "));
		pdfTable.addCell(createRightBorderCell(""));
	}

	private void addRoundTableRow(PdfPTable pdfTable, String round) {
		pdfTable.addCell(createEmptyTopCell());
		pdfTable.addCell(createTopBorderCell(round + "."));
		pdfTable.addCell(createEmptyTopCell());
		pdfTable.addCell(createEmptyTopCell());
		pdfTable.addCell(createEmptyTopCell());
		pdfTable.addCell(createRightTopBorderSmallCell(""));
	}

}
