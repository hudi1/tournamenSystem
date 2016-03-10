package it.finalStanding;

import java.util.List;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.tahom.processor.service.finalStanding.FinalStandingModel;
import org.tahom.processor.service.finalStanding.FinalStandingService;
import org.tahom.repository.model.FinalStanding;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.GroupsPlayOffType;
import org.tahom.repository.model.Tournament;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("../clear-all.xml"), @Setup() })
public class FinalStandingIT {

	@Autowired
	private FinalStandingService finalStandingService;

	@Autowired
	private FinalStandingModel standingModel;

	@Test
	@Verify("getFinalStandingsTest-verify.xml")
	public void getFinalStandingsTest() {
		List<FinalStanding> finalStandings = finalStandingService.getFinalStandings(new Tournament()._setId(1));
		Assert.assertSame(8, finalStandings.size());
	}

	@Test
	@Verify("processFinalStandingsTest-verify.xml")
	public void processFinalStandingsTest() {
		finalStandingService.processFinalStandings(new Tournament()._setId(1));
	}

	@Test
	@Verify("updatePromotingFinalStandingsTest-verify.xml")
	public void updatePromotingFinalStandingsTest() {
		finalStandingService.updatePromotingFinalStandings(new Tournament()._setId(1));
	}

	@Test
	@Verify("updateNotPromotingFinalStandingsTest-verify.xml")
	public void updateNotPromotingFinalStandingsTest() {
		finalStandingService.updateNotPromotingFinalStandings(new Tournament()._setId(1)._setPlayOffFinal(8),
		        new Groups()._setId(3)._setName("A")._setPlayOffType(GroupsPlayOffType.FINAL));
	}

}
