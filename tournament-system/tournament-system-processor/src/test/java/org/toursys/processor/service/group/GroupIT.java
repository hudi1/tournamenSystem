package org.toursys.processor.service.group;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("/clear-all.xml"), @Setup() })
public class GroupIT {

    @Test
    @Verify("createSeasonTest-verify.xml")
    public void createSeasonTest() {
        // Season season = seasonService.createSeason(new Season("season", user));
        // Assert.assertNotNull(season.getId());
    }

}
