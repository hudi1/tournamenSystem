package org.tahom.processor.service.group.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupPageOverviewDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<GroupsOverviewDto> groups;

	public List<GroupsOverviewDto> getGroups() {
		if (groups == null) {
			groups = new ArrayList<GroupsOverviewDto>();
		}
		return groups;
	}

}
