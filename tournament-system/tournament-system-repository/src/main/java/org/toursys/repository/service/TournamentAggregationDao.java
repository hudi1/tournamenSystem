package org.toursys.repository.service;

import java.util.List;

import org.toursys.repository.model.FinalStanding;
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

    public int updateGame(Game game);

    public int deleteGame(Game game);

    public Game getGame(Game game);

    public List<Game> getListGames(Game game);

    public Groups createGroup(Groups group);

    public int updateGroup(Groups group);

    public int deleteGroup(Groups group);

    public Groups getGroup(Groups group);

    public List<Groups> getListGroups(Groups group);

    public Player createPlayer(Player player);

    public int updatePlayer(Player player);

    public int deletePlayer(Player player);

    public Player getPlayer(Player player);

    public List<Player> getListPlayers(Player player);

    public PlayerResult createPlayerResult(Player player, Groups group);

    public int updatePlayerResult(PlayerResult playerResult);

    public int deletePlayerResult(PlayerResult playerResult);

    public PlayerResult getPlayerResult(PlayerResult playerResult);

    public List<PlayerResult> getRegistratedPlayerResult(Tournament tournament);

    public List<PlayerResult> getListPlayerResults(PlayerResult form);

    public Season createSeason(Season season);

    public int updateSeason(Season season);

    public int deleteSeason(Season season);

    public Season getSeason(Season season);

    public List<Season> getListSeasons(Season season);

    public Tournament createTournament(Tournament tournaments);

    public int updateTournament(Tournament tournament);

    public int deleteTournament(Tournament tournament);

    public Tournament getTournament(Tournament tournament);

    public List<Tournament> getListTournaments(Tournament tournament);

    public List<Player> getNotRegistratedPlayers(Tournament tournament);

    public PlayOffGame createPlayOffGame(PlayerResult homePlayer, PlayerResult awayPlayer, Groups group, int position);

    public int updatePlayOffGame(PlayOffGame playOffGame);

    public int deletePlayOffGame(PlayOffGame playOffGame);

    public PlayOffGame getPlayOffGame(PlayOffGame playOffGame);

    public List<PlayOffGame> getListPlayOffGames(PlayOffGame playOffGame);

    public PlayOffResult createPlayOffResult(PlayOffGame playOffGame);

    public int updatePlayOffResult(PlayOffResult playOffResult);

    public int deletePlayOffResult(PlayOffResult playOffResult);

    public PlayOffResult getPlayOffResult(PlayOffResult playOffResult);

    public List<PlayOffResult> getListPlayOffResults(PlayOffResult playOffResult);

    public User createUser(User user);

    public int updateUser(User user);

    public int deleteUser(User user);

    public User getUser(User user);

    public List<User> getListUsers(User user);

    public FinalStanding createFinalStanding(FinalStanding finalStanding);

    public int updateFinalStanding(FinalStanding finalStanding);

    public int deleteFinalStanding(FinalStanding finalStanding);

    public FinalStanding getFinalStanding(FinalStanding finalStanding);

    public List<FinalStanding> getListFinalStandings(FinalStanding finalStanding);

}
