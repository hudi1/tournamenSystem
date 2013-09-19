package org.toursys.processor.service;

import java.util.List;

import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;

public class PlayerService extends AbstractService {

    // Basic operations

    public Player createPlayer(Player player) {
        return tournamentAggregationDao.createPlayer(player);
    }

    public Player getPlayer(Player player) {
        return tournamentAggregationDao.getPlayer(player);
    }

    public int updatePlayer(Player player) {
        return tournamentAggregationDao.updatePlayer(player);
    }

    public int deletePlayer(Player player) {
        return tournamentAggregationDao.deletePlayer(player);
    }

    public List<Player> getPlayers(Player player) {
        player.setInit(Player.Association.user.name());
        return tournamentAggregationDao.getListPlayers(player);
    }

    // Advanced operations

    public List<Player> getNotRegistratedPlayers(Tournament tournament) {
        return tournamentAggregationDao.getNotRegistratedPlayers(tournament);
    }

}
