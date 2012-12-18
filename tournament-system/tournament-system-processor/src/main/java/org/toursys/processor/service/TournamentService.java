package org.toursys.processor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.toursys.processor.comparators.BasicComparator;
import org.toursys.repository.form.GameForm;
import org.toursys.repository.form.GroupForm;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GroupType;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Round;
import org.toursys.repository.model.Score;
import org.toursys.repository.model.Season;
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

    public List<Player> getAllPlayers() {
        return tournamentAggregationDao.getAllPlayers();
    }

    public List<Player> getNotRegistratedPlayers(Tournament tournament) {
        return tournamentAggregationDao.getNotRegistratedPlayers(tournament);
    }

    public List<Season> getAllSeasons() {
        return tournamentAggregationDao.getAllSeasons();
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

    public List<Tournament> findTournamentsBySeason(Tournament tournament) {
        return tournamentAggregationDao.findTournamentsBySeason(tournament);
    }

    public void updateTournament(Tournament tournament) {
        tournamentAggregationDao.updateTournament(tournament);
    }

    public void createTournament(Season season, Tournament... tournaments) {
        tournamentAggregationDao.createTournament(season, tournaments);
    }

    public void deleteTournament(Tournament tournament) {
        tournamentAggregationDao.deleteTournament(tournament);
    }

    public void createPlayerResult(Player player, Groups group) {
        tournamentAggregationDao.createPlayerResult(player, group);
    }

    public void deletePlayerResult(PlayerResult playerResult, Tournament tournament) {
        tournamentAggregationDao.deletePlayerResult(playerResult);

        /*
         * List<PlayerResult> finalPlayers = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(playerResult
         * .getPlayer())); if (!finalPlayers.isEmpty()) { for (PlayerResult finalPlayer : finalPlayers) {
         * tournamentAggregationDao.deletePlayerResult(playerResult); } }
         */
    }

    public List<PlayerResult> getRegistratedPlayerResult(Tournament tournament) {
        return tournamentAggregationDao.getRegistratedPlayerResult(tournament);
    }

    public void updateGroups(Groups group) {
        tournamentAggregationDao.updateGroup(group);
    }

    public List<Groups> findGroups(GroupForm groupForm) {
        return tournamentAggregationDao.findGroups(groupForm);
    }

    public void createBasicPlayerResult(Tournament tournament, Player player, Groups group) {
        if (group.getName() != null) {
            Groups savedGroup = tournamentAggregationDao.getGroup(group._setTournament(tournament)._setGroupType(
                    GroupType.B.value()));
            if (savedGroup == null) {
                savedGroup = tournamentAggregationDao.createGroup(group);
            }

            tournamentAggregationDao.createPlayerResult(player, savedGroup);
        }
    }

    public List<Game> findGame(GameForm gameForm) {
        return tournamentAggregationDao.findGame(gameForm);
    }

    public void updateGame(Game game) {
        tournamentAggregationDao.updateGame(game);
        Game game1 = tournamentAggregationDao.findGame(
                new GameForm(game.getAwayPlayerResult(), game.getHomePlayerResult())).get(0);
        if (game.equals(game1)) {
            game1 = tournamentAggregationDao.findGame(
                    new GameForm(game.getHomePlayerResult(), game.getAwayPlayerResult())).get(0);
        }
        tournamentAggregationDao.updateGame(game1);
    }

    public void createGames(List<PlayerResult> playerResults) {
        if (!playerResults.isEmpty() && playerResults.get(0).getGames().size() < playerResults.size()) {
            for (PlayerResult playerResult1 : playerResults) {
                for (PlayerResult playerResult2 : playerResults) {
                    if (!playerResult1.equals(playerResult2)) {
                        if (tournamentAggregationDao.findGame(new GameForm(playerResult1, playerResult2)).isEmpty()) {
                            tournamentAggregationDao.createGame(playerResult1, playerResult2);
                        }
                    }
                }
            }
        }
    }

    public List<Game> getSchedule(Groups group, Tournament tournament) {
        List<Game> games = new ArrayList<Game>();
        List<Round> schedule = new ArrayList<Round>();

        /*
         * if (group.getGroupType().equals(GroupType.F.value()) && group.getName().equals("A")) { List<Groups>
         * basicGroupss = tournamentAggregationDao.findGroups(new GroupForm(tournament, GroupType.B));
         * List<PlayerResult> finalPlayers = tournamentAggregationDao.findPlayerResult(new PlayerResultForm( tournament,
         * group));
         * 
         * if (basicGroupss.size() > 1) { LinkedList<List<PlayerResult>> playerByGroup = new
         * LinkedList<List<PlayerResult>>(); for (int i = 0; i < basicGroupss.size(); i++) { playerByGroup.add(i, new
         * ArrayList<PlayerResult>()); List<PlayerResult> players = tournamentAggregationDao.findPlayerResult(new
         * PlayerResultForm( tournament, basicGroupss.get(i))); for (PlayerResult playerResult : players) { for
         * (PlayerResult finalPlayerResult : finalPlayers) { if
         * (playerResult.getPlayer().equals(finalPlayerResult.getPlayer())) {
         * playerByGroup.get(i).add(finalPlayerResult); break; } } } }
         * 
         * // TODO otestova ? if (playerByGroup.size() % 2 == 1) { playerByGroup.add(playerByGroup.size(), new
         * ArrayList<PlayerResult>()); }
         * 
         * int finalPlayerCount = playerByGroup.size();
         * 
         * int finalRoundCount = finalPlayerCount - 1;
         * 
         * for (int i = 0; i < finalRoundCount; i++) { schedule.add(i, new Round()); createRound(schedule.get(i),
         * playerByGroup); rotatePlayerByGroup(playerByGroup); } } } else { List<PlayerResult> playerResults =
         * tournamentAggregationDao.findPlayerResult(new PlayerResultForm( tournament, group));
         * 
         * // if we have odd number of player if (playerResults.size() % 2 == 1) {
         * playerResults.add(playerResults.size(), new PlayerResult()); }
         * 
         * int playerCount = playerResults.size();
         * 
         * int roundCount = playerCount - 1;
         * 
         * PlayerResult[] temporaryField = createTemporaryField(playerResults); int countGames = playerCount / 2; for
         * (int i = 0; i < roundCount; i++) { schedule.add(i, new Round()); createRound(schedule.get(i), temporaryField,
         * countGames); temporaryField = rotateTemporaryField(temporaryField, playerCount); }
         * balanceFirstPlayer(roundCount, schedule); }
         * 
         * int count = 0; for (Round round : schedule) { List<Game> temporaryGames = round.getGames(); for (Game game :
         * temporaryGames) { if (game != null) { game.setHockey(count % group.getNumberOfHockey() +
         * group.getIndexOfFirstHockey()); game.setRound(count / group.getNumberOfHockey() + 1); games.add(game);
         * count++; } } }
         */
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
                if (players[i].getId() != 0 && players[playerCount - i - 1].getId() != 0) {
                    round.getGames().add(
                            tournamentAggregationDao.findGame(new GameForm(players[i], players[playerCount - i - 1]))
                                    .get(0));
                } else {
                    round.getGames().add(null);
                }
            } else {
                if (players[i].getId() != 0 && players[playerCount - i - 1].getId() != 0) {
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
                            new GameForm(game.getAwayPlayerResult(), game.getHomePlayerResult())).get(0);
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
        Integer homeScore = 0;
        Integer awayScore = 0;
        for (Game game : playerResult.getGames()) {
            // TODO zistit preco ked playerResult nema ziadnu hru tak tam nacitava jednu z id 0 ???
            if (game.getId() != 0) {
                if (game.getHomeScore() != null && game.getAwayScore() != null) {
                    if (game.getHomeScore() > game.getAwayScore()) {
                        points += tournament.getWinPoints();
                    } else if (game.getHomeScore().equals(game.getAwayScore())) {
                        points += 1;
                    }
                    homeScore += game.getHomeScore();
                    awayScore += game.getAwayScore();
                }
            }
        }
        playerResult.setPoints(points);
        playerResult.setScore(new Score(homeScore, awayScore));
        tournamentAggregationDao.updatePlayerResult(playerResult);
    }

    public void createFinalGroup(Tournament tournament) {
        List<Groups> basicGroupss = tournamentAggregationDao.findGroups(new GroupForm(tournament, GroupType.B));
        List<Groups> finalGroupss = tournamentAggregationDao.findGroups(new GroupForm(tournament, GroupType.F));
        /*
         * if (!finalGroupss.isEmpty()) { for (Groups finalGroups : finalGroupss) {
         * tournamentAggregationDao.deleteGroup(finalGroups); } }
         * 
         * Groups finalGroups = new Groups(); for (Groups group : basicGroupss) { List<PlayerResult> playerResults =
         * tournamentAggregationDao.findPlayerResult(new PlayerResultForm( tournament, group));
         * Collections.sort(playerResults, new RankComparator()); int promotingA = Math.min(playerResults.size(),
         * tournament.getPromotingA()); finalGroupss = tournamentAggregationDao.findGroups(new GroupForm("A",
         * tournament, GroupType.F)); if (finalGroupss.isEmpty()) { finalGroups = new Groups();
         * finalGroups.setName("A"); finalGroups.setGroupsType(GroupType.F);
         * finalGroups.setTournamentId(tournament.getTournamentId());
         * tournamentAggregationDao.createGroups(finalGroups); }
         * 
         * finalGroups = tournamentAggregationDao.findGroups(new GroupForm("A", tournament, GroupType.F)).get(0);
         * 
         * for (int i = 0; i < promotingA; i++) { PlayerResult playerResult = new PlayerResult();
         * playerResult.setPlayer(playerResults.get(i).getPlayer()); playerResult.setPoints(0);
         * playerResult.setScore("0:0"); playerResult.setTournamentGroups(finalGroups);
         * tournamentAggregationDao.createPlayerResult(playerResult); }
         * 
         * byte[] nextGroupsByte = { (byte) 66 }; int countLowerGroup = 1; while (true) { String nextGroups = new
         * String(nextGroupsByte);
         * 
         * int promotingLower = Math.min(playerResults.size(), tournament.getPromotingA() + countLowerGroup
         * tournament.getPromotingLower()); int startIndex = promotingA + ((countLowerGroup - 1) *
         * tournament.getPromotingLower());
         * 
         * if (startIndex < promotingLower) { finalGroupss = tournamentAggregationDao.findGroups(new
         * GroupForm(nextGroups, tournament, GroupType.F)); if (finalGroupss.isEmpty()) { finalGroups = new Groups();
         * finalGroups.setName(nextGroups); finalGroups.setGroupsType(GroupType.F);
         * finalGroups.setTournamentId(tournament.getTournamentId());
         * tournamentAggregationDao.createGroups(finalGroups); }
         * 
         * finalGroups = tournamentAggregationDao.findGroups( new GroupForm(nextGroups, tournament,
         * GroupType.F)).get(0);
         * 
         * for (int i = startIndex; i < promotingLower; i++) { PlayerResult playerResult = new PlayerResult();
         * playerResult.setPlayer(playerResults.get(i).getPlayer()); playerResult.setPoints(0);
         * playerResult.setScore("0:0"); playerResult.setTournamentGroups(finalGroups);
         * tournamentAggregationDao.createPlayerResult(playerResult); }
         * 
         * } if (playerResults.size() == promotingLower) { break; } nextGroupsByte[0]++; countLowerGroup++; }
         * 
         * }
         * 
         * finalGroupss = tournamentAggregationDao.findGroups(new GroupForm(tournament, GroupType.F)); for (Groups group
         * : finalGroupss) { List<PlayerResult> finalPlayer = tournamentAggregationDao.findPlayerResult(new
         * PlayerResultForm(tournament, group)); createGames(finalPlayer); }
         */
    }

    public void copyResult(Tournament tournament) {
        /*
         * List<Groups> basicGroupss = tournamentAggregationDao.findGroups(new GroupForm(tournament, GroupType.B));
         * List<Groups> finalGroupss = tournamentAggregationDao.findGroups(new GroupForm(tournament, GroupType.F));
         * 
         * if (!finalGroupss.isEmpty()) { Groups finalGroups = finalGroupss.get(0); List<PlayerResult>
         * finalPlayerResults = tournamentAggregationDao.findPlayerResult(new PlayerResultForm( tournament,
         * finalGroups)); for (Groups basicGroups : basicGroupss) { List<PlayerResult> playerResults =
         * tournamentAggregationDao.findPlayerResult(new PlayerResultForm( tournament, basicGroups)); for (PlayerResult
         * finalPlayerResult : finalPlayerResults) { for (PlayerResult playerResult : playerResults) { if
         * (playerResult.getPlayer().equals(finalPlayerResult.getPlayer())) { List<Game> finalGames =
         * finalPlayerResult.getGames(); List<Game> basicGames = playerResult.getGames(); for (Game finalGame :
         * finalGames) { for (Game basicGame : basicGames) { if
         * (finalGame.getOpponent().getPlayer().equals(basicGame.getOpponent().getPlayer()) &&
         * finalGame.getPlayerResult().getPlayer() .equals(basicGame.getPlayerResult().getPlayer())) {
         * finalGame.getResult().setLeftSide(basicGame.getResult().getLeftSide());
         * finalGame.getResult().setRightSide(basicGame.getResult().getRightSide());
         * finalGame.getResult().setOvertime(basicGame.getResult().getOvertime());
         * tournamentAggregationDao.updateResult(finalGame.getResult()); break; } } } } } } }
         * calculatePlayerResults(finalPlayerResults, tournament); }
         */
    }

    private void sortPlayerResult(List<PlayerResult> playerResults, Tournament tournament) {
        Collections.sort(playerResults, new BasicComparator());
        /*
         * for (int i = 0; i < playerResults.size(); i++) { playerResults.get(i).setRank(i + 1); }
         * 
         * List<PlayerResult> temporatyPlayerResult = new ArrayList<PlayerResult>();
         * temporatyPlayerResult.add(playerResults.get(0).clone()); int actualRank = 0; for (int i = 0; i <
         * playerResults.size() - 1; i++) { if (playerResults.get(i).getPoints() == playerResults.get(i +
         * 1).getPoints()) { temporatyPlayerResult.add(playerResults.get(i + 1).clone());
         * 
         * } if (playerResults.get(i).getPoints() != playerResults.get(i + 1).getPoints() || (i == playerResults.size()
         * - 2)) { if (temporatyPlayerResult.size() > 2) { for (PlayerResult playerResult : temporatyPlayerResult) {
         * boolean delGame = true; List<Game> g1 = new ArrayList<Game>(playerResult.getGames()); for (Game game : g1) {
         * for (PlayerResult playerResult2 : temporatyPlayerResult) { if (playerResult2.equals(game.getOpponent())) {
         * delGame = false; break; } } if (delGame) { playerResult.getGames().remove(game); } delGame = true; } } for
         * (PlayerResult playerResult : temporatyPlayerResult) { calculatePlayerResult(playerResult, tournament); }
         * 
         * Collections.sort(temporatyPlayerResult, new AdvantageComparator());
         * 
         * for (PlayerResult playerResult1 : playerResults) { for (int j = 0; j < temporatyPlayerResult.size(); j++) {
         * if (playerResult1.equals(temporatyPlayerResult.get(j))) { playerResult1.setRank(j + 1 + actualRank); } } }
         * 
         * } temporatyPlayerResult.clear(); temporatyPlayerResult.add(playerResults.get(i + 1).clone()); actualRank = i
         * + 1; }
         * 
         * }
         * 
         * for (int i = 0; i < playerResults.size(); i++) {
         * tournamentAggregationDao.updatePlayerResult(playerResults.get(i)); } Collections.sort(playerResults, new
         * RankComparator());
         */
    }

    public List<Game> createPlayOffGames(Tournament tournament) {
        List<Groups> finalGroupss = tournamentAggregationDao.findGroups(new GroupForm(tournament, GroupType.F));
        List<Game> playOffGames = new LinkedList<Game>();
        /*
         * for (Groups group : finalGroupss) {
         * 
         * Game game = new Game(); game.setGameId(-1); playOffGames.add(game);
         * 
         * Groups playOffGroups = new Groups(); String playOffGroupsName = "P" + group.getName();
         * playOffGroups.setName(playOffGroupsName); playOffGroups.setGroupsType(GroupType.P);
         * playOffGroups.setTournamentId(tournament.getTournamentId());
         * 
         * List<Groups> playOffGroupss = tournamentAggregationDao.findGroups(new GroupForm(playOffGroupsName,
         * tournament)); if (playOffGroupss.isEmpty()) { tournamentAggregationDao.createGroups(playOffGroups);
         * playOffGroups = tournamentAggregationDao.findGroups(new GroupForm(playOffGroupsName, tournament)) .get(0); }
         * else { playOffGroups = playOffGroupss.get(0); }
         * 
         * List<PlayerResult> players = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(tournament,
         * group)); Collections.sort(players, new RankComparator());
         * 
         * int currentPlayOff; if (group.getName().equals("A")) { currentPlayOff = tournament.getPlayOffA(); } else {
         * currentPlayOff = tournament.getPlayOffLower(); }
         * 
         * List<PlayerResult> playOffPlayer = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(
         * tournament, playOffGroups));
         * 
         * if (players.size() >= currentPlayOff) { for (int i = 0; i < currentPlayOff - 1; i = i + 2) { PlayerResult
         * betterPlayer = new PlayerResult(); PlayerResult worsePlayer = new PlayerResult();
         * 
         * if (currentPlayOff != playOffPlayer.size()) { for (PlayerResult playerResult : playOffPlayer) {
         * tournamentAggregationDao.deletePlayerResult(playerResult); }
         * 
         * betterPlayer.setTournamentGroups(playOffGroups); betterPlayer.setPlayer(players.get(i).getPlayer());
         * betterPlayer.setScore("0:0"); betterPlayer.setPoints(0);
         * tournamentAggregationDao.createPlayerResult(betterPlayer);
         * 
         * worsePlayer.setTournamentGroups(playOffGroups); worsePlayer.setPlayer(players.get(i + 1).getPlayer());
         * worsePlayer.setScore("0:0"); worsePlayer.setPoints(0);
         * tournamentAggregationDao.createPlayerResult(worsePlayer);
         * 
         * } else { betterPlayer = playOffPlayer.get(i);
         * 
         * if (!betterPlayer.getPlayer().equals(players.get(i).getPlayer())) {
         * betterPlayer.setPlayer(players.get(i).getPlayer());
         * tournamentAggregationDao.updatePlayerResult(betterPlayer); }
         * 
         * worsePlayer = playOffPlayer.get(i + 1); if (!worsePlayer.getPlayer().equals(players.get(i + 1).getPlayer()))
         * { worsePlayer.setPlayer(players.get(i + 1).getPlayer());
         * tournamentAggregationDao.updatePlayerResult(worsePlayer); } } } }
         * 
         * List<PlayerResult> finalPlayerResults = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(
         * tournament, playOffGroups)); createPlayOffGames(finalPlayerResults, playOffGames);
         * 
         * }
         */

        return playOffGames;
    }

    private void createPlayOffGames(List<PlayerResult> finalPlayerResults, List<Game> playOffGames) {
        /*
         * List<PlayerResult> nextPlayerResults = new LinkedList<PlayerResult>(); if (finalPlayerResults.size() > 1) {
         * playOffGames.add(new Game());
         * 
         * for (int i = 0; i < finalPlayerResults.size() / 2; i++) {
         * 
         * List<Game> games = tournamentAggregationDao.findGame(new GameForm(finalPlayerResults.get(i),
         * finalPlayerResults.get(finalPlayerResults.size() - i - 1))); if (games.isEmpty() &&
         * finalPlayerResults.get(i).getPlayerResultId() != 0 && finalPlayerResults.get(finalPlayerResults.size() - i -
         * 1).getPlayerResultId() != 0) { createPlayOffGames(finalPlayerResults.get(i),
         * finalPlayerResults.get(finalPlayerResults.size() - i - 1)); } else { int leftWins = 0; int rightWins = 0; for
         * (Game game : games) { Result result = game.getResult(); if (result.getLeftSide() != null &&
         * result.getRightSide() != null) { if (result.getLeftSide() > result.getRightSide()) { leftWins++; } else if
         * (result.getLeftSide() < result.getRightSide()) { rightWins++; } } } if (rightWins > leftWins) {
         * nextPlayerResults.add(finalPlayerResults.get(finalPlayerResults.size() - i - 1)); } else if (rightWins <
         * leftWins) { nextPlayerResults.add(finalPlayerResults.get(i)); } else { nextPlayerResults.add(new
         * PlayerResult()); } }
         * 
         * games = tournamentAggregationDao.findGame(new GameForm(finalPlayerResults.get(i), finalPlayerResults
         * .get(finalPlayerResults.size() - i - 1))); if (!games.isEmpty()) { playOffGames.add(games.get(0)); }
         * 
         * } createPlayOffGames(nextPlayerResults, playOffGames); }
         */
    }

    private void createPlayOffGames(PlayerResult betterPlayer, PlayerResult worsePlayer) {
        /*
         * for (int i = 0; i < 9; i++) { Game game = new Game(betterPlayer, worsePlayer);
         * tournamentAggregationDao.createResult(game.getResult()); tournamentAggregationDao.createGame(game);
         * 
         * game = new Game(worsePlayer, betterPlayer); tournamentAggregationDao.createResult(game.getResult());
         * tournamentAggregationDao.createGame(game); }
         */
    }
}