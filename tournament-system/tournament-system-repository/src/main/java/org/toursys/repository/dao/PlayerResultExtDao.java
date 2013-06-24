package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Tournament;

public interface PlayerResultExtDao extends PlayerResultDao {

    public List<PlayerResult> listRegistratedPlayerResult(Tournament tournament);

}
