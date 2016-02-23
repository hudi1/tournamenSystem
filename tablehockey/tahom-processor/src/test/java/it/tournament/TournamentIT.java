package it.tournament;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.tahom.processor.service.season.SeasonService;
import org.tahom.processor.service.tournament.TournamentService;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.TournamentSortType;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("../clear-all.xml"), @Setup() })
public class TournamentIT {

	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private SeasonService seasonService;

	@Test
	@Verify("createTournamentTest-verify.xml")
	public void createTournamentTest() {
		Season season = seasonService.getAllSeasons().get(0);
		Tournament tournament = tournamentService.createTournament(season, new Tournament()._setFinalPromoting(5)
		        ._setLowerPromoting(6)._setMinPlayersInGroup(7)._setName("tournament")._setPlayOffFinal(8)
		        ._setPlayOffLower(9)._setOpen(true)._setSortType(TournamentSortType.CZ)._setWinPoints(10));
		Assert.assertNotNull(tournament.getId());
	}

	@Test
	@Verify("getTournamentTest-verify.xml")
	public void getTournamentTest() {
		Tournament tournament = tournamentService.getTournament(new Tournament()._setId(1));
		Assert.assertNotNull(tournament);
	}

	@Test
	@Verify("deleteTournamentTest-verify.xml")
	public void deleteTournamentTest() {
		int count = tournamentService.deleteTournament(new Tournament()._setId(1));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("updateTournamentTest-verify.xml")
	public void updateTournamentTest() {
		Tournament tournament = tournamentService.getTournament(new Tournament()._setId(1));
		int count = tournamentService.updateTournament(tournament._setFinalPromoting(5)._setLowerPromoting(6)
		        ._setMinPlayersInGroup(7)._setName("tournament")._setPlayOffFinal(8)._setPlayOffLower(9)._setOpen(true)
		        ._setSortType(TournamentSortType.CZ)._setWinPoints(10));
		Assert.assertNotSame(0, count);
	}

}
