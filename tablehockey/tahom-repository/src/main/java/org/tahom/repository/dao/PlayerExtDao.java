package org.tahom.repository.dao;

import java.util.List;

import org.tahom.repository.dao.PlayerDao;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.impl.StatisticForm;

public interface PlayerExtDao extends PlayerDao {

    public List<Player> listNotRegisteredPlayers(Tournament tournament);

    public List<Player> listPlayersGames(StatisticForm statisticForm);

}
