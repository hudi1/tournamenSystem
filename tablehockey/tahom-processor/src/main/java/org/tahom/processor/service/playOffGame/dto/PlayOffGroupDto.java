package org.tahom.processor.service.playOffGame.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayOffGroupDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private List<PlayOffGameDto> playOffGames;

	public List<PlayOffGameDto> getPlayOffGames() {
		if (playOffGames == null) {
			playOffGames = new ArrayList<PlayOffGameDto>();
		}
		return playOffGames;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}