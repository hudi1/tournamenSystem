package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Player;
import org.toursys.repository.model.StatisticForm;
import org.toursys.repository.model.Tournament;

public interface PlayerExtDao extends PlayerDao {

    public List<Player> listNotRegisteredPlayers(Tournament tournament);

    public List<Player> listPlayersGames(StatisticForm statisticForm);

}
