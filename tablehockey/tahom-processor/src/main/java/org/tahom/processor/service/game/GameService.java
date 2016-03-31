package org.tahom.processor.service.game;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.dao.GameExtDao;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Game.Attribute;
import org.tahom.repository.model.Participant;

public class GameService {

	@Inject
	private GameExtDao gameDao;

	@Inject
	private GameModel gameModel;

	@Transactional(readOnly = true)
	public List<Game> getFullGames(Game game) {
		game.setInit(Game.Association.homeParticipant.name(), Game.Association.awayParticipant.name());
		return gameDao.list(game);
	}

	@Transactional
	public void createGames(List<Participant> tournamentParticipants, Participant newParticipant) {
		for (Participant participant : tournamentParticipants) {
			if (!participant.equals(newParticipant)) {
				Game game = new Game();
				game.setAwayParticipant(participant);
				game.setHomeParticipant(newParticipant);
				gameDao.insert(game);
				gameModel.changeParticipants(game);
				gameDao.insert(game);
			}
		}
	}

	@Transactional
	public void updateBothGames(List<GameDto> schedule) {
		for (GameDto game : schedule) {
			if (game.getGameId() == null) {
				continue;
			}
			gameModel.assignWinner(game);
			updateBothGames(game);
		}
	}

	private void updateBothGames(GameDto gameDto) {
		Game game = gameModel.createGameFromDto(gameDto);
		game._setNull(Attribute.result);
		gameDao.update(game);

		gameModel.changeParticipants(game);
		if (game.getResult() != null) {
			game.setResult(game.getResult().revert());
		}
		gameDao.updateOppositeGame(game);
	}

}