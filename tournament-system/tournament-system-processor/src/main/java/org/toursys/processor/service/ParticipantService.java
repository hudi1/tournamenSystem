package org.toursys.processor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.sorter.SkTournamentSorter;
import org.toursys.processor.sorter.TournamentSorter;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;

public class ParticipantService extends AbstractService {

    private GroupService groupService;

    // Basic operations

    @Transactional
    public Participant createParticipant(Player player, Groups group) {
        logger.debug("Create participant: " + player + " " + group);
        return tournamentAggregationDao.createParticipant(player, group);
    }

    @Transactional(readOnly = true)
    public Participant getParticipant(Participant participant) {
        logger.debug("Get participant: " + participant);
        participant.setInit(Participant.Association.games.name(), Participant.Association.player.name());
        return tournamentAggregationDao.getParticipant(participant);
    }

    @Transactional
    public int updateParticipant(Participant participant) {
        logger.debug("Update participant: " + participant);
        return tournamentAggregationDao.updateParticipant(participant);
    }

    @Transactional
    public int deleteParticipant(Participant participant) {
        logger.debug("Delete participant: " + participant);
        return tournamentAggregationDao.deleteParticipant(participant);
    }

    @Transactional(readOnly = true)
    public List<Participant> getParticipants(Participant participant) {
        logger.debug("Get list participants: " + participant);
        participant.setInit(Participant.Association.games.name(), Participant.Association.player.name());
        return tournamentAggregationDao.getListParticipants(participant);
    }

    // Advanced operations

    @Transactional
    public int deletePlayerParticipant(Participant participant, Tournament tournament) {
        logger.debug("Delete player participant: " + participant + " " + tournament);
        int count = 0;
        List<Participant> participants = tournamentAggregationDao.getListParticipants(new Participant()._setPlayer(
                participant.getPlayer())._setInit(Participant.Association.group));

        for (Participant deletedParticipant : participants) {
            if (deletedParticipant.getGroup().getTournament().equals(tournament)) {
                count += deleteParticipant(deletedParticipant);
                Groups group = deletedParticipant.getGroup();
                participants = tournamentAggregationDao.getListParticipants(new Participant()._setGroup(group));
                if (participants.isEmpty()) {
                    groupService.deleteGroup(group);
                }
            }
        }

        return count;
    }

    @Transactional(readOnly = true)
    public List<Participant> getRegistratedParticipant(Tournament tournament) {
        logger.debug("Get registrated participant in tournament: " + tournament);
        return tournamentAggregationDao.getRegisteredParticipant(tournament);
    }

    @Transactional
    public Participant createBasicParticipant(Tournament tournament, Player player, Groups group) {
        logger.debug("Creating basic participant, player: " + player + " group: " + group + " tournament:" + tournament);
        if (group.getName() != null) {
            group.setTournament(tournament);
            List<Groups> savedGroups = groupService.getGroups(group);
            Groups savedGroup;
            if (savedGroups.isEmpty()) {
                savedGroup = groupService.createGroup(group._setCopyResult(false)._setPlayThirdPlace(false)
                        ._setNumberOfHockey(1)._setIndexOfFirstHockey(1));
            } else {
                savedGroup = savedGroups.get(0);
            }
            return tournamentAggregationDao.createParticipant(player, savedGroup);
        }
        return null;
    }

    @Transactional
    public void sortParticipants(List<Participant> participants, Tournament tournament) {
        long time = System.currentTimeMillis();
        logger.debug("Sort participants: " + tournament);

        // TODO CZ
        TournamentSorter sorter = new SkTournamentSorter(tournament);
        sorter.sort(participants);

        for (Participant participant : participants) {
            updateParticipant(participant._setNull(Participant.Attribute.equalRank));
        }

        time = System.currentTimeMillis() - time;
        logger.debug("End: Calculate participants: " + time + " ms");
    }

    @Transactional(readOnly = true)
    public LinkedList<List<Participant>> getAdvancedPlayersByGroup(Groups group, Tournament tournament,
            List<Participant> finalParticipants) {
        logger.debug("Get Advanced Players By Group" + group);

        LinkedList<List<Participant>> playerByGroup = new LinkedList<List<Participant>>();

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
            playerByGroup.add(i, new ArrayList<Participant>());
            List<Participant> players = getParticipants(new Participant()._setGroup(basicGroups.get(i)));
            Collections.sort(players, new RankComparator());
            int promotingCount;

            if (group.getName().equals("A")) {
                promotingCount = tournament.getFinalPromoting();
            } else {
                promotingCount = tournament.getLowerPromoting();
            }
            for (Participant participant : players.subList(Math.min(startIndex, playerByGroup.size()),
                    Math.min(players.size(), promotingCount + startIndex))) {
                for (Participant finalParticipant : finalParticipants) {
                    if (participant.getPlayer().equals(finalParticipant.getPlayer())) {
                        playerByGroup.get(i).add(finalParticipant);
                        break;
                    }
                }
            }
        }
        logger.debug("Return get Advanced Players By Group" + playerByGroup);
        return playerByGroup;
    }

    public Set<Participant> getGoldGoalParticipants(List<Participant> participants) {
        Set<Participant> participantsSet = new HashSet<Participant>();

        for (Participant participant : participants) {
            if (participant.getEqualRank() != null) {
                participantsSet.add(participant);
            }
        }
        return participantsSet;
    }

    public List<Participant> getSortedParticipants(Participant participant) {
        List<Participant> participants = getParticipants(participant);
        Collections.sort(participants, new RankComparator());
        return participants;
    }

    @Required
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}