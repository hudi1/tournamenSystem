package org.toursys.repository.service;

import java.util.List;

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

public interface TournamentAggregationDao {

    public void createGame(Game game);

    public void updateGame(Game game);

    public void deleteGame(Game game);

    public List<Game> findGame(GameForm gameForm);

    public void createPlayer(Player player);

    public void updatePlayer(Player player);

    public void deletePlayer(Player player);

    public List<Player> getAllPlayer();

    public void createPlayerResult(PlayerResult playerResult);

    public void updatePlayerResult(PlayerResult playerResult);

    public void deletePlayerResult(PlayerResult playerResult);

    public List<PlayerResult> findPlayerResult(PlayerResultForm playerResultForm);

    public void createResult(Result result);

    public void updateResult(Result result);

    public void deleteResult(Result result);

    public List<Result> getAllResult();

    public void createSeason(Season season);

    public void updateSeason(Season season);

    public void deleteSeason(Season season);

    public List<Season> getAllSeason();

    public void createTable(Table table);

    public void updateTable(Table table);

    public void deleteTable(Table table);

    public List<Table> findTable(TableForm tableForm);

    public void createTournament(Tournament tournament);

    public void updateTournament(Tournament tournament);

    public void deleteTournament(Tournament tournament);

    public List<Tournament> findTournament(TournamentForm tournamentForm);

}
