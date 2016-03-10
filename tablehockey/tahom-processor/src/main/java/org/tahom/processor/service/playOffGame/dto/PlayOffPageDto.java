package org.tahom.processor.service.playOffGame.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayOffPageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<PlayOffGroupDto> playOffGroups;

	public List<PlayOffGroupDto> getPlayOffGroups() {
		if (playOffGroups == null) {
			playOffGroups = new ArrayList<PlayOffGroupDto>();
		}
		return playOffGroups;
	}
}