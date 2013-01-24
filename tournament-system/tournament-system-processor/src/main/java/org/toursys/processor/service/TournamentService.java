package org.toursys.processor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.toursys.processor.TournamentException;
import org.toursys.processor.comparators.AdvantageComparator;
import org.toursys.processor.comparators.BasicComparator;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.GroupType;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Round;
import org.toursys.repository.model.Score;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentService {

    private TournamentAggregationDao tournamentAggregationDao;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final int maxBestOfGame = 9;

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

    public List<PlayerResult> getPlayerResultInGroup(PlayerResult playerResult) {
        playerResult.setInit(PlayerResult.Association.games.name(), PlayerResult.Association.player.name());
        return tournamentAggregationDao.getListPlayerResult(playerResult);
    }

    public void updateGroups(Groups group) {
        tournamentAggregationDao.updateGroup(group);
    }

    public List<Groups> getBasicGroups(Groups group) {
        group.setGroupType(GroupType.B.name());
        logger.info("get basic groups: " + group.toStringFull());
        return tournamentAggregationDao.getListGroups(group);
    }

    public List<Groups> getFinalGroups(Groups group) {
        group.setGroupType(GroupType.F.name());
        logger.info("get basic groups: " + group.toStringFull());
        return tournamentAggregationDao.getListGroups(group);
    }

    public void createBasicPlayerResult(Tournament tournament, Player player, Groups group) {
        logger.info("creating basic playerResult, player: " + player + " group: " + group);
        if (group.getName() != null) {
            Groups savedGroup = tournamentAggregationDao.getGroup(group._setTournament(tournament)._setGroupType(
                    GroupType.B.value()));
            if (savedGroup == null) {
                savedGroup = tournamentAggregationDao.createGroup(group);
            }
            tournamentAggregationDao.createPlayerResult(player, savedGroup);
        }
    }

    public List<Game> findGame(Game game) {
        return tournamentAggregationDao.findGame(game);
    }

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

    public void createGames(List<PlayerResult> playerResults) {
        logger.info("creating games, playerResult: " + Arrays.toString(playerResults.toArray()));
        if (!playerResults.isEmpty() && playerResults.get(0).getGames().size() < playerResults.size() - 1) {
            for (PlayerResult playerResult1 : playerResults) {
                for (PlayerResult playerResult2 : playerResults) {
                    if (!playerResult1.equals(playerResult2)) {
                        if (tournamentAggregationDao.findGame(
                                new Game()._setHomePlayerResult(playerResult1)._setAwayPlayerResult(playerResult2))
                                .isEmpty()) {
                            playerResult1.getGames().add(
                                    tournamentAggregationDao.createGame(playerResult1, playerResult2));
                        }
                    }
                }
            }
        }
    }

    public List<GameImpl> getSchedule(Groups group, Tournament tournament, List<PlayerResult> playerResults) {
        logger.info("creating schedule: " + Arrays.toString(playerResults.toArray()));
        if (group.getCopyResult()) {
            return createAdvancedSchedule(tournament, group, playerResults);
        } else {
            return createBasicSchedule(group, playerResults);
        }
    }

    /**
     * Create schedule where games are counted from previous groups
     * 
     */
    private List<GameImpl> createAdvancedSchedule(Tournament tournament, Groups group, List<PlayerResult> playerResults) {
        List<GameImpl> games = new ArrayList<GameImpl>();
        List<Round> schedule = new ArrayList<Round>();

        List<Groups> basicGroupss = getBasicGroups(new Groups()._setTournament(tournament));
        List<PlayerResult> finalPlayers = tournamentAggregationDao.getListPlayerResult(new PlayerResult()
                ._setGroup(group));

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

            // TODO otestovat ?
            if (playerByGroup.size() % 2 == 1) {
                playerByGroup.add(playerByGroup.size(), new ArrayList<PlayerResult>());
            }

            int finalPlayerCount = playerByGroup.size();

            int finalRoundCount = finalPlayerCount - 1;

            for (int i = 0; i < finalRoundCount; i++) {
                schedule.add(i, new Round());
                createRound(schedule.get(i), playerByGroup, i % 2 == 0);
                rotatePlayerByGroup(playerByGroup);
            }

            int count = 0;
            for (Round round : schedule) {
                List<Game> temporaryGames = round.getGames();
                for (Game game : temporaryGames) {
                    if (game != null) {
                        GameImpl gameImpl = new GameImpl(game);
                        gameImpl.setHockey(count % group.getNumberOfHockey() + group.getIndexOfFirstHockey());
                        gameImpl.setRound(count / group.getNumberOfHockey() + 1);
                        games.add(gameImpl);
                        count++;
                    }
                }
            }
        }
        return games;
    }

    private List<GameImpl> createBasicSchedule(Groups group, List<PlayerResult> playerResults) {

        List<GameImpl> games = new ArrayList<GameImpl>();
        List<Round> schedule = new ArrayList<Round>();
        int currentPlayerCount = playerResults.size();

        if (playerResults.size() % 2 == 1) {
            playerResults.add(playerResults.size(), new PlayerResult());
        }

        int playerCount = playerResults.size();

        int roundCount = playerCount - 1;

        PlayerResult[] temporaryField = createTemporaryField(playerResults);
        int countGames = playerCount / 2;
        for (int i = 0; i < roundCount; i++) {
            schedule.add(i, new Round());
            createRound(schedule.get(i), temporaryField, countGames, i % 2 == 0);
            temporaryField = rotateTemporaryField(temporaryField, playerCount);
        }

        int count = 0;
        for (Round round : schedule) {
            List<Game> temporaryGames = round.getGames();
            for (Game game : temporaryGames) {
                if (game != null) {
                    GameImpl gameImpl = new GameImpl(game);
                    gameImpl.setHockey(count % group.getNumberOfHockey() + group.getIndexOfFirstHockey());
                    gameImpl.setRound(count / group.getNumberOfHockey() + 1);
                    games.add(gameImpl);
                    count++;
                }
            }
        }

        if (currentPlayerCount % 2 == 1) {
            playerResults.remove(playerResults.size() - 1);
        }

        return games;
    }

    private void rotatePlayerByGroup(LinkedList<List<PlayerResult>> playerByGroup) {
        List<PlayerResult> lastGroupPlayer = playerByGroup.removeLast();
        playerByGroup.add(1, lastGroupPlayer);
    }

    private void createRound(Round round, LinkedList<List<PlayerResult>> playerByGroup, boolean isBalanced) {
        int count = 1;
        List<PlayerResult> twoPlayerResult = new ArrayList<PlayerResult>();
        for (List<PlayerResult> playerResults : playerByGroup) {
            twoPlayerResult.addAll(playerResults);
            if (count % 2 == 0) {
                PlayerResult[] fieldPlayerResul = twoPlayerResult.toArray(new PlayerResult[0]);
                for (int i = 0; i < twoPlayerResult.size() / 2; i++) {
                    createRound(round, fieldPlayerResul, twoPlayerResult.size() / 2, isBalanced);
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

    private void addNewGame(Round round, PlayerResult homePlayer, PlayerResult awayPlayer) {
        if (homePlayer.getId() != null && awayPlayer.getId() != null) {
            for (Game game : homePlayer.getGames()) {
                if (game.getAwayPlayerResult().getId().equals(awayPlayer.getId())) {
                    game._setHomePlayerResult(homePlayer)._setAwayPlayerResult(awayPlayer);
                    round.getGames().add(game);
                    break;
                }
            }
        } else {
            // TODO ??
            round.getGames().add(null);
        }

    }

    private void createRound(Round round, PlayerResult[] players, int gameCountInRound, boolean isBalanced) {
        int playerCount = players.length;
        for (int i = 0; i < gameCountInRound; i++) {
            boolean even = (i % 2 == 0);
            if (even) {
                if (!round.getGames().isEmpty() && !isBalanced) {
                    addNewGame(round, players[i], players[playerCount - i - 1]);
                } else {
                    addNewGame(round, players[playerCount - i - 1], players[i]);
                }
            } else {
                if (!round.getGames().isEmpty() && !isBalanced) {
                    addNewGame(round, players[playerCount - i - 1], players[i]);
                } else {
                    addNewGame(round, players[i], players[playerCount - i - 1]);
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
        logger.info("calculating playerResult: " + playerResult);
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
    }

    public void createFinalGroup(Tournament tournament) {
        logger.info("creating final groups " + tournament);
        List<Groups> basicGroupss = getBasicGroups(new Groups()._setTournament(tournament));
        List<Groups> finalGroupss = getFinalGroups(new Groups()._setTournament(tournament));

        if (!finalGroupss.isEmpty()) {
            for (Groups finalGroups : finalGroupss) {
                tournamentAggregationDao.deleteGroup(finalGroups);
            }
        }

        Groups finalGroups;
        for (Groups group : basicGroupss) {
            List<PlayerResult> playerResults = tournamentAggregationDao.getListPlayerResult(new PlayerResult()
                    ._setGroup(group));
            Collections.sort(playerResults, new RankComparator());
            int promotingA = Math.min(playerResults.size(), tournament.getFinalPromoting());
            finalGroups = new Groups("A", 1, GroupType.F.name(), 1, tournament, true, false);
            finalGroups = tournamentAggregationDao.createGroup(finalGroups);

            for (int i = 0; i < promotingA; i++) {
                tournamentAggregationDao.createPlayerResult(playerResults.get(i).getPlayer(), finalGroups);
            }

            byte[] nextGroupsByte = { (byte) 66 };
            int countLowerGroup = 1;
            while (true) {
                String nextGroups = new String(nextGroupsByte);

                int promotingLower = Math.min(playerResults.size(), tournament.getFinalPromoting() + countLowerGroup
                        * tournament.getLowerPromoting());
                int startIndex = promotingA + ((countLowerGroup - 1) * tournament.getLowerPromoting());

                if (startIndex < promotingLower) {
                    finalGroups = new Groups(nextGroups, 1, GroupType.F.name(), 1, tournament, false, false);
                    finalGroups = tournamentAggregationDao.createGroup(finalGroups);

                    for (int i = startIndex; i < promotingLower; i++) {
                        tournamentAggregationDao.createPlayerResult(playerResults.get(i).getPlayer(), finalGroups);
                    }
                }

                if (playerResults.size() == promotingLower) {
                    break;
                }
                nextGroupsByte[0]++;
                countLowerGroup++;
            }
        }

        finalGroupss = getFinalGroups(new Groups()._setTournament(tournament));
        for (Groups group : finalGroupss) {
            List<PlayerResult> finalPlayer = tournamentAggregationDao.getListPlayerResult(new PlayerResult()
                    ._setGroup(group));
            createGames(finalPlayer);
        }

    }

    public void copyResult(Tournament tournament) {
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
    }

    private PlayerResult createNewPlayerResult(PlayerResult playerResult) {
        PlayerResult pom = new PlayerResult();
        pom._setGroup(playerResult.getGroup())._setId(playerResult.getId())._setPlayer(playerResult.getPlayer())
                ._setPoints(playerResult.getPoints())._setRank(playerResult.getRank())
                ._setScore(playerResult.getScore());
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

    public List<PlayOffGame> getPlayOffGames(Tournament tournament, Groups group) {

        if (group == null) {
            return new ArrayList<PlayOffGame>();
        }

        List<PlayerResult> players = getPlayerResultInGroup(new PlayerResult()._setGroup(group));
        Collections.sort(players, new RankComparator());

        int playOffPlayerCount = getPlayOffPlayerCount(group, tournament);

        int playerPlayOffCountAfterCheckThirdPlace = checkThirdPlace(group, playOffPlayerCount);

        checkPlayOffCount(group, players.size(), playOffPlayerCount);

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
        throw new RuntimeException("Invalid position to get round");
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

    public PlayOffResult updatePlayOffResult(PlayOffResult playOffResult) {
        return tournamentAggregationDao.updatePlayOffResult(playOffResult);
    }

    private Map<Integer, List<Player>> getWinnersByRound(List<PlayOffGame> playOffGames, List<Player> players) {
        Map<Integer, List<Player>> winnersByRound = new TreeMap<Integer, List<Player>>(Collections.reverseOrder());

        for (PlayOffGame playOffGame : playOffGames) {
            int round = getRound(playOffGames.size(), playOffGame.getPosition());
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
                if (round == getRound(playOffGames.size(), playOffGames.size())) {

                    players.add(playOffGame.getHomePlayer());
                    players.add(playOffGame.getAwayPlayer());

                } else if (round < (getRound(playOffGames.size(), playOffGames.size()) - 1)) {
                    winnersByRound.get(round).add(playOffGame.getAwayPlayer());
                }
            } else if (homeWin < awayWin) {
                if (round == getRound(playOffGames.size(), playOffGames.size())) {

                    players.add(playOffGame.getAwayPlayer());
                    players.add(playOffGame.getHomePlayer());

                } else if (round < (getRound(playOffGames.size(), playOffGames.size()) - 1)) {
                    winnersByRound.get(round).add(playOffGame.getHomePlayer());
                }
            } else {
                if (round == getRound(playOffGames.size(), playOffGames.size())) {
                    players.add(new Player());
                    players.add(new Player());
                } else if (round < (getRound(playOffGames.size(), playOffGames.size()) - 1)) {
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
            for (PlayerResult playerResult : playerResults.subList(Math.max(playOffGames.size(), 2),
                    playerResults.size())) {
                if (!players.contains(playerResult.getPlayer()))
                    players.add(playerResult.getPlayer());
            }

        }
        return players;
    }
}