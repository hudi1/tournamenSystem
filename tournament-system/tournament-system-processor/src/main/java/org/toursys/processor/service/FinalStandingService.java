package org.toursys.processor.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.util.GroupsName;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Tournament;

public class FinalStandingService extends AbstractService {

    private GroupService groupService;
    private PlayerResultService playerResultService;

    // Basic operations

    public FinalStanding createFinalStanding(FinalStanding finalStanding) {
        logger.info("Creating final standing: " + finalStanding);
        return tournamentAggregationDao.createFinalStanding(finalStanding);
    }

    public FinalStanding getFinalStanding(FinalStanding finalStanding) {
        return tournamentAggregationDao.getFinalStanding(finalStanding);
    }

    public int updateFinalStanding(FinalStanding finalStanding) {
        logger.info("Updating final standing: " + finalStanding);
        return tournamentAggregationDao.updateFinalStanding(finalStanding);
    }

    public int deleteFinalStanding(FinalStanding finalStanding) {
        return tournamentAggregationDao.deleteFinalStanding(finalStanding);
    }

    public List<FinalStanding> getFinalStandings(Tournament tournament) {
        FinalStanding finalStanding = new FinalStanding()._setTournament(tournament);
        finalStanding.setInit(FinalStanding.Association.player.name());
        return tournamentAggregationDao.getListFinalStandings(finalStanding);
    }

    // Advanced operations

    public void processFinalStandings(Tournament tournament, int playerCount) {
        List<FinalStanding> finalStandings = getFinalStandings(tournament);
        for (FinalStanding finalStanding : finalStandings) {
            deleteFinalStanding(finalStanding);
        }
        for (int i = 1; i < playerCount + 1; i++) {
            FinalStanding finalStanding = new FinalStanding();
            finalStanding.setFinalRank(i);
            finalStanding.setTournament(tournament);
            createFinalStanding(finalStanding);
        }
    }

    public void updateFinalStandings(List<PlayerResult> playerResults, int round, int playerPlayOffCount,
            Tournament tournament, int playerGroupCountSuffix) {
        Collections.sort(playerResults, new RankComparator());
        // TODO optimalizovat !!!
        Collections.reverse(playerResults);
        int maxRound = TournamentUtil.binlog(playerPlayOffCount);
        int actualRank = (int) Math.pow(2, maxRound - (round - 2)) + playerGroupCountSuffix;
        for (PlayerResult playerResult : playerResults) {
            FinalStanding finalStanding = getFinalStanding(new FinalStanding()._setFinalRank(actualRank)
                    ._setTournament(tournament));
            if ((finalStanding != null && playerResult != null)
                    && (finalStanding.getPlayer() == null || !finalStanding.getPlayer()
                            .equals(playerResult.getPlayer()))) {
                finalStanding.setPlayer(playerResult.getPlayer());
                updateFinalStanding(finalStanding);
            }
            actualRank--;
        }
        playerResults.clear();
    }

    public void updateNotPromotingFinalStandings(List<PlayerResult> playerResults, Groups group, Tournament tournament) {
        logger.info("Start updating  not promoting final standing");
        GroupsName groupsName = new GroupsName();
        String previousGroupName = groupsName.getPrevious(group.getName());

        int previousPlayerResultCount = 0;
        if (previousGroupName.length() > 0) {
            Groups previousGroup = groupService.getGroup(new Groups()._setName(previousGroupName)._setTournament(
                    tournament));
            List<PlayerResult> previousPlayerResult = playerResultService.getPlayerResults(new PlayerResult()
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
            FinalStanding finalStanding = getFinalStanding(new FinalStanding()._setFinalRank(
                    i + previousPlayerResultCount)._setTournament(tournament));
            if (finalStanding != null
                    && (finalStanding.getPlayer() == null || !finalStanding.getPlayer().equals(
                            playerResults.get(i - 1).getPlayer()))) {
                finalStanding.setPlayer(playerResults.get(i - 1).getPlayer());
                updateFinalStanding(finalStanding);
            }
        }
    }

    @Required
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Required
    public void setPlayerResultService(PlayerResultService playerResultService) {
        this.playerResultService = playerResultService;
    }
}