package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Tournament implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private long tournamentId;

    private String name;

    private long seasonId;

    public String getName() {
        return name;
    }

    public Tournament() {
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(long seasonId) {
        this.seasonId = seasonId;
    }

    @Override
    public Tournament clone() {
        Tournament tournament = new Tournament();
        tournament.setSeasonId(getSeasonId());
        tournament.setName(getName());
        tournament.setTournamentId(getTournamentId());
        return tournament;
    }

}
