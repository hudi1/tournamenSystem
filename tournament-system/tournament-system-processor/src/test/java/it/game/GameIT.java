package it.game;

import java.util.List;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.toursys.processor.schedule.RoundRobinSchedule;
import org.toursys.processor.service.game.GameService;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.processor.service.schedule.ScheduleService;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Results;
import org.toursys.repository.model.Tournament;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("../clear-all.xml"), @Setup() })
public class GameIT {

	@Autowired
	private GameService gameService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private ParticipantService participantService;

	@Test
	@Verify("updateBothGamesTest-verify.xml")
	public void updateBothGamesTest() {
		List<Participant> participants = participantService.getParticipandByGroup(new Groups()._setId(1)
		        ._setType(GroupsType.BASIC)._setCopyResult(false));
		RoundRobinSchedule schedule = scheduleService.getSchedule(new Tournament()._setId(1), new Groups()._setId(1)
		        ._setType(GroupsType.BASIC)._setCopyResult(false)._setNumberOfHockey(2)._setIndexOfFirstHockey(1),
		        participants);
		List<GameImpl> games = schedule.getSchedule();
		for (GameImpl gameImpl : games) {
			gameImpl.setResult(new Results("9:9"));
		}
		gameService.updateBothGames(games);
	}
}
