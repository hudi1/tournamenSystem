package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.form.GameForm;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.PlayerResult;

public interface GameDao {

    public Game createGame(PlayerResult homePlayer, PlayerResult awayPlayer);

    public Game updateGame(Game game);

    public boolean deleteGame(Game game);

    public Game getGame(Game game);

    public List<Game> findGame(GameForm gameForm);
}
