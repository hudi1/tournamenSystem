package org.tahom.processor.service.participant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.tahom.processor.service.game.GameModel;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.processor.service.participant.dto.ParticipantDto;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Participant;

public class ParticipantModel {

	@Inject
	private GameModel gameModel;

	private static final String NAME_DELIMETER = " ";

	public Collection<ParticipantDto> createParticipantsDto(Collection<Participant> participants) {
		List<ParticipantDto> participantsDto = new ArrayList<ParticipantDto>();
		for (Participant participant : participants) {
			ParticipantDto participantDto = createParticipantDto(participant);
			participantDto.getGames().addAll(getGames(participant, participants));
			participantsDto.add(participantDto);
		}

		return participantsDto;
	}

	private ParticipantDto createParticipantDto(Participant participant) {
		ParticipantDto participantDto = new ParticipantDto();
		participantDto.setName(participant.getPlayer().getName() + NAME_DELIMETER
		        + participant.getPlayer().getSurname());
		participantDto.setShortName(participant.getPlayer().getName().charAt(0) + "." + NAME_DELIMETER
		        + participant.getPlayer().getSurname());
		participantDto.setPoints(participant.getPoints());
		participantDto.setId(participant.getId());
		participantDto.setRank(participant.getRank());
		participantDto.setScore(participant.getScore());

		return participantDto;
	}

	private List<GameDto> getGames(Participant currentParticipant, Collection<Participant> participants) {
		List<GameDto> games = new ArrayList<GameDto>();
		for (Participant participant : participants) {
			if (participant.equals(currentParticipant)) {
				games.add(gameModel.createTempResultGame());
			} else {
				for (Game game : participant.getGames()) {
					if (game.getAwayParticipant().equals(currentParticipant)) {
						games.add(gameModel.createGameDto(game));
					}
				}
			}
		}
		return games;
	}
}
