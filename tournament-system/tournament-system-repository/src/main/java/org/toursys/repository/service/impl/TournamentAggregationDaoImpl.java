package org.toursys.repository.service.impl;

import java.util.List;

import org.toursys.repository.dao.FinalStandingDao;
import org.toursys.repository.dao.GameDao;
import org.toursys.repository.dao.GroupsDao;
import org.toursys.repository.dao.PlayOffGameDao;
import org.toursys.repository.dao.PlayOffResultDao;
import org.toursys.repository.dao.PlayerExtDao;
import org.toursys.repository.dao.PlayerResultExtDao;
import org.toursys.repository.dao.SeasonDao;
import org.toursys.repository.dao.TournamentDao;
import org.toursys.repository.dao.UserDao;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Score;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentAggregationDaoImpl implements TournamentAggregationDao {

    private GameDao gameDao;
    private PlayOffGameDao playOffGameDao;
    private PlayOffResultDao playOffResultDao;
    private PlayerExtDao playerDao;
    private PlayerResultExtDao playerResultDao;
    private SeasonDao seasonDao;
    private GroupsDao groupsDao;
    private TournamentDao tournamentDao;
    private UserDao userDao;
    private FinalStandingDao finalStandingDao;

    @Override
    public Game createGame(PlayerResult homePlayer, PlayerResult awayPlayer) {
        return gameDao.insert(new Game(homePlayer, awayPlayer));
    }

    @Override
    public int updateGame(Game game) {
        return gameDao.update(game);
    }

    @Override
    public int deleteGame(Game game) {
        return gameDao.delete(game);
    }

    @Override
    public Game getGame(Game game) {
        return gameDao.get(game);
    }

    @Override
    public List<Game> findGame(Game game) {
        return gameDao.list(game);
    }

    @Override
    public Groups createGroup(Groups group) {
        return groupsDao.insert(group);
    }

    @Override
    public int updateGroup(Groups group) {
        return groupsDao.update(group);
    }

    @Override
    public int deleteGroup(Groups group) {
        return groupsDao.delete(group);
    }

    @Override
    public Groups getGroup(Groups group) {
        return groupsDao.get(group);
    }

    @Override
    public List<Groups> getListGroups(Groups group) {
        return groupsDao.list(group);
    }

    @Override
    public Player createPlayer(Player player) {
        return playerDao.insert(player);
    }

    @Override
    public int updatePlayer(Player player) {
        return playerDao.update(player);
    }

    @Override
    public int deletePlayer(Player player) {
        return playerDao.delete(player);
    }

    @Override
    public Player getPlayer(Player player) {
        return playerDao.get(player);
    }

    @Override
    public List<Player> getListPlayers(Player player) {
        return playerDao.list(player);
    }

    @Override
    public List<Player> getNotRegistratedPlayers(Tournament tournament) {
        return playerDao.listNotRegistratedPlayers(tournament);
    }

    @Override
    public PlayerResult createPlayerResult(Player player, Groups group) {
        return playerResultDao.insert(new PlayerResult(0, group, player, new Score(0, 0)));
    }

    @Override
    public int updatePlayerResult(PlayerResult playerResult) {
        return playerResultDao.update(playerResult);
    }

    @Override
    public int deletePlayerResult(PlayerResult playerResult) {
        return playerResultDao.delete(playerResult);
    }

    @Override
    public PlayerResult getPlayerResult(PlayerResult playerResult) {
        return playerResultDao.get(playerResult);
    }

    @Override
    public List<PlayerResult> getRegistratedPlayerResult(Tournament tournament) {
        return playerResultDao.listRegistratedPlayerResult(tournament);
    }

    @Override
    public List<PlayerResult> getListPlayerResult(PlayerResult playerResult) {
        return playerResultDao.list(playerResult);
    }

    @Override
    public Season createSeason(Season season) {
        return seasonDao.insert(season);
    }

    @Override
    public int updateSeason(Season season) {
        return seasonDao.update(season);
    }

    @Override
    public int deleteSeason(Season season) {
        return seasonDao.delete(season);
    }

    @Override
    public Season getSeason(Season season) {
        return seasonDao.get(season);
    }

    @Override
    public List<Season> getListSeason(Season season) {
        return seasonDao.list(season);
    }

    @Override
    public List<Season> getAllSeasons() {
        return seasonDao.list(new Season());
    }

    @Override
    public Tournament createTournament(Tournament tournament) {
        return tournamentDao.insert(tournament);
    }

    @Override
    public int updateTournament(Tournament tournament) {
        return tournamentDao.update(tournament);
    }

    @Override
    public int deleteTournament(Tournament tournament) {
        return tournamentDao.delete(tournament);
    }

    @Override
    public Tournament getTournament(Tournament tournament) {
        return tournamentDao.get(tournament);
    }

    @Override
    public List<Tournament> getListTournaments(Tournament tournament) {
        return tournamentDao.list(tournament);
    }

    @Override
    public PlayOffGame createPlayOffGame(PlayerResult homePlayer, PlayerResult awayPlayer, Groups group, int position) {
        return playOffGameDao.insert(new PlayOffGame(group, position)._setAwayPlayerResult(awayPlayer)
                ._setHomePlayerResult(homePlayer));
    }

    @Override
    public int updatePlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.update(playOffGame);
    }

    @Override
    public int deletePlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.delete(playOffGame);
    }

    @Override
    public PlayOffGame getPlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.get(playOffGame);
    }

    @Override
    public List<PlayOffGame> findPlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.list(playOffGame);
    }

    @Override
    public PlayOffResult createPlayOffResult(PlayOffGame playOffGame) {
        return playOffResultDao.insert(new PlayOffResult(false, playOffGame));
    }

    @Override
    public int updatePlayOffResult(PlayOffResult playOffResult) {
        return playOffResultDao.update(playOffResult);
    }

    @Override
    public int deletePlayOffResult(PlayOffResult playOffResult) {
        return playOffResultDao.delete(playOffResult);
    }

    @Override
    public PlayOffResult getPlayOffResult(PlayOffResult playOffResult) {
        return playOffResultDao.get(playOffResult);
    }

    @Override
    public List<PlayOffResult> findPlayOffResult(PlayOffResult playOffResult) {
        return playOffResultDao.list(playOffResult);
    }

    @Override
    public FinalStanding createFinalStanding(FinalStanding finalStanding) {
        return finalStandingDao.insert(finalStanding);
    }

    @Override
    public int updateFinalStanding(FinalStanding finalStanding) {
        return finalStandingDao.update(finalStanding);
    }

    @Override
    public int deleteFinalStanding(FinalStanding finalStanding) {
        return finalStandingDao.delete(finalStanding);
    }

    @Override
    public FinalStanding getFinalStanding(FinalStanding finalStanding) {
        return finalStandingDao.get(finalStanding);
    }

    @Override
    public List<FinalStanding> findFinalStanding(FinalStanding finalStanding) {
        return finalStandingDao.list(finalStanding);
    }

    @Override
    public User createUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public int updateUser(User user) {
        return userDao.update(user);
    }

    @Override
    public int deleteUser(User user) {
        return userDao.delete(user);
    }

    @Override
    public User getUser(User user) {
        return userDao.get(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.list(new User());
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public void setSeasonDao(SeasonDao seasonDao) {
        this.seasonDao = seasonDao;
    }

    public void setTournamentDao(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    public void setGroupsDao(GroupsDao groupsDao) {
        this.groupsDao = groupsDao;
    }

    public void setPlayOffGameDao(PlayOffGameDao playOffGameDao) {
        this.playOffGameDao = playOffGameDao;
    }

    public void setPlayOffResultDao(PlayOffResultDao playOffResultDao) {
        this.playOffResultDao = playOffResultDao;
    }

    public void setPlayerDao(PlayerExtDao playerDao) {
        this.playerDao = playerDao;
    }

    public void setPlayerResultDao(PlayerResultExtDao playerResultDao) {
        this.playerResultDao = playerResultDao;
    }

    public void setFinalStandingDao(FinalStandingDao finalStandingDao) {
        this.finalStandingDao = finalStandingDao;
    }

}
