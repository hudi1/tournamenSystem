package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.form.PlayerForm;
import org.toursys.repository.model.Player;

public interface PlayerDao {

    public Player createPlayer(Player player);

    public Player updatePlayer(Player player);

    public boolean deletePlayer(Player player);

    public Player getPlayer(Player player);

    public List<Player> getAllPlayers();

    public List<Player> getNotRegistrationPlayers(PlayerForm playerForm);

}
