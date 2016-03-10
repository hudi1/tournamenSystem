package org.tahom.processor.service.finalStanding.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FinalStandingPageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<FinalStandingDto> finalStandings;

	public List<FinalStandingDto> getFinalStandings() {
		if (finalStandings == null) {
			finalStandings = new ArrayList<FinalStandingDto>();
		}
		return finalStandings;
	}
}