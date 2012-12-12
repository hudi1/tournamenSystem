package org.toursys.repository.service.impl;

import java.util.List;

import org.toursys.repository.dao.GameDao;
import org.toursys.repository.dao.GroupDao;
import org.toursys.repository.dao.PlayerDao;
import org.toursys.repository.dao.PlayerResultDao;
import org.toursys.repository.dao.SeasonDao;
import org.toursys.repository.dao.TournamentDao;
import org.toursys.repository.form.GameForm;
import org.toursys.repository.form.GroupForm;
import org.toursys.repository.form.PlayerForm;
import org.toursys.repository.form.PlayerResultForm;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentAggregationDaoImpl implements TournamentAggregationDao {

    private GameDao gameDao;
    private PlayerDao playerDao;
    private PlayerResultDao playerResultDao;
    private SeasonDao seasonDao;
    private GroupDao groupDao;
    private TournamentDao tournamentDao;

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
    public List<Game> findGame(GameForm gameForm) {
        return gameDao.findGame(gameForm);
    }

    @Override
    public Tournament createGroup(Tournament tournament, Groups... groups) {
        return groupDao.createGroup(tournament, groups);
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
    public List<Groups> findGroups(GroupForm groupForm) {
        return groupDao.findGroups(groupForm);
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
    public List<Player> getAllPlayers() {
        return playerDao.getAllPlayers();
    }

    @Override
    public List<Player> getNotRegistrationPlayers(PlayerForm playerForm) {
        return playerDao.getNotRegistrationPlayers(playerForm);
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
    public List<PlayerResult> findPlayerResult(PlayerResultForm playerResultForm) {
        return playerResultDao.findPlayerResult(playerResultForm);
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
    public List<Tournament> findTournaments(Tournament tournament) {
        return tournamentDao.findTournaments(tournament);
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

}
