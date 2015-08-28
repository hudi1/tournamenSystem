package org.toursys.web.util;

import org.toursys.repository.model.Game;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;

public class WebUtil {

    public static String getParticipandPlayerFullName(Participant participant) {
        String playerName = "";

        if (participant.getPlayer() != null) {
            playerName = getPlayerFullName(participant.getPlayer());
        }

        return playerName;
    }

    public static String getPlayerFullName(Player player) {
        String playerName = "";

        if (player != null) {
            playerName = player.getName() + " " + player.getSurname() + " " + player.getPlayerDiscriminator();
        }

        return playerName;
    }

    public static String getParticipandPlayerShortName(Participant participant) {
        String playerName = "";

        if (participant.getPlayer() != null) {
            playerName = participant.getPlayer().getName().charAt(0) + ". " + participant.getPlayer().getSurname()
                    + " " + participant.getPlayer().getPlayerDiscriminator();
        }

        return playerName;
    }

    public static Game findParticipantGame(Participant participant, Participant rowParticipant) {
        for (Game game : participant.getGames()) {
            if (game.getAwayParticipant().equals(rowParticipant)) {
                return game;
            }
        }
        return null;
    }

}
