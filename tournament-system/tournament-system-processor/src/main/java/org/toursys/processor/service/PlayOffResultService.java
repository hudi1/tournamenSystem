package org.toursys.processor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;

public class PlayOffResultService extends AbstractService {

    // Basic operations

    @Transactional
    public PlayOffResult createPlayOffResult(PlayOffGame playOffGame) {
        logger.debug("Create playerOff result: " + playOffGame);
        return tournamentAggregationDao.createPlayOffResult(playOffGame);
    }

    @Transactional(readOnly = true)
    public PlayOffResult getPlayOffResult(PlayOffResult playOffResult) {
        logger.debug("Get playerOff result: " + playOffResult);
        return tournamentAggregationDao.getPlayOffResult(playOffResult);
    }

    @Transactional
    public int updatePlayOffResult(PlayOffResult playOffResult) {
        logger.debug("Update playerOff result: " + playOffResult);
        return tournamentAggregationDao.updatePlayOffResult(playOffResult);
    }

    @Transactional
    public int deletePlayOffResult(PlayOffResult playOffResult) {
        logger.debug("Delete playerOff result: " + playOffResult);
        return tournamentAggregationDao.deletePlayOffResult(playOffResult);
    }

    @Transactional(readOnly = true)
    public List<PlayOffResult> getPlayOffResults(PlayOffResult playOffResult) {
        logger.debug("Get list playerOff results: " + playOffResult);
        return tournamentAggregationDao.getListPlayOffResults(playOffResult);
    }
}