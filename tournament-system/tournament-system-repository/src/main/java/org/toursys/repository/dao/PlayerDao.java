package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;

public interface PlayerDao {

    public Player createPlayer(Player player);

    public Player updatePlayer(Player player);

    public boolean deletePlayer(Player player);

    public Player getPlayer(Player player);

    public List<Player> getAllPlayers();

    public List<Player> getNotRegistratedPlayers(Tournament tournament);

}
