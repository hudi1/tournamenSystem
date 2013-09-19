package org.toursys.processor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.PlayerResult;

public class GameService extends AbstractService {

    // Basic operations

    public Game createGame(PlayerResult homePlayer, PlayerResult awayPlayer) {
        return tournamentAggregationDao.createGame(homePlayer, awayPlayer);
    }

    public Game getGame(Game game) {
        return tournamentAggregationDao.getGame(game);
    }

    public int updateGame(Game game) {
        logger.info("updating game: " + game);
        return tournamentAggregationDao.updateGame(game);
    }

    public int deleteGame(Game game) {
        return tournamentAggregationDao.deleteGame(game);
    }

    public List<Game> getGames(Game game) {
        game.setInit(Game.Association.homePlayerResult.name(), Game.Association.awayPlayerResult.name());
        return tournamentAggregationDao.getListGames(game);
    }

    // Advanced operations

    // TODO ked sa budes nudit toto treba nejak vyriesit. nie je to ciste
    // riesenie ale zatim to funguje a nie je to
    // pomale
    public void updateBothGames(Game game) {
        updateGame(game);
        Game gameDb = getGame(new Game()._setHomePlayerResult(game.getAwayPlayerResult())._setAwayPlayerResult(
                game.getHomePlayerResult()));
        gameDb.setHomeScore(game.getAwayScore());
        gameDb.setAwayScore(game.getHomeScore());
        updateGame(gameDb);
    }

    @Transactional
    public void processGames(final List<PlayerResult> playerResults) {
        long time = System.currentTimeMillis();
        if (!playerResults.isEmpty() && playerResults.get(0).getGames().size() < playerResults.size() - 1) {

            // TODO zamysliet sa ci je v poriadku ked mazeme vysledky, pridat
            // aspon nejake varovanie
            if (!playerResults.get(0).getGames().isEmpty()) {
                for (PlayerResult playerResult : playerResults) {
                    for (Game game : playerResult.getGames()) {
                        deleteGame(game);
                    }
                }
            }

            for (PlayerResult playerResult1 : playerResults) {
                for (PlayerResult playerResult2 : playerResults) {
                    if (!playerResult1.equals(playerResult2)) {
                        playerResult1.getGames().add(createGame(playerResult1, playerResult2));
                    }
                }
            }
        }
        time = System.currentTimeMillis() - time;
        logger.debug("Celkova doba: " + time + " ms");
    }
}