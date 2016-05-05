package it.ithfTournament;

import java.util.Date;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.tahom.processor.service.ithf.IthfService;
import org.tahom.repository.model.IthfTournament;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.impl.Series;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("../clear-all.xml"), @Setup() })
public class IthfTournamentIT {

	@Autowired
	private IthfService ithfService;

	@Test
	@Verify("createTournamentTest-verify.xml")
	public void createTournamentTest() {

		IthfTournament ithfTournament = ithfService.createIthfTournament(new IthfTournament()._setDate(new Date())
				._setSeries(new Series("World Tour,Czech Open"))._setPlayer(new Player()._setId(1)));

		Assert.assertNotNull(ithfTournament.getId());
	}

}
