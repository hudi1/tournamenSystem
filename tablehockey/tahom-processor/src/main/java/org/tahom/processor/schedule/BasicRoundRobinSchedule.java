package org.tahom.processor.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;

public class BasicRoundRobinSchedule extends RoundRobinSchedule {
	private int playerCount;
	private int roundCount;
	private List<Participant> participants;
	private boolean addEmptyGame;

	public BasicRoundRobinSchedule(Groups group, List<Participant> participants) {
		super(group);
		this.participants = new ArrayList<Participant>(participants);
		this.roundCount = participants.size();
		this.playerCount = this.participants.size();
	}

	protected void createSchedule() {
		schedule = new ArrayList<GameDto>();
		for (int i = 0; i < roundCount; i++) {
			LinkedList<Participant> roundParticipants = prepareParticipants(i + 1);
			addRoundGames(roundParticipants);
			Collections.rotate(participants, 1);
			if (addEmptyGame) {
				addEmptyGameToSchedule();
				addEmptyGame = false;
			}
		}
	}

	private LinkedList<Participant> prepareParticipants(int round) {
		LinkedList<Participant> roundParticipants = new LinkedList<Participant>(participants);
		if (playerCount % 2 == 0) {
			if (round > roundCount / 2) {
				roundParticipants.removeLast();
				addEmptyGame = true;
			}
		} else {
			roundParticipants.remove(playerCount / 2);
		}
		return roundParticipants;
	}

	private void addRoundGames(LinkedList<Participant> roundParticipants) {
		while (roundParticipants.size() > 1) {
			addGameToSchedule(roundParticipants.removeFirst(), roundParticipants.removeLast());
		}
	}

}