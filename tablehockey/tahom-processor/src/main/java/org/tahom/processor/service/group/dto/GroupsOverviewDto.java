package org.tahom.processor.service.group.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.tahom.processor.service.participant.dto.ParticipantDto;

public class GroupsOverviewDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ParticipantDto> participants;
	private Integer id;
	private String groupName;

	public List<ParticipantDto> getParticipants() {
		if (participants == null) {
			participants = new ArrayList<ParticipantDto>();
		}
		return participants;
	}

	public List<ParticipantDto> getParticipantsHeader() {
		return getParticipants();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
