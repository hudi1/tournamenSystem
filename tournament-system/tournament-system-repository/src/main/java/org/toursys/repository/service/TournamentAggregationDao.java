package org.toursys.repository.service;

import java.util.List;

import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

public interface TournamentAggregationDao {

    public Game createGame(PlayerResult homePlayer, PlayerResult awayPlayer);

    public Game updateGame(Game game);

    public boolean deleteGame(Game game);

    public Game getGame(Game game);

    public List<Game> findGame(Game game);

    public Groups createGroup(Groups group);

    public Groups updateGroup(Groups group);

    public boolean deleteGroup(Groups group);

    public Groups getGroup(Groups group);

    public List<Groups> getListGroups(Groups group);

    public Player createPlayer(Player player);

    public Player updatePlayer(Player player);

    public boolean deletePlayer(Player player);

    public Player getPlayer(Player player);

    public List<Player> getListPlayers(Player player);

    public PlayerResult createPlayerResult(Player player, Groups group);

    public PlayerResult updatePlayerResult(PlayerResult playerResult);

    public boolean deletePlayerResult(PlayerResult playerResult);

    public PlayerResult getPlayerResult(PlayerResult playerResult);

    public List<PlayerResult> getRegistratedPlayerResult(Tournament tournament);

    public List<PlayerResult> getListPlayerResult(PlayerResult form);

    public Season createSeason(Season season);

    public Season updateSeason(Season season);

    public boolean deleteSeason(Season season);

    public Season getSeason(Season season);

    public List<Season> getAllSeasons();

    public List<Season> getListSeason(Season season);

    public Season createTournament(Season season, Tournament... tournaments);

    public Tournament updateTournament(Tournament tournament);

    public boolean deleteTournament(Tournament tournament);

    public Tournament getTournament(Tournament tournament);

    public List<Tournament> getListTournaments(Tournament tournament);

    public List<Player> getNotRegistratedPlayers(Tournament tournament);

    public PlayOffGame createPlayOffGame(Player homePlayer, Player awayPlayer, Groups group, int position);

    public PlayOffGame updatePlayOffGame(PlayOffGame playOffGame);

    public boolean deletePlayOffGame(PlayOffGame playOffGame);

    public PlayOffGame getPlayOffGame(PlayOffGame playOffGame);

    public List<PlayOffGame> findPlayOffGame(PlayOffGame playOffGame);

    public PlayOffResult createPlayOffResult(PlayOffGame playOffGame);

    public PlayOffResult updatePlayOffResult(PlayOffResult playOffResult);

    public boolean deletePlayOffResult(PlayOffResult playOffResult);

    public PlayOffResult getPlayOffResult(PlayOffResult playOffResult);

    public List<PlayOffResult> findPlayOffResult(PlayOffResult playOffResult);

    public User createUser(User user);

    public User updateUser(User user);

    public boolean deleteUser(User user);

    public User getUser(User user);

    public List<User> getAllUsers();

}
