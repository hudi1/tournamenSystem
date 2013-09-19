package org.toursys.processor.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.util.GroupsName;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Tournament;

public class GroupService extends AbstractService {

    private PlayerResultService playerResultService;
    private GameService gameService;

    // Basic operations

    public Groups createGroup(Groups group) {
        return tournamentAggregationDao.createGroup(group);
    }

    public Groups getGroup(Groups group) {
        return tournamentAggregationDao.getGroup(group);
    }

    public int updateGroup(Groups group) {
        return tournamentAggregationDao.updateGroup(group);
    }

    public int deleteGroup(Groups group) {
        return tournamentAggregationDao.deleteGroup(group);
    }

    public List<Groups> getGroups(Groups group) {
        return tournamentAggregationDao.getListGroups(group);
    }

    // Advanced operations

    public int updateGroups(Tournament tournament, Groups group) {
        if (group.getCopyResult()) {
            // TODO nasetovat hracov getPlayerResultInGroup(new PlayerResult()._setGroup(group));
            List<PlayerResult> player = group.getPlayerResults();
            // TODO co ked ich bude menej postupovat ako je zadane
            if (tournament.getFinalPromoting() % 2 == 0) {
                group.setNumberOfHockey(player.size() / 2);
            } else {
                List<Groups> groups = getBasicGroups(new Groups()._setTournament(tournament));
                group.setNumberOfHockey(player.size() / groups.size());
            }
        }
        return updateGroup(group);
    }

    public List<Groups> getBasicGroups(Groups group) {
        logger.info("get basic groups: " + group.toStringFull());
        group.setType(GroupsType.BASIC);
        return getGroups(group);
    }

    public List<Groups> getFinalGroups(Groups group) {
        logger.info("get basic groups: " + group.toStringFull());
        group.setType(GroupsType.FINAL);
        return getGroups(group);
    }

    @Transactional
    public void createFinalGroup(Tournament tournament) {
        long time = System.currentTimeMillis();
        logger.info("creating final groups in tournament: " + tournament);
        List<Groups> basicGroups = getBasicGroups(new Groups()._setTournament(tournament));
        List<Groups> finalGroups = getFinalGroups(new Groups()._setTournament(tournament));

        if (!finalGroups.isEmpty()) {
            for (Groups finalGroup : finalGroups) {
                deleteGroup(finalGroup);
            }
        }

        boolean createGroups = true;
        Map<String, Groups> groupByName = new HashMap<String, Groups>();

        Groups finalGroup = new Groups();
        GroupsName groupsName = new GroupsName();
        String groupName = null;

        for (Groups group : basicGroups) {
            List<PlayerResult> playerResults = playerResultService
                    .getPlayerResults(new PlayerResult()._setGroup(group));
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
                finalGroup = createGroup(finalGroup);
                groupByName.put(groupName, finalGroup);
            } else {
                finalGroup = groupByName.get(groupName);
                logger.info("getting final group " + finalGroup);
            }

            for (int i = 0; i < promotingA; i++) {
                logger.info("creating player result " + playerResults.get(i).getPlayer());
                PlayerResult playerResult = playerResultService.createPlayerResult(playerResults.get(i).getPlayer(),
                        finalGroup);
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
                        finalGroup = createGroup(finalGroup);
                        groupByName.put(nextGroups, finalGroup);
                    } else {
                        finalGroup = groupByName.get(nextGroups);
                        logger.info("getting final group " + finalGroup);
                    }
                    for (int i = startIndex; i < promotingLower; i++) {
                        logger.info("creating player result " + playerResults.get(i).getPlayer());
                        PlayerResult playerResult = playerResultService.createPlayerResult(playerResults.get(i)
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
                playerResultService.updatePlayerResult(playerResult);
            }

            logger.info("deleting final group " + finalGroup);
            deleteGroup(finalGroup);
            groupByName.remove(finalGroup);
        }

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
                List<PlayerResult> finalPlayerResults = playerResultService.getPlayerResults(new PlayerResult()
                        ._setGroup(finalGroups));
                for (Groups basicGroups : basicGroupss) {
                    List<PlayerResult> playerResults = playerResultService.getPlayerResults(new PlayerResult()
                            ._setGroup(basicGroups));
                    for (PlayerResult finalPlayerResult : finalPlayerResults) {
                        for (PlayerResult playerResult : playerResults) {
                            if (playerResult.getPlayer().getId().equals(finalPlayerResult.getPlayer().getId())) {
                                List<Game> finalGames = gameService.getGames(new Game()
                                        ._setHomePlayerResult(finalPlayerResult));
                                List<Game> basicGames = gameService.getGames(new Game()
                                        ._setHomePlayerResult(playerResult));
                                for (Game finalGame : finalGames) {
                                    for (Game basicGame : basicGames) {
                                        if (finalGame.getAwayPlayerResult().getPlayer().getId()
                                                .equals(basicGame.getAwayPlayerResult().getPlayer().getId())) {
                                            finalGame.setAwayScore(basicGame.getAwayScore());
                                            finalGame.setHomeScore(basicGame.getHomeScore());
                                            gameService.updateGame(finalGame);
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

    @Transactional
    public void resetEqualRank(Groups group) {
        List<PlayerResult> players = playerResultService.getPlayerResults(new PlayerResult()._setGroup(group));
        for (PlayerResult playerResult : players) {
            playerResult.setEqualRank(null);
            playerResultService.updatePlayerResult(playerResult);
        }
    }

    @Required
    public void setPlayerResultService(PlayerResultService playerResultService) {
        this.playerResultService = playerResultService;
    }

    @Required
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}