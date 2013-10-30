package org.toursys.processor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.TournamentException;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.util.GroupsName;
import org.toursys.processor.util.PositionCounter;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffGameWinner;
import org.toursys.repository.model.Tournament;

public class PlayOffGameService extends AbstractService {

    private FinalStandingService finalStandingService;
    private ParticipantService participantService;
    private GroupService groupService;

    // Basic operations

    @Transactional
    public PlayOffGame createPlayOffGame(Participant homePlayer, Participant awayPlayer, Groups group, int position) {
        logger.debug("Create playerOff game: " + homePlayer + " " + awayPlayer + " " + group + " " + position);
        return tournamentAggregationDao.createPlayOffGame(homePlayer, awayPlayer, group, position);
    }

    @Transactional(readOnly = true)
    public PlayOffGame getPlayOffGame(PlayOffGame playOffGame) {
        logger.debug("Get playerOff game: " + playOffGame);
        return tournamentAggregationDao.getPlayOffGame(playOffGame);
    }

    @Transactional
    public int updatePlayOffGame(PlayOffGame playOffGame) {
        logger.debug("Update playerOff game: " + playOffGame);
        return tournamentAggregationDao.updatePlayOffGame(playOffGame);
    }

    @Transactional
    public int deletePlayOffGame(PlayOffGame playOffGame) {
        logger.debug("Delete playerOff game: " + playOffGame);
        return tournamentAggregationDao.deletePlayOffGame(playOffGame);
    }

    @Transactional(readOnly = true)
    public List<PlayOffGame> getPlayOffGames(PlayOffGame playOffGame) {
        logger.debug("Get list playerOff games: " + playOffGame);
        playOffGame.setInit(PlayOffGame.Association.awayParticipant.name(),
                PlayOffGame.Association.homeParticipant.name());
        return tournamentAggregationDao.getListPlayOffGames(playOffGame);
    }

    // Advanced operations

    @Transactional
    public int updatePlayOffGameResult(PlayOffGame playOffGame) {
        logger.debug("Update playerOff game result: " + playOffGame);

        if (playOffGame.getResults() != null) {
            String[] splitResults = playOffGame.getResults().split(",");
            int homeWinnerCount = 0;
            int awayWinnerCount = 0;
            for (int i = 0; i < splitResults.length; i++) {
                if (splitResults[i].contains(":")) {
                    int homeScore = Integer.parseInt(splitResults[i].split(":")[0]);
                    int awayScore;
                    try {
                        awayScore = Integer.parseInt(splitResults[i].split(":")[1]);
                    } catch (NumberFormatException e) {
                        awayScore = Integer.parseInt(splitResults[i].split(":")[1].substring(0,
                                splitResults[i].split(":")[1].length() - 1));

                    }
                    if (homeScore > awayScore) {
                        homeWinnerCount++;
                    } else if (homeScore < awayScore) {
                        awayWinnerCount++;
                    }

                }
            }

            if (homeWinnerCount > awayWinnerCount) {
                playOffGame.setWinner(PlayOffGameWinner.HOME);
            } else if (homeWinnerCount < awayWinnerCount) {
                playOffGame.setWinner(PlayOffGameWinner.AWAY);
            } else {
                playOffGame.setWinner(null);
            }
        }
        return tournamentAggregationDao.updatePlayOffGame(playOffGame);
    }

    @Transactional
    public void processPlayOffGames(Tournament tournament) {
        long time = System.currentTimeMillis();
        logger.debug("Process playerOff games: " + tournament);

        List<Groups> finalGroups = groupService.getFinalGroups(new Groups()._setTournament(tournament));

        for (Groups group : finalGroups) {

            List<Participant> players = participantService.getParticipants(new Participant()._setGroup(group));
            Collections.sort(players, new RankComparator());

            int playOffPlayerCount = getPlayOffPlayerCount(group, tournament);

            int playerPlayOffCountAfterCheckThirdPlace = checkThirdPlace(group, playOffPlayerCount);

            // checkPlayOffCount(group, players.size(), playOffPlayerCount);

            while (players.size() < playOffPlayerCount) {
                players.add(new Participant());
            }

            LinkedList<Participant> playOffPlayer = new LinkedList<Participant>(players.subList(0, playOffPlayerCount));

            List<PlayOffGame> finalgames = getPlayOffGames(new PlayOffGame()._setGroup(group));
            Collections.sort(finalgames, new Comparator<PlayOffGame>() {
                @Override
                public int compare(PlayOffGame o1, PlayOffGame o2) {
                    return o1.getPosition().compareTo(o2.getPosition());
                }
            });
            deletePlayOffGames(finalgames);
            createPlayOffGames(playOffPlayer, group, playerPlayOffCountAfterCheckThirdPlace);
        }
        time = System.currentTimeMillis() - time;
        logger.debug("End: Process playerOff games: " + time + " ms");
    }

    public void updateNextRoundPlayOffGames(Tournament tournament) {

        logger.debug("Update next round playOff games: " + tournament);

        List<Groups> finalGroups = groupService.getFinalGroups(new Groups()._setTournament(tournament));

        for (Groups group : finalGroups) {

            int playerPlayOffCount = getPlayOffPlayerCount(group, tournament);
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

            int lastRound = 1;
            List<PlayOffGame> playOffGames = getPlayOffGames(new PlayOffGame()._setGroup(group));

            List<Participant> finalStandings = new ArrayList<Participant>();
            int gameCount = (playOffGames.size() % 2 == 1) ? playOffGames.size() - 1 : playOffGames.size() - 2;
            for (int i = 0; i < gameCount; i++) {
                int round = TournamentUtil.getRound(playerPlayOffCount, i + 1);
                PlayOffGame nextGame1 = playOffGames.get(i);
                updatePlayOffGame(nextGame1, playOffGames, i + 1, playerPlayOffCount, round != lastRound,
                        finalStandings, round, tournament, previousParticipantCount);
                lastRound = round;
            }
            // nehra sa o tretie miesto
            if (playOffGames.size() % 2 == 1) {
                System.out.println("nehrame o tretie miesto");
                finalStandingService.updatePromotingFinalStandings(finalStandings, lastRound + 1, playerPlayOffCount,
                        tournament, previousParticipantCount);
            }

            int position = 1 + previousParticipantCount;
            for (int i = gameCount; i < playOffGames.size(); i++) {
                FinalStanding firstFinalStanding = finalStandingService.getFinalStanding(new FinalStanding()
                        ._setFinalRank(position)._setTournament(tournament));
                FinalStanding secondFinalStanding = finalStandingService.getFinalStanding(new FinalStanding()
                        ._setFinalRank(position + 1)._setTournament(tournament));

                if (playOffGames.get(i).getWinner() != null) {
                    if (playOffGames.get(i).getWinner().equals(PlayOffGameWinner.HOME)) {
                        firstFinalStanding.setPlayer(playOffGames.get(i).getHomeParticipant().getPlayer());
                        secondFinalStanding.setPlayer(playOffGames.get(i).getAwayParticipant().getPlayer());
                    } else if (playOffGames.get(i).getWinner().equals(PlayOffGameWinner.AWAY)) {
                        firstFinalStanding.setPlayer(playOffGames.get(i).getAwayParticipant().getPlayer());
                        secondFinalStanding.setPlayer(playOffGames.get(i).getHomeParticipant().getPlayer());
                    }
                } else {
                    firstFinalStanding.setPlayer(null);
                    secondFinalStanding.setPlayer(null);
                }
                finalStandingService.updateFinalStanding(firstFinalStanding);
                finalStandingService.updateFinalStanding(secondFinalStanding);
                position += 2;
            }
        }

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
            deletePlayOffGame(playOffGame);
        }
    }

    private List<PlayOffGame> createPlayOffGames(LinkedList<Participant> playOffPlayer, Groups group,
            int playerPlayOffCountAfterCheckThirdPlace) {
        List<PlayOffGame> playOffGames = new LinkedList<PlayOffGame>();
        int playerCount = playOffPlayer.size();
        PositionCounter pc = new PositionCounter(playerCount);
        int position = 0;
        for (int i = 0; i < playerPlayOffCountAfterCheckThirdPlace; i++) {

            PlayOffGame playOffGame;
            if (!playOffPlayer.isEmpty()) {
                position = pc.getPosition(i);
                playOffGame = createPlayOffGame(playOffPlayer.removeFirst(), playOffPlayer.removeLast(), group,
                        position);
                position = playerCount / 2;
            } else {
                playOffGame = createPlayOffGame(null, null, group, ++position);
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

    private static int getPosition(int i, int size) {
        if (i == 1) {
            return 1;
        } else if (i == size) {
            return size - 1;
        }
        if (i == 2) {
            return size;
        }
        if (i == 3) {
            return size / 2 + 1;
        }
        if (i == 4) {
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

    private void updatePlayOffGame(PlayOffGame playOffGame, List<PlayOffGame> playOffGames, int position,
            int playerPlayOffCount, boolean newRound, List<Participant> finalStandings, int round,
            Tournament tournament, int playerGroupCountSuffix) {

        if (newRound) {
            finalStandingService.updatePromotingFinalStandings(finalStandings, round, playerPlayOffCount, tournament,
                    playerGroupCountSuffix);
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

        if (playOffGame.getWinner() != null) {
            if (playOffGame.getWinner().equals(PlayOffGameWinner.HOME)) {
                finalStandings.add(playOffGame.getAwayParticipant());
                if (position % 2 == 1) {
                    tempPlayOffGame._setHomeParticipant((playOffGame.getHomeParticipant()));
                    if (isThirdPlace) {
                        tempThirdPlayOffGame._setHomeParticipant((playOffGame.getAwayParticipant()));
                    }
                } else {
                    tempPlayOffGame._setAwayParticipant((playOffGame.getHomeParticipant()));
                    if (isThirdPlace) {
                        tempThirdPlayOffGame._setAwayParticipant((playOffGame.getAwayParticipant()));
                    }
                }
            } else if (playOffGame.getWinner().equals(PlayOffGameWinner.HOME)) {
                finalStandings.add(playOffGame.getHomeParticipant());
                if (position % 2 == 1) {
                    tempPlayOffGame._setHomeParticipant((playOffGame.getAwayParticipant()));
                    if (isThirdPlace) {
                        tempThirdPlayOffGame._setHomeParticipant((playOffGame.getHomeParticipant()));
                    }
                } else {
                    tempPlayOffGame._setAwayParticipant((playOffGame.getAwayParticipant()));
                    if (isThirdPlace) {
                        tempThirdPlayOffGame._setAwayParticipant((playOffGame.getHomeParticipant()));
                    }
                }
            }
        } else {
            finalStandings.add(new Participant());
            if (position % 2 == 1) {
                tempPlayOffGame._setHomeParticipant((null));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setHomeParticipant((null));
                }
            } else {
                tempPlayOffGame._setAwayParticipant((null));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setAwayParticipant((null));
                }
            }
        }

        updatePlayOffGame(tempPlayOffGame);
        if (tempThirdPlayOffGame != null) {
            updatePlayOffGame(tempThirdPlayOffGame);
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

    @Required
    public void setFinalStandingService(FinalStandingService finalStandingService) {
        this.finalStandingService = finalStandingService;
    }

    @Required
    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Required
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}