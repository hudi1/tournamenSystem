package org.toursys.repository.service;

import java.util.List;

import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

public interface TournamentAggregationDao {

    public Game createGame(Participant homePlayer, Participant awayPlayer);

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

    public Participant createParticipant(Player player, Groups group);

    public int updateParticipant(Participant participant);

    public int deleteParticipant(Participant participant);

    public Participant getParticipant(Participant participant);

    public List<Participant> getRegisteredParticipant(Tournament tournament);

    public List<Participant> getListParticipants(Participant form);

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

    public List<Player> getNotRegisteredPlayers(Tournament tournament);

    public PlayOffGame createPlayOffGame(Participant homePlayer, Participant awayPlayer, Groups group, int position);

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
