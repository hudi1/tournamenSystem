package org.tahom.processor.service.player;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.tahom.repository.dao.PlayerExtDao;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Player.Attribute;
import org.tahom.repository.model.StatisticForm;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.User;

public class PlayerService {

    @Inject
    private PlayerExtDao playerDao;

    @Transactional
    public Player createPlayer(User user, Player player) {
        player.setUser(user);
        Player playerForm = new Player(player.getName(), player.getSurname(), player.getPlayerDiscriminator(), user);
        Player dbPlayer = playerDao.get(playerForm);

        if (dbPlayer == null) {
            return playerDao.insert(player);
        } else {
            player.setNull(Attribute.club);
            playerDao.update(player._setId(dbPlayer.getId()));
            return player;
        }
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
    public List<Player> getUserPlayers(User user) {
        Player player = new Player();
        player.setUser(user);
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
