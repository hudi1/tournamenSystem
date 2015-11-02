package org.toursys.processor.service.playOffGame;

import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;

public class PlayOffGameModel {

    public PlayOffGame createPlayOffGame(Participant homeParticipant, Participant awayParticipant, Groups group,
            int position) {
        PlayOffGame playOffGame = new PlayOffGame(group, position);
        playOffGame.setHomeParticipant(homeParticipant);
        playOffGame.setAwayParticipant(awayParticipant);
        return playOffGame;
    }

}
