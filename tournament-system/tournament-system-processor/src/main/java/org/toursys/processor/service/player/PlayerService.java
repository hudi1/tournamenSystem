package org.toursys.processor.service.player;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.dao.PlayerExtDao;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Player.Attribute;
import org.toursys.repository.model.StatisticForm;
import org.toursys.repository.model.Tournament;

public class PlayerService {

    @Inject
    private PlayerExtDao playerDao;

    @Transactional
    public Player createPlayer(Player player) {

        Player dbPlayer = getPlayer(player._setClub(null));

        if (dbPlayer == null) {
            return playerDao.insert(player);
        } else {
            return dbPlayer;
        }
    }

    @Transactional(readOnly = true)
    public Player getPlayer(Player player) {
        return playerDao.get(player);
    }

    @Transactional
    public int updatePlayer(Player player) {
        player.setNull(Attribute.ithfId, Attribute.club, Attribute.worldRanking);
        return playerDao.update(player);
    }

    @Transactional
    public int deletePlayer(Player player) {
        return playerDao.delete(player);
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayers(Player player) {
        player.setInit(Player.Association.user.name());
        return playerDao.list(player);
    }

    @Transactional(readOnly = true)
    public List<Player> getNotRegisteredPlayers(Tournament tournament) {
        return playerDao.listNotRegisteredPlayers(tournament);
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayersGames(StatisticForm statisticForm) {
        return playerDao.listPlayersGames(statisticForm);
    }

}
