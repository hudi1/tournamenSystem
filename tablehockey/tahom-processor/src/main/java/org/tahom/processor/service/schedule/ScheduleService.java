package org.tahom.processor.service.schedule;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.tahom.processor.schedule.AdvancedRoundRobinSchedule;
import org.tahom.processor.schedule.BasicLessHockeyRoundRobinSchedule;
import org.tahom.processor.schedule.BasicRoundRobinSchedule;
import org.tahom.processor.schedule.EmptyRoundRobinSchedule;
import org.tahom.processor.schedule.RoundRobinSchedule;
import org.tahom.processor.service.finalStanding.FinalStandingService;
import org.tahom.processor.service.game.GameService;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.processor.service.playOffGame.PlayOffGameService;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Tournament;

public class ScheduleService {

	@Inject
	private ParticipantService participantService;

	@Inject
	private GameService gameService;

	@Inject
	private PlayOffGameService playOffGameService;

	@Inject
	private FinalStandingService finalStandingService;

	public RoundRobinSchedule getSchedule(Tournament tournament, Groups group, List<Participant> participants) {
		RoundRobinSchedule roundRobinSchedule;

		if (group.getType() != GroupsType.BASIC && group.getCopyResult()) {
			LinkedList<List<Participant>> participantByGroup = participantService.getAdvancedPlayersByGroup(group,
			        tournament, participants);
			return new AdvancedRoundRobinSchedule(group, participantByGroup);
		} else {
			if (participants.size() < 2) {
				roundRobinSchedule = new EmptyRoundRobinSchedule();
			} else if (participants.size() / 2 <= group.getNumberOfHockey()) {
				roundRobinSchedule = new BasicRoundRobinSchedule(group, participants);
			} else {
				roundRobinSchedule = new BasicLessHockeyRoundRobinSchedule(group, participants);
			}
		}
		return roundRobinSchedule;
	}

	public void saveSchedule(Tournament tournament, Groups group, List<GameDto> scheduleGames) {
		gameService.updateBothGames(scheduleGames);
		List<Participant> participants = participantService.getParticipantByGroup(group);
		participantService.sortParticipantsByRank(participants, tournament);

		if (GroupsType.FINAL.equals(group.getType())) {
			playOffGameService.updatePlayOffGames(tournament, group);
			finalStandingService.updateNotPromotingFinalStandings(tournament, group);
		}
	}

}