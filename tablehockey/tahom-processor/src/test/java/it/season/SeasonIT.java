package it.season;

import java.util.List;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.tahom.processor.service.season.SeasonService;
import org.tahom.processor.service.user.UserService;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.User;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("../clear-all.xml"), @Setup() })
public class SeasonIT {

	@Autowired
	private SeasonService seasonService;

	@Autowired
	private UserService userService;

	@Test
	@Verify("createSeasonTest-verify.xml")
	public void createSeasonTest() {
		User user = userService.getUser(new User()._setUserName("admin"));
		Season season = seasonService.createSeason(user, new Season()._setName("season"));
		Assert.assertNotNull(season.getId());
	}

	@Test
	@Verify("getAllSeasonsTest-verify.xml")
	public void getAllSeasonsTest() {
		List<Season> seasons = seasonService.getAllSeasons();
		Assert.assertSame(1, seasons.size());
	}

	@Test
	@Verify("updateSeasonTest-verify.xml")
	public void updateSeasonTest() {
		User user = userService.getUser(new User()._setUserName("admin"));
		Season season = seasonService.getUserSeasons(user).get(0);
		int count = seasonService.updateSeason(season._setName("seasonEdit"));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("getAllSeasonsTest-verify.xml")
	public void getSeasonTest() {
		User user = userService.getUser(new User()._setUserName("admin"));
		List<Season> seasons = seasonService.getUserSeasons(user);
		Assert.assertSame(1, seasons.size());
	}

	@Test
	@Verify("deleteSeasonTest-verify.xml")
	public void deleteSeasonTest() {
		int count = seasonService.deleteSeason(new Season()._setId(1));
		Assert.assertNotSame(0, count);
	}

}
