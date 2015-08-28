package org.toursys.processor.service.playOffGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.BadOptionsTournamentException;
import org.toursys.processor.service.group.GroupService;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.processor.util.PositionCounter;
import org.toursys.repository.dao.PlayOffGameDao;
import org.toursys.repository.model.GameStatus;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsPlayOffType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Result;
import org.toursys.repository.model.Tournament;

public class PlayOffGameService {

    @Inject
    private ParticipantService participantService;

    @Inject
    private GroupService groupService;

    @Inject
    private PlayOffGameDao playOffGameDao;

    @Transactional
    public PlayOffGame createPlayOffGame(Participant homeParticipant, Participant awayParticipant, Groups group,
            int position) {
        PlayOffGame playOffGame = new PlayOffGame(group, position);
        playOffGame.setHomeParticipant(homeParticipant);
        playOffGame.setAwayParticipant(awayParticipant);

        return playOffGameDao.insert(playOffGame);
    }

    @Transactional
    public PlayOffGame createPlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.insert(playOffGame);
    }

    @Transactional(readOnly = true)
    public PlayOffGame getPlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.get(playOffGame);
    }

    @Transactional
    public int updatePlayOffGame(PlayOffGame playOffGame) {
        playOffGame.setNull(PlayOffGame.Attribute.homeParticipant, PlayOffGame.Attribute.awayParticipant,
                PlayOffGame.Attribute.result, PlayOffGame.Attribute.status);
        return playOffGameDao.update(playOffGame);
    }

    @Transactional
    public int deletePlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.delete(playOffGame);
    }

    @Transactional(readOnly = true)
    public List<PlayOffGame> getPlayOffGames(PlayOffGame playOffGame) {
        playOffGame.setInit(PlayOffGame.Association.awayParticipant.name(),
                PlayOffGame.Association.homeParticipant.name());
        return playOffGameDao.list(playOffGame);
    }

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

        return updatePlayOffGame(playOffGame);
    }

    @Transactional
    public void processPlayOffGames(Tournament tournament) {

        List<Groups> finalGroups = groupService.getFinalGroups(tournament);
        int groupIndex = 0;

        for (Groups group : finalGroups) {
            List<Participant> players = new ArrayList<Participant>();
            if (GroupsPlayOffType.CROSS.equals(group.getPlayOffType())) {
                groupIndex++;
                List<Participant> tempPart = participantService.getParticipandByGroup(group);
                if (groupIndex % 2 == 0) {
                    Collections.reverse(tempPart);
                }
                players.addAll(tempPart);
                if (finalGroups.size() != groupIndex) {
                    continue;
                }
            } else {
                players = participantService.getParticipandByGroup(group);
            }
            int playOffPlayerCount = getPlayOffPlayerCount(group, tournament);

            while (players.size() < playOffPlayerCount) {
                players.add(new Participant());
            }

            LinkedList<Participant> playOffPlayer = new LinkedList<Participant>(players.subList(0, playOffPlayerCount));

            List<PlayOffGame> finalgames = getPlayOffGames(new PlayOffGame()._setGroup(group));
            deletePlayOffGames(finalgames);
            createPlayOffGames(playOffPlayer, group, playOffPlayerCount);
        }
    }

    @Transactional
    public void updatePlayOffGames(Tournament tournament, Groups group) {

        List<Participant> players = participantService.getParticipandByGroup(group);

        int playOffPlayerCount = getPlayOffPlayerCount(group, tournament);

        while (players.size() < playOffPlayerCount) {
            players.add(new Participant());
        }

        LinkedList<Participant> playOffPlayer = new LinkedList<Participant>(players.subList(0, playOffPlayerCount));

        List<PlayOffGame> finalgames = getPlayOffGames(new PlayOffGame()._setGroup(group));
        updatePlayOffGames(playOffPlayer, finalgames, group, playOffPlayerCount);

    }

    @Transactional
    public void updateNextRoundPlayOffGames(Tournament tournament) {

        List<Groups> finalGroups = groupService.getFinalGroups(tournament);
        for (Groups group : finalGroups) {

            if (GroupsPlayOffType.CROSS.equals(group.getPlayOffType())) {
                continue;
            }

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
        case CROSS:
            if (tournament.getPlayOffFinal() != tournament.getPlayOffLower()) {
                throw new BadOptionsTournamentException();
            }
            playOffPlayerCount += tournament.getPlayOffFinal();
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
                playOffGame = createPlayOffGame(playOffPlayer.removeFirst(), playOffPlayer.removeLast(), group,
                        pc.getPosition(i));
                position = playerCount / 2;
            } else {
                playOffGame = createPlayOffGame(null, null, group, ++position);
            }
            playOffGames.add(playOffGame);
        }
    }

    private void updatePlayOffGames(LinkedList<Participant> playOffPlayer, List<PlayOffGame> finalgames, Groups group,
            int playOffPlayerCount) {
        int playerCount = playOffPlayer.size();
        PositionCounter pc = new PositionCounter(playerCount);
        int position = 0;

        for (int i = 0; i < playOffPlayerCount; i++) {
            if (!playOffPlayer.isEmpty()) {
                PlayOffGame playOffGame = getPlayOffGameByPosition(finalgames, pc.getPosition(i));
                playOffGame.setHomeParticipant(playOffPlayer.removeFirst());
                playOffGame.setAwayParticipant(playOffPlayer.removeLast());
                position = playerCount / 2;
                updatePlayOffGame(playOffGame);
            } else {
                PlayOffGame playOffGame = getPlayOffGameByPosition(finalgames, ++position);
                playOffGame.setHomeParticipant(null);
                playOffGame.setAwayParticipant(null);
                playOffGame.setResult(null);
                playOffGame.setStatus(null);
                updatePlayOffGame(playOffGame);
            }
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

}