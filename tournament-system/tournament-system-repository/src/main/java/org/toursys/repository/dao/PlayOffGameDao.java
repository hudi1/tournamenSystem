package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Player;

public interface PlayOffGameDao {

    public PlayOffGame createPlayOffGame(Player homePlayer, Player awayPlayer, Groups Group, int position);

    public PlayOffGame updatePlayOffGame(PlayOffGame playOffGame);

    public boolean deletePlayOffGame(PlayOffGame playOffGame);

    public PlayOffGame getPlayOffGame(PlayOffGame playOffGame);

    public List<PlayOffGame> findPlayOffGame(PlayOffGame playOffGame);
}
