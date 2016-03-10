package org.tahom.processor.service.group.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.processor.service.participant.dto.ParticipantDto;
import org.tahom.repository.model.Groups;

public class GroupPageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Groups group;
	private List<Groups> groups;
	private List<ParticipantDto> participants;
	private Set<ParticipantDto> goldGoalParticipants;
	private List<GameDto> schedule;

	public Groups getGroup() {
		return group;
	}

	public void setGroup(Groups group) {
		this.group = group;
	}

	public List<Groups> getGroups() {
		if (groups == null) {
			groups = new ArrayList<Groups>();
		}
		return groups;
	}

	public List<ParticipantDto> getParticipants() {
		if (participants == null) {
			participants = new ArrayList<ParticipantDto>();
		}
		return participants;
	}

	public Set<ParticipantDto> getGoldGoalParticipants() {
		if (goldGoalParticipants == null) {
			goldGoalParticipants = new HashSet<ParticipantDto>();
		}
		return goldGoalParticipants;
	}

	public List<GameDto> getSchedule() {
		if (schedule == null) {
			schedule = new ArrayList<GameDto>();
		}
		return schedule;
	}

	public List<ParticipantDto> getParticipantsHeader() {
		return getParticipants();
	}

}