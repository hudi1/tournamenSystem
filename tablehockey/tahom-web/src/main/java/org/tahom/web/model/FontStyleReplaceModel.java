package org.tahom.web.model;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.GameStatus;

public class FontStyleReplaceModel extends AbstractReadOnlyModel<String> {

	private static final long serialVersionUID = 1L;

	private GameDto gameDto;

	public FontStyleReplaceModel(GameDto gameDto) {
		this.gameDto = gameDto;
	}

	@Override
	public String getObject() {
		if (gameDto.getResult() == null || gameDto.getResult().getResults() == null
		        || gameDto.getResult().getResults().isEmpty()) {
			return "";
		}
		if (GameStatus.DRAW.equals(gameDto.getGameStatus())) {
			return "font-style: italic;";
		}
		return "font-weight:bold;";
	}
}
