package org.toursys.processor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.comparators.AdvantageComparator;
import org.toursys.processor.comparators.BasicComparator;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Score;
import org.toursys.repository.model.Tournament;

public class PlayerResultService extends AbstractService {

    private GroupService groupService;

    // Basic operations

    public PlayerResult createPlayerResult(Player player, Groups group) {
        return tournamentAggregationDao.createPlayerResult(player, group);
    }

    public PlayerResult getPlayerResult(PlayerResult playerResult) {
        playerResult.setInit(PlayerResult.Association.games.name(), PlayerResult.Association.player.name());
        return tournamentAggregationDao.getPlayerResult(playerResult);
    }

    public int updatePlayerResult(PlayerResult playerResult) {
        return tournamentAggregationDao.updatePlayerResult(playerResult);
    }

    public int deletePlayerResult(PlayerResult playerResult) {
        int count = 0;
        List<PlayerResult> playerResults = tournamentAggregationDao.getListPlayerResults(new PlayerResult()._setGroup(
                playerResult.getGroup())._setPlayer(playerResult.getPlayer()));
        for (PlayerResult deletedPlayerResult : playerResults) {
            count += deletePlayerResult(deletedPlayerResult);
        }
        return count;
    }

    @Transactional
    public List<PlayerResult> getPlayerResults(PlayerResult playerResult) {
        playerResult.setInit(PlayerResult.Association.games.name(), PlayerResult.Association.player.name());
        return tournamentAggregationDao.getListPlayerResults(playerResult);
    }

    // Advanced operations

    public List<PlayerResult> getRegistratedPlayerResult(Tournament tournament) {
        return tournamentAggregationDao.getRegistratedPlayerResult(tournament);
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

    private PlayerResult clonePlayerResult(PlayerResult playerResult) {
        PlayerResult clone = new PlayerResult();
        clone._setGroup(playerResult.getGroup())._setId(playerResult.getId())._setPlayer(playerResult.getPlayer())
                ._setPoints(playerResult.getPoints())._setRank(playerResult.getRank())
                ._setScore(playerResult.getScore())._setEqualRank(playerResult.getEqualRank());
        clone.getGames().addAll(playerResult.getGames());
        return clone;
    }

    private void sortPlayerResult(List<PlayerResult> playerResults, Tournament tournament) {
        logger.info("sort playerResults: " + Arrays.toString(playerResults.toArray()));

        Collections.sort(playerResults, new BasicComparator());

        for (int i = 0; i < playerResults.size(); i++) {
            playerResults.get(i).setRank(i + 1);
        }

        List<PlayerResult> temporatyPlayerResult = new ArrayList<PlayerResult>();
        temporatyPlayerResult.add(clonePlayerResult(playerResults.get(0)));
        int actualRank = 0;
        for (int i = 0; i < playerResults.size() - 1; i++) {
            if (playerResults.get(i).getPoints() == playerResults.get(i + 1).getPoints()) {
                temporatyPlayerResult.add(clonePlayerResult(playerResults.get(i + 1)));

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
                temporatyPlayerResult.add(clonePlayerResult(playerResults.get(i + 1)));
                actualRank = i + 1;
            }
        }

        for (int i = 0; i < playerResults.size(); i++) {
            tournamentAggregationDao.updatePlayerResult(playerResults.get(i));
        }
        Collections.sort(playerResults, new RankComparator());
    }

    // vrati hracov pre rozpis kde sa pocitaju vysledky(postupujuci hraci)
    public LinkedList<List<PlayerResult>> getAdvancedPlayersByGroup(Groups group, Tournament tournament,
            List<PlayerResult> finalPlayerResults) {
        LinkedList<List<PlayerResult>> playerByGroup = new LinkedList<List<PlayerResult>>();

        List<Groups> basicGroups = groupService.getBasicGroups(new Groups()._setTournament(tournament));
        if (basicGroups.size() < 2 || group.getType().equals(GroupsType.BASIC)) {
            return playerByGroup;
        }

        List<Groups> finalGroups = groupService.getFinalGroups(new Groups()._setTournament(tournament));
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
            List<PlayerResult> players = getPlayerResults(new PlayerResult()._setGroup(basicGroups.get(i)));
            Collections.sort(players, new RankComparator());
            int promotingCount;

            if (group.getName().equals("A")) {
                promotingCount = tournament.getFinalPromoting();
            } else {
                promotingCount = tournament.getLowerPromoting();
            }
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

    @Required
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

}