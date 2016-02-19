package org.tahom.repository.dao;

import org.tahom.repository.dao.GameDao;
import org.tahom.repository.model.Game;

public interface GameExtDao extends GameDao {

    public int updateOppositeGame(Game Game);

}
