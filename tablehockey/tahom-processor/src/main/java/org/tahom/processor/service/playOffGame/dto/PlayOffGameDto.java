package org.tahom.processor.service.playOffGame.dto;

import org.tahom.processor.service.game.dto.GameDto;

public class PlayOffGameDto extends GameDto {

	private static final long serialVersionUID = 1L;

	private String roundName;

	public String getRoundName() {
		return roundName;
	}

	public void setRoundName(String roundName) {
		this.roundName = roundName;
	}

}
