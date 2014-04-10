package org.toursys.processor.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.util.PositionCounter;
import org.toursys.repository.model.GameStatus;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Result;
import org.toursys.repository.model.Tournament;

public class PlayOffGameService extends AbstractService {

    private ParticipantService participantService;
    private GroupService groupService;

    // Basic operations

    @Transactional
    public PlayOffGame createPlayOffGame(Participant homePlayer, Participant awayPlayer, Groups group, int position) {
        logger.debug("Create playerOff game: " + homePlayer + " " + awayPlayer + " " + group + " " + position);
        return tournamentAggregationDao.createPlayOffGame(homePlayer, awayPlayer, group, position);
    }

    @Transactional
    public PlayOffGame createPlayOffGame(PlayOffGame playOffGame) {
        logger.debug("Create playerOff game: " + playOffGame.toStringFull());
        return tournamentAggregationDao.createPlayOffGame(playOffGame);
    }

    @Transactional(readOnly = true)
    public PlayOffGame getPlayOffGame(PlayOffGame playOffGame) {
        logger.debug("Get playerOff game: " + playOffGame);
        return tournamentAggregationDao.getPlayOffGame(playOffGame);
    }

    @Transactional
    public int updatePlayOffGame(PlayOffGame playOffGame) {
        logger.debug("Update playerOff game: " + playOffGame);
        playOffGame.setNull(PlayOffGame.Attribute.homeParticipant, PlayOffGame.Attribute.awayParticipant,
                PlayOffGame.Attribute.result, PlayOffGame.Attribute.status);
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
        if (playOffGame.getResult() != null) {

            int homeWinnerCount = 0;
            int awayWinnerCount = 0;
            for (Result result : playOffGame.getResult().getResults()) {
                if (result.getLeftSide() > result.getRightSide()) {
                    homeWinnerCount++;
                } else if (result.getLeftSide() < result.getRightSide()) {
                    awayWinnerCount++;
                }
            }

            if (homeWinnerCount > awayWinnerCount) {
                playOffGame.setStatus(GameStatus.WIN);
            } else if (homeWinnerCount < awayWinnerCount) {
                playOffGame.setStatus(GameStatus.LOSE);
            } else {
                playOffGame.setStatus(null);
            }
        } else {
            playOffGame.setStatus(null);
        }

        logger.debug("Update playerOff game result: " + playOffGame);
        return updatePlayOffGame(playOffGame);
    }

    @Transactional
    public void processPlayOffGames(Tournament tournament) {
        long time = System.currentTimeMillis();
        logger.debug("Process playerOff games: " + tournament);

        List<Groups> finalGroups = groupService.getFinalGroups(new Groups()._setTournament(tournament));

        for (Groups group : finalGroups) {

            List<Participant> players = participantService.getSortedParticipants(new Participant()._setGroup(group));

            int playOffPlayerCount = getPlayOffPlayerCount(group, tournament);

            while (players.size() < playOffPlayerCount) {
                players.add(new Participant());
            }

            LinkedList<Participant> playOffPlayer = new LinkedList<Participant>(players.subList(0, playOffPlayerCount));

            List<PlayOffGame> finalgames = getPlayOffGames(new PlayOffGame()._setGroup(group));
            deletePlayOffGames(finalgames);
            createPlayOffGames(playOffPlayer, group, playOffPlayerCount);
        }
        time = System.currentTimeMillis() - time;
        logger.debug("End: Process playerOff games: " + time + " ms");
    }

    @Transactional
    public void updateNextRoundPlayOffGames(Tournament tournament) {
        logger.debug("Update next round playOff games: " + tournament);

        List<Groups> finalGroups = groupService.getFinalGroups(new Groups()._setTournament(tournament));
        for (Groups group : finalGroups) {

            int playerPlayOffCount = getPlayOffPlayerCount(group, tournament);
            List<PlayOffGame> playOffGames = getPlayOffGames(new PlayOffGame()._setGroup(group));

            // u finale a o tretie miesto uz nie je co updatovat
            int gameCount = playOffGames.size() - 2;
            for (int i = 0; i < gameCount; i++) {
                updatePlayOffGame(playOffGames, i + 1, playerPlayOffCount);
            }
        }
    }

    private int getPlayOffPlayerCount(Groups group, Tournament tournament) {
        int playOffPlayerCount = 0;

        switch (group.getPlayOffType()) {
        case FINAL:
            playOffPlayerCount += tournament.getPlayOffFinal();
            break;
        case LOWER:
            playOffPlayerCount += tournament.getPlayOffLower();
            break;
        default:
            break;
        }
        return playOffPlayerCount;
    }

    private void deletePlayOffGames(List<PlayOffGame> finalgames) {
        for (PlayOffGame playOffGame : finalgames) {
            deletePlayOffGame(playOffGame);
        }
    }

    private void createPlayOffGames(LinkedList<Participant> playOffPlayer, Groups group, int playOffPlayerCount) {
        List<PlayOffGame> playOffGames = new LinkedList<PlayOffGame>();
        int playerCount = playOffPlayer.size();
        PositionCounter pc = new PositionCounter(playerCount);
        int position = 0;
        for (int i = 0; i < playOffPlayerCount; i++) {

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
    }

    private void updatePlayOffGame(List<PlayOffGame> playOffGames, int position, int playerPlayOffCount) {

        PlayOffGame playOffGame = playOffGames.get(position - 1);
        int nextPosition = nextGame(playerPlayOffCount, position);
        PlayOffGame tempPlayOffGame = getPlayOffGameByPosition(playOffGames, nextPosition);

        PlayOffGame tempThirdPlayOffGame = null;
        // hra o tretie miesto bude ako posledna hra
        boolean isThirdPlace = (nextPosition == playOffGames.size() - 1);
        if (isThirdPlace) {
            tempThirdPlayOffGame = getPlayOffGameByPosition(playOffGames, nextPosition + 1);
        }

        if (playOffGame.getStatus() != null) {
            if (playOffGame.getStatus().equals(GameStatus.WIN)) {
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
            } else if (playOffGame.getStatus().equals(GameStatus.LOSE)) {
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
            } else {
                tempPlayOffGame.setStatus(null);
            }
        } else {
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
    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Required
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}