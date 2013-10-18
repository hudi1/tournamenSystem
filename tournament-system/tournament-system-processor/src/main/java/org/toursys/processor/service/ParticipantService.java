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
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Score;
import org.toursys.repository.model.Tournament;

public class ParticipantService extends AbstractService {

    private GroupService groupService;

    // Basic operations

    public Participant createParticipant(Player player, Groups group) {
        return tournamentAggregationDao.createParticipant(player, group);
    }

    public Participant getParticipant(Participant participant) {
        participant.setInit(Participant.Association.games.name(), Participant.Association.player.name());
        return tournamentAggregationDao.getParticipant(participant);
    }

    public int updateParticipant(Participant participant) {
        return tournamentAggregationDao.updateParticipant(participant);
    }

    public int deleteParticipant(Participant participant) {
        int count = 0;
        List<Participant> participants = tournamentAggregationDao.getListParticipants(new Participant()._setGroup(
                participant.getGroup())._setPlayer(participant.getPlayer()));
        for (Participant deletedParticipant : participants) {
            count += deleteParticipant(deletedParticipant);
        }
        return count;
    }

    @Transactional
    public List<Participant> getParticipants(Participant participant) {
        participant.setInit(Participant.Association.games.name(), Participant.Association.player.name());
        return tournamentAggregationDao.getListParticipants(participant);
    }

    // Advanced operations

    public List<Participant> getRegistratedParticipant(Tournament tournament) {
        return tournamentAggregationDao.getRegistratedParticipant(tournament);
    }

    public Participant createBasicParticipant(Tournament tournament, Player player, Groups group) {
        logger.info("creating basic participant, player: " + player + " group: " + group);
        if (group.getName() != null) {
            Groups savedGroup = tournamentAggregationDao.getGroup(group._setTournament(tournament)._setType(
                    GroupsType.BASIC));
            if (savedGroup == null) {
                savedGroup = tournamentAggregationDao.createGroup(group._setCopyResult(false)._setPlayThirdPlace(false)
                        ._setNumberOfHockey(1)._setIndexOfFirstHockey(1));
            }
            return tournamentAggregationDao.createParticipant(player, savedGroup);
        }
        return null;
    }

    public void calculateParticipants(List<Participant> participants, Tournament tournament) {
        for (Participant participant : participants) {
            calculateParticipant(participant, tournament);
        }
        if (participants.size() > 0) {
            sortParticipant(participants, tournament);
        }
    }

    private void calculateParticipant(Participant participant, Tournament tournament) {
        long time = System.currentTimeMillis();
        logger.info("calculating participant: " + participant);
        int points = 0;
        Integer homeScore = 0;
        Integer awayScore = 0;
        for (Game game : participant.getGames()) {
            // TODO zistit preco ked participant nema ziadnu hru tak tam
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
        participant.setPoints(points);
        participant.setScore(new Score(homeScore, awayScore));
        time = System.currentTimeMillis() - time;
        logger.debug("Celkova doba: " + time + " ms");
    }

    private Participant cloneParticipant(Participant participant) {
        Participant clone = new Participant();
        clone._setGroup(participant.getGroup())._setId(participant.getId())._setPlayer(participant.getPlayer())
                ._setPoints(participant.getPoints())._setRank(participant.getRank())._setScore(participant.getScore())
                ._setEqualRank(participant.getEqualRank());
        clone.getGames().addAll(participant.getGames());
        return clone;
    }

    private void sortParticipant(List<Participant> participants, Tournament tournament) {
        logger.info("sort participants: " + Arrays.toString(participants.toArray()));

        Collections.sort(participants, new BasicComparator());

        for (int i = 0; i < participants.size(); i++) {
            participants.get(i).setRank(i + 1);
        }

        List<Participant> temporatyParticipant = new ArrayList<Participant>();
        temporatyParticipant.add(cloneParticipant(participants.get(0)));
        int actualRank = 0;
        for (int i = 0; i < participants.size() - 1; i++) {
            if (participants.get(i).getPoints() == participants.get(i + 1).getPoints()) {
                temporatyParticipant.add(cloneParticipant(participants.get(i + 1)));

            }
            if (participants.get(i).getPoints() != participants.get(i + 1).getPoints()
                    || (i == participants.size() - 2)) {
                if (temporatyParticipant.size() > 2) {
                    for (Participant participant : temporatyParticipant) {
                        boolean delGame = true;
                        List<Game> g1 = new ArrayList<Game>(participant.getGames());
                        for (Game game : g1) {
                            for (Participant participant2 : temporatyParticipant) {
                                if (participant2.equals(game.getAwayParticipant())) {
                                    delGame = false;
                                    break;
                                }
                            }
                            if (delGame) {
                                participant.getGames().remove(game);
                            }
                            delGame = true;
                        }
                    }
                    for (Participant participant : temporatyParticipant) {
                        calculateParticipant(participant, tournament);
                    }

                    Collections.sort(temporatyParticipant, new AdvantageComparator());

                    for (Participant participant1 : participants) {
                        for (int j = 0; j < temporatyParticipant.size(); j++) {
                            if (participant1.equals(temporatyParticipant.get(j))) {
                                participant1.setRank(j + 1 + actualRank);
                            }
                        }
                    }

                }
                temporatyParticipant.clear();
                temporatyParticipant.add(cloneParticipant(participants.get(i + 1)));
                actualRank = i + 1;
            }
        }

        for (int i = 0; i < participants.size(); i++) {
            tournamentAggregationDao.updateParticipant(participants.get(i));
        }
        Collections.sort(participants, new RankComparator());
    }

    // vrati hracov pre rozpis kde sa pocitaju vysledky(postupujuci hraci)
    public LinkedList<List<Participant>> getAdvancedPlayersByGroup(Groups group, Tournament tournament,
            List<Participant> finalParticipants) {
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
        return playerByGroup;
    }

    @Required
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

}