package org.toursys.web.util;

import org.toursys.repository.model.Participant;

public class WebUtil {

    public static String getParticipandPlayerFullName(Participant participant) {
        String playerName = "";

        if (participant.getPlayer() != null) {
            playerName = participant.getPlayer().getName() + " " + participant.getPlayer().getSurname() + " "
                    + participant.getPlayer().getPlayerDiscriminator();
        }

        return playerName;
    }

}
