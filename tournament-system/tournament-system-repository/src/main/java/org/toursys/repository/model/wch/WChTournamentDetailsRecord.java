package org.toursys.repository.model.wch;

import java.io.Serializable;

public class WChTournamentDetailsRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String points;
    private String tournament;

    public WChTournamentDetailsRecord(String points, String tournament) {
        this.points = points;
        this.tournament = tournament;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    @Override
    public String toString() {
        return "WChTournamentDetailsRecord [points=" + points + ", tournament=" + tournament + "]";
    }

}
