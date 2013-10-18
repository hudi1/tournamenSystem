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
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

public class GroupService extends AbstractService {

    private ParticipantService participantService;
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
            // TODO nasetovat hracov getParticipantInGroup(new Participant()._setGroup(group));
            List<Participant> player = group.getParticipants();
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
            List<Participant> participants = participantService.getParticipants(new Participant()._setGroup(group));
            Collections.sort(participants, new RankComparator());
            int promotingA = Math.min(participants.size(), tournament.getFinalPromoting());
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
                logger.info("creating player result " + participants.get(i).getPlayer());
                Participant participant = participantService.createParticipant(participants.get(i).getPlayer(),
                        finalGroup);
                finalGroup.getParticipants().add(participant);
            }

            int countLowerGroup = 1;
            while (true) {

                String nextGroups = groupsName.getNext(groupName);
                int promotingLower = Math.min(participants.size(), tournament.getFinalPromoting() + countLowerGroup
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
                        logger.info("creating player result " + participants.get(i).getPlayer());
                        Participant participant = participantService.createParticipant(participants.get(i).getPlayer(),
                                finalGroup);
                        finalGroup.getParticipants().add(participant);
                    }
                }

                if (participants.size() == promotingLower) {
                    break;
                }
                countLowerGroup++;
                groupName = nextGroups;
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