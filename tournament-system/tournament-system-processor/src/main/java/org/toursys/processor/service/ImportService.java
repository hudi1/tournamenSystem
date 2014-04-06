package org.toursys.processor.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.html.PlayersHtmlImportFactory;
import org.toursys.processor.html.TournamentHtmlImportFactory;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

public class ImportService extends AbstractService {

    private PlayerService playerService;
    private ParticipantService participantService;
    private GameService gameService;

    @Transactional
    public void importTournament(String url, Tournament tournament, User user) throws Exception {

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
    }

    @Transactional
    public void importPlayers(String url, User user) throws Exception {
        List<Player> players = PlayersHtmlImportFactory.createdImportedPlayers(url, user);
        for (Player player : players) {
            playerService.createPlayer(player);
        }
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public ParticipantService getParticipantService() {
        return participantService;
    }

    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    public GameService getGameService() {
        return gameService;
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

}
