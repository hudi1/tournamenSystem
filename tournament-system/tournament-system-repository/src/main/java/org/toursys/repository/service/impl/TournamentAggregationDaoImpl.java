package org.toursys.repository.service.impl;

import java.util.List;

import org.toursys.repository.dao.GameDao;
import org.toursys.repository.dao.GroupDao;
import org.toursys.repository.dao.PlayOffGameDao;
import org.toursys.repository.dao.PlayOffResultDao;
import org.toursys.repository.dao.PlayerDao;
import org.toursys.repository.dao.PlayerResultDao;
import org.toursys.repository.dao.SeasonDao;
import org.toursys.repository.dao.TournamentDao;
import org.toursys.repository.dao.UserDao;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentAggregationDaoImpl implements TournamentAggregationDao {

    private GameDao gameDao;
    private PlayOffGameDao playOffGameDao;
    private PlayOffResultDao playOffResultDao;
    private PlayerDao playerDao;
    private PlayerResultDao playerResultDao;
    private SeasonDao seasonDao;
    private GroupDao groupDao;
    private TournamentDao tournamentDao;
    private UserDao userDao;

    @Override
    public Game createGame(PlayerResult homePlayer, PlayerResult awayPlayer) {
        return gameDao.createGame(homePlayer, awayPlayer);
    }

    @Override
    public Game updateGame(Game game) {
        return gameDao.updateGame(game);
    }

    @Override
    public boolean deleteGame(Game game) {
        return gameDao.deleteGame(game);
    }

    @Override
    public Game getGame(Game game) {
        return gameDao.getGame(game);
    }

    @Override
    public List<Game> findGame(Game game) {
        return gameDao.findGame(game);
    }

    @Override
    public Groups createGroup(Groups group) {
        return groupDao.createGroup(group);
    }

    @Override
    public Groups updateGroup(Groups group) {
        return groupDao.updateGroup(group);
    }

    @Override
    public boolean deleteGroup(Groups group) {
        return groupDao.deleteGroup(group);
    }

    @Override
    public Groups getGroup(Groups group) {
        return groupDao.getGroup(group);
    }

    @Override
    public List<Groups> getListGroups(Groups group) {
        return groupDao.getListGroups(group);
    }

    @Override
    public Player createPlayer(Player player) {
        return playerDao.createPlayer(player);
    }

    @Override
    public Player updatePlayer(Player player) {
        return playerDao.updatePlayer(player);
    }

    @Override
    public boolean deletePlayer(Player player) {
        return playerDao.deletePlayer(player);
    }

    @Override
    public Player getPlayer(Player player) {
        return playerDao.getPlayer(player);
    }

    @Override
    public List<Player> getListPlayers(Player player) {
        return playerDao.getListPlayers(player);
    }

    @Override
    public PlayerResult createPlayerResult(Player player, Groups group) {
        return playerResultDao.createPlayerResult(player, group);
    }

    @Override
    public PlayerResult updatePlayerResult(PlayerResult playerResult) {
        return playerResultDao.updatePlayerResult(playerResult);
    }

    @Override
    public boolean deletePlayerResult(PlayerResult playerResult) {
        return playerResultDao.deletePlayerResult(playerResult);
    }

    @Override
    public PlayerResult getPlayerResult(PlayerResult playerResult) {
        return playerResultDao.getPlayerResult(playerResult);
    }

    @Override
    public List<PlayerResult> getRegistratedPlayerResult(Tournament tournament) {
        return playerResultDao.getRegistratedPlayerResult(tournament);
    }

    @Override
    public List<PlayerResult> getListPlayerResult(PlayerResult playerResult) {
        return playerResultDao.getListPlayerResult(playerResult);
    }

    @Override
    public Season createSeason(Season season) {
        return seasonDao.createSeason(season);
    }

    @Override
    public Season updateSeason(Season season) {
        return seasonDao.updateSeason(season);
    }

    @Override
    public boolean deleteSeason(Season season) {
        return seasonDao.deleteSeason(season);
    }

    @Override
    public Season getSeason(Season season) {
        return seasonDao.getSeason(season);
    }

    @Override
    public List<Season> getAllSeasons() {
        return seasonDao.getAllSeasons();
    }

    @Override
    public List<Season> getListSeason(Season season) {
        return seasonDao.getListSeason(season);
    }

    @Override
    public Season createTournament(Season season, Tournament... tournaments) {
        return tournamentDao.createTournament(season, tournaments);
    }

    @Override
    public Tournament updateTournament(Tournament tournament) {
        return tournamentDao.updateTournament(tournament);
    }

    @Override
    public boolean deleteTournament(Tournament tournament) {
        return tournamentDao.deleteTournament(tournament);
    }

    @Override
    public Tournament getTournament(Tournament tournament) {
        return tournamentDao.getTournament(tournament);
    }

    @Override
    public List<Tournament> getListTournaments(Tournament tournament) {
        return tournamentDao.getListTournaments(tournament);
    }

    @Override
    public List<Player> getNotRegistratedPlayers(Tournament tournament) {
        return playerDao.getNotRegistratedPlayers(tournament);
    }

    @Override
    public PlayOffGame createPlayOffGame(Player homePlayer, Player awayPlayer, Groups group, int position) {
        return playOffGameDao.createPlayOffGame(homePlayer, awayPlayer, group, position);
    }

    @Override
    public PlayOffGame updatePlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.updatePlayOffGame(playOffGame);
    }

    @Override
    public boolean deletePlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.deletePlayOffGame(playOffGame);
    }

    @Override
    public PlayOffGame getPlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.getPlayOffGame(playOffGame);
    }

    @Override
    public List<PlayOffGame> findPlayOffGame(PlayOffGame playOffGame) {
        return playOffGameDao.findPlayOffGame(playOffGame);
    }

    @Override
    public PlayOffResult createPlayOffResult(PlayOffGame playOffGame) {
        return playOffResultDao.createPlayOffResult(playOffGame);
    }

    @Override
    public PlayOffResult updatePlayOffResult(PlayOffResult playOffResult) {
        return playOffResultDao.updatePlayOffResult(playOffResult);
    }

    @Override
    public boolean deletePlayOffResult(PlayOffResult playOffResult) {
        return playOffResultDao.deletePlayOffResult(playOffResult);
    }

    @Override
    public PlayOffResult getPlayOffResult(PlayOffResult playOffResult) {
        return playOffResultDao.getPlayOffResult(playOffResult);
    }

    @Override
    public List<PlayOffResult> findPlayOffResult(PlayOffResult playOffResult) {
        return playOffResultDao.findPlayOffResult(playOffResult);
    }

    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean deleteUser(User user) {
        return userDao.deleteUser(user);
    }

    @Override
    public User getUser(User user) {
        return userDao.getUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void setPlayerResultDao(PlayerResultDao playerResultDao) {
        this.playerResultDao = playerResultDao;
    }

    public void setSeasonDao(SeasonDao seasonDao) {
        this.seasonDao = seasonDao;
    }

    public void setTournamentDao(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public void setPlayOffGameDao(PlayOffGameDao playOffGameDao) {
        this.playOffGameDao = playOffGameDao;
    }

    public void setPlayOffResultDao(PlayOffResultDao playOffResultDao) {
        this.playOffResultDao = playOffResultDao;
    }

}
