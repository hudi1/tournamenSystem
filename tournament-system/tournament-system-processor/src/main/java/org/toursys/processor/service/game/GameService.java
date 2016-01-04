package org.toursys.processor.service.game;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.dao.GameExtDao;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Game.Attribute;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Participant;

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
	public void updateBothGames(List<GameImpl> schedule) {
		for (GameImpl gameImpl : schedule) {
			if (gameImpl.getId() == null) {
				continue;
			}
			updateBothGames(gameImpl);
		}
	}

	// TODO ked sa budes nudit toto treba nejak vyriesit. nie je to ciste
	// riesenie ale zatim to funguje a nie je to
	// pomale
	private void updateBothGames(Game game) {
		game._setNull(Attribute.result);
		gameDao.update(game);

		gameModel.changeParticipants(game);
		if (game.getResult() != null) {
			game.setResult(game.getResult().revert());
		}
		gameDao.updateOppositeGame(game);
	}

}