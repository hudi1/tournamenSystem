package org.tahom.processor.service.registration;

import javax.inject.Inject;

import org.tahom.processor.service.participant.ParticipantModel;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.processor.service.player.PlayerService;
import org.tahom.processor.service.registration.dto.RegistrationPageDto;
import org.tahom.repository.model.Tournament;

public class RegistrationService {

	@Inject
	private PlayerService playerService;

	@Inject
	private ParticipantService participantService;

	@Inject
	private ParticipantModel participantModel;

	public RegistrationPageDto getRegistrationPageDto(Tournament tournament, String groupName) {
		RegistrationPageDto dto = new RegistrationPageDto();
		dto.setGroupName(groupName);
		dto.getNotRegistratedPlayers().addAll(playerService.getNotRegisteredPlayers(tournament));
		dto.getParticipants().addAll(participantService.getRegistratedParticipant(tournament));
		dto.getTournamentParticipants().addAll(participantModel.createParticipantsDto(dto.getParticipants()));
		return dto;
	}

}
