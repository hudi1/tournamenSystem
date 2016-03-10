package org.tahom.processor.callable;

import org.tahom.processor.service.playOffGame.dto.PlayOffGameDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffGroupDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffPageDto;
import org.tahom.repository.model.Results;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PlayOffPdfCallable extends AbstractPdfCallable<PlayOffPageDto> {

	public PlayOffPdfCallable(String path, PlayOffPageDto dto) {
		super(path, dto);
	}

	private static final String FILE_TYPE = "playOff";

	@Override
	protected void processDocument(Document document, PlayOffPageDto playOffPageDto, PdfWriter writer) throws Exception {
		PdfPTable pdfTable = new PdfPTable(3);
		pdfTable.setLockedWidth(true);
		pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);

		pdfTable.setTotalWidth(800);
		pdfTable.setWidths(new float[] { 6f, 6f, 18f });

		for (PlayOffGroupDto group : playOffPageDto.getPlayOffGroups()) {
			pdfTable.addCell(createLeftAlignCell(group.getName()));
			pdfTable.addCell(createEmptyCell());
			pdfTable.addCell(createEmptyCell());

			for (PlayOffGameDto playOffGame : group.getPlayOffGames()) {

				// TODO ako prelozit roundName
				pdfTable.addCell(createLeftAlignCell(playOffGame.getRoundName()));

				// TODO po jednom ?
				if (playOffGame.getPlayerName() != null && playOffGame.getOpponentName() != null) {
					String playerName = playOffGame.getPlayerName().split(" ", 2)[0].charAt(0) + ". "
					        + playOffGame.getPlayerName().split(" ", 2)[1];
					String oponentName = playOffGame.getOpponentName().split(" ", 2)[0].charAt(0) + ". "
					        + playOffGame.getOpponentName().split(" ", 2)[1];
					pdfTable.addCell(createLeftAlignCell(playerName + " : " + oponentName));

					Results results = playOffGame.getResult();

					if (results != null) {
						pdfTable.addCell(createLeftAlignCell(results.toString()));
					} else {
						pdfTable.addCell(createEmptyCell());
					}
				} else {
					pdfTable.addCell(createEmptyCell());
					pdfTable.addCell(createEmptyCell());
				}
			}
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
