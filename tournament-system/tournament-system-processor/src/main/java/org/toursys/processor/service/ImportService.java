package org.toursys.processor.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.html.PlayersHtmlImportFactory;
import org.toursys.processor.html.TournamentHtmlImportFactory;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

public class ImportService extends AbstractService {

    private PlayerService playerService;
    private ParticipantService participantService;
    private GameService gameService;
    private PlayOffGameService playOffGameService;
    private FinalStandingService finalStandingService;

    @Transactional
    public void importTournament(String url, Tournament tournament, User user) {

        importPlayers(url + "index.htm", user);

        LinkedList<Participant> participants = TournamentHtmlImportFactory.createImportedParticipants(url);

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
            savedParticipants.add(participantService.createBasicParticipant(tournament, participant.getPlayer(),
                    participant.getGroup()));
        }

        TournamentHtmlImportFactory.createImportedGames(url, savedParticipants);

        for (Participant participant : savedParticipants) {
            participantService.updateParticipant(participant);
            for (Game game : participant.getGames()) {
                gameService.createGame(game);
            }
        }

        List<PlayOffGame> playOffGames = TournamentHtmlImportFactory.createPlayOffGames(url, savedParticipants);
        for (PlayOffGame playOffGame : playOffGames) {
            playOffGameService.createPlayOffGame(playOffGame);
        }

        List<FinalStanding> finalStandings = TournamentHtmlImportFactory.createFinalStandings(url, savedParticipants);
        for (FinalStanding finalStanding : finalStandings) {
            finalStanding.setTournament(tournament);
            finalStandingService.createFinalStanding(finalStanding);
        }

    }

    @Transactional
    public void importPlayers(String url, User user) {
        List<Player> players = PlayersHtmlImportFactory.createdImportedPlayers(url, user);
        for (Player player : players) {
            playerService.createPlayer(player);
        }
    }

    @Required
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Required
    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Required
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Required
    public void setPlayOffGameService(PlayOffGameService playOffGameService) {
        this.playOffGameService = playOffGameService;
    }

    @Required
    public void setFinalStandingService(FinalStandingService finalStandingService) {
        this.finalStandingService = finalStandingService;
    }

}
