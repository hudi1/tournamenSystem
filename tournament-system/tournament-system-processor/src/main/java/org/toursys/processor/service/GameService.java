package org.toursys.processor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Participant;

public class GameService extends AbstractService {

    // Basic operations

    @Transactional
    public Game createGame(Participant homePlayer, Participant awayPlayer) {
        logger.debug("Create game: " + homePlayer + " " + awayPlayer);
        return tournamentAggregationDao.createGame(homePlayer, awayPlayer);
    }

    @Transactional
    public Game createGame(Game game) {
        logger.debug("Create game: " + game);
        return tournamentAggregationDao.createGame(game);
    }

    @Transactional(readOnly = true)
    public Game getGame(Game game) {
        logger.debug("Get game: " + game);
        return tournamentAggregationDao.getGame(game);
    }

    @Transactional
    public int updateGame(Game game) {
        logger.debug("Updating game: " + game);
        game.setNull(Game.Attribute.homeScore, Game.Attribute.awayScore);
        return tournamentAggregationDao.updateGame(game);
    }

    @Transactional
    public int deleteGame(Game game) {
        logger.debug("Delete game: " + game);
        return tournamentAggregationDao.deleteGame(game);
    }

    @Transactional(readOnly = true)
    public List<Game> getGames(Game game) {
        game.setInit(Game.Association.homeParticipant.name(), Game.Association.awayParticipant.name());
        logger.debug("Get list games: " + game);
        return tournamentAggregationDao.getListGames(game);
    }

    // Advanced operations

    // TODO ked sa budes nudit toto treba nejak vyriesit. nie je to ciste
    // riesenie ale zatim to funguje a nie je to
    // pomale
    private void updateBothGames(Game game) {
        logger.info("Update both games: " + game);
        updateGame(game);
        Game gameDb = getGame(new Game()._setHomeParticipant(game.getAwayParticipant())._setAwayParticipant(
                game.getHomeParticipant()));
        gameDb.setHomeScore(game.getAwayScore());
        gameDb.setAwayScore(game.getHomeScore());
        updateGame(gameDb);
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

    @Transactional
    public void processGames(final List<Participant> participants) {
        logger.debug("Process games: " + participants);
        long time = System.currentTimeMillis();
        if (!participants.isEmpty() && participants.get(0).getGames().size() < participants.size() - 1) {

            // TODO zamysliet sa ci je v poriadku ked mazeme vysledky, pridat
            // aspon nejake varovanie
            for (Participant participant : participants) {
                for (Game game : participant.getGames()) {
                    deleteGame(game);
                }
                participant.getGames().clear();
            }

            for (Participant participant1 : participants) {
                for (Participant participant2 : participants) {
                    if (!participant1.equals(participant2)) {
                        participant1.getGames().add(createGame(participant1, participant2));
                    }
                }
            }
        }
        time = System.currentTimeMillis() - time;
        logger.debug("End: Process games: " + time + " ms");
    }

}