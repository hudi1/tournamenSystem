package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Player;

public interface PlayerDao {

    public void createPlayer(Player player);

    public void updatePlayer(Player player);

    public void deletePlayer(Player player);

    public List<Player> getAllPlayer();
}
