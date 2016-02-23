package it.player;

import java.util.List;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.tahom.processor.service.player.PlayerService;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.StatisticForm;
import org.tahom.repository.model.Surname;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.User;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("../clear-all.xml"), @Setup() })
public class PlayerIT {

	@Autowired
	private PlayerService playerService;

	private User user;
	private Tournament tournament;

	@Before
	public void setup() {
		user = new User()._setId(1);
		tournament = new Tournament()._setId(1);
	}

	@Test
	@Verify("createPlayerTest-verify.xml")
	public void createPlayerTest() {
		Player player = playerService.createPlayer(user,
		        new Player()._setClub("club")._setName("name")._setSurname(new Surname("surname"))._setWorldRanking(1)
		                ._setIthfId(1));
		Assert.assertNotNull(player.getId());
	}

	@Test
	@Verify("getUserPlayersTest-verify.xml")
	public void createExistingPlayerTest() {
		Player player = playerService.createPlayer(user, new Player()._setClub("adminClub")._setName("adminName")
		        ._setSurname(new Surname("adminSurname")));
		Assert.assertEquals((Integer) 1, player.getId());
	}

	@Test
	@Verify("updatePlayerTest-verify.xml")
	public void updatePlayerTest() {
		Player player = playerService.getUserPlayers(user).get(0);
		int count = playerService.updatePlayer(player._setName("nameEdit")._setSurname(new Surname("surnameEdit"))
		        ._setClub("clubEdit")._setWorldRanking(1)._setIthfId(1));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("getUserPlayersTest-verify.xml")
	public void getUserPlayerTest() {
		List<Player> seasons = playerService.getUserPlayers(user);
		Assert.assertSame(2, seasons.size());
	}

	@Test
	@Verify("deletePlayerTest-verify.xml")
	public void deletePlayerTest() {
		int count = playerService.deletePlayer(new Player()._setId(1));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("getUserPlayersTest-verify.xml")
	public void getNotRegisteredPlayers() {
		List<Player> players = playerService.getNotRegisteredPlayers(tournament);
		Assert.assertSame(1, players.size());
	}

	@Test
	@Verify("getUserPlayersTest-verify.xml")
	public void getPlayersGames() {
		List<Player> players = playerService.getPlayersGames(new StatisticForm()._setUser(user));
		Assert.assertSame(2, players.size());
	}

}
