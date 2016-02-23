package org.tahom.web.util;

import org.tahom.repository.model.Game;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Player;

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
			playerName = player.getName() + " " + player.getSurname();
		}

		return playerName;
	}

	public static String getParticipandPlayerShortName(Participant participant) {
		String playerName = "";

		if (participant.getPlayer() != null) {
			playerName = participant.getPlayer().getName().charAt(0) + ". " + participant.getPlayer().getSurname();
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
