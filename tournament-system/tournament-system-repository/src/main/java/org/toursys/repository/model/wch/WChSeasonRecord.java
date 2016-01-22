package org.toursys.repository.model.wch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WChSeasonRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<WChTournamentDetailsRecord> tournamentDetails = new ArrayList<WChTournamentDetailsRecord>();
    private WChTournamentDetailsRecord nationalChampionship;

    public List<WChTournamentDetailsRecord> getTournamentDetails() {
        return tournamentDetails;
    }

    public void setTournamentDetails(List<WChTournamentDetailsRecord> tournamentDetails) {
        this.tournamentDetails = tournamentDetails;
    }

    public WChTournamentDetailsRecord getNationalChampionship() {
        return nationalChampionship;
    }

    public void setNationalChampionship(WChTournamentDetailsRecord nationalChampionship) {
        this.nationalChampionship = nationalChampionship;
    }

    @Override
    public String toString() {
        return "WChSeasonRecord [tournamentDetails=" + tournamentDetails + ", nationalChampionship="
                + nationalChampionship + "]";
    }

}
