package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Tournament;

public interface PlayerResultDao {

    public PlayerResult createPlayerResult(Player player, Groups group);

    public PlayerResult updatePlayerResult(PlayerResult playerResult);

    public boolean deletePlayerResult(PlayerResult playerResult);

    public PlayerResult getPlayerResult(PlayerResult playerResult);

    public List<PlayerResult> getRegistratedPlayerResult(Tournament tournament);

}
