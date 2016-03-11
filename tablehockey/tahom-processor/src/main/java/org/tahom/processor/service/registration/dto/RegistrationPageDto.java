package org.tahom.processor.service.registration.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.tahom.processor.service.participant.dto.ParticipantDto;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Player;

public class RegistrationPageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String groupName;
	private List<ParticipantDto> tournamentParticipants;
	private List<Participant> participants;
	private List<Player> notRegistratedPlayers;
	private RegistrationOptions registrationOptions;
	private String selectOptions;

	public RegistrationPageDto() {
		registrationOptions = new RegistrationOptions();
		selectOptions = registrationOptions.get(0);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<ParticipantDto> getTournamentParticipants() {
		if (tournamentParticipants == null) {
			tournamentParticipants = new ArrayList<ParticipantDto>();
		}
		return tournamentParticipants;
	}

	public List<Player> getNotRegistratedPlayers() {
		if (notRegistratedPlayers == null) {
			notRegistratedPlayers = new ArrayList<Player>();
		}
		return notRegistratedPlayers;
	}

	public RegistrationOptions getRegistrationOptions() {
		return registrationOptions;
	}

	public String getSelectOptions() {
		return selectOptions;
	}

	public void setSelectOptions(String selectOptions) {
		this.selectOptions = selectOptions;
	}

	public List<Participant> getParticipants() {
		if (participants == null) {
			participants = new ArrayList<Participant>();
		}
		return participants;
	}

}
