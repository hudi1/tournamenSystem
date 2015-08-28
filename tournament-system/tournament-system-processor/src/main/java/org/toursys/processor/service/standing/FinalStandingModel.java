package org.toursys.processor.service.standing;

import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.FinalStanding.Attribute;
import org.toursys.repository.model.Tournament;

public class FinalStandingModel {

    public FinalStanding createFinalStanding(Tournament tournament, int rank) {
        FinalStanding finalStanding = new FinalStanding();
        finalStanding.setFinalRank(rank);
        finalStanding.setTournament(tournament);
        finalStanding.setNull(Attribute.player);
        return finalStanding;
    }
}
