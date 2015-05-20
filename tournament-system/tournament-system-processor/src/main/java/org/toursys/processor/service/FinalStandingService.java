package org.toursys.processor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.BadOptionsTournamentException;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.util.GroupsName;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.GameStatus;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsPlayOffType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
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
    public void processFinalStandings(Tournament tournament) {
        logger.debug("Process final standings: " + tournament);
        int playerCount = participantService.getRegistratedParticipant(tournament).size();
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

    private void updateRoundFinalStandings(List<Participant> participants, int round, int playerPlayOffCount,
            Tournament tournament, int playerGroupCountSuffix) {
        logger.debug("Update promiting final standings" + Arrays.toString(participants.toArray()) + " " + round + " "
                + playerPlayOffCount + " " + tournament + " " + playerGroupCountSuffix);
        long time = System.currentTimeMillis();
        Collections.sort(participants, new RankComparator());
        int maxRound = TournamentUtil.binlog(playerPlayOffCount);
        int actualRank = (int) Math.pow(2, maxRound - round) + 1 + playerGroupCountSuffix;
        for (Participant participant : participants) {
            FinalStanding finalStanding = getFinalStanding(new FinalStanding()._setFinalRank(actualRank)
                    ._setTournament(tournament));
            if ((finalStanding != null && participant != null)
                    && (finalStanding.getPlayer() == null || !finalStanding.getPlayer().equals(participant.getPlayer()))) {
                finalStanding.setPlayer(participant.getPlayer());
                updateFinalStanding(finalStanding);
            }
            actualRank++;
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
        switch (group.getPlayOffType()) {
        case FINAL:
            playOffCountPlayer = tournament.getPlayOffFinal();
            break;
        case LOWER:
            playOffCountPlayer = tournament.getPlayOffLower();
            break;
        case CROSS:
            if (tournament.getPlayOffFinal() != tournament.getPlayOffLower()) {
                throw new BadOptionsTournamentException();
            }
            playOffCountPlayer += tournament.getPlayOffFinal();
            break;
        default:
            break;
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

    @Transactional
    public void updatePromotingFinalStandings(Tournament tournament) {
        logger.debug("Update final standings: " + tournament);

        int startGroupSufix = 0;
        // (Ked je play off o 16 hracov ale su tam len 4 tak treba odcitat inac v dalsej skupiny nenajde final standing)
        int startGroupMinusSufix = 0;

        List<Groups> finalGroups = groupService.getFinalGroups(new Groups()._setTournament(tournament));
        for (Groups group : finalGroups) {

            if (GroupsPlayOffType.CROSS.equals(group.getPlayOffType())) {
                List<PlayOffGame> playOffGames = tournamentAggregationDao.getListPlayOffGames(new PlayOffGame()
                        ._setGroup(group)._setInit(PlayOffGame.Association.awayParticipant)
                        ._setInit(PlayOffGame.Association.homeParticipant));
                for (int i = 0; i < playOffGames.size(); i++) {
                    FinalStanding firstFinalStanding = getFinalStanding(new FinalStanding()._setFinalRank(i)
                            ._setTournament(tournament));
                    FinalStanding secondFinalStanding = getFinalStanding(new FinalStanding()._setFinalRank(i + 1)
                            ._setTournament(tournament));
                    if (GameStatus.WIN.equals(playOffGames.get(i).getStatus())) {
                        if (playOffGames.get(i).getHomeParticipant() != null) {
                            firstFinalStanding.setPlayer(playOffGames.get(i).getHomeParticipant().getPlayer());
                        }
                        if (playOffGames.get(i).getAwayParticipant() != null) {
                            secondFinalStanding.setPlayer(playOffGames.get(i + 1).getAwayParticipant().getPlayer());
                        }
                    } else if (GameStatus.LOSE.equals(playOffGames.get(i).getStatus())) {
                        if (playOffGames.get(i).getHomeParticipant() != null) {
                            firstFinalStanding.setPlayer(playOffGames.get(i + 1).getHomeParticipant().getPlayer());
                        }
                        if (playOffGames.get(i).getAwayParticipant() != null) {
                            secondFinalStanding.setPlayer(playOffGames.get(i).getAwayParticipant().getPlayer());
                        }
                    }
                    updateFinalStanding(firstFinalStanding);
                    updateFinalStanding(secondFinalStanding);
                }

            } else {

                List<PlayOffGame> playOffGames = tournamentAggregationDao.getListPlayOffGames(new PlayOffGame()
                        ._setGroup(group)._setInit(PlayOffGame.Association.awayParticipant)
                        ._setInit(PlayOffGame.Association.homeParticipant));
                int startGroupMinusSufixTemp = 0;

                Map<Integer, List<Participant>> losersPerRound = new HashMap<Integer, List<Participant>>();
                for (int i = 0; i < playOffGames.size() - 4; i++) {
                    Integer round = TournamentUtil.getRound(playOffGames.size(), playOffGames.get(i).getPosition());
                    Participant participant = null;
                    if (GameStatus.WIN.equals(playOffGames.get(i).getStatus())) {
                        participant = playOffGames.get(i).getAwayParticipant();
                    } else if (GameStatus.LOSE.equals(playOffGames.get(i).getStatus())) {
                        participant = playOffGames.get(i).getHomeParticipant();
                    }

                    if (participant != null) {
                        if (!losersPerRound.containsKey(round)) {
                            losersPerRound.put(round, new ArrayList<Participant>());
                        }
                        losersPerRound.get(round).add(participant);
                    }

                    if (round == 1) {
                        if (playOffGames.get(i).getHomeParticipant() == null) {
                            startGroupMinusSufixTemp++;
                        }
                        if (playOffGames.get(i).getAwayParticipant() == null) {
                            startGroupMinusSufixTemp++;
                        }
                    }
                }

                int maxRound = TournamentUtil.binlog(playOffGames.size());
                for (int i = 1; i < maxRound; i++) {
                    if (losersPerRound.get(i) != null) {
                        updateRoundFinalStandings(losersPerRound.get(i), i, playOffGames.size(), tournament,
                                startGroupSufix - startGroupMinusSufix);
                    }
                }

                int position = 4 + startGroupSufix - startGroupMinusSufix;
                for (int i = playOffGames.size() - 1; i > playOffGames.size() - 3; i--) {
                    FinalStanding firstFinalStanding = getFinalStanding(new FinalStanding()._setFinalRank(position - 1)
                            ._setTournament(tournament));
                    FinalStanding secondFinalStanding = getFinalStanding(new FinalStanding()._setFinalRank(position)
                            ._setTournament(tournament));

                    if (playOffGames.get(i).getStatus() != null
                            && !GameStatus.DRAW.equals(playOffGames.get(i).getStatus())) {
                        if (playOffGames.get(i).getStatus().equals(GameStatus.WIN)) {
                            if (playOffGames.get(i).getHomeParticipant() != null) {
                                firstFinalStanding.setPlayer(playOffGames.get(i).getHomeParticipant().getPlayer());
                            } else {
                                firstFinalStanding.setPlayer(null);
                            }
                            if (playOffGames.get(i).getAwayParticipant() != null) {
                                secondFinalStanding.setPlayer(playOffGames.get(i).getAwayParticipant().getPlayer());
                            } else {
                                secondFinalStanding.setPlayer(null);
                            }
                        } else if (playOffGames.get(i).getStatus().equals(GameStatus.LOSE)) {
                            if (playOffGames.get(i).getAwayParticipant() != null) {
                                firstFinalStanding.setPlayer(playOffGames.get(i).getAwayParticipant().getPlayer());
                            } else {
                                firstFinalStanding.setPlayer(null);
                            }
                            if (playOffGames.get(i).getHomeParticipant() != null) {
                                secondFinalStanding.setPlayer(playOffGames.get(i).getHomeParticipant().getPlayer());
                            } else {
                                secondFinalStanding.setPlayer(null);
                            }
                        }
                    } else {
                        firstFinalStanding.setPlayer(null);
                        secondFinalStanding.setPlayer(null);
                    }
                    updateFinalStanding(firstFinalStanding);
                    updateFinalStanding(secondFinalStanding);
                    position -= 2;
                }

                switch (group.getPlayOffType()) {
                case FINAL:
                    startGroupSufix += tournament.getPlayOffFinal();
                    break;
                case LOWER:
                    startGroupSufix += tournament.getPlayOffLower();
                    break;
                case CROSS:
                    if (tournament.getPlayOffFinal() != tournament.getPlayOffLower()) {
                        throw new BadOptionsTournamentException();
                    }
                    startGroupSufix += tournament.getPlayOffFinal();
                    break;
                default:
                    break;
                }
                startGroupMinusSufix += startGroupMinusSufixTemp;
            }
        }
    }
}