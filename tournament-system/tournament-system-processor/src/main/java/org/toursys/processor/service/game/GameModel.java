package org.toursys.processor.service.game;

import org.toursys.repository.model.Game;
import org.toursys.repository.model.Participant;

public class GameModel {

    public void changeParticipants(Game game) {
        Participant temp = game.getAwayParticipant();
        game.setAwayParticipant(game.getHomeParticipant());
        game.setHomeParticipant(temp);
    }

    public Game createOppositeGame(Game gameDb) {
        Game game = new Game();
        game.setAwayParticipant(game.getHomeParticipant());
        game.setHomeParticipant(game.getAwayParticipant());
        return game;
    }
}
