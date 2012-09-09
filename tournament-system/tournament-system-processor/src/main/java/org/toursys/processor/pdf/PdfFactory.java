package org.toursys.processor.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.toursys.repository.model.Game;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Table;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfFactory {

    private static final int PLAYERS_PER_PAGE = 6;
    private static final int LISTS_TABLE_WIDTH = 120;

    public static File createSchedule(String path, List<Game> schedule) throws Exception {
        Document document = new Document();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyyHH:mm:ss");
        File file = new File(path + "schedule" + df.format(new Date()) + ".pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        document.setPageSize(PageSize.A4.rotate());
        document.newPage();

        PdfContentByte canvas = writer.getDirectContent();
        ColumnText column = new ColumnText(canvas);
        float[][] x = { { 5, 190 }, { 190, 375 }, { 375, 560 }, { 560, 745 } };

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
            // }
            // document.newPage();

        }
        if (document.getPageNumber() == 0) {
            writer.setPageEmpty(false);
            document.newPage();
        }

        document.close();
        /*
         * PdfPTable pdfTable = new PdfPTable(4); pdfTable.setHeaderRows(0);
         * pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT); pdfTable.setTotalWidth(200);
         * pdfTable.setLockedWidth(true); pdfTable.setWidths(new float[] { 1f, 1f, 10f, 1f });
         * pdfTable.setKeepTogether(true); pdfTable.setSplitLate(false); pdfTable.setSplitRows(false);
         * pdfTable.writeSelectedRows(0, -1, 23, 40, 500, 500, canvas); // pdfTable.writeSelectedRows(rowStart, rowEnd,
         * xPos, yPos, canvas) // writer.get
         * 
         * for (Game game : schedule) { pdfTable.addCell(createCell(game.getRound().toString()));
         * pdfTable.addCell(createCell(game.getHockey().toString()));
         * pdfTable.addCell(createCell(game.getPlayerResult().getPlayer().getSurname() + " " +
         * game.getPlayerResult().getPlayer().getName().charAt(0) + " vs " + game.getOpponent().getPlayer().getSurname()
         * + " " + game.getOpponent().getPlayer().getName().charAt(0))); pdfTable.addCell(createCell(":")); }
         * 
         * // document.add(pdfTable); document.close(); return file;
         */
        return file;
    }

    private static PdfPTable createTable(List<Game> schedule) throws Exception {
        PdfPTable pdfTable = new PdfPTable(6);

        pdfTable.setKeepTogether(true);
        pdfTable.setSplitLate(false);
        pdfTable.setSplitRows(false);

        pdfTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        pdfTable.setHeaderRows(0);
        pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfTable.setTotalWidth(185);
        pdfTable.setLockedWidth(true);
        pdfTable.setWidths(new float[] { 1f, 15f, 2f, 1f, 2f, 0.5f });
        int round = 1;

        pdfTable.addCell(createEmptyCell());
        pdfTable.addCell(createCell("1"));
        pdfTable.addCell(createEmptyCell());
        pdfTable.addCell(createEmptyCell());
        pdfTable.addCell(createEmptyCell());
        pdfTable.addCell(createEmptyCell());

        for (Game game : schedule) {
            if (game.getRound() != round) {

                round++;
                pdfTable.addCell(createEmptySmallCell());
                pdfTable.addCell(createEmptySmallCell());
                pdfTable.addCell(createEmptySmallCell());
                pdfTable.addCell(createEmptySmallCell());
                pdfTable.addCell(createEmptySmallCell());
                pdfTable.addCell(createRightBorderSmallCell(""));

                PdfPCell cell = createEmptyCell();
                cell.setBorder(Rectangle.TOP);
                cell.setPhrase(new Phrase(game.getRound().toString()));
                pdfTable.addCell(createEmptyTopCell());
                pdfTable.addCell(cell);
                pdfTable.addCell(createEmptyTopCell());
                pdfTable.addCell(createEmptyTopCell());
                pdfTable.addCell(createEmptyTopCell());
                pdfTable.addCell(createRightTopBorderSmallCell(""));
            }

            pdfTable.addCell(createLeftAlignCell(game.getHockey().toString()));
            pdfTable.addCell(createLeftAlignCell(game.getPlayerResult().getPlayer().getSurname() + " "
                    + game.getPlayerResult().getPlayer().getName().charAt(0) + " - "
                    + game.getOpponent().getPlayer().getSurname() + " "
                    + game.getOpponent().getPlayer().getName().charAt(0)));
            pdfTable.addCell(createBorderCell("  "));
            pdfTable.addCell(createCell(":"));
            pdfTable.addCell(createBorderCell("  "));
            pdfTable.addCell(createRightBorderCell(""));

            pdfTable.addCell(createEmptySmallCell());
            pdfTable.addCell(createEmptySmallCell());
            pdfTable.addCell(createEmptySmallCell());
            pdfTable.addCell(createEmptySmallCell());
            pdfTable.addCell(createEmptySmallCell());
            pdfTable.addCell(createRightBorderSmallCell(""));

        }
        return pdfTable;
    }

    public static File createSheets(String path, List<Game> schedule, Table table) throws Exception {
        Document document = new Document();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyyHH:mm:ss");
        File file = new File(path + "sheets" + df.format(new Date()) + ".pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        LinkedHashMap<PlayerResult, List<Game>> wholeSheets = new LinkedHashMap<PlayerResult, List<Game>>();
        int maxRound = schedule.get(schedule.size() - 1).getRound();
        int spaceCount = 0;
        if (maxRound > 30) {
            spaceCount = 1;
        } else if (maxRound > 20) {
            spaceCount = 2;
        } else if (maxRound > 10) {
            spaceCount = 4;
        } else {
            spaceCount = 6;
        }
        for (Game game : schedule) {
            if (wholeSheets.get(game.getOpponent()) == null) {
                wholeSheets.put(game.getOpponent(), new ArrayList<Game>());
            }
            if (wholeSheets.get(game.getPlayerResult()) == null) {
                wholeSheets.put(game.getPlayerResult(), new ArrayList<Game>());
            }

            wholeSheets.get(game.getOpponent()).add(game);
            wholeSheets.get(game.getPlayerResult()).add(game);
        }

        for (int i = 0; i <= wholeSheets.size() / PLAYERS_PER_PAGE; i++) {
            int toIndex = i * PLAYERS_PER_PAGE + PLAYERS_PER_PAGE - 1;
            if (i == wholeSheets.size()) {
                toIndex = i * PLAYERS_PER_PAGE + wholeSheets.size() % PLAYERS_PER_PAGE;
            }
            Map sheets = subMap(wholeSheets, i * PLAYERS_PER_PAGE, toIndex);

            if (sheets.size() == 0) {
                break;
            }

            PdfPTable pdfTable = new PdfPTable(sheets.size());
            pdfTable.setHeaderRows(1);

            pdfTable.setTotalWidth(LISTS_TABLE_WIDTH * sheets.size());
            pdfTable.setLockedWidth(true);
            pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            // pdfTable.set

            document.setPageSize(PageSize.A4.rotate());
            document.newPage();

            Set<Entry<PlayerResult, List<Game>>> entrySet = sheets.entrySet();

            // hlavicka
            for (Map.Entry<PlayerResult, List<Game>> entry : entrySet) {
                PlayerResult playerResult = entry.getKey();
                pdfTable.addCell(createCell("(" + table.getName() + ") " + playerResult.getPlayer().getSurname() + " "
                        + playerResult.getPlayer().getName().charAt(0)));
            }

            // hokeje
            for (int j = 0; j < sheets.size(); j++) {
                PdfPCell cell = new PdfPCell(new Phrase("         H", new Font(Font.FontFamily.TIMES_ROMAN, 10)));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                pdfTable.addCell(cell);
            }

            for (Map.Entry<PlayerResult, List<Game>> entry : entrySet) {
                PlayerResult playerResult = entry.getKey();
                List<Game> games = entry.getValue();

                PdfPTable nestedTable = new PdfPTable(3);
                nestedTable.setHeaderRows(0);
                nestedTable.setTotalWidth(LISTS_TABLE_WIDTH);
                nestedTable.setLockedWidth(true);
                nestedTable.setWidths(new float[] { 2f, 2f, 10f });

                int round = 1;
                for (Game game : games) {
                    Player player;
                    if (game.getOpponent().equals(playerResult)) {
                        player = game.getPlayerResult().getPlayer();
                    } else {
                        player = game.getOpponent().getPlayer();
                    }

                    while (round != game.getRound()) {
                        nestedTable.addCell(createCell(round + ")"));
                        nestedTable.addCell(createCell("-"));
                        nestedTable.addCell(createCell("-------"));

                        for (int j = 0; j < spaceCount; j++) {
                            nestedTable.addCell(createEmptyCell());
                        }
                        round++;
                    }

                    nestedTable.addCell(createCell(game.getRound() + ")"));
                    nestedTable.addCell(createCell(game.getHockey().toString()));
                    nestedTable.addCell(createCell(player.getSurname() + " " + player.getName().charAt(0)));

                    round++;

                    for (int j = 0; j < spaceCount; j++) {
                        nestedTable.addCell(createEmptyCell());
                    }
                }
                PdfPCell cell = createEmptyCell();
                cell.addElement(nestedTable);
                pdfTable.addCell(cell);
            }
            document.add(pdfTable);

        }

        if (document.getPageNumber() == 0) {
            writer.setPageEmpty(false);
            document.newPage();
        }

        document.close();

        return file;
    }

    private static PdfPCell createEmptySmallCell() {
        PdfPCell cell = new PdfPCell(new Phrase("", new Font(FontFamily.TIMES_ROMAN, 1)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private static PdfPCell createRightTopBorderSmallCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(FontFamily.TIMES_ROMAN, 1)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT + Rectangle.TOP);
        return cell;
    }

    private static PdfPCell createRightBorderSmallCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(FontFamily.TIMES_ROMAN, 1)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT);
        return cell;
    }

    private static PdfPCell createRightBorderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT);
        return cell;
    }

    private static PdfPCell createLeftBorderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.LEFT);
        return cell;
    }

    private static PdfPCell createBorderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }

    private static PdfPCell createLeftAlignCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setNoWrap(true);
        return cell;
    }

    private static PdfPCell createCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private static PdfPCell createEmptyCell() {
        return createCell(" ");
    }

    private static PdfPCell createEmptyTopCell() {
        PdfPCell cell = createCell(" ");
        cell.setBorder(Rectangle.TOP);
        return cell;
    }

    private static <K, V> LinkedHashMap<K, V> subMap(LinkedHashMap<K, V> map, int fromIndex, int toIndex) {
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

}