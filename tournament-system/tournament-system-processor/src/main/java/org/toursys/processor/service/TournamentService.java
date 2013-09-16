package org.toursys.processor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.toursys.processor.util.GroupsName;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Score;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentService {

    private TournamentAggregationDao tournamentAggregationDao;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final int maxBestOfGame = 9;

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

    public int deletePlayerResult(PlayerResult playerResult) {
        int count = 0;
        List<PlayerResult> playerResults = tournamentAggregationDao.getListPlayerResult(new PlayerResult()._setGroup(
                playerResult.getGroup())._setPlayer(playerResult.getPlayer()));
        for (PlayerResult deletedPlayerResult : playerResults) {
            count = count + tournamentAggregationDao.deletePlayerResult(deletedPlayerResult);
        }
        return count;
    }

    public List<PlayerResult> getRegistratedPlayerResult(Tournament tournament) {
        return tournamentAggregationDao.getRegistratedPlayerResult(tournament);
    }

    @Transactional
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
        logger.info("get basic groups: " + group.toStringFull());
        group.setType(GroupsType.BASIC);
        return tournamentAggregationDao.getListGroups(group);
    }

    public List<Groups> getFinalGroups(Groups group) {
        logger.info("get basic groups: " + group.toStringFull());
        group.setType(GroupsType.FINAL);
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
        game.setInit(Game.Association.homePlayerResult.name(), Game.Association.awayPlayerResult.name());
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

        if (group.getType() != GroupsType.BASIC && group.getCopyResult()) {
            roundRobinSchedule = new AdvancedRoundRobinSchedule(group, getAdvancedPlayersByGroup(group, tournament,
                    playerResults));
        } else {
            roundRobinSchedule = new BasicRoundRobinSchedule(group, playerResults);
        }

        time = System.currentTimeMillis() - time;
        logger.debug("Celkova doba: " + time + " ms");
        return roundRobinSchedule.getSchedule();
    }

    // vrati hracov pre rozpis kde sa pocitaju vysledky(postupujuci hraci)
    private LinkedList<List<PlayerResult>> getAdvancedPlayersByGroup(Groups group, Tournament tournament,
            List<PlayerResult> finalPlayerResults) {
        LinkedList<List<PlayerResult>> playerByGroup = new LinkedList<List<PlayerResult>>();

        List<Groups> basicGroups = getBasicGroups(new Groups()._setTournament(tournament));
        if (basicGroups.size() < 2 || group.getType().equals(GroupsType.BASIC)) {
            return playerByGroup;
        }

        List<Groups> finalGroups = getFinalGroups(new Groups()._setTournament(tournament));
        Collections.sort(finalGroups, new Comparator<Groups>() {
            @Override
            public int compare(Groups o1, Groups o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        int startIndex = 0;
        for (int i = 0; i < finalGroups.size(); i++) {

            if (finalGroups.get(i).equals(group)) {
                break;
            }
            if (i == 0) {
                startIndex = startIndex + tournament.getFinalPromoting();
            } else {
                startIndex = startIndex + tournament.getLowerPromoting();
            }
        }

        for (int i = 0; i < basicGroups.size(); i++) {
            playerByGroup.add(i, new ArrayList<PlayerResult>());
            List<PlayerResult> players = getPlayerResultInGroup(new PlayerResult()._setGroup(basicGroups.get(i)));
            Collections.sort(players, new RankComparator());
            int promotingCount;
            System.out.println("ZZZZZZZZZZZZZZZZZZZZ" + group.getName());

            if (group.getName().equals("A")) {
                promotingCount = tournament.getFinalPromoting();
            } else {
                promotingCount = tournament.getLowerPromoting();
            }
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + promotingCount);
            for (PlayerResult playerResult : players.subList(Math.min(startIndex, playerByGroup.size()),
                    Math.min(players.size(), promotingCount + startIndex))) {
                for (PlayerResult finalPlayerResult : finalPlayerResults) {
                    if (playerResult.getPlayer().equals(finalPlayerResult.getPlayer())) {
                        playerByGroup.get(i).add(finalPlayerResult);
                        break;
                    }
                }
            }
        }
        return playerByGroup;
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
        logger.info("creating final groups in tournament: " + tournament);
        List<Groups> basicGroups = getBasicGroups(new Groups()._setTournament(tournament));
        List<Groups> finalGroups = getFinalGroups(new Groups()._setTournament(tournament));

        if (!finalGroups.isEmpty()) {
            for (Groups finalGroup : finalGroups) {
                tournamentAggregationDao.deleteGroup(finalGroup);
            }
        }

        boolean createGroups = true;
        Map<String, Groups> groupByName = new HashMap<String, Groups>();

        Groups finalGroup = new Groups();
        GroupsName groupsName = new GroupsName();
        String groupName = null;
        // byte[] nextGroupsByte = new byte[1];

        for (Groups group : basicGroups) {
            // nextGroupsByte[0] = 66;
            List<PlayerResult> playerResults = getPlayerResultInGroup(new PlayerResult()._setGroup(group));
            Collections.sort(playerResults, new RankComparator());
            int promotingA = Math.min(playerResults.size(), tournament.getFinalPromoting());
            groupName = groupsName.getFirst();
            if (createGroups) {
                int hockeyCount;
                if (promotingA % 2 == 0) {
                    hockeyCount = (promotingA * basicGroups.size()) / 2;
                } else {
                    hockeyCount = promotingA;
                }
                finalGroup = new Groups(groupName, hockeyCount, GroupsType.FINAL, 1, tournament, true, false);
                logger.info("creating final group " + finalGroup);
                finalGroup = tournamentAggregationDao.createGroup(finalGroup);
                groupByName.put(groupName, finalGroup);
            } else {
                finalGroup = groupByName.get(groupName);
                logger.info("getting final group " + finalGroup);
            }

            for (int i = 0; i < promotingA; i++) {
                logger.info("creating player result " + playerResults.get(i).getPlayer());
                PlayerResult playerResult = tournamentAggregationDao.createPlayerResult(playerResults.get(i)
                        .getPlayer(), finalGroup);
                finalGroup.getPlayerResults().add(playerResult);
            }

            int countLowerGroup = 1;
            while (true) {

                String nextGroups = groupsName.getNext(groupName);
                int promotingLower = Math.min(playerResults.size(), tournament.getFinalPromoting() + countLowerGroup
                        * tournament.getLowerPromoting());
                int startIndex = promotingA + ((countLowerGroup - 1) * tournament.getLowerPromoting());

                if (startIndex < promotingLower) {
                    if (((promotingLower - startIndex) / basicGroups.size()) < tournament.getMinPlayersInGroup()) {

                    }
                    if (createGroups) {
                        finalGroup = new Groups(nextGroups, 1, GroupsType.FINAL, 1, tournament, false, false);
                        logger.info("creating final group " + finalGroup);
                        finalGroup = tournamentAggregationDao.createGroup(finalGroup);
                        groupByName.put(nextGroups, finalGroup);
                    } else {
                        finalGroup = groupByName.get(nextGroups);
                        logger.info("getting final group " + finalGroup);
                    }
                    for (int i = startIndex; i < promotingLower; i++) {
                        logger.info("creating player result " + playerResults.get(i).getPlayer());
                        PlayerResult playerResult = tournamentAggregationDao.createPlayerResult(playerResults.get(i)
                                .getPlayer(), finalGroup);
                        finalGroup.getPlayerResults().add(playerResult);
                    }
                }

                if (playerResults.size() == promotingLower) {
                    break;
                }
                countLowerGroup++;
                groupName = nextGroups;
            }
            createGroups = false;
        }

        if (finalGroup.getPlayerResults().size() < tournament.getMinPlayersInGroup() && groupName != null
                && !groupsName.getFirst().equals(groupName)) {

            for (PlayerResult playerResult : finalGroup.getPlayerResults()) {
                String previousGroupName = groupsName.getPrevious(groupName);
                playerResult.setGroup(groupByName.get(previousGroupName));
                tournamentAggregationDao.updatePlayerResult(playerResult);
            }

            logger.info("deleting final group " + finalGroup);
            tournamentAggregationDao.deleteGroup(finalGroup);
            groupByName.remove(finalGroup);
        }

        /*
         * for (Groups group : groupByName.values()) { createGames(group.getPlayerResults()); }
         */

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
                playOffGame = tournamentAggregationDao.createPlayOffGame(playOffPlayer.removeFirst(),
                        playOffPlayer.removeLast(), group, getPosition(i, playOffPlayer.size() / 2));
            } else {
                playOffGame = tournamentAggregationDao.createPlayOffGame(null, null, group, i + 1);
            }
            for (int j = 0; j < maxBestOfGame; j++) {
                PlayOffResult result = tournamentAggregationDao.createPlayOffResult(playOffGame);
                playOffGame.getPlayOffResults().add(result);
            }
            playOffGames.add(playOffGame);
        }

        Collections.sort(playOffGames, new Comparator<PlayOffGame>() {
            @Override
            public int compare(PlayOffGame o1, PlayOffGame o2) {
                return o1.getPosition().compareTo(o2.getPosition());
            }
        });
        return playOffGames;
    }

    private int getPosition(int i, int size) {
        if (i == 1) {
            return 1;
        } else if (i == size) {
            return size - 1;
        }
        if (i == 2) {
            return size;
        }
        if (i == 3) {
            return size / 2;
        } else {
            if (i % 2 == 0) {
                return size / 2 + (i - 3);
            } else {
                return size / 2 - (i - 4);
            }
        }
    }

    private int checkThirdPlace(Groups group, int playOffPlayerCount) {
        int playerPlayOffCountAfterCheckThirdPlace = playOffPlayerCount;
        if (playerPlayOffCountAfterCheckThirdPlace == 2 || !group.getPlayThirdPlace()) {
            playerPlayOffCountAfterCheckThirdPlace--;
        }
        return playerPlayOffCountAfterCheckThirdPlace;
    }

    @Transactional
    // TODO dost dlho to trva
    public List<PlayOffGame> getPlayOffGames(Tournament tournament, Groups group) {
        long time = System.currentTimeMillis();
        List<PlayOffGame> playOffGames;

        if (group == null) {
            return new ArrayList<PlayOffGame>();
        }
        List<PlayerResult> players = getPlayerResultInGroup(new PlayerResult()._setGroup(group));
        Collections.sort(players, new RankComparator());

        GroupsName groupsName = new GroupsName();
        String previousGroupName = groupsName.getPrevious(group.getName());

        int previousPlayerResultCount = 0;
        if (previousGroupName.length() > 0) {
            Groups previousGroup = tournamentAggregationDao.getGroup(new Groups()._setName(previousGroupName)
                    ._setTournament(tournament));
            List<PlayerResult> previousPlayerResult = tournamentAggregationDao.getListPlayerResult(new PlayerResult()
                    ._setGroup(previousGroup));
            previousPlayerResultCount = previousPlayerResult.size();
        }

        int playOffPlayerCount = getPlayOffPlayerCount(group, tournament);

        int playerPlayOffCountAfterCheckThirdPlace = checkThirdPlace(group, playOffPlayerCount);

        // checkPlayOffCount(group, players.size(), playOffPlayerCount);

        while (players.size() < playOffPlayerCount) {
            players.add(new PlayerResult());
        }

        LinkedList<PlayerResult> playOffPlayer = new LinkedList<PlayerResult>(players.subList(0, playOffPlayerCount));

        List<PlayOffGame> finalgames = getPlayOffGames(new PlayOffGame()._setGroup(group));
        Collections.sort(finalgames, new Comparator<PlayOffGame>() {
            @Override
            public int compare(PlayOffGame o1, PlayOffGame o2) {
                return o1.getPosition().compareTo(o2.getPosition());
            }
        });
        if (finalgames.size() != playerPlayOffCountAfterCheckThirdPlace) {
            logger.info("Creating playOff games");
            deletePlayOffGames(finalgames);
            playOffGames = createPlayOffGames(playOffPlayer, group, playerPlayOffCountAfterCheckThirdPlace);
        } else {
            logger.info("Updating playOff games");
            updatePlayOffGames(playOffPlayerCount, finalgames, tournament, previousPlayerResultCount);
            playOffGames = finalgames;
        }
        time = System.currentTimeMillis() - time;
        logger.debug("Celkova doba: " + time + " ms");
        return playOffGames;
    }

    @Transactional
    private List<PlayOffGame> getPlayOffGames(PlayOffGame playOffGame) {
        playOffGame.setInit(PlayOffGame.Association.playOffResults.name(),
                PlayOffGame.Association.awayPlayerResult.name(), PlayOffGame.Association.homePlayerResult.name());
        return tournamentAggregationDao.findPlayOffGame(playOffGame);
    }

    public void updatePlayOffGames(int playerPlayOffCount, List<PlayOffGame> playOffGames, Tournament tournament,
            int playerGroupCountSuffix) {
        int lastRound = 1;

        ArrayList<PlayerResult> finalStandings = new ArrayList<PlayerResult>();
        int gameCount = (playOffGames.size() % 2 == 1) ? playOffGames.size() - 1 : playOffGames.size() - 2;
        for (int i = 0; i < gameCount; i++) {
            int round = TournamentUtil.getRound(playerPlayOffCount, i + 1);
            PlayOffGame nextGame1 = playOffGames.get(i);
            updatePlayOffGame(nextGame1, playOffGames, i + 1, playerPlayOffCount, round != lastRound, finalStandings,
                    round, tournament, playerGroupCountSuffix);
            lastRound = round;
        }
        // nehra sa o tretie miesto
        if (playOffGames.size() % 2 == 1) {
            updateFinalStandings(finalStandings, lastRound + 1, playerPlayOffCount, tournament, playerGroupCountSuffix);
        }

        int position = 1;
        for (int i = gameCount; i < playOffGames.size(); i++) {
            int homeWin = 0;
            int awayWin = 0;
            FinalStanding firstFinalStanding = tournamentAggregationDao.getFinalStanding(new FinalStanding()
                    ._setFinalRank(position)._setTournament(tournament));
            FinalStanding secondFinalStanding = tournamentAggregationDao.getFinalStanding(new FinalStanding()
                    ._setFinalRank(position + 1)._setTournament(tournament));

            for (PlayOffResult playOffResult : playOffGames.get(i).getPlayOffResults()) {

                if (playOffResult.getHomeScore() != null && playOffResult.getAwayScore() != null) {

                    if (playOffResult.getHomeScore() > playOffResult.getAwayScore()) {
                        homeWin++;
                    } else if (playOffResult.getHomeScore() < playOffResult.getAwayScore()) {
                        awayWin++;
                    }
                }
            }

            if (homeWin > awayWin) {
                firstFinalStanding.setPlayer(playOffGames.get(i).getHomePlayerResult().getPlayer());
                secondFinalStanding.setPlayer(playOffGames.get(i).getAwayPlayerResult().getPlayer());
            } else if (homeWin < awayWin) {
                firstFinalStanding.setPlayer(playOffGames.get(i).getAwayPlayerResult().getPlayer());
                secondFinalStanding.setPlayer(playOffGames.get(i).getHomePlayerResult().getPlayer());
            } else {
                firstFinalStanding.setPlayer(null);
                secondFinalStanding.setPlayer(null);
            }
            tournamentAggregationDao.updateFinalStanding(firstFinalStanding);
            tournamentAggregationDao.updateFinalStanding(secondFinalStanding);
            position += 2;
        }

    }

    public void createFinalStandings(Tournament tournament, int playerCount) {
        List<FinalStanding> finalStandings = tournamentAggregationDao.findFinalStanding(new FinalStanding()
                ._setTournament(tournament));
        for (FinalStanding finalStanding : finalStandings) {
            tournamentAggregationDao.deleteFinalStanding(finalStanding);
        }
        for (int i = 1; i < playerCount + 1; i++) {
            FinalStanding finalStanding = new FinalStanding();
            finalStanding.setFinalRank(i);
            finalStanding.setTournament(tournament);
            logger.info("Creating final standing: " + finalStanding);
            tournamentAggregationDao.createFinalStanding(finalStanding);
        }
    }

    private void updateFinalStandings(List<PlayerResult> playerResults, int round, int playerPlayOffCount,
            Tournament tournament, int playerGroupCountSuffix) {
        Collections.sort(playerResults, new RankComparator());
        // TODO optimalizovat !!!
        Collections.reverse(playerResults);
        int maxRound = TournamentUtil.binlog(playerPlayOffCount);
        int actualRank = (int) Math.pow(2, maxRound - (round - 2)) + playerGroupCountSuffix;
        for (PlayerResult playerResult : playerResults) {
            FinalStanding finalStanding = tournamentAggregationDao.getFinalStanding(new FinalStanding()._setFinalRank(
                    actualRank)._setTournament(tournament));
            if ((finalStanding != null && playerResult != null)
                    && (finalStanding.getPlayer() == null || !finalStanding.getPlayer()
                            .equals(playerResult.getPlayer()))) {
                finalStanding.setPlayer(playerResult.getPlayer());
                logger.info("Updating final standing: " + finalStanding);
                tournamentAggregationDao.updateFinalStanding(finalStanding);
            }
            actualRank--;
        }
        playerResults.clear();
    }

    private void updatePlayOffGame(PlayOffGame playOffGame, List<PlayOffGame> playOffGames, int position,
            int playerPlayOffCount, boolean newRound, List<PlayerResult> finalStandings, int round,
            Tournament tournament, int playerGroupCountSuffix) {

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

        if (newRound) {
            updateFinalStandings(finalStandings, round, playerPlayOffCount, tournament, playerGroupCountSuffix);
        }

        int nextPosition = nextGame(playerPlayOffCount, position);
        PlayOffGame tempPlayOffGame = getPlayOffGameByPosition(playOffGames, nextPosition);
        PlayOffGame tempThirdPlayOffGame = null;
        // tretie miesto vytvarame ak mame tolko hier kolko hracov (ak nie je tretie miesto tak je to minus jedna a
        // zaroven je nasledujuca hra je finale)
        boolean isThirdPlace = (nextPosition == playOffGames.size() - 1) && (playOffGames.size() == playerPlayOffCount);
        if (isThirdPlace) {
            tempThirdPlayOffGame = getPlayOffGameByPosition(playOffGames, nextPosition + 1);
        }

        if (homeWin > awayWin) {
            finalStandings.add(playOffGame.getAwayPlayerResult());
            if (position % 2 == 1) {
                tempPlayOffGame._setHomePlayerResult((playOffGame.getHomePlayerResult()));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setHomePlayerResult((playOffGame.getAwayPlayerResult()));
                }
            } else {
                tempPlayOffGame._setAwayPlayerResult((playOffGame.getHomePlayerResult()));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setAwayPlayerResult((playOffGame.getAwayPlayerResult()));
                }
            }
        } else if (homeWin < awayWin) {
            finalStandings.add(playOffGame.getHomePlayerResult());
            if (position % 2 == 1) {
                tempPlayOffGame._setHomePlayerResult((playOffGame.getAwayPlayerResult()));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setHomePlayerResult((playOffGame.getHomePlayerResult()));
                }
            } else {
                tempPlayOffGame._setAwayPlayerResult((playOffGame.getAwayPlayerResult()));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setAwayPlayerResult((playOffGame.getHomePlayerResult()));
                }
            }
        } else {
            finalStandings.add(new PlayerResult());
            if (position % 2 == 1) {
                tempPlayOffGame._setHomePlayerResult((null));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setHomePlayerResult((null));
                }
            } else {
                tempPlayOffGame._setAwayPlayerResult((null));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setAwayPlayerResult((null));
                }
            }
        }

        logger.info("Updating playOff game");
        tournamentAggregationDao.updatePlayOffGame(tempPlayOffGame);
        if (tempThirdPlayOffGame != null) {
            logger.info("Updating third playOff game");
            tournamentAggregationDao.updatePlayOffGame(tempThirdPlayOffGame);
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

    public int updatePlayOffResult(PlayOffResult playOffResult) {
        return tournamentAggregationDao.updatePlayOffResult(playOffResult);
    }

    /*
     * private Map<Integer, List<Player>> getWinnersByRound(List<PlayOffGame> playOffGames, List<Player> players) {
     * Map<Integer, List<Player>> winnersByRound = new TreeMap<Integer, List<Player>>(Collections.reverseOrder()); int
     * playerCountInPlayOff = playOffGames.size(); boolean thirdGame = true; if (playerCountInPlayOff % 2 == 1) {
     * playerCountInPlayOff++; thirdGame = false; }
     * 
     * for (PlayOffGame playOffGame : playOffGames) { int round = getRound(playerCountInPlayOff,
     * playOffGame.getPosition()); if (winnersByRound.get(round) == null) { winnersByRound.put(round, new
     * ArrayList<Player>()); }
     * 
     * int homeWin = 0; int awayWin = 0;
     * 
     * for (PlayOffResult playOffResult : playOffGame.getPlayOffResults()) { if (playOffResult.getHomeScore() != null &&
     * playOffResult.getAwayScore() != null) { if (playOffResult.getHomeScore() > playOffResult.getAwayScore()) {
     * homeWin++; } else if (playOffResult.getHomeScore() < playOffResult.getAwayScore()) { awayWin++; } } }
     * 
     * if (homeWin > awayWin) { if (round == getRound(playerCountInPlayOff, playOffGames.size())) {
     * 
     * players.add(playOffGame.getHomePlayer()); players.add(playOffGame.getAwayPlayer());
     * 
     * } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
     * winnersByRound.get(round).add(playOffGame.getAwayPlayer()); } } else if (homeWin < awayWin) { if (round ==
     * getRound(playerCountInPlayOff, playOffGames.size())) {
     * 
     * players.add(playOffGame.getAwayPlayer()); players.add(playOffGame.getHomePlayer());
     * 
     * } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
     * winnersByRound.get(round).add(playOffGame.getHomePlayer()); } } else { if (round ==
     * getRound(playerCountInPlayOff, playOffGames.size())) { players.add(new Player()); players.add(new Player()); }
     * else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
     * winnersByRound.get(round).add(new Player()); } } } return winnersByRound; }
     * 
     * /* public List<Player> createFinalStandings(Map<Groups, List<PlayOffGame>> plaOffGamesByGroup) { List<Player>
     * players = new ArrayList<Player>(); for (Map.Entry<Groups, List<PlayOffGame>> entry :
     * plaOffGamesByGroup.entrySet()) { Groups group = entry.getKey(); List<PlayOffGame> playOffGames =
     * entry.getValue();
     * 
     * Map<Integer, List<Player>> winnersByRound = getWinnersByRound(playOffGames, players);
     * 
     * List<PlayerResult> playerResults = getPlayerResultInGroup(new PlayerResult()._setGroup(group));
     * Collections.sort(playerResults, new RankComparator());
     * 
     * for (Map.Entry<Integer, List<Player>> winners : winnersByRound.entrySet()) { List<Player> winnerPlayers =
     * winners.getValue();
     * 
     * for (PlayerResult playerResult : playerResults) { for (Player player : winnerPlayers) { if
     * (playerResult.getPlayer().getId().equals(player.getId())) { players.add(player); break; } } } for (Player player
     * : winnerPlayers) { if (player.getId() == 0) { players.add(new Player()); } } }
     * 
     * if (playerResults.size() > playOffGames.size()) { for (PlayerResult playerResult :
     * playerResults.subList(Math.max(playOffGames.size(), 2), playerResults.size())) { if
     * (!players.contains(playerResult.getPlayer())) players.add(playerResult.getPlayer()); } }
     * 
     * }
     * 
     * // kvoli dorovnavaniu hracov aby sa hralo play off aj ked je malo hracov
     * players.removeAll(Collections.singleton(null)); return players; }
     * 
     * public void createFinalStandings(Tournament tournament) { logger.info("creating final standings in tournament " +
     * tournament);
     * 
     * List<Groups> groups = getFinalGroups(new Groups()._setTournament(tournament)); Collections.sort(groups, new
     * Comparator<Groups>() {
     * 
     * @Override public int compare(Groups o1, Groups o2) { return o1.getName().compareTo(o2.getName()); } }); for
     * (Groups group : groups) { List<PlayerResult> playerResults = getPlayerResultInGroup(new
     * PlayerResult()._setGroup(group)); Collections.sort(playerResults, new RankComparator());
     * 
     * List<PlayOffGame> playOffGames = getPlayOffGames(new PlayOffGame()._setGroup(group)); Map<Integer, List<Player>>
     * losersByRound = new TreeMap<Integer, List<Player>>(Collections.reverseOrder()); int playerCountInPlayOff =
     * playOffGames.size(); boolean thirdGame = true; if (playerCountInPlayOff % 2 == 1) { playerCountInPlayOff++;
     * thirdGame = false; } int finalRank = 0; FinalStanding savedFinalStanding = null;
     * 
     * for (PlayOffGame playOffGame : playOffGames) { int round = getRound(playerCountInPlayOff,
     * playOffGame.getPosition()); if (losersByRound.get(round) == null) { losersByRound.put(round, new
     * ArrayList<Player>()); }
     * 
     * int homeWin = 0; int awayWin = 0; Player winner = null; Player loser = null; boolean saveFinalStanding = true;
     * 
     * for (PlayOffResult playOffResult : playOffGame.getPlayOffResults()) { if (playOffResult.getHomeScore() != null &&
     * playOffResult.getAwayScore() != null) { if (playOffResult.getHomeScore() > playOffResult.getAwayScore()) {
     * homeWin++; } else if (playOffResult.getHomeScore() < playOffResult.getAwayScore()) { awayWin++; } } }
     * 
     * if (homeWin > awayWin) { if (round == getRound(playerCountInPlayOff, playOffGames.size())) { winner =
     * playOffGame.getHomePlayer(); loser = playOffGame.getAwayPlayer(); finalRank++;
     * 
     * } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
     * losersByRound.get(round).add(playOffGame.getAwayPlayer()); saveFinalStanding = false; } } else if (homeWin <
     * awayWin) { if (round == getRound(playerCountInPlayOff, playOffGames.size())) {
     * 
     * winner = playOffGame.getAwayPlayer(); loser = playOffGame.getHomePlayer(); finalRank++;
     * 
     * } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
     * losersByRound.get(round).add(playOffGame.getHomePlayer()); saveFinalStanding = false; } } else { if (round ==
     * getRound(playerCountInPlayOff, playOffGames.size())) { finalRank++;
     * 
     * } else if (!thirdGame || (round < (getRound(playerCountInPlayOff, playOffGames.size()) - 1))) {
     * losersByRound.get(round).add(new Player()); saveFinalStanding = false; } }
     * 
     * if (saveFinalStanding) { FinalStanding finalStandingWinner = new FinalStanding();
     * finalStandingWinner.setFinalRank(finalRank); finalStandingWinner.setTournament(tournament); savedFinalStanding =
     * tournamentAggregationDao.getFinalStanding(finalStandingWinner);
     * 
     * if (savedFinalStanding == null) { finalStandingWinner.setPlayer(winner); logger.info("creating final standings1:"
     * + finalStandingWinner); tournamentAggregationDao.createFinalStanding(finalStandingWinner); } else {
     * savedFinalStanding.setPlayer(winner); logger.info("updating final standings1:" + savedFinalStanding);
     * tournamentAggregationDao.updateFinalStanding(savedFinalStanding); }
     * 
     * FinalStanding finalStandingLoser = new FinalStanding(); finalStandingLoser.setFinalRank(++finalRank);
     * finalStandingLoser.setTournament(tournament); savedFinalStanding =
     * tournamentAggregationDao.getFinalStanding(finalStandingLoser);
     * 
     * if (savedFinalStanding == null) { finalStandingLoser.setPlayer(loser); logger.info("creating final standings2:" +
     * finalStandingLoser); tournamentAggregationDao.createFinalStanding(finalStandingLoser); } else {
     * savedFinalStanding.setPlayer(loser); logger.info("updating final standings2:" + savedFinalStanding);
     * tournamentAggregationDao.updateFinalStanding(savedFinalStanding); } } }
     * 
     * for (Map.Entry<Integer, List<Player>> winners : losersByRound.entrySet()) { List<Player> loserPlayers =
     * winners.getValue();
     * 
     * for (PlayerResult playerResult : playerResults) { for (Player player : loserPlayers) { if
     * (playerResult.getPlayer().getId().equals(player.getId())) {
     * 
     * FinalStanding finalStandingWinner = new FinalStanding(); finalStandingWinner.setFinalRank(++finalRank);
     * finalStandingWinner.setTournament(tournament); savedFinalStanding =
     * tournamentAggregationDao.getFinalStanding(finalStandingWinner);
     * 
     * if (savedFinalStanding == null) { finalStandingWinner.setPlayer(player); logger.info("creating final standings3:"
     * + finalStandingWinner); tournamentAggregationDao.createFinalStanding(finalStandingWinner); } else {
     * savedFinalStanding.setPlayer(player); logger.info("updating final standings3:" + savedFinalStanding);
     * tournamentAggregationDao.updateFinalStanding(savedFinalStanding); } break; } } } for (Player player :
     * loserPlayers) { if (player.getId() == 0) { FinalStanding finalStandingWinner = new FinalStanding();
     * finalStandingWinner.setFinalRank(++finalRank); finalStandingWinner.setTournament(tournament);
     * 
     * savedFinalStanding = tournamentAggregationDao.getFinalStanding(finalStandingWinner);
     * 
     * if (savedFinalStanding == null) { finalStandingWinner.setPlayer(null); logger.info("creating final standings4:" +
     * finalStandingWinner); tournamentAggregationDao.createFinalStanding(finalStandingWinner); } else { // TODO
     * otestovat savedFinalStanding.setPlayer(null); logger.info("updating final standings4:" + savedFinalStanding);
     * tournamentAggregationDao.updateFinalStanding(savedFinalStanding); } } } }
     * 
     * if (playerResults.size() > playerCountInPlayOff) { for (PlayerResult playerResult :
     * playerResults.subList(Math.max(playOffGames.size(), 2), playerResults.size())) { FinalStanding
     * finalStandingWinner = new FinalStanding(); finalStandingWinner.setFinalRank(++finalRank);
     * finalStandingWinner.setTournament(tournament); savedFinalStanding =
     * tournamentAggregationDao.getFinalStanding(finalStandingWinner);
     * 
     * if (savedFinalStanding == null) { finalStandingWinner.setPlayer(playerResult.getPlayer());
     * logger.info("creating final standings5:" + finalStandingWinner);
     * tournamentAggregationDao.createFinalStanding(finalStandingWinner); } else {
     * savedFinalStanding.setPlayer(playerResult.getPlayer()); logger.info("updating final standings5:" +
     * savedFinalStanding); tournamentAggregationDao.updateFinalStanding(savedFinalStanding); } } } } }
     */

    public List<FinalStanding> getFinalStandings(Tournament tournament) {
        FinalStanding finalStanding = new FinalStanding()._setTournament(tournament);
        finalStanding.setInit(FinalStanding.Association.player.name());
        return tournamentAggregationDao.findFinalStanding(finalStanding);
    }

    public void resetEqualRank(Groups group) {
        List<PlayerResult> players = tournamentAggregationDao.getListPlayerResult(new PlayerResult()._setGroup(group));
        for (PlayerResult playerResult : players) {
            playerResult.setEqualRank(null);
            tournamentAggregationDao.updatePlayerResult(playerResult);
        }
    }

    public User createUser(User user) {
        user.setPassword(TournamentUtil.encryptUserPassword(user.getPassword()));
        return tournamentAggregationDao.createUser(user);
    }

    public int updateUser(User user) {
        return tournamentAggregationDao.updateUser(user);
    }

    public int deleteUser(User user) {
        user.setPassword(TournamentUtil.encryptUserPassword(user.getPassword()));
        return tournamentAggregationDao.deleteUser(user);
    }

    public User getUser(User user) {
        return tournamentAggregationDao.getUser(user);
    }

    public List<User> getAllUsers() {
        return tournamentAggregationDao.getAllUsers();
    }

    public void updateNotPromotingFinalStandings(List<PlayerResult> playerResults, Groups group, Tournament tournament) {
        logger.info("Start updating  not promoting final standing");
        GroupsName groupsName = new GroupsName();
        String previousGroupName = groupsName.getPrevious(group.getName());

        int previousPlayerResultCount = 0;
        if (previousGroupName.length() > 0) {
            Groups previousGroup = tournamentAggregationDao.getGroup(new Groups()._setName(previousGroupName)
                    ._setTournament(tournament));
            List<PlayerResult> previousPlayerResult = tournamentAggregationDao.getListPlayerResult(new PlayerResult()
                    ._setGroup(previousGroup));
            previousPlayerResultCount = previousPlayerResult.size();
        }
        int playOffCountPlayer = 0;
        if (group.getName().equals("A")) {
            playOffCountPlayer = tournament.getPlayOffA();
        } else {
            playOffCountPlayer = tournament.getPlayOffLower();
        }

        for (int i = playerResults.size(); i > playOffCountPlayer; i--) {
            FinalStanding finalStanding = tournamentAggregationDao.getFinalStanding(new FinalStanding()._setFinalRank(
                    i + previousPlayerResultCount)._setTournament(tournament));
            if (finalStanding != null
                    && (finalStanding.getPlayer() == null || !finalStanding.getPlayer().equals(
                            playerResults.get(i - 1).getPlayer()))) {
                finalStanding.setPlayer(playerResults.get(i - 1).getPlayer());
                logger.info("Updating not promoting final standing: " + finalStanding);
                tournamentAggregationDao.updateFinalStanding(finalStanding);
            }
        }

    }

    @Required
    public void setTournamentAggregationDao(TournamentAggregationDao tournamentAggregationDao) {
        this.tournamentAggregationDao = tournamentAggregationDao;
    }

}