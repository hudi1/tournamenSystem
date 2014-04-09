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
import org.toursys.repository.model.GroupsPlayOffType;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

public class GroupService extends AbstractService {

    private ParticipantService participantService;
    private GameService gameService;

    // Basic operations

    @Transactional
    public Groups createGroup(Groups group) {
        logger.debug("Create group: " + group);
        return tournamentAggregationDao.createGroup(group);
    }

    @Transactional(readOnly = true)
    public Groups getGroup(Groups group) {
        logger.debug("Get group: " + group);
        return tournamentAggregationDao.getGroup(group);
    }

    @Transactional
    public int updateGroup(Groups group) {
        logger.debug("Update group: " + group);
        return tournamentAggregationDao.updateGroup(group);
    }

    @Transactional
    public int deleteGroup(Groups group) {
        logger.debug("Delete group: " + group);
        return tournamentAggregationDao.deleteGroup(group);
    }

    @Transactional(readOnly = true)
    public List<Groups> getGroups(Groups group) {
        logger.debug("Get list groups: " + group);
        return tournamentAggregationDao.getListGroups(group);
    }

    // Advanced operations

    @Transactional(readOnly = true)
    public List<Groups> getBasicGroups(Groups group) {
        logger.debug("Get basic groups: " + group);
        group.setType(GroupsType.BASIC);
        return getGroups(group);
    }

    @Transactional(readOnly = true)
    public List<Groups> getFinalGroups(Groups group) {
        logger.debug("Get final groups: " + group);
        group.setType(GroupsType.FINAL);
        return getGroups(group);
    }

    @Transactional
    public void createFinalGroup(Tournament tournament) {
        long time = System.currentTimeMillis();
        logger.debug("Creating final groups in tournament: " + tournament);
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
            List<Participant> participants = participantService.getParticipants(new Participant()._setGroup(group));
            Collections.sort(participants, new RankComparator());
            int promotingFinal = Math.min(participants.size(), tournament.getFinalPromoting());
            groupName = groupsName.getFirst();
            if (createGroups) {
                int hockeyCount;
                if (promotingFinal % 2 == 0) {
                    hockeyCount = (promotingFinal * basicGroups.size()) / 2;
                } else {
                    hockeyCount = promotingFinal;
                }
                finalGroup = new Groups()._setName(groupName)._setPlayOffType(GroupsPlayOffType.FINAL)
                        ._setNumberOfHockey(hockeyCount)._setTournament(tournament)._setType(GroupsType.FINAL)
                        ._setCopyResult(true);
                logger.trace("Create final group " + finalGroup);
                finalGroup = createGroup(finalGroup);
                groupByName.put(groupName, finalGroup);
            } else {
                finalGroup = groupByName.get(groupName);
                logger.trace("Get final group " + finalGroup);
            }

            for (int i = 0; i < promotingFinal; i++) {
                Participant participant = participantService.createParticipant(participants.get(i).getPlayer(),
                        finalGroup);
                finalGroup.getParticipants().add(participant);
            }

            int countLowerGroup = 1;
            while (true) {

                String nextGroups = groupsName.getNext(groupName);
                int promotingLower = Math.min(participants.size(), tournament.getFinalPromoting() + countLowerGroup
                        * tournament.getLowerPromoting());
                int startIndex = promotingFinal + ((countLowerGroup - 1) * tournament.getLowerPromoting());

                if (startIndex < promotingLower) {
                    if (createGroups) {
                        finalGroup = new Groups()._setName(nextGroups)._setTournament(tournament)
                                ._setType(GroupsType.FINAL);
                        logger.trace("Create final group " + finalGroup);
                        finalGroup = createGroup(finalGroup);
                        groupByName.put(nextGroups, finalGroup);
                    } else {
                        finalGroup = groupByName.get(nextGroups);
                        logger.trace("Get final group " + finalGroup);
                    }
                    for (int i = startIndex; i < promotingLower; i++) {
                        logger.trace("Create participant " + participants.get(i).getPlayer());
                        Participant participant = participantService.createParticipant(participants.get(i).getPlayer(),
                                finalGroup);
                        finalGroup.getParticipants().add(participant);
                    }
                }

                groupName = nextGroups;

                if (participants.size() == promotingLower) {
                    break;
                }
                countLowerGroup++;
            }
            createGroups = false;
        }

        if (finalGroup.getParticipants().size() < tournament.getMinPlayersInGroup() && groupName != null
                && !groupsName.getFirst().equals(groupName)) {

            for (Participant participant : finalGroup.getParticipants()) {
                String previousGroupName = groupsName.getPrevious(groupName);
                participant.setGroup(groupByName.get(previousGroupName));
                participantService.updateParticipant(participant);
            }

            logger.trace("deleting final group " + finalGroup);
            deleteGroup(finalGroup);
            groupByName.remove(finalGroup);
        }

        time = System.currentTimeMillis() - time;
        logger.debug("End: Creating final groups in tournament: " + time + " ms");
    }

    @Transactional
    public void copyResult(Tournament tournament) {
        long time = System.currentTimeMillis();
        logger.debug("Copy result: " + tournament);
        List<Groups> basicGroupss = getBasicGroups(new Groups()._setTournament(tournament));
        List<Groups> finalGroupss = getFinalGroups(new Groups()._setTournament(tournament));

        for (Groups finalGroups : finalGroupss) {
            if (finalGroups.getCopyResult()) {
                List<Participant> finalParticipants = participantService.getParticipants(new Participant()
                        ._setGroup(finalGroups));
                for (Groups basicGroups : basicGroupss) {
                    List<Participant> participants = participantService.getParticipants(new Participant()
                            ._setGroup(basicGroups));
                    for (Participant finalParticipant : finalParticipants) {
                        for (Participant participant : participants) {
                            if (participant.getPlayer().getId().equals(finalParticipant.getPlayer().getId())) {
                                List<Game> finalGames = gameService.getGames(new Game()
                                        ._setHomeParticipant(finalParticipant));
                                List<Game> basicGames = gameService.getGames(new Game()
                                        ._setHomeParticipant(participant));
                                for (Game finalGame : finalGames) {
                                    for (Game basicGame : basicGames) {
                                        if (finalGame.getAwayParticipant().getPlayer().getId()
                                                .equals(basicGame.getAwayParticipant().getPlayer().getId())) {
                                            finalGame.setResult(basicGame.getResult());
                                            gameService.updateGame(finalGame);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    List<Participant> savedParticipants = participantService.getParticipants(new Participant()
                            ._setGroup(finalGroups));
                    participantService.sortParticipants(savedParticipants, tournament);
                }
            }
        }
        time = System.currentTimeMillis() - time;
        logger.debug("End: Copy result: " + time + " ms");
    }

    @Transactional
    public void resetEqualRank(Groups group) {
        logger.debug("Reset equal rank in group: " + group);
        List<Participant> players = participantService.getParticipants(new Participant()._setGroup(group));
        for (Participant participant : players) {
            participant.setEqualRank(null);
            participantService.updateParticipant(participant);
        }
    }

    @Required
    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Required
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}