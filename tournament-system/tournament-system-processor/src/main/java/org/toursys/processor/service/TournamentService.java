package org.toursys.processor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.toursys.processor.cache.TournamentCache;
import org.toursys.processor.comparators.AdvantageComparator;
import org.toursys.processor.comparators.BasicComparator;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.repository.form.GameForm;
import org.toursys.repository.form.PlayerForm;
import org.toursys.repository.form.PlayerResultForm;
import org.toursys.repository.form.TableForm;
import org.toursys.repository.form.TournamentForm;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Result;
import org.toursys.repository.model.Round;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Table;
import org.toursys.repository.model.TableType;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentService {

    private TournamentAggregationDao tournamentAggregationDao;

    public void createPlayer(Player player) {
        tournamentAggregationDao.createPlayer(player);
    }

    public void updatePlayer(Player player) {
        tournamentAggregationDao.updatePlayer(player);
    }

    public void deletePlayer(Player player) {
        tournamentAggregationDao.deletePlayer(player);
    }

    public List<Player> getAllPlayer() {
        return tournamentAggregationDao.getAllPlayer();
    }

    public List<Player> getNotRegistrationPlayer(PlayerForm playerForm) {
        return tournamentAggregationDao.getNotRegistrationPlayer(playerForm);
    }

    public List<Season> getAllSeason() {
        return tournamentAggregationDao.getAllSeason();
    }

    public void updateSeason(Season season) {
        tournamentAggregationDao.updateSeason(season);
    }

    public void createSeason(Season season) {
        tournamentAggregationDao.createSeason(season);
    }

    public void deleteSeason(Season season) {
        tournamentAggregationDao.deleteSeason(season);
    }

    public List<Tournament> findTournament(TournamentForm tournamentForm) {
        return tournamentAggregationDao.findTournament(tournamentForm);
    }

    public void updateTournament(Tournament tournament) {
        tournamentAggregationDao.updateTournament(tournament);
    }

    public void createTournament(Tournament tournament) {
        tournamentAggregationDao.createTournament(tournament);
    }

    public void deleteTournament(Tournament tournament) {
        tournamentAggregationDao.deleteTournament(tournament);
    }

    public void createPlayerResult(PlayerResult playerResult) {
        tournamentAggregationDao.createPlayerResult(playerResult);
    }

    public void deletePlayerResultAndTable(PlayerResult playerResult, Tournament tournament) {
        tournamentAggregationDao.deletePlayerResult(playerResult);

        List<PlayerResult> finalPlayers = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(playerResult
                .getPlayer()));
        if (!finalPlayers.isEmpty()) {
            for (PlayerResult finalPlayer : finalPlayers) {
                deletePlayerResultAndTable(finalPlayer, tournament);
            }
        }

        List<PlayerResult> players = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(tournament,
                playerResult.getTournamentTable()));
        if (players.isEmpty()) {
            tournamentAggregationDao.deleteTable(playerResult.getTournamentTable());
        }
    }

    public List<PlayerResult> findPlayerResult(PlayerResultForm playerResultForm) {
        return tournamentAggregationDao.findPlayerResult(playerResultForm);
    }

    public void updateTable(Table table) {
        tournamentAggregationDao.updateTable(table);
    }

    public List<Table> findTable(TableForm tableForm) {
        return tournamentAggregationDao.findTable(tableForm);
    }

    public void createPlayerResult(Tournament tournament, Player player, Table table, TableType tableType) {
        Table currentTable = null;
        if (table.getName() != null) {
            List<Table> tables = tournamentAggregationDao.findTable(new TableForm(table.getName(), tournament));
            if (tables.isEmpty()) {
                table.setTournamentId(tournament.getTournamentId());
                table.setTableType(tableType);
                tournamentAggregationDao.createTable(table);
            } else {
                currentTable = tables.get(0);
            }

            if (currentTable == null) {
                currentTable = tournamentAggregationDao.findTable(new TableForm(table.getName(), tournament)).get(0);
            }

            PlayerResult playerResult = new PlayerResult();
            playerResult.setPlayer(player);
            playerResult.setPoints(0);
            playerResult.setRank(0);
            playerResult.setScore("0:0");
            playerResult.setTournamentTable(currentTable);
            tournamentAggregationDao.createPlayerResult(playerResult);

        }
    }

    public List<Game> findGame(GameForm gameForm) {
        return tournamentAggregationDao.findGame(gameForm);
    }

    public void updateGame(Game game) {
        tournamentAggregationDao.updateResult(game.getResult());
        Game game1 = tournamentAggregationDao.findGame(new GameForm(game.getOpponent(), game.getPlayerResult())).get(0);
        if (game.equals(game1)) {
            game1 = tournamentAggregationDao.findGame(new GameForm(game.getPlayerResult(), game.getOpponent())).get(0);
        }

        game1.getResult().setLeftSide(game.getResult().getRightSide());
        game1.getResult().setRightSide(game.getResult().getLeftSide());
        game1.getResult().setOvertime(game.getResult().getOvertime());

        tournamentAggregationDao.updateResult(game1.getResult());
    }

    public void createGames(List<PlayerResult> playerResults) {
        if (!playerResults.isEmpty() && playerResults.get(0).getGames().size() < playerResults.size()) {
            for (PlayerResult playerResult1 : playerResults) {
                for (PlayerResult playerResult2 : playerResults) {
                    if (!playerResult1.equals(playerResult2)) {
                        if (tournamentAggregationDao.findGame(new GameForm(playerResult1, playerResult2)).isEmpty()) {
                            Game game = new Game(playerResult1, playerResult2);
                            tournamentAggregationDao.createResult(game.getResult());
                            tournamentAggregationDao.createGame(game);
                        }
                    }
                }
            }
        }
    }

    public List<Game> getSchedule(Table table, Tournament tournament) {
        List<Game> games = new ArrayList<Game>();
        List<Round> schedule = new ArrayList<Round>();

        if (table.getTableType().equals(TableType.F) && table.getName().equals("A")) {
            List<Table> basicTables = tournamentAggregationDao.findTable(new TableForm(tournament, TableType.B));
            List<PlayerResult> finalPlayers = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(
                    tournament, table));

            if (basicTables.size() > 1) {
                LinkedList<List<PlayerResult>> playerByGroup = new LinkedList<List<PlayerResult>>();
                for (int i = 0; i < basicTables.size(); i++) {
                    playerByGroup.add(i, new ArrayList<PlayerResult>());
                    List<PlayerResult> players = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(
                            tournament, basicTables.get(i)));
                    for (PlayerResult playerResult : players) {
                        for (PlayerResult finalPlayerResult : finalPlayers) {
                            if (playerResult.getPlayer().equals(finalPlayerResult.getPlayer())) {
                                playerByGroup.get(i).add(finalPlayerResult);
                                break;
                            }
                        }
                    }
                }

                // TODO otestova ?
                if (playerByGroup.size() % 2 == 1) {
                    playerByGroup.add(playerByGroup.size(), new ArrayList<PlayerResult>());
                }

                int finalPlayerCount = playerByGroup.size();

                int finalRoundCount = finalPlayerCount - 1;

                for (int i = 0; i < finalRoundCount; i++) {
                    schedule.add(i, new Round());
                    createRound(schedule.get(i), playerByGroup);
                    rotatePlayerByGroup(playerByGroup);
                }
            }
        } else {
            List<PlayerResult> playerResults = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(
                    tournament, table));

            // if we have odd number of player
            if (playerResults.size() % 2 == 1) {
                playerResults.add(playerResults.size(), new PlayerResult());
            }

            int playerCount = playerResults.size();

            int roundCount = playerCount - 1;

            PlayerResult[] temporaryField = createTemporaryField(playerResults);
            int countGames = playerCount / 2;
            for (int i = 0; i < roundCount; i++) {
                schedule.add(i, new Round());
                createRound(schedule.get(i), temporaryField, countGames);
                temporaryField = rotateTemporaryField(temporaryField, playerCount);
            }
            balanceFirstPlayer(roundCount, schedule);
        }

        int count = 0;
        for (Round round : schedule) {
            List<Game> temporaryGames = round.getGames();
            for (Game game : temporaryGames) {
                if (game != null) {
                    game.setHockey(count % table.getNumberOfHockey() + table.getIndexOfFirstHockey());
                    game.setRound(count / table.getNumberOfHockey() + 1);
                    games.add(game);
                    count++;
                }
            }
        }

        return games;
    }

    private void rotatePlayerByGroup(LinkedList<List<PlayerResult>> playerByGroup) {
        List<PlayerResult> lastGroupPlayer = playerByGroup.removeLast();
        playerByGroup.add(1, lastGroupPlayer);
    }

    private void createRound(Round round, LinkedList<List<PlayerResult>> playerByGroup) {

        int count = 1;
        List<PlayerResult> twoPlayerResult = new ArrayList<PlayerResult>();
        for (List<PlayerResult> playerResults : playerByGroup) {
            twoPlayerResult.addAll(playerResults);
            if (count % 2 == 0) {
                PlayerResult[] fieldPlayerResul = twoPlayerResult.toArray(new PlayerResult[0]);
                for (int i = 0; i < twoPlayerResult.size() / 2; i++) {
                    createRound(round, fieldPlayerResul, twoPlayerResult.size() / 2);
                    fieldPlayerResul = rotatePlayerResult(fieldPlayerResul, i);
                }
                twoPlayerResult.clear();
            }
            count++;
        }

    }

    private PlayerResult[] rotatePlayerResult(PlayerResult[] fieldPlayerResult, int round) {
        PlayerResult pom = fieldPlayerResult[fieldPlayerResult.length / 2 - 1];
        PlayerResult pom1 = fieldPlayerResult[fieldPlayerResult.length - 1];

        // prva polovica sa toci doprava
        for (int i = (fieldPlayerResult.length / 2) - 1; i > 0; i--) {
            fieldPlayerResult[i] = fieldPlayerResult[i - 1];
        }
        fieldPlayerResult[0] = pom;

        for (int i = fieldPlayerResult.length - 1; i > fieldPlayerResult.length / 2; i--) {
            fieldPlayerResult[i] = fieldPlayerResult[i - 1];
        }
        fieldPlayerResult[fieldPlayerResult.length / 2] = pom1;

        if ((fieldPlayerResult.length % 4 == 0) && ((round + 2) == (fieldPlayerResult.length / 4) + 1)) {
            pom1 = fieldPlayerResult[fieldPlayerResult.length - 1];

            for (int i = fieldPlayerResult.length - 1; i > fieldPlayerResult.length / 2; i--) {
                fieldPlayerResult[i] = fieldPlayerResult[i - 1];
            }
            fieldPlayerResult[fieldPlayerResult.length / 2] = pom1;
        }

        return fieldPlayerResult;
    }

    private void createRound(Round round, PlayerResult[] players, int gameCountInRound) {
        int playerCount = players.length;
        for (int i = 0; i < gameCountInRound; i++) {
            boolean even = (i % 2 == 0);
            if (even) {
                if (players[i].getPlayerResultId() != 0 && players[playerCount - i - 1].getPlayerResultId() != 0) {
                    round.getGames().add(
                            tournamentAggregationDao.findGame(new GameForm(players[i], players[playerCount - i - 1]))
                                    .get(0));
                } else {
                    round.getGames().add(null);
                }
            } else {
                if (players[i].getPlayerResultId() != 0 && players[playerCount - i - 1].getPlayerResultId() != 0) {
                    round.getGames().add(
                            tournamentAggregationDao.findGame(new GameForm(players[playerCount - i - 1], players[i]))
                                    .get(0));
                } else {
                    round.getGames().add(null);
                }
            }
        }
    }

    private void balanceFirstPlayer(int pocetKol, List<Round> schedule) {
        for (int i = 0; i < pocetKol; i++) {
            if (i % 2 == 0) {
                if (schedule.get(i).getGames().get(0) != null) {

                    Game game = schedule.get(i).getGames().get(0);
                    Game oppositeGame = tournamentAggregationDao.findGame(
                            new GameForm(game.getOpponent(), game.getPlayerResult())).get(0);
                    schedule.get(i).getGames().remove(0);
                    schedule.get(i).getGames().add(0, oppositeGame);
                }
            }
        }
    }

    /**
     * Example:[1 2 3 4 5 6] -> games 1-6, 2-5, 3-4
     * 
     * @return [1 6 2 3 4 5] -> games 1-5, 6-4, 2-3
     */
    private PlayerResult[] rotateTemporaryField(PlayerResult[] tymyPomocnePole, int playerCount) {
        PlayerResult pomocny = tymyPomocnePole[playerCount - 1];
        for (int i = playerCount - 1; i > 1; i--) {
            tymyPomocnePole[i] = tymyPomocnePole[i - 1];
        }
        tymyPomocnePole[1] = pomocny;
        return tymyPomocnePole;
    }

    private PlayerResult[] createTemporaryField(List<PlayerResult> playerResult) {
        return playerResult.toArray(new PlayerResult[0]);
    }

    @Required
    public void setTournamentAggregationDao(TournamentAggregationDao tournamentAggregationDao) {
        this.tournamentAggregationDao = tournamentAggregationDao;
    }

    public void calculatePlayerResults(List<PlayerResult> playerResults, Tournament tournament) {
        for (PlayerResult playerResult : playerResults) {
            calculatePlayerResult(playerResult, tournament);
        }
        if (playerResults.size() > 0) {
            sortPlayerResult(playerResults, tournament);
        }
    }

    private void calculatePlayerResult(PlayerResult playerResult, Tournament tournament) {
        int points = 0;
        Integer leftSide = 0;
        Integer rightSide = 0;
        for (Game game : playerResult.getGames()) {
            // TODO zistit preco ked playerResult nema ziadnu hru tak tam nacitava jednu z id 0 ???
            if (game.getGameId() != 0) {
                Result result = game.getResult();
                if (result.getLeftSide() != null && result.getRightSide() != null) {
                    if (result.getLeftSide() > result.getRightSide()) {
                        points += tournament.getWinPoints();
                    } else if (result.getLeftSide().equals(result.getRightSide())) {
                        points += 1;
                    }
                    leftSide += result.getLeftSide();
                    rightSide += result.getRightSide();
                }
            }
        }
        playerResult.setPoints(points);
        playerResult.setScore(leftSide + ":" + rightSide);
        tournamentAggregationDao.updatePlayerResult(playerResult);
    }

    public void createFinalGroup(Tournament tournament) {
        List<Table> basicTables = tournamentAggregationDao.findTable(new TableForm(tournament, TableType.B));
        List<Table> finalTables = tournamentAggregationDao.findTable(new TableForm(tournament, TableType.F));

        if (!finalTables.isEmpty()) {
            for (Table finalTable : finalTables) {
                /*
                 * List<PlayerResult> tablePlayerResult = tournamentAggregationDao.findPlayerResult(new
                 * PlayerResultForm( tournament, finalTable)); for (PlayerResult playerResult : tablePlayerResult) { for
                 * (Game game : playerResult.getGames()) { if (game.getGameId() != 0) {
                 * tournamentAggregationDao.deleteGame(game); tournamentAggregationDao.deleteResult(game.getResult()); }
                 * } }
                 */
                tournamentAggregationDao.deleteTable(finalTable);
            }
        }

        Table finalTable = new Table();
        for (Table table : basicTables) {
            List<PlayerResult> playerResults = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(
                    tournament, table));
            Collections.sort(playerResults, new RankComparator());
            int promotingA = Math.min(playerResults.size(), tournament.getPromotingA());
            finalTables = tournamentAggregationDao.findTable(new TableForm("A", tournament, TableType.F));
            if (finalTables.isEmpty()) {
                finalTable = new Table();
                finalTable.setName("A");
                finalTable.setTableType(TableType.F);
                finalTable.setTournamentId(tournament.getTournamentId());
                tournamentAggregationDao.createTable(finalTable);
            }

            finalTable = tournamentAggregationDao.findTable(new TableForm("A", tournament, TableType.F)).get(0);

            for (int i = 0; i < promotingA; i++) {
                PlayerResult playerResult = new PlayerResult();
                playerResult.setPlayer(playerResults.get(i).getPlayer());
                playerResult.setPoints(0);
                playerResult.setScore("0:0");
                playerResult.setTournamentTable(finalTable);
                tournamentAggregationDao.createPlayerResult(playerResult);
            }

            byte[] nextTableByte = { (byte) 66 };
            int countLowerGroup = 1;
            while (true) {
                String nextTable = new String(nextTableByte);

                int promotingLower = Math.min(playerResults.size(), tournament.getPromotingA() + countLowerGroup
                        * tournament.getPromotingLower());
                int startIndex = promotingA + ((countLowerGroup - 1) * tournament.getPromotingLower());

                if (startIndex < promotingLower) {
                    finalTables = tournamentAggregationDao.findTable(new TableForm(nextTable, tournament, TableType.F));
                    if (finalTables.isEmpty()) {
                        finalTable = new Table();
                        finalTable.setName(nextTable);
                        finalTable.setTableType(TableType.F);
                        finalTable.setTournamentId(tournament.getTournamentId());
                        tournamentAggregationDao.createTable(finalTable);
                    }

                    finalTable = tournamentAggregationDao.findTable(new TableForm(nextTable, tournament, TableType.F))
                            .get(0);

                    for (int i = startIndex; i < promotingLower; i++) {
                        PlayerResult playerResult = new PlayerResult();
                        playerResult.setPlayer(playerResults.get(i).getPlayer());
                        playerResult.setPoints(0);
                        playerResult.setScore("0:0");
                        playerResult.setTournamentTable(finalTable);
                        tournamentAggregationDao.createPlayerResult(playerResult);
                    }

                }
                if (playerResults.size() == promotingLower) {
                    break;
                }
                nextTableByte[0]++;
                countLowerGroup++;
            }

        }

        finalTables = tournamentAggregationDao.findTable(new TableForm(tournament, TableType.F));
        for (Table table : finalTables) {
            List<PlayerResult> finalPlayer = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(tournament,
                    table));
            createGames(finalPlayer);
        }
    }

    public void copyResult(Tournament tournament) {
        List<Table> basicTables = tournamentAggregationDao.findTable(new TableForm(tournament, TableType.B));
        List<Table> finalTables = tournamentAggregationDao.findTable(new TableForm(tournament, TableType.F));
        if (!finalTables.isEmpty()) {
            Table finalTable = finalTables.get(0);
            List<PlayerResult> finalPlayerResults = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(
                    tournament, finalTable));
            for (Table basicTable : basicTables) {
                List<PlayerResult> playerResults = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(
                        tournament, basicTable));
                for (PlayerResult finalPlayerResult : finalPlayerResults) {
                    for (PlayerResult playerResult : playerResults) {
                        if (playerResult.getPlayer().equals(finalPlayerResult.getPlayer())) {
                            List<Game> finalGames = finalPlayerResult.getGames();
                            List<Game> basicGames = playerResult.getGames();
                            for (Game finalGame : finalGames) {
                                for (Game basicGame : basicGames) {
                                    if (finalGame.getOpponent().getPlayer().equals(basicGame.getOpponent().getPlayer())
                                            && finalGame.getPlayerResult().getPlayer()
                                                    .equals(basicGame.getPlayerResult().getPlayer())) {
                                        finalGame.getResult().setLeftSide(basicGame.getResult().getLeftSide());
                                        finalGame.getResult().setRightSide(basicGame.getResult().getRightSide());
                                        finalGame.getResult().setOvertime(basicGame.getResult().getOvertime());
                                        tournamentAggregationDao.updateResult(finalGame.getResult());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            calculatePlayerResults(finalPlayerResults, tournament);
        }

    }

    private void sortPlayerResult(List<PlayerResult> playerResults, Tournament tournament) {
        Collections.sort(playerResults, new BasicComparator());

        for (int i = 0; i < playerResults.size(); i++) {
            playerResults.get(i).setRank(i + 1);
        }

        List<PlayerResult> temporatyPlayerResult = new ArrayList<PlayerResult>();
        temporatyPlayerResult.add(playerResults.get(0).clone());
        int actualRank = 0;
        for (int i = 0; i < playerResults.size() - 1; i++) {
            if (playerResults.get(i).getPoints() == playerResults.get(i + 1).getPoints()) {
                temporatyPlayerResult.add(playerResults.get(i + 1).clone());

            }
            if (playerResults.get(i).getPoints() != playerResults.get(i + 1).getPoints()
                    || (i == playerResults.size() - 2)) {
                if (temporatyPlayerResult.size() > 2) {
                    for (PlayerResult playerResult : temporatyPlayerResult) {
                        boolean delGame = true;
                        List<Game> g1 = new ArrayList<Game>(playerResult.getGames());
                        for (Game game : g1) {
                            for (PlayerResult playerResult2 : temporatyPlayerResult) {
                                if (playerResult2.equals(game.getOpponent())) {
                                    delGame = false;
                                    break;
                                }
                            }
                            if (delGame) {
                                playerResult.getGames().remove(game);
                            }
                            delGame = true;
                        }
                    }
                    for (PlayerResult playerResult : temporatyPlayerResult) {
                        calculatePlayerResult(playerResult, tournament);
                    }

                    Collections.sort(temporatyPlayerResult, new AdvantageComparator());

                    for (PlayerResult playerResult1 : playerResults) {
                        for (int j = 0; j < temporatyPlayerResult.size(); j++) {
                            if (playerResult1.equals(temporatyPlayerResult.get(j))) {
                                playerResult1.setRank(j + 1 + actualRank);
                            }
                        }
                    }

                }
                temporatyPlayerResult.clear();
                temporatyPlayerResult.add(playerResults.get(i + 1).clone());
                actualRank = i + 1;
            }

        }

        for (int i = 0; i < playerResults.size(); i++) {
            tournamentAggregationDao.updatePlayerResult(playerResults.get(i));
        }
        Collections.sort(playerResults, new RankComparator());
    }

    public List<Game> createPlayOffGames(Tournament tournament) {
        List<Table> finalTables = tournamentAggregationDao.findTable(new TableForm(tournament, TableType.F));
        List<Game> playOffGames = new LinkedList<Game>();
        for (Table table : finalTables) {

            Game game = new Game();
            game.setGameId(-1);
            playOffGames.add(game);

            Table playOffTable = new Table();
            String playOffTableName = "P" + table.getName();
            playOffTable.setName(playOffTableName);
            playOffTable.setTableType(TableType.P);
            playOffTable.setTournamentId(tournament.getTournamentId());

            List<Table> playOffTables = tournamentAggregationDao.findTable(new TableForm(playOffTableName, tournament));
            if (playOffTables.isEmpty()) {
                tournamentAggregationDao.createTable(playOffTable);
                playOffTable = tournamentAggregationDao.findTable(new TableForm(playOffTableName, tournament)).get(0);
            } else {
                playOffTable = playOffTables.get(0);
            }

            List<PlayerResult> players = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(tournament,
                    table));
            Collections.sort(players, new RankComparator());

            int currentPlayOff;
            if (table.getName().equals("A")) {
                currentPlayOff = tournament.getPlayOffA();
            } else {
                currentPlayOff = tournament.getPlayOffLower();
            }

            List<PlayerResult> playOffPlayer = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(
                    tournament, playOffTable));

            if (players.size() >= currentPlayOff) {
                for (int i = 0; i < currentPlayOff - 1; i = i + 2) {
                    PlayerResult betterPlayer = new PlayerResult();
                    PlayerResult worsePlayer = new PlayerResult();

                    if (currentPlayOff != playOffPlayer.size()) {
                        for (PlayerResult playerResult : playOffPlayer) {
                            tournamentAggregationDao.deletePlayerResult(playerResult);
                        }

                        betterPlayer.setTournamentTable(playOffTable);
                        betterPlayer.setPlayer(players.get(i).getPlayer());
                        betterPlayer.setScore("0:0");
                        betterPlayer.setPoints(0);
                        tournamentAggregationDao.createPlayerResult(betterPlayer);

                        worsePlayer.setTournamentTable(playOffTable);
                        worsePlayer.setPlayer(players.get(i + 1).getPlayer());
                        worsePlayer.setScore("0:0");
                        worsePlayer.setPoints(0);
                        tournamentAggregationDao.createPlayerResult(worsePlayer);

                    } else {
                        betterPlayer = playOffPlayer.get(i);

                        if (!betterPlayer.getPlayer().equals(players.get(i).getPlayer())) {
                            betterPlayer.setPlayer(players.get(i).getPlayer());
                            tournamentAggregationDao.updatePlayerResult(betterPlayer);
                        }

                        worsePlayer = playOffPlayer.get(i + 1);
                        if (!worsePlayer.getPlayer().equals(players.get(i + 1).getPlayer())) {
                            worsePlayer.setPlayer(players.get(i + 1).getPlayer());
                            tournamentAggregationDao.updatePlayerResult(worsePlayer);
                        }
                    }
                }
            }

            List<PlayerResult> finalPlayerResults = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(
                    tournament, playOffTable));
            createPlayOffGames(finalPlayerResults, playOffGames);

        }

        return playOffGames;
    }

    private void createPlayOffGames(List<PlayerResult> finalPlayerResults, List<Game> playOffGames) {
        List<PlayerResult> nextPlayerResults = new LinkedList<PlayerResult>();
        if (finalPlayerResults.size() > 1) {
            playOffGames.add(new Game());

            for (int i = 0; i < finalPlayerResults.size() / 2; i++) {

                List<Game> games = tournamentAggregationDao.findGame(new GameForm(finalPlayerResults.get(i),
                        finalPlayerResults.get(finalPlayerResults.size() - i - 1)));
                if (games.isEmpty() && finalPlayerResults.get(i).getPlayerResultId() != 0
                        && finalPlayerResults.get(finalPlayerResults.size() - i - 1).getPlayerResultId() != 0) {
                    createPlayOffGames(finalPlayerResults.get(i),
                            finalPlayerResults.get(finalPlayerResults.size() - i - 1));
                } else {
                    int leftWins = 0;
                    int rightWins = 0;
                    for (Game game : games) {
                        Result result = game.getResult();
                        if (result.getLeftSide() != null && result.getRightSide() != null) {
                            if (result.getLeftSide() > result.getRightSide()) {
                                leftWins++;
                            } else if (result.getLeftSide() < result.getRightSide()) {
                                rightWins++;
                            }
                        }
                    }
                    if (rightWins > leftWins) {
                        nextPlayerResults.add(finalPlayerResults.get(finalPlayerResults.size() - i - 1));
                    } else if (rightWins < leftWins) {
                        nextPlayerResults.add(finalPlayerResults.get(i));
                    } else {
                        nextPlayerResults.add(new PlayerResult());
                    }
                }

                games = tournamentAggregationDao.findGame(new GameForm(finalPlayerResults.get(i), finalPlayerResults
                        .get(finalPlayerResults.size() - i - 1)));
                if (!games.isEmpty()) {
                    playOffGames.add(games.get(0));
                }

            }
            createPlayOffGames(nextPlayerResults, playOffGames);
        }
    }

    private void createPlayOffGames(PlayerResult betterPlayer, PlayerResult worsePlayer) {
        for (int i = 0; i < 9; i++) {
            Game game = new Game(betterPlayer, worsePlayer);
            tournamentAggregationDao.createResult(game.getResult());
            tournamentAggregationDao.createGame(game);

            game = new Game(worsePlayer, betterPlayer);
            tournamentAggregationDao.createResult(game.getResult());
            tournamentAggregationDao.createGame(game);
        }
    }

    // TODO
    public void inicialize(Tournament tournament) {
        Map<String, List<Table>> tablesMap = new HashMap<String, List<Table>>();
        Map<Table, List<PlayerResult>> playersMap = new HashMap<Table, List<PlayerResult>>();

        List<Table> tables = tournamentAggregationDao.findTable(new TableForm(tournament));

        for (Table table : tables) {
            if (tablesMap.get(table.getName()) == null) {
                tablesMap.put(table.getName(), new ArrayList<Table>());
            }
            tablesMap.get(table.getName()).add(table);
            List<PlayerResult> player = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(tournament,
                    table));
            if (playersMap.get(table) == null) {
                playersMap.put(table, new ArrayList<PlayerResult>());
            }
            playersMap.get(table).addAll(player);
        }
        TournamentCache tournamentCache = TournamentCache.getInstance();
        tournamentCache.setTables(tablesMap);
        tournamentCache.setPlayers(playersMap);

    }
}