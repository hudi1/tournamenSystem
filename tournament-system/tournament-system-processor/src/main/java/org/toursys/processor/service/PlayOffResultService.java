package org.toursys.processor.service;

import java.util.List;

import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;

public class PlayOffResultService extends AbstractService {

    // Basic operations

    public PlayOffResult createPlayOffResult(PlayOffGame playOffGame) {
        return tournamentAggregationDao.createPlayOffResult(playOffGame);
    }

    public PlayOffResult getPlayOffResult(PlayOffResult playOffResult) {
        return tournamentAggregationDao.getPlayOffResult(playOffResult);
    }

    public int updatePlayOffResult(PlayOffResult playOffResult) {
        return tournamentAggregationDao.updatePlayOffResult(playOffResult);
    }

    public int deletePlayOffResult(PlayOffResult playOffResult) {
        return tournamentAggregationDao.deletePlayOffResult(playOffResult);
    }

    public List<PlayOffResult> getPlayOffResults(PlayOffResult playOffResult) {
        return tournamentAggregationDao.getListPlayOffResults(playOffResult);
    }
}