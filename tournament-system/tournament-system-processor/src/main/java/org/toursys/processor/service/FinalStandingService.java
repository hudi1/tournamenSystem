package org.toursys.processor.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.util.GroupsName;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

public class FinalStandingService extends AbstractService {

    private GroupService groupService;
    private ParticipantService participantService;

    // Basic operations

    @Transactional
    public FinalStanding createFinalStanding(FinalStanding finalStanding) {
        logger.debug("Create final standing: " + finalStanding);
        return tournamentAggregationDao.createFinalStanding(finalStanding);
    }

    @Transactional(readOnly = true)
    public FinalStanding getFinalStanding(FinalStanding finalStanding) {
        logger.debug("Get final standing: " + finalStanding);
        return tournamentAggregationDao.getFinalStanding(finalStanding);
    }

    @Transactional
    public int updateFinalStanding(FinalStanding finalStanding) {
        logger.info("Update final standing: " + finalStanding);
        return tournamentAggregationDao.updateFinalStanding(finalStanding);
    }

    @Transactional
    public int deleteFinalStanding(FinalStanding finalStanding) {
        logger.info("Delete final standing: " + finalStanding);
        return tournamentAggregationDao.deleteFinalStanding(finalStanding);
    }

    @Transactional(readOnly = true)
    public List<FinalStanding> getFinalStandings(Tournament tournament) {
        FinalStanding finalStanding = new FinalStanding()._setTournament(tournament);
        finalStanding.setInit(FinalStanding.Association.player.name());
        logger.debug("Get list final standings: " + finalStanding);
        return tournamentAggregationDao.getListFinalStandings(finalStanding);
    }

    // Advanced operations

    @Transactional
    public void processFinalStandings(Tournament tournament, int playerCount) {
        logger.debug("Process final standings: " + tournament);
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

    @Transactional
    public void updatePromotingFinalStandings(List<Participant> participants, int round, int playerPlayOffCount,
            Tournament tournament, int playerGroupCountSuffix) {
        logger.debug("Update promiting final standings");
        long time = System.currentTimeMillis();
        Collections.sort(participants, new RankComparator());
        Collections.reverse(participants);
        int maxRound = TournamentUtil.binlog(playerPlayOffCount);
        int actualRank = (int) Math.pow(2, maxRound - (round - 2)) + playerGroupCountSuffix;
        for (Participant participant : participants) {
            FinalStanding finalStanding = getFinalStanding(new FinalStanding()._setFinalRank(actualRank)
                    ._setTournament(tournament));
            if ((finalStanding != null && participant != null)
                    && (finalStanding.getPlayer() == null || !finalStanding.getPlayer().equals(participant.getPlayer()))) {
                finalStanding.setPlayer(participant.getPlayer());
                updateFinalStanding(finalStanding);
            }
            actualRank--;
        }
        participants.clear();
        time = System.currentTimeMillis() - time;
        logger.debug("End:  Update promiting final standings: " + time + " ms");
    }

    @Transactional
    public void updateNotPromotingFinalStandings(List<Participant> participants, Groups group, Tournament tournament) {
        logger.info("Update not promoting final standings");
        long time = System.currentTimeMillis();
        GroupsName groupsName = new GroupsName();
        String previousGroupName = groupsName.getPrevious(group.getName());

        int previousParticipantCount = 0;
        if (previousGroupName.length() > 0) {
            Groups previousGroup = groupService.getGroup(new Groups()._setName(previousGroupName)._setTournament(
                    tournament));
            List<Participant> previousParticipant = participantService.getParticipants(new Participant()
                    ._setGroup(previousGroup));
            previousParticipantCount = previousParticipant.size();
        }
        int playOffCountPlayer = 0;
        if (group.getName().equals("A")) {
            playOffCountPlayer = tournament.getPlayOffA();
        } else {
            playOffCountPlayer = tournament.getPlayOffLower();
        }

        for (int i = participants.size(); i > playOffCountPlayer; i--) {
            FinalStanding finalStanding = getFinalStanding(new FinalStanding()._setFinalRank(
                    i + previousParticipantCount)._setTournament(tournament));
            if (finalStanding != null
                    && (finalStanding.getPlayer() == null || !finalStanding.getPlayer().equals(
                            participants.get(i - 1).getPlayer()))) {
                finalStanding.setPlayer(participants.get(i - 1).getPlayer());
                updateFinalStanding(finalStanding);
            }
        }
        time = System.currentTimeMillis() - time;
        logger.debug("End:  Update not promoting final standings: " + time + " ms");
    }

    @Required
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Required
    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }
}