package org.tahom.processor.schedule;

import java.util.ArrayList;

import org.tahom.processor.service.game.dto.GameDto;

public class EmptyRoundRobinSchedule extends RoundRobinSchedule {

	public EmptyRoundRobinSchedule() {
		super(null);
	}

	@Override
	protected void createSchedule() {
		schedule = new ArrayList<GameDto>();
	}

}
