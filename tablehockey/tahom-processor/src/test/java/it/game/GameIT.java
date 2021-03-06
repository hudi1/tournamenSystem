package it.game;

import java.util.List;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.tahom.processor.schedule.RoundRobinSchedule;
import org.tahom.processor.service.game.GameService;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.processor.service.schedule.ScheduleService;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.impl.Results;

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
		RoundRobinSchedule schedule = scheduleService.getSchedule(new Tournament()._setId(1), new Groups()._setId(1)
		        ._setType(GroupsType.BASIC)._setCopyResult(false)._setNumberOfHockey(2)._setIndexOfFirstHockey(1));
		List<GameDto> games = schedule.getSchedule();
		for (GameDto gameDto : games) {
			gameDto.setResult(new Results("9:9"));
		}
		gameService.updateBothGames(games);
	}
}
