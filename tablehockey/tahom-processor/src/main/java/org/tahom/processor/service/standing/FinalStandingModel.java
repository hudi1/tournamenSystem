package org.tahom.processor.service.standing;

import org.tahom.repository.model.FinalStanding;
import org.tahom.repository.model.FinalStanding.Attribute;
import org.tahom.repository.model.Tournament;

public class FinalStandingModel {

    public FinalStanding createFinalStanding(Tournament tournament, int rank) {
        FinalStanding finalStanding = new FinalStanding();
        finalStanding.setFinalRank(rank);
        finalStanding.setTournament(tournament);
        finalStanding.setNull(Attribute.player);
        return finalStanding;
    }
}
