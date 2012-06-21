package org.toursys.repository.service.impl;

import java.util.List;

import org.toursys.repository.dao.GameDao;
import org.toursys.repository.dao.PlayerDao;
import org.toursys.repository.dao.PlayerResultDao;
import org.toursys.repository.dao.ResultDao;
import org.toursys.repository.dao.SeasonDao;
import org.toursys.repository.dao.TableDao;
import org.toursys.repository.dao.TournamentDao;
import org.toursys.repository.form.GameForm;
import org.toursys.repository.form.PlayerResultForm;
import org.toursys.repository.form.TableForm;
import org.toursys.repository.form.TournamentForm;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Result;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Table;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentAggregationDaoImpl implements TournamentAggregationDao {

	private GameDao gameDao;
	private PlayerDao playerDao;
	private PlayerResultDao playerResultDao;
	private ResultDao resultDao;
	private SeasonDao seasonDao;
	private TableDao tableDao;
	private TournamentDao tournamentDao;

	@Override
	public void createGame(Game game) {
		gameDao.createGame(game);
	}

	@Override
	public void updateGame(Game game) {
		gameDao.updateGame(game);
	}

	@Override
	public void deleteGame(Game game) {
		gameDao.deleteGame(game);
	}

	@Override
	public List<Game> findGame(GameForm gameForm) {
		return gameDao.findGame(gameForm);
	}

	@Override
	public void createPlayer(Player player) {
		playerDao.createPlayer(player);
	}

	@Override
	public void updatePlayer(Player player) {
		playerDao.updatePlayer(player);
	}

	@Override
	public void deletePlayer(Player player) {
		playerDao.deletePlayer(player);
	}

	@Override
	public List<Player> getAllPlayer() {
		return playerDao.getAllPlayer();
	}

	@Override
	public void createPlayerResult(PlayerResult playerResult) {
		playerResultDao.createPlayerResult(playerResult);
	}

	@Override
	public void updatePlayerResult(PlayerResult playerResult) {
		playerResultDao.updatePlayerResult(playerResult);
	}

	@Override
	public void deletePlayerResult(PlayerResult playerResult) {
		playerResultDao.deletePlayerResult(playerResult);
	}

	@Override
	public List<PlayerResult> findPlayerResult(PlayerResultForm playerResultForm) {
		return playerResultDao.findPlayerResult(playerResultForm);
	}

	@Override
	public void createResult(Result result) {
		resultDao.createResult(result);
	}

	@Override
	public void updateResult(Result result) {
		resultDao.updateResult(result);
	}

	@Override
	public void deleteResult(Result result) {
		resultDao.deleteResult(result);
	}

	@Override
	public List<Result> getAllResult() {
		return resultDao.getAllResult();
	}

	@Override
	public void createSeason(Season season) {
		seasonDao.createSeason(season);
	}

	@Override
	public void updateSeason(Season season) {
		seasonDao.updateSeason(season);

	}

	@Override
	public void deleteSeason(Season season) {
		seasonDao.deleteSeason(season);
	}

	@Override
	public List<Season> getAllSeason() {
		return seasonDao.getAllSeason();
	}

	@Override
	public void createTable(Table table) {
		tableDao.createTable(table);
	}

	@Override
	public void updateTable(Table table) {
		tableDao.updateTable(table);
	}

	@Override
	public void deleteTable(Table table) {
		tableDao.deleteTable(table);
	}

	@Override
	public List<Table> findTable(TableForm tableForm) {
		return tableDao.findTable(tableForm);
	}

	@Override
	public void createTournament(Tournament tournament) {
		tournamentDao.createTournament(tournament);
	}

	@Override
	public void updateTournament(Tournament tournament) {
		tournamentDao.updateTournament(tournament);
	}

	@Override
	public void deleteTournament(Tournament tournament) {
		tournamentDao.deleteTournament(tournament);
	}

	@Override
	public List<Tournament> findTournament(TournamentForm tournamentForm) {
		return tournamentDao.findTournament(tournamentForm);
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

	public void setResultDao(ResultDao resultDao) {
		this.resultDao = resultDao;
	}

	public void setSeasonDao(SeasonDao seasonDao) {
		this.seasonDao = seasonDao;
	}

	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
	}

	public void setTournamentDao(TournamentDao tournamentDao) {
		this.tournamentDao = tournamentDao;
	}

}
