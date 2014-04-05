package org.toursys.processor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;

public class PlayerService extends AbstractService {

    // Basic operations

    @Transactional
    public Player createPlayer(Player player) {
        logger.debug("Create player: " + player);

        Player dbPlayer = getPlayer(player._setClub(null));

        if (dbPlayer == null) {
            return tournamentAggregationDao.createPlayer(player);
        } else {
            logger.warn("Player already exists: " + dbPlayer);
            return dbPlayer;
        }
    }

    @Transactional(readOnly = true)
    public Player getPlayer(Player player) {
        logger.debug("Get player: " + player);
        return tournamentAggregationDao.getPlayer(player);
    }

    @Transactional
    public int updatePlayer(Player player) {
        logger.debug("Update player: " + player);
        return tournamentAggregationDao.updatePlayer(player);
    }

    @Transactional
    public int deletePlayer(Player player) {
        logger.debug("Delete player: " + player);
        return tournamentAggregationDao.deletePlayer(player);
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayers(Player player) {
        logger.debug("Get list players: " + player);
        player.setInit(Player.Association.user.name());
        return tournamentAggregationDao.getListPlayers(player);
    }

    // Advanced operations

    @Transactional(readOnly = true)
    public List<Player> getNotRegisteredPlayers(Tournament tournament) {
        logger.debug("Get not registered player: " + tournament);
        return tournamentAggregationDao.getNotRegisteredPlayers(tournament);
    }

}
