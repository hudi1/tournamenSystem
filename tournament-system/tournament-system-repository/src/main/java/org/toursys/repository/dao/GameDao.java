package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.form.GameForm;
import org.toursys.repository.model.Game;

public interface GameDao {

    public void createGame(Game game);

    public void updateGame(Game game);

    public void deleteGame(Game game);

    public List<Game> findGame(GameForm gameForm);
}
