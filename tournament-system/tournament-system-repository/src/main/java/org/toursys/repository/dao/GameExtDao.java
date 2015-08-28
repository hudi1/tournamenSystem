package org.toursys.repository.dao;

import org.toursys.repository.model.Game;

public interface GameExtDao extends GameDao {

    public int updateOppositeGame(Game Game);

}
