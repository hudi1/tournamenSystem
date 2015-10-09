package org.toursys.processor.service.tournament;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.toursys.processor.service.season.SeasonService;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.TournamentSortType;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("/clear-all.xml"), @Setup() })
public class TournamentIT {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private SeasonService seasonService;

    private Season season;

    @Before
    public void setUp() {
        season = seasonService.getAllSeasons().get(0);
    }

    @Test
    @Verify("createTournamentTest-verify.xml")
    public void createSeasonTest() {
        Tournament tournament = tournamentService.createTournament(season, new Tournament()._setFinalPromoting(5)
                ._setLowerPromoting(6)._setMinPlayersInGroup(7)._setName("tournament")._setPlayOffFinal(8)
                ._setPlayOffLower(9)._setPublish(true)._setSortType(TournamentSortType.CZ)._setWinPoints(10));
        Assert.assertNotNull(tournament.getId());
    }

    @Test
    @Verify("getTournamentTest-verify.xml")
    public void getSeasonTest() {
        Tournament tournament = tournamentService.getTournament(new Tournament()._setId(1));
        Assert.assertNotNull(tournament);
    }

    @Test
    @Verify("deleteTournamentTest-verify.xml")
    public void deleteSeasonTest() {
        int count = tournamentService.deleteTournament(new Tournament()._setId(1));
        Assert.assertNotSame(0, count);
    }

    @Test
    @Verify("updateTournamentTest-verify.xml")
    public void updateSeasonTest() {
        int count = tournamentService.updateTournament(new Tournament()._setFinalPromoting(5)._setLowerPromoting(6)
                ._setMinPlayersInGroup(7)._setName("tournament")._setPlayOffFinal(8)._setPlayOffLower(9)
                ._setPublish(true)._setSortType(TournamentSortType.CZ)._setWinPoints(10)._setId(1));
        Assert.assertNotSame(0, count);
    }

}
