package org.tahom.processor.service.group;

import java.util.ArrayList;
import java.util.List;

import org.tahom.processor.service.group.dto.GroupsOverviewDto;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.GroupsPlayOffType;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Tournament;

public class GroupModel {

	public Groups createGroup(Tournament tournament) {
		Groups group = new Groups();
		group.setTournament(tournament);
		return group;
	}

	public Groups createBasicGroup(Tournament tournament) {
		Groups group = createGroup(tournament);
		group.setType(GroupsType.BASIC);
		return group;
	}

	public Groups createFinalGroup(Tournament tournament) {
		Groups group = createGroup(tournament);
		group.setType(GroupsType.FINAL);
		return group;
	}

	public void initDefaultGroup(Groups group) {
		if (group != null) {
			group.setCopyResult(false);
			group.setPlayThirdPlace(false);
			group.setNumberOfHockey(1);
			group.setIndexOfFirstHockey(1);
		}
	}

	public Groups createFullFinalGroup(Tournament tournament, String name, int numberOfHockey,
	        GroupsPlayOffType playOffType) {
		Groups group = createFinalGroup(tournament);
		group.setName(name);
		group.setNumberOfHockey(numberOfHockey);
		group.setPlayOffType(playOffType);
		if (GroupsPlayOffType.FINAL.equals(playOffType)) {
			group.setCopyResult(true);
		}
		return group;
	}

	public List<GroupsOverviewDto> getGroupsOverviewDto(List<Groups> groups) {
		List<GroupsOverviewDto> result = new ArrayList<GroupsOverviewDto>();
		for (Groups group : groups) {
			GroupsOverviewDto dto = new GroupsOverviewDto();
			dto.setId(group.getId());
			dto.setGroupName(group.getName());
			result.add(dto);
		}
		return result;
	}

}
