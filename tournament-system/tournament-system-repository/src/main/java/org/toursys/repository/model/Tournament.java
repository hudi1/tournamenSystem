package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Tournament implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private long tournamentId;

    private String name;

    private long seasonId;

    private int promotingA;

    private int promotingLower;

    private int winPoints;

    private int playOffA;

    private int playOffLower;

    public String getName() {
        return name;
    }

    public Tournament() {
        winPoints = 2;
        promotingA = 6;
        promotingLower = 5;
        playOffA = 16;
        playOffLower = 8;
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
        tournament.setPromotingA(getPromotingA());
        tournament.setPromotingLower(getPromotingLower());
        return tournament;
    }

    public int getPromotingA() {
        return promotingA;
    }

    public void setPromotingA(int promotingA) {
        this.promotingA = promotingA;
    }

    public int getPromotingLower() {
        return promotingLower;
    }

    public void setPromotingLower(int promotingLower) {
        this.promotingLower = promotingLower;
    }

    public int getWinPoints() {
        return winPoints;
    }

    public void setWinPoints(int winPoints) {
        this.winPoints = winPoints;
    }

    public int getPlayOffA() {
        return playOffA;
    }

    public void setPlayOffA(int playOffA) {
        this.playOffA = playOffA;
    }

    public int getPlayOffLower() {
        return playOffLower;
    }

    public void setPlayOffLower(int playOffLower) {
        this.playOffLower = playOffLower;
    }

}
