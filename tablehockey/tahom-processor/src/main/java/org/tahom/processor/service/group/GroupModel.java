package org.tahom.processor.service.group;

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

}
