package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;

public interface PlayOffResultDao {

    public PlayOffResult createPlayOffResult(PlayOffGame playOffGame);

    public PlayOffResult updatePlayOffResult(PlayOffResult playOffResult);

    public boolean deletePlayOffResult(PlayOffResult playOffResult);

    public PlayOffResult getPlayOffResult(PlayOffResult playOffResult);

    public List<PlayOffResult> findPlayOffResult(PlayOffResult playOffResult);
}
