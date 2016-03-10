package org.tahom.processor.service.finalStanding;

import java.util.List;

import org.tahom.processor.service.finalStanding.dto.FinalStandingDto;
import org.tahom.processor.service.finalStanding.dto.FinalStandingPageDto;
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

	public FinalStandingPageDto createFinalStandingsDto(List<FinalStanding> finalStandings) {
		FinalStandingPageDto finalStandingsDto = new FinalStandingPageDto();
		for (FinalStanding finalStanding : finalStandings) {
			FinalStandingDto finalStandingDto = new FinalStandingDto();
			if (finalStanding.getPlayer() != null) {
				finalStandingDto.setName(finalStanding.getPlayer().getName() + " "
				        + finalStanding.getPlayer().getSurname().toString());
				finalStandingDto.setClub(finalStanding.getPlayer().getClub());
			}
			finalStandingDto.setRank(finalStanding.getFinalRank());
			finalStandingsDto.getFinalStandings().add(finalStandingDto);
		}
		return finalStandingsDto;
	}
}
