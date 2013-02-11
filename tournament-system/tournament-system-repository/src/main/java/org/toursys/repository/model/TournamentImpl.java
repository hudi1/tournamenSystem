package org.toursys.repository.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TournamentImpl extends Tournament {

    private static final long serialVersionUID = 1L;

    private Map<String, Groups> basicGroups;
    private Map<String, Groups> finalGroups;
    private Map<String, Groups> groups;
    private Groups lastKnowGroup;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public TournamentImpl(Tournament tournament) {

        this.setFinalPromoting(tournament.getFinalPromoting());
        this.setLowerPromoting(tournament.getLowerPromoting());
        this.setId(tournament.getId());
        this.setName(tournament.getName());
        this.setPlayOffA(tournament.getPlayOffA());
        this.setPlayOffLower(tournament.getPlayOffLower());
        this.setWinPoints(tournament.getWinPoints());
        this.setSeason(tournament.getSeason());
        this.getGroups().addAll(tournament.getGroups());
        this.setMinPlayersInGroup(tournament.getMinPlayersInGroup());
    }

    public Map<String, Groups> getAllGroups() {
        if (groups == null)
            groups = new ConcurrentHashMap<String, Groups>();
        return groups;
    }

    public Map<String, Groups> getBasicGroups() {
        if (basicGroups == null)
            basicGroups = new ConcurrentHashMap<String, Groups>();
        return basicGroups;
    }

    public Map<String, Groups> getFinalGroups() {
        if (finalGroups == null)
            finalGroups = new ConcurrentHashMap<String, Groups>();
        return finalGroups;
    }

    public Groups getLastKnowGroup() {
        return lastKnowGroup;
    }

    public void setLastKnowGroup(Groups lastKnowGroup) {
        this.lastKnowGroup = lastKnowGroup;
    }

}
