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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Player;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfFactory {

    private static final int PLAYERS_PER_PAGE = 6;
    private static final int LISTS_TABLE_WIDTH = 134;
    private static final Logger logger = LoggerFactory.getLogger(PdfFactory.class);

    public static File createTable(String path, List<Participant> players, Groups group) throws Exception {
        Document document = new Document();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyyHH-mm-ss");
        File file = new File(path + "group" + df.format(new Date()) + ".pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        document.setPageSize(PageSize.A4.rotate());
        document.newPage();

        PdfPTable pdfTable = new PdfPTable(players.size() + 4);
        pdfTable.setHeaderRows(1);
        pdfTable.setLockedWidth(true);
        float[] widths = new float[players.size() + 4];
        widths[0] = 6f;
        for (int i = 1; i < widths.length; i++) {
            widths[i] = 2f;
        }
        pdfTable.setTotalWidth(800);
        pdfTable.setWidths(widths);

        pdfTable.addCell(createCenterAlignBorderCell("name"));
        for (Participant participant : players) {
            // TODO index miesto inicialok
            pdfTable.addCell(createCenterAlignBorderCell(participant.getPlayer().getName().charAt(0) + "."
                    + participant.getPlayer().getSurname().charAt(0) + "."));
        }
        pdfTable.addCell(createCenterAlignBorderCell("score"));
        pdfTable.addCell(createCenterAlignBorderCell("points"));
        pdfTable.addCell(createCenterAlignBorderCell("rank"));

        for (Participant participant1 : players) {
            pdfTable.addCell(createCenterAlignBorderCell(participant1.getPlayer().getName().charAt(0) + "."
                    + participant1.getPlayer().getSurname() + " " + participant1.getPlayer().getPlayerDiscriminator()));

            for (Participant participant2 : players) {
                if (participant1.equals(participant2)) {
                    pdfTable.addCell(createCenterAlignBorderCell("X"));
                } else {
                    Game game = null;
                    for (Game pomGame : participant1.getGames()) {
                        if (pomGame.getAwayParticipant().getId().equals(participant2.getId())) {
                            game = pomGame;
                            break;
                        }
                    }

                    if (game == null) {
                        pdfTable.addCell(createEmptyCell());
                    } else {
                        String result = (game.getHomeScore() == null) ? "" : game.getHomeScore() + ":"
                                + ((game.getAwayScore() == null) ? "" : game.getAwayScore());
                        pdfTable.addCell(createCenterAlignBorderCell(result));
                    }
                }
            }
            pdfTable.addCell(createCenterAlignBorderCell(participant1.getScore().toString()));
            pdfTable.addCell(createCenterAlignBorderCell(((Integer) participant1.getPoints()).toString()));
            pdfTable.addCell(createCenterAlignBorderCell(participant1.getRank().toString()));
        }

        document.add(new Paragraph("Group: " + group.getName()));
        document.add(new Paragraph(""));
        document.add(pdfTable);

        closePdfFile(document, writer);

        return file;
    }

    private static void closePdfFile(Document document, PdfWriter writer) {
        if (document.getPageNumber() == 0) {
            writer.setPageEmpty(false);
            document.newPage();
        }

        document.close();
    }

    public static File createSchedule(String path, List<GameImpl> schedule) throws Exception {
        Document document = new Document();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyyHH-mm-ss");
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
        }
        closePdfFile(document, writer);

        return file;
    }

    private static PdfPTable createTable(List<GameImpl> schedule) throws Exception {
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

        for (GameImpl game : schedule) {
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
            pdfTable.addCell(createLeftAlignCell(game.getHomeParticipant().getPlayer().getName().charAt(0) + "."
                    + game.getHomeParticipant().getPlayer().getSurname() + " "
                    + game.getHomeParticipant().getPlayer().getPlayerDiscriminator() + " - "
                    + game.getAwayParticipant().getPlayer().getName().charAt(0) + " "
                    + game.getAwayParticipant().getPlayer().getSurname() + " "
                    + game.getAwayParticipant().getPlayer().getPlayerDiscriminator()));
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

    public static File createSheets(String path, List<GameImpl> schedule, Groups group) throws Exception {
        Document document = new Document();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyyHH-mm-ss");
        File file = new File(path + "sheets" + df.format(new Date()) + ".pdf");

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        LinkedHashMap<Participant, List<GameImpl>> wholeSheets = new LinkedHashMap<Participant, List<GameImpl>>();
        if (!schedule.isEmpty()) {
            int maxRound = schedule.get(schedule.size() - 1).getRound();
            int spaceCount = 0;
            if (maxRound > 15) {
                spaceCount = 0;
            } else if (maxRound > 10) {
                spaceCount = 1;
            } else if (maxRound > 5) {
                spaceCount = 2;
            } else {
                spaceCount = 4;
            }

            for (GameImpl game : schedule) {
                if (wholeSheets.get(game.getAwayParticipant()) == null) {
                    wholeSheets.put(game.getAwayParticipant(), new ArrayList<GameImpl>());
                }
                if (wholeSheets.get(game.getHomeParticipant()) == null) {
                    wholeSheets.put(game.getHomeParticipant(), new ArrayList<GameImpl>());
                }

                wholeSheets.get(game.getAwayParticipant()).add(game);
                wholeSheets.get(game.getHomeParticipant()).add(game);
            }

            for (int i = 0; i <= wholeSheets.size() / PLAYERS_PER_PAGE; i++) {
                int toIndex = i * PLAYERS_PER_PAGE + PLAYERS_PER_PAGE - 1;
                if (i == wholeSheets.size()) {
                    toIndex = i * PLAYERS_PER_PAGE + wholeSheets.size() % PLAYERS_PER_PAGE;
                }
                Map<Participant, List<GameImpl>> sheets = subMap(wholeSheets, i * PLAYERS_PER_PAGE, toIndex);

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

                Set<Entry<Participant, List<GameImpl>>> entrySet = sheets.entrySet();

                // hlavicka
                for (Map.Entry<Participant, List<GameImpl>> entry : entrySet) {
                    Participant participant = entry.getKey();
                    pdfTable.addCell(createCell("(" + group.getName() + ") "
                            + participant.getPlayer().getName().charAt(0) + "." + participant.getPlayer().getSurname()
                            + " " + participant.getPlayer().getPlayerDiscriminator()));
                }

                // hokeje
                for (int j = 0; j < sheets.size(); j++) {
                    PdfPCell cell = new PdfPCell(new Phrase("                H", new Font(Font.FontFamily.TIMES_ROMAN,
                            10)));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    pdfTable.addCell(cell);
                }

                for (Map.Entry<Participant, List<GameImpl>> entry : entrySet) {
                    Participant participant = entry.getKey();
                    List<GameImpl> games = entry.getValue();

                    PdfPTable nestedTable = new PdfPTable(3);
                    nestedTable.setHeaderRows(0);
                    nestedTable.setTotalWidth(LISTS_TABLE_WIDTH);
                    nestedTable.setLockedWidth(true);
                    nestedTable.setWidths(new float[] { 4f, 4f, 10f });

                    int round = 1;
                    for (GameImpl game : games) {
                        Player player;
                        String side;
                        if (game.getAwayParticipant().equals(participant)) {
                            player = game.getHomeParticipant().getPlayer();
                            side = "S";
                        } else {
                            player = game.getAwayParticipant().getPlayer();
                            side = "F";
                        }

                        while (round != game.getRound()) {
                            nestedTable.addCell(createCell(round + ")"));
                            nestedTable.addCell(createCell("-"));
                            nestedTable.addCell(createCell("-------"));

                            for (int j = 0; j < spaceCount; j++) {
                                nestedTable.addCell(createEmptyCell());
                                nestedTable.addCell(createEmptyCell());
                                nestedTable.addCell(createEmptyCell());
                            }
                            round++;
                        }

                        nestedTable.addCell(createCell(game.getRound() + ")"));
                        nestedTable.addCell(createCell(game.getHockey().toString() + side));
                        nestedTable
                                .addCell(createLeftAlignCell(player.getSurname() + " " + player.getName().charAt(0)));

                        round++;

                        for (int j = 0; j < spaceCount; j++) {
                            nestedTable.addCell(createEmptyCell());
                            nestedTable.addCell(createEmptyCell());
                            nestedTable.addCell(createEmptyCell());
                        }
                    }
                    PdfPCell cell = createEmptyCell();
                    cell.addElement(nestedTable);
                    pdfTable.addCell(cell);
                }
                document.add(pdfTable);

            }
        }

        closePdfFile(document, writer);

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
        PdfPCell cell = createCell(text);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT);
        return cell;
    }

    private static PdfPCell createCenterAlignBorderCell(String text) {
        PdfPCell cell = createCell(text);
        cell.setBorder(Rectangle.BOX);
        return cell;
    }

    private static PdfPCell createBorderCell(String text) {
        PdfPCell cell = createCell(text);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.BOX);
        return cell;
    }

    private static PdfPCell createLeftAlignCell(String text) {
        PdfPCell cell = createCell(text);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
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

    public static File createPlayOff(String path, Map<Groups, List<PlayOffGame>> playOffGamesByGroup) throws Exception {

        Document document = new Document();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyyHH-mm-ss");
        File file = new File(path + "playOff" + df.format(new Date()) + ".pdf");

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        document.newPage();

        PdfPTable pdfTable = new PdfPTable(3);
        // pdfTable.setHeaderRows(1);
        pdfTable.setLockedWidth(true);
        pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);

        pdfTable.setTotalWidth(400);
        pdfTable.setWidths(new float[] { 1f, 9f, 18f });

        for (Map.Entry<Groups, List<PlayOffGame>> entry : playOffGamesByGroup.entrySet()) {
            pdfTable.addCell(createLeftAlignCell(entry.getKey().getName()));
            pdfTable.addCell(createEmptyCell());
            pdfTable.addCell(createEmptyCell());

            for (PlayOffGame playOffGame : entry.getValue()) {

                pdfTable.addCell(createLeftAlignCell(getRound(entry.getValue().size(), playOffGame.getPosition()) + ""));

                if (playOffGame.getHomeParticipant() != null && playOffGame.getAwayParticipant() != null) {
                    String playersGame = playOffGame.getHomeParticipant().getPlayer().getName().charAt(0) + "."
                            + playOffGame.getHomeParticipant().getPlayer().getSurname() + " "
                            + playOffGame.getHomeParticipant().getPlayer().getPlayerDiscriminator() + " : "
                            + playOffGame.getAwayParticipant().getPlayer().getName().charAt(0) + "."
                            + playOffGame.getAwayParticipant().getPlayer().getSurname() + " "
                            + playOffGame.getAwayParticipant().getPlayer().getPlayerDiscriminator();

                    pdfTable.addCell(createLeftAlignCell(playersGame));

                    String results = playOffGame.getResults();

                    if (!results.isEmpty()) {
                        pdfTable.addCell(createLeftAlignCell(results));
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

        closePdfFile(document, writer);

        return file;
    }

    private static int getRound(int playerCount, int position) {
        // pri vypisovani kola treba rozlisit prve
        if (position == 0) {
            return 0;
        }
        int rounds = binlog(playerCount);
        int gamesCount = 0;
        for (int i = 1; i <= rounds; i++) {
            gamesCount += playerCount / Math.pow(2, i);
            if (gamesCount > (position - 1)) {
                return i;
            }
        }
        logger.error("playerCount: " + playerCount + " position: " + position);
        throw new RuntimeException("Invalid position to get round");
    }

    private static int binlog(int bits) // returns 0 for bits=0
    {
        int log = 0;
        if ((bits & 0xffff0000) != 0) {
            bits >>>= 16;
            log = 16;
        }
        if (bits >= 256) {
            bits >>>= 8;
            log += 8;
        }
        if (bits >= 16) {
            bits >>>= 4;
            log += 4;
        }
        if (bits >= 4) {
            bits >>>= 2;
            log += 2;
        }
        return log + (bits >>> 1);
    }

    public static File createFinalStandings(String path, List<FinalStanding> finalStandings) throws Exception {
        Document document = new Document();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyyHH-mm-ss");
        File file = new File(path + "playOff" + df.format(new Date()) + ".pdf");

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        document.newPage();

        PdfPTable pdfTable = new PdfPTable(3);
        pdfTable.setLockedWidth(true);
        pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);

        pdfTable.setTotalWidth(400);
        pdfTable.setWidths(new float[] { 2f, 5f, 5f });

        for (FinalStanding finalStanding : finalStandings) {
            pdfTable.addCell(createLeftAlignCell(finalStanding.getFinalRank() + "."));
            pdfTable.addCell(createLeftAlignCell((finalStanding.getPlayer().getSurname() != null) ? finalStanding
                    .getPlayer().getSurname() + " " + finalStanding.getPlayer().getPlayerDiscriminator() : ""));
            pdfTable.addCell(createLeftAlignCell((finalStanding.getPlayer().getName() != null) ? finalStanding
                    .getPlayer().getName() : " " + finalStanding.getPlayer().getPlayerDiscriminator()));
        }

        document.add(pdfTable);

        closePdfFile(document, writer);

        return file;
    }
}