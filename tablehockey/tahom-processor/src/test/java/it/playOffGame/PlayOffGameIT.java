package it.playOffGame;

import java.util.List;
import java.util.Locale;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.tahom.processor.service.playOffGame.PlayOffGameModel;
import org.tahom.processor.service.playOffGame.PlayOffGameService;
import org.tahom.processor.service.playOffGame.dto.PlayOffGameDto;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.GroupsPlayOffType;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.PlayOffGame;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.impl.Results;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("../clear-all.xml"), @Setup() })
public class PlayOffGameIT {

	@Autowired
	private PlayOffGameService playOffGameService;

	@Autowired
	private PlayOffGameModel playOffGameModel;

	@Test
	@Verify("createPlayOffGameTest-verify.xml")
	public void createPlayOffGameTest() {
		PlayOffGame playOffGame = playOffGameService.createPlayOffGame(playOffGameModel.createPlayOffGame(
		        new Participant()._setId(1), new Participant()._setId(2), new Groups()._setId(1), 1));
		Assert.assertNotNull(playOffGame.getId());
	}

	@Test
	@Verify("deletePlayOffGameTest-verify.xml")
	public void deletePlayOffGameTest() {
		int count = playOffGameService.deletePlayOffGame(new PlayOffGame()._setId(1));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("updatePlayOffGameTest-verify.xml")
	public void updatePlayOffGameTest() {
		int count = playOffGameService.updatePlayOffGame(new PlayOffGame()._setId(1)._setAwayParticipant(null)
		        ._setHomeParticipant(null)._setResult(null)._setStatus(null));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("getPlayOffGameTest-verify.xml")
	public void getPlayOffGameTest() {
		PlayOffGame playOffGame = playOffGameService.getPlayOffGame(new PlayOffGame()._setId(1));
		Assert.assertNotNull(playOffGame);
	}

	@Test
	@Verify("getPlayOffGameTest-verify.xml")
	public void getPlayOffGamesTest() {
		List<PlayOffGame> playOffGames = playOffGameService.getPlayOffGames(new PlayOffGame()._setGroup(new Groups()
		        ._setId(4)));
		Assert.assertSame(8, playOffGames.size());
	}

	@Test
	@Verify("updatePlayOffGameResultTest-verify.xml")
	public void updatePlayOffGameResultTest() {
		PlayOffGameDto dto = new PlayOffGameDto();
		dto.setGameId(1);
		dto.setResult(new Results("1:0,1:2,4:1"));
		int count = playOffGameService.updatePlayOffGameResult(dto);
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("updatePlayOffGamesTest-verify.xml")
	public void updatePlayOffGamesTest() {
		playOffGameService.updatePlayOffGames(new Tournament()._setId(1)._setPlayOffFinal(8)._setLowerPromoting(2)
		        ._setFinalPromoting(1), new Groups()._setId(4)._setPlayOffType(GroupsPlayOffType.FINAL));
	}

	@Test
	@Verify("updateNextRoundPlayOffGamesTest-verify.xml")
	public void updateNextRoundPlayOffGamesTest() {
		playOffGameService.updateNextRoundPlayOffGames(new Tournament()._setId(1)._setPlayOffFinal(8)
		        ._setLowerPromoting(2)._setFinalPromoting(1));
	}

	@Test
	@Verify("getPlayOffGamesByGroupTest-verify.xml")
	public void getPlayOffGamesByGroupTest() {
		List<PlayOffGameDto> playOffGames = playOffGameService.getPlayOffGamesByGroup(new Groups()._setId(4),
		        Locale.ENGLISH);
		Assert.assertSame(8, playOffGames.size());
	}

	@Test
	@Verify("getFullPlayOffGamesTest-verify.xml")
	public void getFullPlayOffGamesTest() {
		List<PlayOffGame> playOffGames = playOffGameService.getFullPlayOffGames(new Groups()._setId(4));
		Assert.assertSame(8, playOffGames.size());
	}

}
