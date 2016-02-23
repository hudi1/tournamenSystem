package org.tahom.processor.schedule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tahom.processor.service.game.dto.GameDto;

public class EmptyRoundScheduleTest {

	private RoundRobinSchedule schedule;

	@Before
	public void setup() {
		schedule = new EmptyRoundRobinSchedule();
	}

	@Test
	public void test() throws Exception {
		List<GameDto> games = schedule.getSchedule();

		Assert.assertTrue(games.isEmpty());
	}
}