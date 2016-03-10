package org.tahom.processor.callable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.processor.service.group.dto.GroupPageDto;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class SheetsPdfCallable extends AbstractPdfCallable<GroupPageDto> {

	public SheetsPdfCallable(String path, GroupPageDto dto) {
		super(path, dto);
	}

	private static final int PLAYERS_PER_PAGE = 6;
	private static final int LISTS_TABLE_WIDTH = 140;
	private static final String FILE_TYPE = "sheets";

	@Override
	protected void processDocument(Document document, GroupPageDto groupPageDto, PdfWriter writer) throws Exception {
		List<GameDto> schedule = groupPageDto.getSchedule();

		LinkedHashMap<String, List<GameDto>> wholeSheets = new LinkedHashMap<String, List<GameDto>>();
		if (!schedule.isEmpty()) {
			int maxRound = schedule.get(schedule.size() - 1).getRound();
			int spaceCount = Math.max(0, (40 / maxRound) - 1);

			for (GameDto game : schedule) {
				if (game.getPlayerName() != null) {
					if (wholeSheets.get(game.getPlayerName()) == null) {
						wholeSheets.put(game.getPlayerName(), new ArrayList<GameDto>());
					}
					wholeSheets.get(game.getPlayerName()).add(game);
				}

				if (game.getOpponentName() != null) {
					if (wholeSheets.get(game.getOpponentName()) == null) {
						wholeSheets.put(game.getOpponentName(), new ArrayList<GameDto>());
					}
					wholeSheets.get(game.getOpponentName()).add(game);
				}
			}

			for (int i = 0; i <= wholeSheets.size() / PLAYERS_PER_PAGE; i++) {
				int toIndex = i * PLAYERS_PER_PAGE + PLAYERS_PER_PAGE - 1;
				if (i == wholeSheets.size()) {
					toIndex = i * PLAYERS_PER_PAGE + wholeSheets.size() % PLAYERS_PER_PAGE;
				}
				Map<String, List<GameDto>> sheets = subMap(wholeSheets, i * PLAYERS_PER_PAGE, toIndex);

				if (sheets.size() == 0) {
					break;
				}

				PdfPTable pdfTable = new PdfPTable(sheets.size());
				pdfTable.setHeaderRows(1);

				pdfTable.setTotalWidth(LISTS_TABLE_WIDTH * sheets.size());
				pdfTable.setLockedWidth(true);
				pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);

				document.setPageSize(PageSize.A4.rotate());
				document.newPage();

				Set<Entry<String, List<GameDto>>> entrySet = sheets.entrySet();

				// hlavicka
				for (Map.Entry<String, List<GameDto>> entry : entrySet) {
					String participantName = entry.getKey();
					pdfTable.addCell(createCell("(" + groupPageDto.getGroup().getName() + ") " + participantName));
				}

				// hokeje
				for (int j = 0; j < sheets.size(); j++) {
					pdfTable.addCell(createLeftAlignSmallCell("                H"));
				}

				for (Map.Entry<String, List<GameDto>> entry : entrySet) {
					List<GameDto> games = entry.getValue();

					PdfPTable nestedTable = new PdfPTable(3);
					nestedTable.setHeaderRows(0);
					nestedTable.setTotalWidth(LISTS_TABLE_WIDTH);
					nestedTable.setLockedWidth(true);
					nestedTable.setWidths(new float[] { 3f, 3f, 12f });
					nestedTable.setExtendLastRow(true);

					int round = 1;
					for (GameDto game : games) {

						if (game.getRound() >= round) {
							while (round != game.getRound()) {
								addEmptryPlayerRow(nestedTable, round);
								addSpaceRows(nestedTable, spaceCount);
								round++;
							}
						}

						addPlayerRow(nestedTable, game, entry.getKey());
						addSpaceRows(nestedTable, spaceCount);
						round++;
					}

					PdfPCell cell = createEmptyCell();
					cell.addElement(nestedTable);
					pdfTable.addCell(cell);
				}

				document.add(pdfTable);
			}
		}
	}

	private void addEmptryPlayerRow(PdfPTable nestedTable, int round) {
		nestedTable.addCell(createSmallCell(round + ")"));
		nestedTable.addCell(createSmallCell(UNDEFINED_FIELD));
		nestedTable.addCell(createLeftAlignSmallCell(UNDEFINED_PLAYER));
	}

	private void addPlayerRow(PdfPTable nestedTable, GameDto game, String participantName) {
		String playerGameName = null;
		String side = "";
		if (participantName != null) {
			if (game.getOpponentName() != null && game.getOpponentName().equals(participantName)) {
				playerGameName = game.getPlayerName();
				side = "S";
			} else if (game.getPlayerName() != null && game.getPlayerName().equals(participantName)) {
				playerGameName = game.getOpponentName();
				side = "F";
			}
		}

		if (playerGameName != null) {
			nestedTable.addCell(createSmallCell(game.getRound() + ")"));
			nestedTable.addCell(createSmallCell(game.getHockey().toString() + side));
			nestedTable.addCell(createLeftAlignSmallCell(playerGameName));
		} else {
			nestedTable.addCell(createSmallCell(UNDEFINED_FIELD));
			nestedTable.addCell(createSmallCell(UNDEFINED_FIELD));
			nestedTable.addCell(createSmallCell(UNDEFINED_PLAYER));
		}
	}

	private void addSpaceRows(PdfPTable nestedTable, int spaceCount) {
		if (spaceCount == 0) {
			nestedTable.addCell(createHalfEmptyCell());
			nestedTable.addCell(createHalfEmptyCell());
			nestedTable.addCell(createHalfEmptyCell());
		} else {
			for (int j = 0; j < spaceCount; j++) {
				nestedTable.addCell(createSmallEmptyCell());
				nestedTable.addCell(createSmallEmptyCell());
				nestedTable.addCell(createSmallEmptyCell());
			}
		}
	}

	@Override
	protected void initDocument(Document document) {
		document.open();
	}

	@Override
	protected String getFileType() {
		return FILE_TYPE;
	}

}
