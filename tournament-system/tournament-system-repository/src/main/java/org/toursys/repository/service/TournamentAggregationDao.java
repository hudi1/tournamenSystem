package org.toursys.repository.service;

import java.util.List;

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

public interface TournamentAggregationDao {

    public Game createGame(PlayerResult homePlayer, PlayerResult awayPlayer);

    public Game updateGame(Game game);

    public boolean deleteGame(Game game);

    public Game getGame(Game game);

    public List<Game> findGame(GameForm gameForm);

    public Tournament createGroup(Tournament tournament, Groups... groups);

    public Groups updateGroup(Groups group);

    public boolean deleteGroup(Groups group);

    public Groups getGroup(Groups group);

    public List<Groups> findGroups(GroupForm groupForm);

    public Player createPlayer(Player player);

    public Player updatePlayer(Player player);

    public boolean deletePlayer(Player player);

    public Player getPlayer(Player player);

    public List<Player> getAllPlayers();

    public List<Player> getNotRegistrationPlayers(PlayerForm playerForm);

    public PlayerResult createPlayerResult(Player player, Groups group);

    public PlayerResult updatePlayerResult(PlayerResult playerResult);

    public boolean deletePlayerResult(PlayerResult playerResult);

    public PlayerResult getPlayerResult(PlayerResult playerResult);

    public List<PlayerResult> findPlayerResult(PlayerResultForm playerResultForm);

    public Season createSeason(Season season);

    public Season updateSeason(Season season);

    public boolean deleteSeason(Season season);

    public Season getSeason(Season season);

    public List<Season> getAllSeasons();

    public Season createTournament(Season season, Tournament... tournaments);

    public Tournament updateTournament(Tournament tournament);

    public boolean deleteTournament(Tournament tournament);

    public Tournament getTournament(Tournament tournament);

    public List<Tournament> findTournaments(Tournament tournament);

}
