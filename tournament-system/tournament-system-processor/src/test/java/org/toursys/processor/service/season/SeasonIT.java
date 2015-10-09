package org.toursys.processor.service.season;

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
import org.toursys.processor.service.user.UserService;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.User;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("/clear-all.xml"), @Setup() })
public class SeasonIT {

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void setUp() {
        user = userService.getUser(new User()._setUserName("admin"));
    }

    @Test
    @Verify("createSeasonTest-verify.xml")
    public void createSeasonTest() {
        Season season = seasonService.createSeason(new Season("season", user));
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
    public void updateUserTest() {
        int count = seasonService.updateSeason(new Season()._setId(1)._setName("seasonEdit")._setUser(user));
        Assert.assertNotSame(0, count);
    }

    @Test
    @Verify("getAllSeasonsTest-verify.xml")
    public void getSeasonTest() {
        List<Season> seasons = seasonService.getSeasons(new Season()._setUser(user));
        Assert.assertSame(1, seasons.size());
    }

    @Test
    @Verify("deleteSeasonTest-verify.xml")
    public void deleteSeasonTest() {
        int count = seasonService.deleteSeason(new Season()._setId(1));
        Assert.assertNotSame(0, count);
    }

}
