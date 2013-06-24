package org.toursys.processor.service;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.TournamentException;
import org.toursys.processor.comparators.AdvantageComparator;
import org.toursys.processor.comparators.BasicComparator;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.schedule.AdvancedRoundRobinSchedule;
import org.toursys.processor.schedule.BasicRoundRobinSchedule;
import org.toursys.processor.schedule.RoundRobinSchedule;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Round;
import org.toursys.repository.model.Score;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentService {

    private TournamentAggregationDao tournamentAggregationDao;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final int maxBestOfGame = 9;
    public static final String ALGORITHM_SHA = "SHA-512";

    public TournamentService() {

    }

    public Player createPlayer(Player player) {
        return tournamentAggregationDao.createPlayer(player);
    }

    public int updatePlayer(Player player) {
        return tournamentAggregationDao.updatePlayer(player);
    }

    public int deletePlayer(Player player) {
        return tournamentAggregationDao.deletePlayer(player);
    }

    public List<Player> getListPlayer(Player player) {
        player.setInit(Player.Association.user.name());
        return tournamentAggregationDao.getListPlayers(player);
    }

    public List<Player> getNotRegistratedPlayers(Tournament tournament) {
        return tournamentAggregationDao.getNotRegistratedPlayers(tournament);
    }

    public List<Season> getAllSeasons() {
        return tournamentAggregationDao.getAllSeasons();
    }

    public List<Season> getListSeasons(Season season) {
        return tournamentAggregationDao.getListSeason(season);
    }

    public int updateSeason(Season season) {
        return tournamentAggregationDao.updateSeason(season);
    }

    public Season createSeason(Season season) {
        return tournamentAggregationDao.createSeason(season);
    }

    public Season getSeason(Season season) {
        return tournamentAggregationDao.getSeason(season);
    }

    public int deleteSeason(Season season) {
        return tournamentAggregationDao.deleteSeason(season);
    }

    public List<Tournament> getListTournaments(Tournament tournament) {
        return tournamentAggregationDao.getListTournaments(tournament);
    }

    public int updateTournament(Tournament tournament) {
        return tournamentAggregationDao.updateTournament(tournament);
    }

    public Tournament createTournament(Season season, Tournament tournament) {
        return tournamentAggregationDao.createTournament(tournament._setSeason(season));
    }

    public Tournament getTournament(Tournament tournament) {
        return tournamentAggregationDao.getTournament(tournament);
    }

    public int deleteTournament(Tournament tournament) {
        return tournamentAggregationDao.deleteTournament(tournament);
    }

    public PlayerResult createPlayerResult(Player player, Groups group) {
        return tournamentAggregationDao.createPlayerResult(player, group);
    }

    public int updatePlayerResult(PlayerResult playerResult) {
        return tournamentAggregationDao.updatePlayerResult(playerResult);
    }

    public int deletePlayerResult(PlayerResult playerResult, Tournament tournament) {
        return tournamentAggregationDao.deletePlayerResult(playerResult);

        /*
         * List<PlayerResult> finalPlayers = tournamentAggregationDao.findPlayerResult(new PlayerResultForm(playerResult
         * .getPlayer())); if (!finalPlayers.isEmpty()) { for (PlayerResult finalPlayer : finalPlayers) {
         * tournamentAggregationDao.deletePlayerResult(playerResult); } }
         */
    }

    public List<PlayerResult> getRegistratedPlayerResult(Tournament tournament) {
        return tournamentAggregationDao.getRegistratedPlayerResult(tournament);
    }

    public List<PlayerResult> getPlayerResultInGroup(PlayerResult playerResult) {
        playerResult.setInit(PlayerResult.Association.games.name(), PlayerResult.Association.player.name());
        return tournamentAggregationDao.getListPlayerResult(playerResult);
    }

    public int updateGroups(Tournament tournament, Groups group) {
        if (group.getCopyResult()) {
            List<PlayerResult> player = getPlayerResultInGroup(new PlayerResult()._setGroup(group));

            // TODO co ked ich bude menej postupovat ako je zadane
            if (tournament.getFinalPromoting() % 2 == 0) {
                group.setNumberOfHockey(player.size() / 2);
            } else {
                List<Groups> groups = getBasicGroups(new Groups()._setTournament(tournament));
                group.setNumberOfHockey(player.size() / groups.size());
            }

        }

        return tournamentAggregationDao.updateGroup(group);
    }

    public Groups getGroup(Groups group) {
        return tournamentAggregationDao.getGroup(group);
    }

    public List<Groups> getBasicGroups(Groups group) {
        group.setType(GroupsType.BASIC);
        logger.info("get basic groups: " + group.toStringFull());
        return tournamentAggregationDao.getListGroups(group);
    }

    public List<Groups> getFinalGroups(Groups group) {
        group.setType(GroupsType.FINAL);
        logger.info("get basic groups: " + group.toStringFull());
        return tournamentAggregationDao.getListGroups(group);
    }

    public PlayerResult createBasicPlayerResult(Tournament tournament, Player player, Groups group) {
        logger.info("creating basic playerResult, player: " + player + " group: " + group);
        if (group.getName() != null) {
            Groups savedGroup = tournamentAggregationDao.getGroup(group._setTournament(tournament)._setType(
                    GroupsType.BASIC));
            if (savedGroup == null) {
                savedGroup = tournamentAggregationDao.createGroup(group._setCopyResult(false)._setPlayThirdPlace(false)
                        ._setNumberOfHockey(1)._setIndexOfFirstHockey(1));
            }
            return tournamentAggregationDao.createPlayerResult(player, savedGroup);
        }
        return null;
    }

    public List<Game> findGame(Game game) {
        return tournamentAggregationDao.findGame(game);
    }

    // TODO ked sa budes nudit toto treba nejak vyriesit. nie je to ciste
    // riesenie ale zatim to funguje a nie je to
    // pomale
    public void updateGame(Game game) {
        logger.info("updating game: " + game);
        tournamentAggregationDao.updateGame(game);
        Game game1 = tournamentAggregationDao.findGame(
                new Game()._setHomePlayerResult(game.getAwayPlayerResult())._setAwayPlayerResult(
                        game.getHomePlayerResult())).get(0);
        game1.setHomeScore(game.getAwayScore());
        game1.setAwayScore(game.getHomeScore());
        tournamentAggregationDao.updateGame(game1);
    }

    // Velmi dobra vez usetri cas z niekolkych sekund na milisekundy
    @Transactional
    public void createGames(final List<PlayerResult> playerResults) {
        logger.info("creating games, playerResult: " + Arrays.toString(playerResults.toArray()));
        long time = System.currentTimeMillis();
        if (!playerResults.isEmpty() && playerResults.get(0).getGames().size() < playerResults.size() - 1) {

            // TODO zamysliet sa ci je v poriadku ked mazeme vysledky, pridat
            // aspon nejake varovanie
            if (!playerResults.get(0).getGames().isEmpty()) {
                for (PlayerResult playerResult : playerResults) {
                    for (Game game : playerResult.getGames()) {
                        tournamentAggregationDao.deleteGame(game);
                    }
                }
            }

            for (PlayerResult playerResult1 : playerResults) {
                for (PlayerResult playerResult2 : playerResults) {
                    if (!playerResult1.equals(playerResult2)) {
                        playerResult1.getGames().add(tournamentAggregationDao.createGame(playerResult1, playerResult2));
                    }
                }
            }
        }
        time = System.currentTimeMillis() - time;
        logger.debug("Celkova doba: " + time + " ms");
    }

    @Transactional
    public List<GameImpl> getSchedule(Groups group, Tournament tournament, List<PlayerResult> playerResults) {
        long time = System.currentTimeMillis();
        logger.info("creating schedule: " + Arrays.toString(playerResults.toArray()));
        RoundRobinSchedule roundRobinSchedule;

        if (group.getCopyResult()) {
            roundRobinSchedule = new AdvancedRoundRobinSchedule(tournament, group, playerResults, getPlayersByGroup(
                    tournament, playerResults));
        } else {
            roundRobinSchedule = new BasicRoundRobinSchedule(group, playerResults);
        }

        time = System.currentTimeMillis() - time;
        logger.debug("Celkova doba: " + time + " ms");
        return roundRobinSchedule.getSchedule();
    }

    private LinkedList<List<PlayerResult>> getPlayersByGroup(Tournament tournament, List<PlayerResult> finalPlayers) {
        LinkedList<List<PlayerResult>> playerByGroup = new LinkedList<List<PlayerResult>>();

        List<Groups> basicGroups = getBasicGroups(new Groups()._setTournament(tournament));
        if (basicGroups.size() < 2) {
            return playerByGroup;
        }

        for (int i = 0; i < basicGroups.size(); i++) {
            playerByGroup.add(i, new ArrayList<PlayerResult>());
            List<PlayerResult> players = getPlayerResultInGroup(new PlayerResult()._setGroup(basicGroups.get(i)));
            for (PlayerResult playerResult : players) {
                for (PlayerResult finalPlayerResult : finalPlayers) {
                    if (playerResult.getPlayer().equals(finalPlayerResult.getPlayer())) {
                        playerByGroup.get(i).add(finalPlayerResult);
                        break;
                    }
                }
            }
        }
        return playerByGroup;
    }

    /**
     * Create schedule where games are counted from previous groups
     * 
     */
    @Transactional
    private List<GameImpl> createAdvancedSchedule(Tournament tournament, Groups group) {
        List<GameImpl> games = new ArrayList<GameImpl>();
        List<Round> schedule = new ArrayList<Round>();

        List<Groups> basicGroupss = getBasicGroups(new Groups()._setTournament(tournament));
        List<PlayerResult> finalPlayers = getPlayerResultInGroup(new PlayerResult()._setGroup(group));

        if (basicGroupss.size() > 1) {
            LinkedList<List<PlayerResult>> playerByGroup = new LinkedList<List<PlayerResult>>();
            for (int i = 0; i < basicGroupss.size(); i++) {
                playerByGroup.add(i, new ArrayList<PlayerResult>());
                List<PlayerResult> players = tournamentAggregationDao.getListPlayerResult(new PlayerResult()
                        ._setGroup(basicGroupss.get(i)));
                for (PlayerResult playerResult : players) {
                    for (PlayerResult finalPlayerResult : finalPlayers) {
                        if (playerResult.getPlayer().equals(finalPlayerResult.getPlayer())) {
                            playerByGroup.get(i).add(finalPlayerResult);
                            break;
                        }
                    }
                }
            }

            int finalPlayerCount = playerByGroup.size();

            int finalRoundCount = finalPlayerCount - 1;

            // TODO zaistit aby bolo v playerByGroup parny pocet hracov
            for (List<PlayerResult> list : playerByGroup) {
                if (list.size() % 2 == 1) {
                    // list.add(new PlayerResult()._setId(-1));
                }
            }

            for (int i = 0; i < finalRoundCount; i++) {
                schedule.add(i, new Round());
                createRound(schedule.get(i), playerByGroup, i % 2 == 1);
                rotatePlayerByGroup(playerByGroup);
            }

            int count = 0;
            int startRound = 1;
            int hockey;
            List<GameImpl> roundGames = new ArrayList<GameImpl>();
            for (Round round : schedule) {
                List<Game> temporaryGames = round.getGames();
                for (Game game : temporaryGames) {
                    if (game != null) {
                        GameImpl gameImpl = new GameImpl(game);
                        gameImpl.setRound(count / group.getNumberOfHockey() + 1);
                        count++;
                        if (startRound == gameImpl.getRound()) {
                            roundGames.add(gameImpl);
                        } else {
                            rotateGames(roundGames, startRound - 1);
                            hockey = group.getIndexOfFirstHockey();
                            for (GameImpl gameImplTemp : roundGames) {
                                gameImplTemp.setHockey(hockey);
                                hockey++;
                            }
                            games.addAll(roundGames);
                            roundGames.clear();
                            roundGames.add(gameImpl);
                            startRound++;
                        }
                    }
                }
            }
        }
        return games;
    }

    private void rotateGames(List<GameImpl> games, int i) {
        if (i > games.size()) {
            i++;
        }
        Collections.rotate(games, i);
    }

    private void rotatePlayerByGroup(LinkedList<List<PlayerResult>> playerByGroup) {
        List<PlayerResult> lastGroupPlayer = playerByGroup.removeLast();
        playerByGroup.add(1, lastGroupPlayer);
    }

    private void createRound(Round round, LinkedList<List<PlayerResult>> playerByGroup, boolean isBalanced) {
        if (playerByGroup.size() % 2 == 0 || (playerByGroup.size() % 2 == 1 && playerByGroup.get(0).size() % 2 == 1)) {
            Map<Integer, Round> roundMap = new HashMap<Integer, Round>();
            int count = 1;
            List<PlayerResult> twoPlayerResult = new ArrayList<PlayerResult>();
            for (List<PlayerResult> playerResults : playerByGroup) {

                twoPlayerResult.addAll(playerResults);
                if (count % 2 == 0) {
                    PlayerResult[] fieldPlayerResul = twoPlayerResult.toArray(new PlayerResult[0]);
                    for (int i = 0; i < twoPlayerResult.size() / 2; i++) {
                        if (roundMap.get(i) == null) {
                            roundMap.put(i, new Round());
                        }
                        createRound(roundMap.get(i), fieldPlayerResul, twoPlayerResult.size() / 2, isBalanced);
                        fieldPlayerResul = rotatePlayerResult(fieldPlayerResul, i);
                    }
                    twoPlayerResult.clear();
                }
                count++;
            }

            for (Map.Entry<Integer, Round> entry : roundMap.entrySet()) {
                round.getGames().addAll(entry.getValue().getGames());
            }
        } else {
            for (int j = 0; j < 2; j++) {
                Map<Integer, Round> roundMap = new HashMap<Integer, Round>();
                for (int i = 0; i < playerByGroup.size(); i++) {

                    List<PlayerResult> twoPlayerResult = new ArrayList<PlayerResult>();

                    if (playerByGroup.size() % 2 == 1) {
                        if (j % 2 == 0) {
                            if (i % 2 == 0) {
                                if (i == playerByGroup.size() - 1) {
                                    fillVerticalGroup(twoPlayerResult, i, playerByGroup);
                                } else {
                                    fillUpHorizontalGroup(twoPlayerResult, i, playerByGroup);
                                }
                            } else {
                                fillDownHorizontalGroup(twoPlayerResult, i, playerByGroup);
                            }
                        } else {
                            if (i % 2 == 0) {
                                if (i == 0) {
                                    fillVerticalGroup(twoPlayerResult, i, playerByGroup);
                                } else {
                                    fillDownHorizontalGroup(twoPlayerResult, i, playerByGroup);
                                }
                            } else {
                                fillUpHorizontalGroup(twoPlayerResult, i, playerByGroup);
                            }
                        }
                    }

                    PlayerResult[] fieldPlayerResul = twoPlayerResult.toArray(new PlayerResult[0]);

                    for (int k = 0; k < twoPlayerResult.size() / 2; k++) {

                        if (roundMap.get(k) == null) {
                            roundMap.put(k, new Round());
                        }
                        createAdvancedRound(roundMap.get(k), fieldPlayerResul, twoPlayerResult.size() / 2, isBalanced,
                                k);
                        fieldPlayerResul = rotatePlayerResult(fieldPlayerResul, k);
                    }

                    twoPlayerResult.clear();

                }

                for (Map.Entry<Integer, Round> entry : roundMap.entrySet()) {
                    round.getGames().addAll(entry.getValue().getGames());
                }
            }
        }

    }

    private void fillUpHorizontalGroup(List<PlayerResult> twoPlayerResult, int i,
            LinkedList<List<PlayerResult>> playerByGroup) {
        twoPlayerResult.addAll(playerByGroup.get(i).subList(0, playerByGroup.get(i).size() / 2));
        int index = i + 1;
        if (index == playerByGroup.size()) {
            index = 0;
        }
        twoPlayerResult.addAll(playerByGroup.get(index).subList(0, playerByGroup.get(i).size() / 2));
    }

    private void fillDownHorizontalGroup(List<PlayerResult> twoPlayerResult, int i,
            LinkedList<List<PlayerResult>> playerByGroup) {
        twoPlayerResult.addAll(playerByGroup.get(i).subList(playerByGroup.get(i).size() / 2,
                playerByGroup.get(i).size()));
        int index = i + 1;
        if (index == playerByGroup.size()) {
            index = 0;
        }
        twoPlayerResult.addAll(playerByGroup.get(index).subList(playerByGroup.get(i).size() / 2,
                playerByGroup.get(i).size()));
    }

    private void fillVerticalGroup(List<PlayerResult> twoPlayerResult, int i,
            LinkedList<List<PlayerResult>> playerByGroup) {
        twoPlayerResult.addAll(playerByGroup.get(i).subList(0, playerByGroup.get(i).size() / 2));
        int index = i + 1;
        if (index == playerByGroup.size()) {
            index = 0;
        }
        twoPlayerResult.addAll(playerByGroup.get(index).subList(playerByGroup.get(i).size() / 2,
                playerByGroup.get(i).size()));
    }

    private PlayerResult[] rotatePlayerResult(PlayerResult[] fieldPlayerResult, int round) {
        PlayerResult pom = fieldPlayerResult[fieldPlayerResult.length / 2 - 1];
        PlayerResult pom1 = fieldPlayerResult[fieldPlayerResult.length - 1];

        // prva polovica sa toci doprava
        /*
         * for (int i = (fieldPlayerResult.length / 2) - 1; i > 0; i--) { fieldPlayerResult[i] = fieldPlayerResult[i -
         * 1]; } fieldPlayerResult[0] = pom;
         */
        for (int i = fieldPlayerResult.length - 1; i > fieldPlayerResult.length / 2; i--) {
            fieldPlayerResult[i] = fieldPlayerResult[i - 1];
        }
        fieldPlayerResult[fieldPlayerResult.length / 2] = pom1;

        /*
         * if ((fieldPlayerResult.length % 4 == 0) && ((round + 2) == (fieldPlayerResult.length / 4) + 1)) { pom1 =
         * fieldPlayerResult[fieldPlayerResult.length - 1];
         * 
         * for (int i = fieldPlayerResult.length - 1; i > fieldPlayerResult.length / 2; i--) { fieldPlayerResult[i] =
         * fieldPlayerResult[i - 1]; } fieldPlayerResult[fieldPlayerResult.length / 2] = pom1; }
         */

        return fieldPlayerResult;
    }

    private void addNewGame(Round round, PlayerResult homePlayer, PlayerResult awayPlayer) {
        boolean found = false;
        if (homePlayer.getId() != null && awayPlayer.getId() != null) {
            for (Game game : homePlayer.getGames()) {
                if (game.getAwayPlayerResult().getId().equals(awayPlayer.getId())) {
                    game._setHomePlayerResult(homePlayer)._setAwayPlayerResult(awayPlayer);
                    round.getGames().add(game);
                    found = true;
                    break;
                }
            }
            if (/* !found && */(homePlayer.getId().equals(-1) || awayPlayer.getId().equals(-1))) {
                round.getGames().add(new Game());
            }
        }
    }

    private void createAdvancedRound(Round round, PlayerResult[] players, int gameCountInRound, boolean rotateRound,
            int index) {
        int playerCount = players.length;

        for (int i = 0; i < gameCountInRound; i++) {
            boolean rotateGame = (i % 2 == 1);

            if ((i == 0 && rotateRound) || rotateGame) {
                addNewGame(round, players[playerCount - i - 1], players[i]);
            } else
                addNewGame(round, players[i], players[playerCount - i - 1]);
        }
    }

    private void createRound(Round round, PlayerResult[] players, int gameCountInRound, boolean rotateRound) {
        int playerCount = players.length;
        for (int i = 0; i < gameCountInRound; i++) {
            boolean rotateGame = (i % 2 == 1);
            if ((i == 0 && rotateRound) || rotateGame) {
                addNewGame(round, players[playerCount - i - 1], players[i]);
            } else
                addNewGame(round, players[i], players[playerCount - i - 1]);
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

    public void calculatePlayerResults(List<PlayerResult> playerResults, Tournament tournament) {
        for (PlayerResult playerResult : playerResults) {
            calculatePlayerResult(playerResult, tournament);
        }
        if (playerResults.size() > 0) {
            sortPlayerResult(playerResults, tournament);
        }
    }

    private void calculatePlayerResult(PlayerResult playerResult, Tournament tournament) {
        long time = System.currentTimeMillis();
        logger.info("calculating playerResult: " + playerResult);
        int points = 0;
        Integer homeScore = 0;
        Integer awayScore = 0;
        for (Game game : playerResult.getGames()) {
            // TODO zistit preco ked playerResult nema ziadnu hru tak tam
            // nacitava jednu z id 0 ???
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
        time = System.currentTimeMillis() - time;
        logger.debug("Celkova doba: " + time + " ms");
    }

    @Transactional
    public void createFinalGroup(Tournament tournament) {
        long time = System.currentTimeMillis();
        logger.info("creating final groups " + tournament);
        List<Groups> basicGroupss = getBasicGroups(new Groups()._setTournament(tournament));
        List<Groups> finalGroupss = getFinalGroups(new Groups()._setTournament(tournament));

        if (!finalGroupss.isEmpty()) {
            for (Groups finalGroups : finalGroupss) {
                tournamentAggregationDao.deleteGroup(finalGroups);
            }
        }

        boolean createGroups = true;
        Map<String, Groups> groupByName = new HashMap<String, Groups>();

        Groups finalGroup = new Groups();
        byte[] nextGroupsByte = { (byte) 66 };

        for (Groups group : basicGroupss) {
            List<PlayerResult> playerResults = tournamentAggregationDao.getListPlayerResult(new PlayerResult()
                    ._setGroup(group));
            Collections.sort(playerResults, new RankComparator());
            int promotingA = Math.min(playerResults.size(), tournament.getFinalPromoting());
            String groupName = "A";
            if (createGroups) {
                int hockeyCount;
                if (promotingA % 2 == 0) {
                    hockeyCount = (promotingA * basicGroupss.size()) / 2;
                } else {
                    hockeyCount = promotingA;
                }
                finalGroup = new Groups(groupName, hockeyCount, GroupsType.FINAL, 1, tournament, true, false);
                finalGroup = tournamentAggregationDao.createGroup(finalGroup);
                groupByName.put(groupName, finalGroup);
            } else {
                finalGroup = groupByName.get(groupName);
            }

            for (int i = 0; i < promotingA; i++) {
                PlayerResult playerResult = tournamentAggregationDao.createPlayerResult(playerResults.get(i)
                        .getPlayer(), finalGroup);
                finalGroup.getPlayerResults().add(playerResult);
            }

            int countLowerGroup = 1;
            while (true) {

                String nextGroups = new String(nextGroupsByte);
                int promotingLower = Math.min(playerResults.size(), tournament.getFinalPromoting() + countLowerGroup
                        * tournament.getLowerPromoting());
                int startIndex = promotingA + ((countLowerGroup - 1) * tournament.getLowerPromoting());

                if (startIndex < promotingLower) {
                    if (((promotingLower - startIndex) / basicGroupss.size()) < tournament.getMinPlayersInGroup()) {

                    }
                    if (createGroups) {
                        finalGroup = new Groups(nextGroups, 1, GroupsType.FINAL, 1, tournament, false, false);
                        finalGroup = tournamentAggregationDao.createGroup(finalGroup);
                        groupByName.put(nextGroups, finalGroup);
                    } else {
                        finalGroup = groupByName.get(nextGroups);
                    }
                    for (int i = startIndex; i < promotingLower; i++) {
                        PlayerResult playerResult = tournamentAggregationDao.createPlayerResult(playerResults.get(i)
                                .getPlayer(), finalGroup);
                        finalGroup.getPlayerResults().add(playerResult);
                    }
                }

                if (playerResults.size() == promotingLower) {
                    break;
                }
                nextGroupsByte[0]++;
                countLowerGroup++;
            }
            createGroups = false;
        }

        if (finalGroup.getPlayerResults().size() < tournament.getMinPlayersInGroup()) {
            nextGroupsByte[0]--;

            for (PlayerResult playerResult : finalGroup.getPlayerResults()) {
                String previousGroupName = new String(nextGroupsByte);
                playerResult.setGroup(groupByName.get(previousGroupName));
                tournamentAggregationDao.updatePlayerResult(playerResult);
            }

            tournamentAggregationDao.deleteGroup(finalGroup);
            groupByName.remove(finalGroup);
        }

        for (Groups group : groupByName.values()) {
            createGames(group.getPlayerResults());
        }

        /*
         * finalGroupss = getFinalGroups(new Groups()._setTournament(tournament)); for (Groups group : finalGroupss) {
         * List<PlayerResult> finalPlayer = tournamentAggregationDao.getListPlayerResult(new PlayerResult()
         * ._setGroup(group)); createGames(finalPlayer); }
         */
        time = System.currentTimeMillis() - time;
        logger.debug("Celkova doba: " + time + " ms");
    }

    @Transactional
    public void copyResult(Tournament tournament) {
        long time = System.currentTimeMillis();
        logger.info("copy result: " + tournament);
        List<Groups> basicGroupss = getBasicGroups(new Groups()._setTournament(tournament));
        List<Groups> finalGroupss = getFinalGroups(new Groups()._setTournament(tournament));

        for (Groups finalGroups : finalGroupss) {
            if (finalGroups.getCopyResult()) { // TODO otestovat !!!!
                List<PlayerResult> finalPlayerResults = getPlayerResultInGroup(new PlayerResult()
                        ._setGroup(finalGroups));
                for (Groups basicGroups : basicGroupss) {
                    List<PlayerResult> playerResults = getPlayerResultInGroup(new PlayerResult()._setGroup(basicGroups));
                    for (PlayerResult finalPlayerResult : finalPlayerResults) {
                        for (PlayerResult playerResult : playerResults) {
                            if (playerResult.getPlayer().getId().equals(finalPlayerResult.getPlayer().getId())) {
                                List<Game> finalGames = findGame(new Game()._setHomePlayerResult(finalPlayerResult));
                                List<Game> basicGames = findGame(new Game()._setHomePlayerResult(playerResult));
                                for (Game finalGame : finalGames) {
                                    for (Game basicGame : basicGames) {
                                        if (finalGame.getAwayPlayerResult().getPlayer().getId()
                                                .equals(basicGame.getAwayPlayerResult().getPlayer().getId())) {
                                            finalGame.setAwayScore(basicGame.getAwayScore());
                                            finalGame.setHomeScore(basicGame.getHomeScore());
                                            updateGame(finalGame);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        logger.debug("Celkova doba: " + time + " ms");
    }

    private PlayerResult createNewPlayerResult(PlayerResult playerResult) {
        PlayerResult pom = new PlayerResult();
        pom._setGroup(playerResult.getGroup())._setId(playerResult.getId())._setPlayer(playerResult.getPlayer())
                ._setPoints(playerResult.getPoints())._setRank(playerResult.getRank())
                ._setScore(playerResult.getScore())._setEqualRank(playerResult.getEqualRank());
        pom.getGames().addAll(playerResult.getGames());
        return pom;
    }

    private void sortPlayerResult(List<PlayerResult> playerResults, Tournament tournament) {
        logger.info("sort playerResults: " + Arrays.toString(playerResults.toArray()));

        Collections.sort(playerResults, new BasicComparator());

        for (int i = 0; i < playerResults.size(); i++) {
            playerResults.get(i).setRank(i + 1);
        }

        List<PlayerResult> temporatyPlayerResult = new ArrayList<PlayerResult>();
        temporatyPlayerResult.add(createNewPlayerResult(playerResults.get(0)));
        int actualRank = 0;
        for (int i = 0; i < playerResults.size() - 1; i++) {
            if (playerResults.get(i).getPoints() == playerResults.get(i + 1).getPoints()) {
                temporatyPlayerResult.add(createNewPlayerResult(playerResults.get(i + 1)));

            }
            if (playerResults.get(i).getPoints() != playerResults.get(i + 1).getPoints()
                    || (i == playerResults.size() - 2)) {
                if (temporatyPlayerResult.size() > 2) {
                    for (PlayerResult playerResult : temporatyPlayerResult) {
                        boolean delGame = true;
                        List<Game> g1 = new ArrayList<Game>(playerResult.getGames());
                        for (Game game : g1) {
                            for (PlayerResult playerResult2 : temporatyPlayerResult) {
                                if (playerResult2.equals(game.getAwayPlayerResult())) {
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
                temporatyPlayerResult.add(createNewPlayerResult(playerResults.get(i + 1)));
                actualRank = i + 1;
            }
        }

        for (int i = 0; i < playerResults.size(); i++) {
            tournamentAggregationDao.updatePlayerResult(playerResults.get(i));
        }
        Collections.sort(playerResults, new RankComparator());
    }

    private int getPlayOffPlayerCount(Groups group, Tournament tournament) {
        int playOffPlayerCount;

        if (group.getName().equals("A")) {
            playOffPlayerCount = tournament.getPlayOffA();
        } else {
            playOffPlayerCount = tournament.getPlayOffLower();
        }

        return playOffPlayerCount;
    }

    private void checkPlayOffCount(Groups group, int playerSize, int playOffPlayerCount) {
        if (playerSize < playOffPlayerCount) {
            throw new TournamentException("Count of players in group: " + group.getName()
                    + " is too little. Check tournamen options");
        }
    }

    private void deletePlayOffGames(List<PlayOffGame> finalgames) {
        for (PlayOffGame playOffGame : finalgames) {
            tournamentAggregationDao.deletePlayOffGame(playOffGame);
        }
    }

    private List<PlayOffGame> createPlayOffGames(LinkedList<PlayerResult> playOffPlayer, Groups group,
            int playerPlayOffCountAfterCheckThirdPlace) {
        List<PlayOffGame> playOffGames = new LinkedList<PlayOffGame>();

        for (int i = 0; i < playerPlayOffCountAfterCheckThirdPlace; i++) {

            PlayOffGame playOffGame;
            if (!playOffPlayer.isEmpty()) {
                playOffGame = tournamentAggregationDao.createPlayOffGame(playOffPlayer.removeFirst().getPlayer(),
                        playOffPlayer.removeLast().getPlayer(), group, i + 1);
            } else {
                playOffGame = tournamentAggregationDao.createPlayOffGame(null, null, group, i + 1);
            }
            for (int j = 0; j < maxBestOfGame; j++) {
                tournamentAggregationDao.createPlayOffResult(playOffGame);
            }
            playOffGames.add(playOffGame);
        }

        return playOffGames;
    }

    private int checkThirdPlace(Groups group, int playOffPlayerCount) {
        int playerPlayOffCountAfterCheckThirdPlace = playOffPlayerCount;
        if (playerPlayOffCountAfterCheckThirdPlace == 2 || !group.getPlayThirdPlace()) {
            playerPlayOffCountAfterCheckThirdPlace--;
        }
        return playerPlayOffCountAfterCheckThirdPlace;
    }

    @Transactional
    public List<PlayOffGame> getPlayOffGames(Tournament tournament, Groups group) {

        if (group == null) {
            return new ArrayList<PlayOffGame>();
        }

        List<PlayerResult> players = getPlayerResultInGroup(new PlayerResult()._setGroup(group));
        Collections.sort(players, new RankComparator());

        int playOffPlayerCount = getPlayOffPlayerCount(group, tournament);

        int playerPlayOffCountAfterCheckThirdPlace = checkThirdPlace(group, playOffPlayerCount);

        // checkPlayOffCount(group, players.size(), playOffPlayerCount);

        while (players.size() < playOffPlayerCount) {
            players.add(new PlayerResult());
        }

        LinkedList<PlayerResult> playOffPlayer = new LinkedList<PlayerResult>(players.subList(0, playOffPlayerCount));

        List<PlayOffGame> finalgames = getPlayOffGames(new PlayOffGame()._setGroup(group));

        if (finalgames.size() != playerPlayOffCountAfterCheckThirdPlace) {
            deletePlayOffGames(finalgames);
            return createPlayOffGames(playOffPlayer, group, playerPlayOffCountAfterCheckThirdPlace);
        } else {
            updatePlayOffGames(playOffPlayerCount, finalgames);
            return finalgames;
        }
    }

    private List<PlayOffGame> getPlayOffGames(PlayOffGame playOffGame) {
        playOffGame.setInit(PlayOffGame.Association.playOffResults.name(), PlayOffGame.Association.awayPlayer.name(),
                PlayOffGame.Association.homePlayer.name());
        return tournamentAggregationDao.findPlayOffGame(playOffGame);
    }

    public void updatePlayOffGames(int playerPlayOffCount, List<PlayOffGame> playOffGames) {
        for (int i = 1; i < playOffGames.size() - 1; i = i + 2) {
            PlayOffGame nextGame1 = playOffGames.get(i - 1);
            updatePlayOffGame(nextGame1, playOffGames, i, playerPlayOffCount);
            PlayOffGame nextGame2 = playOffGames.get(i);
            updatePlayOffGame(nextGame2, playOffGames, i + 1, playerPlayOffCount);
        }
    }

    private void updatePlayOffGame(PlayOffGame playOffGame, List<PlayOffGame> playOffGames, int position,
            int playerPlayOffCount) {

        int homeWin = 0;
        int awayWin = 0;

        for (PlayOffResult playOffResult : playOffGame.getPlayOffResults()) {

            if (playOffResult.getHomeScore() != null && playOffResult.getAwayScore() != null) {

                if (playOffResult.getHomeScore() > playOffResult.getAwayScore()) {
                    homeWin++;
                } else if (playOffResult.getHomeScore() < playOffResult.getAwayScore()) {
                    awayWin++;
                }
            }
        }

        if (homeWin > awayWin) {
            if (position % 2 == 1) {
                tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                        nextGame(playerPlayOffCount, position))._setHomePlayer(playOffGame.getHomePlayer()));
                if (nextGame(playerPlayOffCount, position) == playOffGames.size() - 1) { // ak mame tretie mesto
                    tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                            nextGame(playerPlayOffCount, position) + 1)._setHomePlayer(playOffGame.getAwayPlayer()));
                }
            } else {
                tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                        nextGame(playerPlayOffCount, position))._setAwayPlayer(playOffGame.getHomePlayer()));
                if (nextGame(playerPlayOffCount, position) == playOffGames.size() - 1) { // ak mame tretie mesto
                    tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                            nextGame(playerPlayOffCount, position) + 1)._setAwayPlayer(playOffGame.getAwayPlayer()));
                }
            }
        } else if (homeWin < awayWin) {
            if (position % 2 == 1) {
                tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                        nextGame(playerPlayOffCount, position))._setHomePlayer(playOffGame.getAwayPlayer()));
                if (nextGame(playerPlayOffCount, position) == playOffGames.size() - 1) { // ak mame tretie mesto
                    tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                            nextGame(playerPlayOffCount, position) + 1)._setHomePlayer(playOffGame.getHomePlayer()));
                }
            } else {
                tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                        nextGame(playerPlayOffCount, position))._setAwayPlayer(playOffGame.getAwayPlayer()));
                if (nextGame(playerPlayOffCount, position) == playOffGames.size() - 1) { // ak mame tretie mesto
                    tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                            nextGame(playerPlayOffCount, position) + 1)._setAwayPlayer(playOffGame.getHomePlayer()));
                }
            }
        } else {
            if (position % 2 == 1) {

                tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                        nextGame(playerPlayOffCount, position))._setHomePlayer(null));
                if (nextGame(playerPlayOffCount, position) == playOffGames.size() - 1) { // ak mame tretie mesto
                    tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                            nextGame(playerPlayOffCount, position) + 1)._setHomePlayer(null));
                }

            } else {
                tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                        nextGame(playerPlayOffCount, position))._setAwayPlayer(null));
                if (nextGame(playerPlayOffCount, position) == playOffGames.size() - 1) { // ak mame tretie mesto
                    tournamentAggregationDao.updatePlayOffGame(getPlayOffGameByPosition(playOffGames,
                            nextGame(playerPlayOffCount, position) + 1)._setAwayPlayer(null));
                }
            }
        }
    }

    private PlayOffGame getPlayOffGameByPosition(List<PlayOffGame> playOffGames, int position) {

        for (PlayOffGame playOffGame : playOffGames) {
            if (playOffGame.getPosition().equals(position)) {
                return playOffGame;
            }
        }
        return null;
    }

    public int nextGame(int playerCount, int currentGame) {
        return (playerCount / 2) + (int) Math.ceil((double) currentGame / 2);
    }

    /*
     * position zacinat od 1
     */
    public int getRound(int playerCount, int position) {
        // pri vypisovani kola treba rozlisit prve
        if (position == 0) {
            return 0;
        }

        if (playerCount == position) {
            return getRound(playerCount, position - 1);
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
        // nemalo by nastat
        return rounds;
    }

    private int binlog(int bits) {
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

    public int updatePlayOffResult(PlayOffResult playOffResult) {
        return tournamentAggregationDao.updatePlayOffResult(playOffResult);
    }

    private Map<Integer, List<Player>> getWinnersByRound(List<PlayOffGame> playOffGames, List<Player> players) {
        Map<Integer, List<Player>> winnersByRound = new TreeMap<Integer, List<Player>>(Collections.reverseOrder());
        int playerCountInPlayOff = playOffGames.size();
        boolean thirdGame = true;
        if (playerCountInPlayOff % 2 == 1) {
            playerCountInPlayOff++;
            thirdGame = false;
        }

        for (PlayOffGame playOffGame : playOffGames) {
            int round = getRound(playerCountInPlayOff, playOffGame.getPosition());
            if (winnersByRound.get(round) == null) {
                winnersByRound.put(round, new ArrayList<Player>());
            }

            int homeWin = 0;
            int awayWin = 0;

            for (PlayOffResult playOffResult : playOffGame.getPlayOffResults()) {
                if (playOffResult.getHomeScore() != null && playOffResult.getAwayScore() != null) {
                    if (playOffResult.getHomeScore() > playOffResult.getAwayScore()) {
                        homeWin++;
                    } else if (playOffResult.getHomeScore() < playOffResult.getAwayScore()) {
                        awayWin++;
                    }
                }
            }

            if (homeWin > awayWin) {
                if (round == getRound(playerCountInPlayOff, playOffGames.size())) {

                    players.add(playOffGame.getHomePlayer());
                    players.add(playOffGame.getAwayPlayer());

                } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
                    winnersByRound.get(round).add(playOffGame.getAwayPlayer());
                }
            } else if (homeWin < awayWin) {
                if (round == getRound(playerCountInPlayOff, playOffGames.size())) {

                    players.add(playOffGame.getAwayPlayer());
                    players.add(playOffGame.getHomePlayer());

                } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
                    winnersByRound.get(round).add(playOffGame.getHomePlayer());
                }
            } else {
                if (round == getRound(playerCountInPlayOff, playOffGames.size())) {
                    players.add(new Player());
                    players.add(new Player());
                } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
                    winnersByRound.get(round).add(new Player());
                }
            }
        }
        return winnersByRound;
    }

    public List<Player> createFinalStandings(Map<Groups, List<PlayOffGame>> plaOffGamesByGroup) {
        List<Player> players = new ArrayList<Player>();
        for (Map.Entry<Groups, List<PlayOffGame>> entry : plaOffGamesByGroup.entrySet()) {
            Groups group = entry.getKey();
            List<PlayOffGame> playOffGames = entry.getValue();

            Map<Integer, List<Player>> winnersByRound = getWinnersByRound(playOffGames, players);

            List<PlayerResult> playerResults = getPlayerResultInGroup(new PlayerResult()._setGroup(group));
            Collections.sort(playerResults, new RankComparator());

            for (Map.Entry<Integer, List<Player>> winners : winnersByRound.entrySet()) {
                List<Player> winnerPlayers = winners.getValue();

                for (PlayerResult playerResult : playerResults) {
                    for (Player player : winnerPlayers) {
                        if (playerResult.getPlayer().getId().equals(player.getId())) {
                            players.add(player);
                            break;
                        }
                    }
                }
                for (Player player : winnerPlayers) {
                    if (player.getId() == 0) {
                        players.add(new Player());
                    }
                }
            }

            if (playerResults.size() > playOffGames.size()) {
                for (PlayerResult playerResult : playerResults.subList(Math.max(playOffGames.size(), 2),
                        playerResults.size())) {
                    if (!players.contains(playerResult.getPlayer()))
                        players.add(playerResult.getPlayer());
                }
            }

        }

        // kvoli dorovnavaniu hracov aby sa hralo play off aj ked je malo hracov
        players.removeAll(Collections.singleton(null));
        return players;
    }

    public void createFinalStandings(Tournament tournament) {
        List<Groups> groups = tournamentAggregationDao.getListGroups(new Groups()._setTournament(tournament));
        Collections.sort(groups, new Comparator<Groups>() {

            @Override
            public int compare(Groups o1, Groups o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (Groups group : groups) {
            List<PlayerResult> playerResults = getPlayerResultInGroup(new PlayerResult()._setGroup(group));
            Collections.sort(playerResults, new RankComparator());

            List<PlayOffGame> playOffGames = getPlayOffGames(new PlayOffGame()._setGroup(group));
            Map<Integer, List<Player>> losersByRound = new TreeMap<Integer, List<Player>>(Collections.reverseOrder());
            int playerCountInPlayOff = playOffGames.size();
            boolean thirdGame = true;
            if (playerCountInPlayOff % 2 == 1) {
                playerCountInPlayOff++;
                thirdGame = false;
            }
            int finalRank = 0;

            for (PlayOffGame playOffGame : playOffGames) {
                int round = getRound(playerCountInPlayOff, playOffGame.getPosition());
                if (losersByRound.get(round) == null) {
                    losersByRound.put(round, new ArrayList<Player>());
                }

                int homeWin = 0;
                int awayWin = 0;
                Player winner = null;
                Player loser = null;

                for (PlayOffResult playOffResult : playOffGame.getPlayOffResults()) {
                    if (playOffResult.getHomeScore() != null && playOffResult.getAwayScore() != null) {
                        if (playOffResult.getHomeScore() > playOffResult.getAwayScore()) {
                            homeWin++;
                        } else if (playOffResult.getHomeScore() < playOffResult.getAwayScore()) {
                            awayWin++;
                        }
                    }
                }

                if (homeWin > awayWin) {
                    if (round == getRound(playerCountInPlayOff, playOffGames.size())) {
                        winner = playOffGame.getHomePlayer();
                        loser = playOffGame.getAwayPlayer();
                        finalRank++;

                    } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
                        losersByRound.get(round).add(playOffGame.getAwayPlayer());
                    }
                } else if (homeWin < awayWin) {
                    if (round == getRound(playerCountInPlayOff, playOffGames.size())) {

                        winner = playOffGame.getAwayPlayer();
                        loser = playOffGame.getHomePlayer();
                        finalRank++;

                    } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
                        losersByRound.get(round).add(playOffGame.getHomePlayer());
                    }
                } else {
                    if (round == getRound(playerCountInPlayOff, playOffGames.size())) {
                        finalRank++;

                    } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
                        losersByRound.get(round).add(new Player());
                    }
                }

                FinalStanding finalStandingWinner = new FinalStanding();
                finalStandingWinner.setFinalRank(finalRank);
                finalStandingWinner.setTournament(tournament);
                finalStandingWinner.setPlayer(winner);
                tournamentAggregationDao.createFinalStanding(finalStandingWinner);

                FinalStanding finalStandingLoser = new FinalStanding();
                finalStandingLoser.setFinalRank(finalRank + 1);
                finalStandingLoser.setTournament(tournament);
                finalStandingLoser.setPlayer(loser);
                tournamentAggregationDao.createFinalStanding(finalStandingLoser);

            }

            for (Map.Entry<Integer, List<Player>> winners : losersByRound.entrySet()) {
                List<Player> loserPlayers = winners.getValue();

                for (PlayerResult playerResult : playerResults) {
                    for (Player player : loserPlayers) {
                        if (playerResult.getPlayer().getId().equals(player.getId())) {

                            FinalStanding finalStandingWinner = new FinalStanding();
                            finalStandingWinner.setFinalRank(finalRank);
                            finalStandingWinner.setTournament(tournament);
                            finalStandingWinner.setPlayer(player);
                            tournamentAggregationDao.createFinalStanding(finalStandingWinner);
                            break;
                        }
                    }
                }
                for (Player player : loserPlayers) {
                    if (player.getId() == 0) {
                        FinalStanding finalStandingWinner = new FinalStanding();
                        finalStandingWinner.setFinalRank(finalRank);
                        finalStandingWinner.setTournament(tournament);
                        finalStandingWinner.setPlayer(null);
                        tournamentAggregationDao.createFinalStanding(finalStandingWinner);
                    }
                }
            }

            if (playerResults.size() > playOffGames.size()) {
                for (PlayerResult playerResult : playerResults.subList(Math.max(playOffGames.size(), 2),
                        playerResults.size())) {
                    FinalStanding finalStandingWinner = new FinalStanding();
                    finalStandingWinner.setFinalRank(finalRank);
                    finalStandingWinner.setTournament(tournament);
                    finalStandingWinner.setPlayer(playerResult.getPlayer());
                    tournamentAggregationDao.createFinalStanding(finalStandingWinner);
                }
            }
        }
    }

    public List<FinalStanding> getFinalStandings(Tournament tournament) {
        return tournamentAggregationDao.findFinalStanding(new FinalStanding()._setTournament(tournament));
    }

    public void resetEqualRank(Groups group) {
        List<PlayerResult> players = tournamentAggregationDao.getListPlayerResult(new PlayerResult()._setGroup(group));
        for (PlayerResult playerResult : players) {
            playerResult.setEqualRank(null);
            tournamentAggregationDao.updatePlayerResult(playerResult);
        }
    }

    public User createUser(User user) {
        user.setPassword(encryptUserPassword(user.getPassword()));
        return tournamentAggregationDao.createUser(user);
    }

    public int updateUser(User user) {
        return tournamentAggregationDao.updateUser(user);
    }

    public int deleteUser(User user) {
        user.setPassword(encryptUserPassword(user.getPassword()));
        return tournamentAggregationDao.deleteUser(user);
    }

    public User getUser(User user) {
        return tournamentAggregationDao.getUser(user);
    }

    public List<User> getAllUsers() {
        return tournamentAggregationDao.getAllUsers();
    }

    public String encryptUserPassword(String password) {
        try {
            final MessageDigest md = MessageDigest.getInstance(ALGORITHM_SHA);
            md.update(password.getBytes("UTF-8"));
            byte[] securePassword = md.digest();
            return bytes2hex(securePassword);
        } catch (Exception e) {
            logger.error("!! ERROR encrypt password", e);
            throw new RuntimeException(e);
        }
    }

    private final String bytes2hex(byte[] value) {
        if (value == null)
            return null;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            if ((value[i] <= 0x0F) && (value[i] >= 0))
                result.append('0');
            result.append(Integer.toHexString(value[i] & 0xFF));
        }
        return result.toString();
    }

    @Required
    public void setTournamentAggregationDao(TournamentAggregationDao tournamentAggregationDao) {
        this.tournamentAggregationDao = tournamentAggregationDao;
    }

}