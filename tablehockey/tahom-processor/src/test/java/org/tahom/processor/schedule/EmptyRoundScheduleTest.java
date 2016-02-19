package org.tahom.processor.schedule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tahom.processor.schedule.EmptyRoundRobinSchedule;
import org.tahom.processor.schedule.RoundRobinSchedule;
import org.tahom.repository.model.GameImpl;

public class EmptyRoundScheduleTest {

	private RoundRobinSchedule schedule;

	@Before
	public void setup() {
		schedule = new EmptyRoundRobinSchedule();
	}

	@Test
	public void test() throws Exception {
		List<GameImpl> games = schedule.getSchedule();

		Assert.assertTrue(games.isEmpty());
	}
}