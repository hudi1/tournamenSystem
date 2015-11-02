package org.toursys.processor.service.imports;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.html.PlayerHtmlUpdateFactory;
import org.toursys.processor.html.PlayersHtmlImportFactory;
import org.toursys.processor.html.TournamentHtmlImportFactory;
import org.toursys.processor.service.game.GameService;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.processor.service.playOffGame.PlayOffGameService;
import org.toursys.processor.service.player.PlayerService;
import org.toursys.processor.service.standing.FinalStandingService;
import org.toursys.repository.dao.FinalStandingDao;
import org.toursys.repository.dao.GameDao;
import org.toursys.repository.dao.ParticipantExtDao;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

public class ImportService {

    @Inject
    private PlayerService playerService;

    @Inject
    private ParticipantService participantService;

    @Inject
    private GameService gameService;

    @Inject
    private PlayOffGameService playOffGameService;

    @Inject
    private FinalStandingService finalStandingService;

    @Inject
    private GameDao gameDao;

    @Inject
    private FinalStandingDao finalStandingDao;

    @Inject
    private ParticipantExtDao participantDao;

    @Transactional
    public void importTournament(String url, Tournament tournament, User user) {

        importPlayers(url + "index.htm", user);

        LinkedList<Participant> participants = TournamentHtmlImportFactory.createImportedParticipants(tournament, url);

        for (Participant participant : participants) {
            List<Player> players = playerService.getNotRegisteredPlayers(tournament);
            Boolean founded = false;
            for (Player player : players) {
                if (player.getSurname().equals(participant.getPlayer().getSurname())) {
                    if (player.getName().charAt(0) == participant.getPlayer().getName().charAt(0)) {
                        if (player.getPlayerDiscriminator().equals(participant.getPlayer().getPlayerDiscriminator())) {
                            founded = true;
                            participant.setPlayer(player);
                            break;
                        }
                    }
                }
            }

            if (!founded) {
                // Parametrizovat a lokalizovat vyjimky
                throw new RuntimeException(participant.getPlayer().toString());
            }
        }

        LinkedList<Participant> savedParticipants = new LinkedList<Participant>();

        for (Participant participant : participants) {
            savedParticipants
                    .add(participantService.createParticipant(participant.getPlayer(), participant.getGroup()));
        }

        TournamentHtmlImportFactory.createImportedGames(url, savedParticipants);

        for (Participant participant : savedParticipants) {
            participantDao.update(participant);
            for (Game game : participant.getGames()) {
                gameDao.insert(game);
            }
        }

        List<PlayOffGame> playOffGames = TournamentHtmlImportFactory.createPlayOffGames(url, savedParticipants);
        for (PlayOffGame playOffGame : playOffGames) {
            playOffGameService.createPlayOffGame(playOffGame);
        }

        List<FinalStanding> finalStandings = TournamentHtmlImportFactory.createFinalStandings(url, savedParticipants);
        for (FinalStanding finalStanding : finalStandings) {
            finalStanding.setTournament(tournament);
            finalStandingDao.insert(finalStanding);
        }

    }

    @Transactional
    public void importPlayers(String url, User user) {
        List<Player> players = PlayersHtmlImportFactory.createdImportedPlayers(url, user);
        for (Player player : players) {
            playerService.createPlayer(player);
        }
    }

    public List<Player> updateOnlinePlayers(List<Player> players) {
        PlayerHtmlUpdateFactory.synchronizedIthfPlayer(players);
        List<Player> notUpdatedPlayer = new ArrayList<Player>();

        for (Player player : players) {
            if (player.getIthfId() == null || player.getWorldRanking() == null) {
                notUpdatedPlayer.add(player);
            } else {
                playerService.updatePlayer(player);
            }
        }

        return notUpdatedPlayer;
    }
}