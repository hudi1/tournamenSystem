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
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Tournament;

public class PlayOffGameService extends AbstractService {

    private FinalStandingService finalStandingService;
    private PlayerResultService playerResultService;
    private PlayOffResultService playOffResultService;
    private GroupService groupService;

    // Basic operations

    public PlayOffGame createPlayOffGame(PlayerResult homePlayer, PlayerResult awayPlayer, Groups group, int position) {
        return tournamentAggregationDao.createPlayOffGame(homePlayer, awayPlayer, group, position);
    }

    public PlayOffGame getPlayOffGame(PlayOffGame playOffGame) {
        return tournamentAggregationDao.getPlayOffGame(playOffGame);
    }

    public int updatePlayOffGame(PlayOffGame playOffGame) {
        return tournamentAggregationDao.updatePlayOffGame(playOffGame);
    }

    public int deletePlayOffGame(PlayOffGame playOffGame) {
        return tournamentAggregationDao.deletePlayOffGame(playOffGame);
    }

    @Transactional
    private List<PlayOffGame> getPlayOffGames(PlayOffGame playOffGame) {
        playOffGame.setInit(PlayOffGame.Association.playOffResults.name(),
                PlayOffGame.Association.awayPlayerResult.name(), PlayOffGame.Association.homePlayerResult.name());
        return tournamentAggregationDao.getListPlayOffGames(playOffGame);
    }

    // Advanced operations

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

    private List<PlayOffGame> createPlayOffGames(LinkedList<PlayerResult> playOffPlayer, Groups group,
            int playerPlayOffCountAfterCheckThirdPlace) {
        List<PlayOffGame> playOffGames = new LinkedList<PlayOffGame>();

        for (int i = 0; i < playerPlayOffCountAfterCheckThirdPlace; i++) {

            PlayOffGame playOffGame;
            if (!playOffPlayer.isEmpty()) {
                playOffGame = createPlayOffGame(playOffPlayer.removeFirst(), playOffPlayer.removeLast(), group,
                        getPosition(i, playOffPlayer.size() / 2));
            } else {
                playOffGame = createPlayOffGame(null, null, group, i + 1);
            }
            for (int j = 0; j < 9; j++) {
                PlayOffResult result = playOffResultService.createPlayOffResult(playOffGame);
                playOffGame.getPlayOffResults().add(result);
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

    private int getPosition(int i, int size) {
        if (i == 1) {
            return 1;
        } else if (i == size) {
            return size - 1;
        }
        if (i == 2) {
            return size;
        }
        if (i == 3) {
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

    @Transactional
    // TODO dost dlho to trva
    public List<PlayOffGame> getPlayOffGames(Tournament tournament, Groups group) {
        long time = System.currentTimeMillis();
        List<PlayOffGame> playOffGames;

        if (group == null) {
            return new ArrayList<PlayOffGame>();
        }
        List<PlayerResult> players = playerResultService.getPlayerResults(new PlayerResult()._setGroup(group));
        Collections.sort(players, new RankComparator());

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

        int playOffPlayerCount = getPlayOffPlayerCount(group, tournament);

        int playerPlayOffCountAfterCheckThirdPlace = checkThirdPlace(group, playOffPlayerCount);

        // checkPlayOffCount(group, players.size(), playOffPlayerCount);

        while (players.size() < playOffPlayerCount) {
            players.add(new PlayerResult());
        }

        LinkedList<PlayerResult> playOffPlayer = new LinkedList<PlayerResult>(players.subList(0, playOffPlayerCount));

        List<PlayOffGame> finalgames = getPlayOffGames(new PlayOffGame()._setGroup(group));
        Collections.sort(finalgames, new Comparator<PlayOffGame>() {
            @Override
            public int compare(PlayOffGame o1, PlayOffGame o2) {
                return o1.getPosition().compareTo(o2.getPosition());
            }
        });
        if (finalgames.size() != playerPlayOffCountAfterCheckThirdPlace) {
            logger.info("Creating playOff games");
            deletePlayOffGames(finalgames);
            playOffGames = createPlayOffGames(playOffPlayer, group, playerPlayOffCountAfterCheckThirdPlace);
        } else {
            logger.info("Updating playOff games");
            updatePlayOffGames(playOffPlayerCount, finalgames, tournament, previousPlayerResultCount);
            playOffGames = finalgames;
        }
        time = System.currentTimeMillis() - time;
        logger.debug("Celkova doba 'getPlayOffGames': " + time + " ms");
        return playOffGames;
    }

    public void updatePlayOffGames(int playerPlayOffCount, List<PlayOffGame> playOffGames, Tournament tournament,
            int playerGroupCountSuffix) {
        int lastRound = 1;

        ArrayList<PlayerResult> finalStandings = new ArrayList<PlayerResult>();
        int gameCount = (playOffGames.size() % 2 == 1) ? playOffGames.size() - 1 : playOffGames.size() - 2;
        for (int i = 0; i < gameCount; i++) {
            int round = TournamentUtil.getRound(playerPlayOffCount, i + 1);
            PlayOffGame nextGame1 = playOffGames.get(i);
            updatePlayOffGame(nextGame1, playOffGames, i + 1, playerPlayOffCount, round != lastRound, finalStandings,
                    round, tournament, playerGroupCountSuffix);
            lastRound = round;
        }
        // nehra sa o tretie miesto
        if (playOffGames.size() % 2 == 1) {
            finalStandingService.updateFinalStandings(finalStandings, lastRound + 1, playerPlayOffCount, tournament,
                    playerGroupCountSuffix);
        }

        int position = 1;
        for (int i = gameCount; i < playOffGames.size(); i++) {
            int homeWin = 0;
            int awayWin = 0;
            FinalStanding firstFinalStanding = finalStandingService.getFinalStanding(new FinalStanding()._setFinalRank(
                    position)._setTournament(tournament));
            FinalStanding secondFinalStanding = finalStandingService.getFinalStanding(new FinalStanding()
                    ._setFinalRank(position + 1)._setTournament(tournament));

            for (PlayOffResult playOffResult : playOffGames.get(i).getPlayOffResults()) {

                if (playOffResult.getHomeScore() != null && playOffResult.getAwayScore() != null) {

                    if (playOffResult.getHomeScore() > playOffResult.getAwayScore()) {
                        homeWin++;
                    } else if (playOffResult.getHomeScore() < playOffResult.getAwayScore()) {
                        awayWin++;
                    }
                }
            }

            if (homeWin > awayWin) {
                firstFinalStanding.setPlayer(playOffGames.get(i).getHomePlayerResult().getPlayer());
                secondFinalStanding.setPlayer(playOffGames.get(i).getAwayPlayerResult().getPlayer());
            } else if (homeWin < awayWin) {
                firstFinalStanding.setPlayer(playOffGames.get(i).getAwayPlayerResult().getPlayer());
                secondFinalStanding.setPlayer(playOffGames.get(i).getHomePlayerResult().getPlayer());
            } else {
                firstFinalStanding.setPlayer(null);
                secondFinalStanding.setPlayer(null);
            }
            finalStandingService.updateFinalStanding(firstFinalStanding);
            finalStandingService.updateFinalStanding(secondFinalStanding);
            position += 2;
        }

    }

    private void updatePlayOffGame(PlayOffGame playOffGame, List<PlayOffGame> playOffGames, int position,
            int playerPlayOffCount, boolean newRound, List<PlayerResult> finalStandings, int round,
            Tournament tournament, int playerGroupCountSuffix) {

        int homeWin = 0;
        int awayWin = 0;

        for (PlayOffResult playOffResult : playOffGame.getPlayOffResults()) {
            if (playOffResult.getHomeScore() != null && playOffResult.getAwayScore() != null) {
                if (playOffResult.getHomeScore() > playOffResult.getAwayScore()) {
                    homeWin++;
                } else if (playOffResult.getHomeScore() < playOffResult.getAwayScore()) {
                    awayWin++;
                }
            }
        }

        if (newRound) {
            finalStandingService.updateFinalStandings(finalStandings, round, playerPlayOffCount, tournament,
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

        if (homeWin > awayWin) {
            finalStandings.add(playOffGame.getAwayPlayerResult());
            if (position % 2 == 1) {
                tempPlayOffGame._setHomePlayerResult((playOffGame.getHomePlayerResult()));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setHomePlayerResult((playOffGame.getAwayPlayerResult()));
                }
            } else {
                tempPlayOffGame._setAwayPlayerResult((playOffGame.getHomePlayerResult()));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setAwayPlayerResult((playOffGame.getAwayPlayerResult()));
                }
            }
        } else if (homeWin < awayWin) {
            finalStandings.add(playOffGame.getHomePlayerResult());
            if (position % 2 == 1) {
                tempPlayOffGame._setHomePlayerResult((playOffGame.getAwayPlayerResult()));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setHomePlayerResult((playOffGame.getHomePlayerResult()));
                }
            } else {
                tempPlayOffGame._setAwayPlayerResult((playOffGame.getAwayPlayerResult()));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setAwayPlayerResult((playOffGame.getHomePlayerResult()));
                }
            }
        } else {
            finalStandings.add(new PlayerResult());
            if (position % 2 == 1) {
                tempPlayOffGame._setHomePlayerResult((null));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setHomePlayerResult((null));
                }
            } else {
                tempPlayOffGame._setAwayPlayerResult((null));
                if (isThirdPlace) {
                    tempThirdPlayOffGame._setAwayPlayerResult((null));
                }
            }
        }

        logger.info("Updating playOff game");
        updatePlayOffGame(tempPlayOffGame);
        if (tempThirdPlayOffGame != null) {
            logger.info("Updating third playOff game");
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

    public int updatePlayOffResult(PlayOffResult playOffResult) {
        return playOffResultService.updatePlayOffResult(playOffResult);
    }

    @Required
    public void setFinalStandingService(FinalStandingService finalStandingService) {
        this.finalStandingService = finalStandingService;
    }

    @Required
    public void setPlayerResultService(PlayerResultService playerResultService) {
        this.playerResultService = playerResultService;
    }

    @Required
    public void setPlayOffResultService(PlayOffResultService playOffResultService) {
        this.playOffResultService = playOffResultService;
    }

    @Required
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}